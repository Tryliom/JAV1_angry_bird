package com.mygdx.game.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AngryBird;
import com.mygdx.game.customExceptions.OutOfSceneryException;

import java.util.ArrayList;

public final class Scenery {

    public static final int BLOCK_SIZE = 80;
    public static final int X_MIN = 400;
    public static final int X_MAX = AngryBird.WORLD_WIDTH;
    public static final int Y_Min = AngryBird.FLOOR_HEIGHT;
    public static final int Y_MAX = AngryBird.WORLD_HEIGHT;

    private ArrayList<PhysicalObject> scene;
    protected PhysicalObject touchedObject;

    public Scenery() {
        scene = new ArrayList<PhysicalObject>();
    }

    /**
     * Add one piece of scenary
     *
     * @param object
     */
    public void add(PhysicalObject object) throws OutOfSceneryException {
        if( object.getX() < X_MIN || object.getX()+object.getWidth() > X_MAX
            || object.getY() < Y_Min || object.getY()+object.getHeight() > Y_MAX )
        {
            throw new OutOfSceneryException("Veuillez replacer votre block");
        }
        moveObject(object);
        scene.add(object);
    }

    private void moveObject(PhysicalObject object){
        for (PhysicalObject p : scene) {
            if (p.overlaps(object)) {
                object.setY(p.getY() + p.getHeight() + 1);
                moveObject(object);
            }
        }
    }

    /**
     * Lay down a line of blocks to act a floor to the scene
     */
    public void addFloor() throws OutOfSceneryException {
        for (int i = 5; i < AngryBird.WORLD_WIDTH / BLOCK_SIZE; i++) {
            add(new WoodenBlock(new Vector2(i * BLOCK_SIZE, AngryBird.FLOOR_HEIGHT)));
        }
    }

    public boolean overlaps(PhysicalObject object){
        for (PhysicalObject p : scene)
            if (p.overlaps(object)) {
                 touchedObject = p;
                return true;
            }
        return false;
    }

    public PhysicalObject getTouchedObject(){
        return touchedObject;
    }
    public void removePig(Pig pig){
        scene.remove(pig);
    }

    public ArrayList<Pig> getPigs()
    {
        ArrayList<Pig> pigs = new ArrayList<Pig>();
        for (PhysicalObject object : scene)
            if (object.getClass() == Pig.class) {
                pigs.add((Pig)object);
            }
        return pigs;
    }
    /**
     * Render the whole scenary
     *
     * @param batch
     */
    public void draw(Batch batch) {
        for (PhysicalObject p : scene) p.draw(batch);
    }

    public ArrayList<PhysicalObject> getScene() {
        return scene;
    }
}