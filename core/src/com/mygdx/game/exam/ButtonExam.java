package com.mygdx.game.exam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.models.TextualObject;

public class ButtonExam extends TextualObject {
    private String value;
    private BitmapFont font = new BitmapFont();
    private GlyphLayout glyphLayout;


    public ButtonExam(String text, String value, Vector2 position, int width, int height) {
        super("button.png", position, width, height, text);
        this.value = value;
        font.setColor(Color.BLACK);
        font.getData().setScale(4);
        glyphLayout = new GlyphLayout();
    }

    public String getValue() {
        return value;
    }

    public void draw(Batch batch) {
        batch.draw(this.getTexture(), this.getWidth(), this.getHeight());
        font.draw(batch, glyphLayout, this.getX(), this.getY());
    }

    public Boolean isTouched(Vector2 point) {
        return this.getBoundingRectangle().contains(point);
    }
}
