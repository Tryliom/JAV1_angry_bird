package com.mygdx.game.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.AngryBird;
import com.mygdx.game.customExceptions.TranslationDoesNotExistException;
import com.mygdx.game.exam.VocProvider;
import com.mygdx.game.exam.Vocabulary;
import com.mygdx.game.interfaces.Scoreable;
import com.mygdx.game.models.Bird;
import com.mygdx.game.models.Bubble;
import com.mygdx.game.models.Button;
import com.mygdx.game.models.Panel;
import com.mygdx.game.models.PhysicalObject;
import com.mygdx.game.models.Pig;
import com.mygdx.game.models.Scenery;
import com.mygdx.game.models.Wasp;
import com.mygdx.game.models.Label;

import java.util.Random;

public class GameScreen extends ApplicationAdapter implements InputProcessor {
    public static Random rand;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public boolean pause;

    public static long startTime = TimeUtils.millis();
    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 900;
    public static final int FLOOR_HEIGHT = 120;
    public static final int X_MIN = 120;
    public static final Vector2 BIRD_START = new Vector2(190, 320);

    private Texture background;

    private Wasp wasp;
    private Bird bird;

    private Bubble bubble;
    private float bubbleTime;

    private Scenery scenery;

    private int score;
    private Button scoreButton;
    private Button pauseButton;
    private VocProvider vocabularyProvider;
    public Vocabulary vocabulary;
    private Label scoreLabel;

    @Override
    public void create() {
        rand = new Random(System.currentTimeMillis());
        batch = new SpriteBatch();
        score = 0;
        scoreLabel = new Label(Color.WHITE);

        background = new Texture(Gdx.files.internal("background.jpg"));
        vocabularyProvider = VocProvider.getInstance();
        vocabulary = vocabularyProvider.pickAVoc();
//        vocabulary = vocabularyProvider.pickVocabulary(0);
        //buttons
        pauseButton = new Button("pause.png", "pause", new Vector2(WORLD_WIDTH / 2 + 75, WORLD_HEIGHT - 150), 100, 100);
        pauseButton.setX(WORLD_WIDTH - pauseButton.getWidth() - 20);
        scoreButton = new Button("score.png", "score", new Vector2(WORLD_WIDTH / 3 + 75, pauseButton.getY()), 150, 100);
        scoreButton.setX(pauseButton.getX() - scoreButton.getWidth() - 20);

        try {
            newScene();
        } catch (TranslationDoesNotExistException e) {
            e.printStackTrace();
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        pause = false;

        Gdx.input.setInputProcessor(this);
    }

    public void newScene() throws TranslationDoesNotExistException {
        scenery = new Scenery(vocabulary);
        try {
            scenery.add(pauseButton);
            scenery.add(scoreButton);
        } catch (Exception e) {
            Gdx.app.log("GameScreen", "Score: " + e.getMessage());
        }
        bird = scenery.bird;
        wasp = scenery.wasp;
        bubble = new Bubble(new Vector2(-Bubble.WIDTH, 0), vocabulary.pickAWord(), 0);
        bubbleTime = 2;
    }

    public void update() throws TranslationDoesNotExistException {
        scoreLabel.setText(String.format("Score: %s", score));

        float dt = Gdx.graphics.getDeltaTime(); // number of milliseconds elapsed since last render
        scenery.update(dt);
        if (scenery.isOutOfScenery(bird)) {
            bird.reset();
        } else if (bird.overlaps(wasp)) {
            AngryBird.getInstance().push(AngryBird.SCREENS_NAME.End);
        } else if (scenery.overlaps(bird) != null) {
            PhysicalObject touchedObject = scenery.overlaps(bird);
            calculateScore(touchedObject);
            scenery.removeObject(touchedObject);
            bird.reset();
        }
        if (bubble.getDuration() > 0) bubble.countdown(dt);
    }

    public void calculateScore(PhysicalObject object) throws TranslationDoesNotExistException {
        if (Pig.class.equals(object.getClass())) {
            Pig pig = (Pig) object;
            if (pig.getWord().getValue(AngryBird.getInstance().getFirst().getISO_639_1()) == scenery.panel.getWord().getValue(AngryBird.getInstance().getFirst().getISO_639_1())) {
                score += pig.incrementScore();
                newScene();
                return;
            }
        }
        if (object instanceof Scoreable) {
            score += ((Scoreable) object).decrementScore();
        }
    }

    @Override
    public void render() {
        try {
            update();
        } catch (TranslationDoesNotExistException e) {
            e.printStackTrace();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        scenery.draw(batch);
        if (bubble != null && bubble.getDuration() > 0) {
            bubble.draw(batch);
        }
        scoreLabel.draw(batch, scoreButton.getX() - scoreLabel.getWidth() - 50, WORLD_HEIGHT - scoreLabel.getHeight() - 25);
        scenery.panel.draw(batch); // I can't understand why this not works on scenery.draw(batch) ....
        batch.end();
    }

    public Vector2 shootZone(Vector3 position) {
        return new Vector2(Math.min(Math.max(0, position.x), BIRD_START.x),
                Math.min(Math.max(FLOOR_HEIGHT, position.y), BIRD_START.y));
    }

    //region Touch Events
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
        PhysicalObject object = scenery.whichObjectTouched(actualPos.x, actualPos.y);
        if (object == null)
            return true;

        if (Pig.class.equals(object.getClass())) {
            try {
                bubble = new Bubble(new Vector2(object.getX(), object.getY() + object.getHeight()), ((Pig) object).getWord(), bubbleTime);
            } catch (TranslationDoesNotExistException e) {
                e.printStackTrace();
            }
            bubble.translateX(-bubble.getWidth() / 1.8f);
        } else if (Bird.class.equals(object.getClass())) {
            bird.isDragged = true;
        } else if (Panel.class.equals(object.getClass())) {
            try {
                Gdx.app.log("GameScreen", ((Panel) object).getWord().getValue(AngryBird.getInstance().getFirst().getISO_639_1()));
            } catch (TranslationDoesNotExistException e) {
                e.printStackTrace();
            }
        } else if (Button.class.equals(object.getClass())) {
            manageButtons((Button) object);
        }
        return true;
    }

    public void manageButtons(Button button) {
        switch (button.getName()) {
            case "score":
                Gdx.app.log("GameScreen", "score touched");
                AngryBird.getInstance().push(AngryBird.SCREENS_NAME.Score);
                break;
            case "pause":
                pause = !pause;
                AngryBird.getInstance().push(AngryBird.SCREENS_NAME.Pause);
                Gdx.app.log("GameScreen", "pause touched");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
        if (bird.isDragged && !bird.isFlying) {
            Vector2 speed = shootZone(actualPos);
            bird.setSpeed(new Vector2(
                    (bird.getOriginX() + bird.WIDTH / 2 - speed.x) * scenery.rubberBandFront.power,
                    (bird.getOriginY() + bird.HEIGHT / 2 - speed.y) * scenery.rubberBandFront.power));
            bird.fly();
            scenery.resetRubbers();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
        if (bird.isDragged && !bird.isFlying) {
            Vector2 dragPos = shootZone(actualPos);
            bird.setPosition(dragPos.x, dragPos.y);

            scenery.rubberBandBack.setDestination(dragPos.x + bird.getWidth() / 2, dragPos.y + bird.getHeight() / 2);
            scenery.rubberBandFront.setDestination(dragPos.x + bird.getWidth() / 2, dragPos.y + bird.getHeight() / 2);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
