package com.mygdx.game.exam;

import com.badlogic.gdx.Gdx;
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


    public ButtonExam(String text, String value, Vector2 position) {
        super("button.png", new Vector2(position.x - 400/2, position.y - 175/2), 400, 175, text);
        this.value = value;
        font.setColor(Color.WHITE);
        font.getData().setScale(4);
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
    }

    public String getValue() {
        return value;
    }

    public void draw(Batch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        font.draw(batch, glyphLayout, this.getX() + (this.getWidth() - glyphLayout.width)/2, this.getY() + (this.getHeight() + glyphLayout.height)/2);
    }

    public Boolean isTouched(Vector2 point) {
        return this.getBoundingRectangle().contains(point);
    }
}
