package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends PhysicalObject {

    public Vector2 speed;

    public MovingObject(Texture texture, int x, int y) {
        super(texture, x, y);
    }


    public void move(int dt)
    {

    }

    public abstract void accelerate(int dt);

}
