package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;

public abstract class TextualObject extends PhysicalObject {
    protected String word;

    public TextualObject(String picname, Vector2 position, int width, int height) {
        super( picname, position, width, height);
    }

    protected String getWord() {
        return word;
    }
}
