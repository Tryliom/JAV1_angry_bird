package com.mygdx.game.models;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.models.data.Word;

public abstract class TextualObject extends PhysicalObject {
    protected Word word;

    public TextualObject(String picname, Vector2 position, int width, int height) {
        super( picname, position, width, height);
        setOrigin(position.x, position.y);
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }
}
