package com.mygdx.game.models;
//TODO Passer en paramètres le nécessaire pour enlever la dépendance

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.customExceptions.OutOfSceneryException;

import java.util.ArrayList;

import static com.mygdx.game.screens.GameScreen.BIRD_START;
import static com.mygdx.game.screens.GameScreen.WORLD_WIDTH;
import static com.mygdx.game.screens.GameScreen.FLOOR_HEIGHT;
import static com.mygdx.game.screens.GameScreen.WORLD_HEIGHT;
import static com.mygdx.game.screens.GameScreen.rand;

import com.mygdx.game.customExceptions.TranslationDoesNotExistException;
import com.mygdx.game.exam.SemanticWord;
import com.mygdx.game.exam.VocProvider;
import com.mygdx.game.exam.Vocabulary;

public final class Scenery {

    public static final int X_MIN = 400;
    public static final int X_MAX = WORLD_WIDTH;
    public static final int Y_MIN = FLOOR_HEIGHT;
    public static final int Y_MAX = WORLD_HEIGHT;

    public static final int BLOCK_SIZE = 80;

    public Bird bird;
    public Wasp wasp;
    public Panel panel;

    private Vocabulary vocabulary;

    public RubberBand rubberBandBack;
    public RubberBand rubberBandFront;
    private Texture slingshotBack;
    private Texture slingshotFront;
    private SemanticWord wordToFind;

    private ArrayList<PhysicalObject> scene;
    private ArrayList<SemanticWord> words = new ArrayList<SemanticWord>();

    public Scenery(Vocabulary vocabulary) throws TranslationDoesNotExistException {

        this.scene = new ArrayList<PhysicalObject>();
        this.vocabulary = vocabulary;

        bird = new Bird(BIRD_START);
        bird.setFrozen(true);
        wasp = new Wasp(new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2), new Vector2(20, 50));
        wordToFind = VocProvider.getInstance().pickAVoc().pickAWord();
        panel = new Panel(new Vector2(20, Y_MAX), wordToFind);

        generateFloor();
        generateTNT(7);
        generatePigs(5);

        slingshotBack = new Texture(Gdx.files.internal("slingshot1.png"));
        slingshotFront = new Texture(Gdx.files.internal("slingshot2.png"));
        rubberBandBack = new RubberBand(bird.getPosition().x + bird.getWidth() - 10, bird.getPosition().y + bird.getHeight() / 2);
        rubberBandFront = new RubberBand(bird.getPosition().x + bird.getWidth() / 2 - 10, bird.getPosition().y + bird.getHeight() / 2);

    }

    /**
     * Add a new element on the scenery
     * @param object
     */
    public void add(PhysicalObject object) throws OutOfSceneryException {
        if (isOutOfScenery(object) || object.getX()+object.getWidth() > WORLD_WIDTH || object.getY() < Y_MIN || object.getY() + object.getHeight() > Y_MAX) {
            throw new OutOfSceneryException("Veuillez replacer votre block");
        }
        moveObjectToTopIfColliding(object);
        scene.add(object);
    }

    public void removeObject(PhysicalObject object) {
        scene.remove(object);
    }

    private void moveObjectToTopIfColliding(PhysicalObject object) {
        for (PhysicalObject p : scene) {
            if (p.overlaps(object)) {
                object.setY(p.getY() + p.getHeight() + 1);
                moveObjectToTopIfColliding(object);
            }
        }
    }

    public PhysicalObject whichObjectTouched(Vector2 position) {
        for (PhysicalObject object : scene)
            if (object.getBoundingRectangle().contains(position.x, position.y))
                return object;
        if (bird.getBoundingRectangle().contains(position.x, position.y))
            return bird;
        if (panel.getBoundingRectangle().contains(position.x, position.y))
            return panel;
        return null;
    }

    public PhysicalObject whichObjectTouched(float x, float y) {
        return whichObjectTouched(new Vector2(x, y));
    }

    public boolean isOutOfScenery(PhysicalObject object) {
        if (object.getX() > WORLD_WIDTH || object.getX() < 0 || object.getY() < FLOOR_HEIGHT)
            return true;
        return false;
    }

    private void generateFloor() {
        try {
            for (int i = 5; i < X_MAX / BLOCK_SIZE; i++) {
                add(new WoodenBlock(new Vector2(i * BLOCK_SIZE, Y_MIN)));
            }
        } catch (Exception e) {
            Gdx.app.log("Angry", "Floor completely generate");
        }
    }

    private void generateTNT(int quantity) {
        for (int i = 0; i < quantity; i++) {
            try {
                add(new Tnt(new Vector2(rand.nextInt(X_MAX - X_MIN) + X_MIN, FLOOR_HEIGHT)));
            } catch (OutOfSceneryException e) {
                Gdx.app.log("OutOfSceneryException", "TNT: " + e.getMessage());
                i--;
            }
        }
    }

    private void generatePigs(int quantity) {
        words.add(wordToFind);
        SemanticWord word;
        for (int i = 0; i < quantity-1; i++) {
            do {
                //get 4 extra words for the game, even if they have already been found
                // If vocabulary has only one last word, player can continue to try to find the word
                word = vocabulary.pickAWord();
            }while (words.contains(word));
            words.add(word);
        }

        for (int i = 0; i < quantity; i++) {
            try {
                Pig pig = new Pig(new Vector2(rand.nextInt(X_MAX - X_MIN) + X_MIN, FLOOR_HEIGHT));
                pig.setWord(words.get(i));
                add(pig);
            } catch (OutOfSceneryException e) {
                Gdx.app.log("OutOfSceneryException", "Pig: " + e.getMessage());
                i--;
            }
        }
    }

    public PhysicalObject overlaps(PhysicalObject object) {
        for (PhysicalObject p : scene)
            if (p.overlaps(object)) {
                return p;
            }
        return null;
    }

    public ArrayList<Pig> getPigs() {
        ArrayList<Pig> pigs = new ArrayList<Pig>();
        for (PhysicalObject object : scene)
            if (object.getClass() == Pig.class) {
                pigs.add((Pig) object);
            }
        return pigs;
    }

    public void resetRubbers() {
        rubberBandBack.reset();
        rubberBandFront.reset();
    }


    public void update(float dt) {
        wasp.accelerate(dt);
        wasp.move(dt);

        if (!bird.isFrozen()) {
            bird.accelerate(dt);
            bird.move(dt);
        }
    }

    /**
     * Render the whole scenary
     *
     * @param batch
     */
    public void draw(Batch batch) {
        wasp.draw(batch);

        for (PhysicalObject p : scene) p.draw(batch);

        rubberBandBack.draw(batch);
        batch.draw(slingshotBack, 200, FLOOR_HEIGHT);
        bird.draw(batch);
        batch.draw(slingshotFront, 200, FLOOR_HEIGHT);
        rubberBandFront.draw(batch);
//        panel.draw(batch); not working on this draw... why? i can't answer 😀
    }

    public ArrayList<PhysicalObject> getScene() {
        return scene;
    }
}