package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;

public class Pig extends TextualObject {
    private int Points;

    private static final String PICNAME = "pig.png";
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    public Pig(Vector2 position, int points){
        super(PICNAME, position, WIDTH, HEIGHT);
        this.Points = points;
    }

    public int getPoints() {
        return Points;
    }

    public String sayWord(){
        return this.getWord();
    }
}
