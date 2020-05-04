package models;

import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends PhysicalObject {
    public Vector2 speed;

    public void move(int dt)
    {

    }

    public abstract void accelerate(int dt);

}
