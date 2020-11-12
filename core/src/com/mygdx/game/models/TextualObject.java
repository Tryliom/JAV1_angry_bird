package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.exam.SemanticWord;

public abstract class TextualObject extends PhysicalObject {
    protected SemanticWord word;
    private String text;

    public TextualObject(String picname, Vector2 position, int width, int height, String text) {
        super( picname, position, width, height);
        this.text = text;
        setOrigin(position.x, position.y);
    }

    public void setWord(SemanticWord word) {
        this.word = word;
    }

    public SemanticWord getWord() {
        return word;
    }

    public String getText() {
        return text;
    }
}
