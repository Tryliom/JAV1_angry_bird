package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Tnt extends PhysicalObject {
    private static final String PICNAME = "tnt.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    private int negativePoints;

    public Tnt(Texture texture, Vector2 position, int negativePoints) {
        super(PICNAME, position, WIDTH, HEIGHT);
        this.negativePoints = negativePoints;
    }
}
