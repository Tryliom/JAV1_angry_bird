package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AngryBird;
import com.mygdx.game.customExceptions.TranslationDoesNotExistException;
import com.mygdx.game.interfaces.Scoreable;

public class Pig extends TextualObject implements Scoreable {
    private static final int points = 10;
    private static final String PICNAME = "pig.png";
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    public Pig(Vector2 position) {
        super(PICNAME, position, WIDTH, HEIGHT, "");
    }

    public String sayWord() throws TranslationDoesNotExistException {
        return this.getWord().getValue(AngryBird.getInstance().getSecond().getISO_639_1());
    }

    @Override
    public int incrementScore() {
        return points;
    }

    @Override
    public int decrementScore() {
        return  -points;
    }
}
