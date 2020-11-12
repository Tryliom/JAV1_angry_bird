package com.mygdx.game.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AngryBird;
import com.mygdx.game.exam.ButtonExam;
import com.mygdx.game.exam.Language;
import com.mygdx.game.exam.VocProvider;

import java.util.ArrayList;

public class WelcomeScreen extends ApplicationAdapter implements InputProcessor {

    private Texture background;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private BitmapFont title;
    private ArrayList<ButtonExam> buttonChooseFirstLanguage = new ArrayList<ButtonExam>();
    private ArrayList<ButtonExam> buttonChooseSecondLanguage = new ArrayList<ButtonExam>();
    private Language firstLanguage = null;
    private Language secondLanguage = null;
    private ButtonExam start;

    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 900;
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        background = new Texture(Gdx.files.internal("background.jpg"));

        initButtons();

        title = new BitmapFont();
        title.setColor(Color.ROYAL);
        title.getData().setScale(6);

        Gdx.input.setInputProcessor(this);
    }

    public void initButtons() {
        this.buttonChooseFirstLanguage.clear();
        this.buttonChooseSecondLanguage.clear();
        // For loop to get all languages
        ArrayList<Language> list = VocProvider.getInstance().getLanguages();
        int yIncrement = 0;
        for (Language lang : list) {
            if (this.firstLanguage == null && (this.secondLanguage == null || !this.secondLanguage.equals(lang)))
                this.buttonChooseFirstLanguage.add(new ButtonExam(lang.getDisplayName(), lang.getISO_639_1(), new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2 - 150 + yIncrement), 100, 20));
            if (this.secondLanguage == null && (this.firstLanguage == null || !this.firstLanguage.equals(lang)))
                this.buttonChooseSecondLanguage.add(new ButtonExam(lang.getDisplayName(), lang.getISO_639_1(), new Vector2(WORLD_WIDTH / 2 + 120, WORLD_HEIGHT / 2 - 150 + yIncrement), 100, 20));
            yIncrement += 30;
        }

        if (canDisplayStartButton()) {
            this.start = new ButtonExam("Démarrer", "start", new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 4), 100, 20);
        }
    }

    @Override
    public void render() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        title.draw(batch, "Exercice de ", WORLD_WIDTH / 2 - 100, WORLD_HEIGHT / 2);
        if (this.firstLanguage != null)
            title.draw(batch, this.firstLanguage.getDisplayName(), WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        title.draw(batch, "en", WORLD_WIDTH / 2 + 110, WORLD_HEIGHT / 2);
        if (this.secondLanguage != null)
            title.draw(batch, this.secondLanguage.getDisplayName(), WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        for (ButtonExam btn : this.buttonChooseFirstLanguage)
            btn.draw(batch);
        for (ButtonExam btn : this.buttonChooseSecondLanguage)
            btn.draw(batch);
        if (canDisplayStartButton()) {
            this.start.draw(batch);
        }
        batch.end();
    }

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

        if (this.firstLanguage == null) {
            for (ButtonExam btn : this.buttonChooseFirstLanguage) {
                if (btn.isTouched(new Vector2(actualPos.x, actualPos.y))) {
                    ArrayList<Language> list = VocProvider.getInstance().getLanguages();
                    for (Language lang : list) {
                        if (lang.getISO_639_1() == btn.getValue()) {
                            this.firstLanguage = lang;
                        }
                    }
                }
            }

            if (this.firstLanguage != null) {
                this.initButtons();
                return true;
            }
        }

        if (this.secondLanguage == null) {
            for (ButtonExam btn : this.buttonChooseSecondLanguage) {
                if (btn.isTouched(new Vector2(actualPos.x, actualPos.y))) {
                    ArrayList<Language> list = VocProvider.getInstance().getLanguages();
                    for (Language lang : list) {
                        if (lang.getISO_639_1() == btn.getValue())
                            this.secondLanguage = lang;
                    }
                }
            }

            if (this.secondLanguage != null) {
                this.initButtons();
                return true;
            }
        }

        if (this.start.isTouched(new Vector2(actualPos.x, actualPos.y)) && canDisplayStartButton()) {
            AngryBird.getInstance().push(AngryBird.SCREENS_NAME.Game);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Boolean canDisplayStartButton() {
        return this.firstLanguage != null && this.secondLanguage != null;
    }
}
