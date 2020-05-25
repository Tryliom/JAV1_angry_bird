package com.mygdx.game.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AngryBird;

import java.util.ArrayList;

public final class Scenery {

    public static final int BLOCK_SIZE = 80;

    private ArrayList<com.mygdx.game.models.PhysicalObject> scene;

    public Scenery() {
        scene = new ArrayList<com.mygdx.game.models.PhysicalObject>();
    }

    /**
     * Add one piece of scenary
     *
     * @param el
     */
    public void addPhysicalElement(com.mygdx.game.models.PhysicalObject el) {
        scene.add(el);
    }

    /**
     * Lay down a line of blocks to act a floor to the scene
     */
    public void addFloor() {
        for (int i = 5; i < AngryBird.WORLD_WIDTH / BLOCK_SIZE; i++) {
            addPhysicalElement( new com.mygdx.game.models.PhysicalObject("block.png", new Vector2(i * BLOCK_SIZE, AngryBird.FLOOR_HEIGHT), BLOCK_SIZE, BLOCK_SIZE));
        }
    }

    /**
     * Render the whole scenary
     *
     * @param batch
     */
    public void draw(Batch batch) {
        for (PhysicalObject p : scene) p.draw(batch);
    }

}