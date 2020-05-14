package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;

public class Bubble extends TextualObject {
    private static final String PICNAME = "buble.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    private int duration;

    public Bubble(Vector2 position, int duration) {
        super(PICNAME, position, WIDTH, HEIGHT);
        this.duration = duration;
    }
}
