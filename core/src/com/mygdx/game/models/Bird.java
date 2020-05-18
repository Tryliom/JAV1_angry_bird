package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;

public class Bird extends MovingObject {

    private static final String PICNAME = "bird.png";
    public boolean isFlying = false;
    public boolean isDragged = false;

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    public Bird(){
        super(PICNAME, new Vector2(100, 200) , WIDTH, HEIGHT, new Vector2(50, 100));
        frozen = true;
    }
    public Bird(Vector2 position){
        super(PICNAME, position, WIDTH, HEIGHT, new Vector2(0,0));
    }

    public void setSpeed(Vector2 speed)
    {
        super.speed = new Vector2(speed.x*5, speed.y*5);
    }

    @Override
    public void accelerate(float deltaTime) {
        speed.y += MovingObject.GRAVITY * 60 * deltaTime;
    }

}
