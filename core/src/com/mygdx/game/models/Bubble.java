package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bubble extends TextualObject {
    private static final String PICNAME = "bubble.png";
    public static final int WIDTH = 164;
    public static final int HEIGHT = 134;
    private float duration;

    protected static GlyphLayout glyphLayout;
    protected BitmapFont font;

    public Bubble(Vector2 position, Word word, float duration) {
        super(PICNAME, position, WIDTH, HEIGHT, "");
        this.duration = duration;
        this.word = word;

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(3);
        glyphLayout = new GlyphLayout(font, word.getFrenchWord());

        setSize(glyphLayout.width * 2, glyphLayout.height * 4);
    }

    public float getDuration() {
        return this.duration;
    }

    public void countdown(float deltaTime)
    {
        this.duration -= deltaTime;
        if(this.duration < 0) this.duration = 0;
    }


    public void draw(SpriteBatch batch)
    {
        super.draw(batch);

        final float fontX = getX() + (getWidth() - glyphLayout.width) / 2;
        final float fontY = getY() + (getHeight() + glyphLayout.height*2f) / 2;
        font.draw(batch, glyphLayout, fontX, fontY);
    }
}
