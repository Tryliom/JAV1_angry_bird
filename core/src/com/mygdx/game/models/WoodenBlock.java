package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class WoodenBlock extends PhysicalObject {
    private static final String PICNAME = "block.png";
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    public WoodenBlock(Vector2 position) {
        super(PICNAME, position, WIDTH, HEIGHT);
    }

}
