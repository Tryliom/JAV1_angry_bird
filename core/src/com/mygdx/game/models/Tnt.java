package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Tnt extends PhysicalObject {
    private static final String PICNAME = "tnt.png";
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    private int negativePoints;

    public Tnt(Vector2 position, int negativePoints) {
        super(PICNAME, position, WIDTH, HEIGHT);
        this.negativePoints = negativePoints;
    }

    public int getNegativePoints() {
        return negativePoints;
    }
}
