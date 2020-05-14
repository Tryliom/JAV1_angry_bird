
package com.mygdx.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.AngryBird;

public class Wasp extends MovingObject {

    private static final String PICNAME = "wasp.png";
    public static final int WIDTH = 140;
    public static final int HEIGHT = 140;

    Vector2 cycleLength = new Vector2(1300,900); // time (1000 = 1sec)
    Vector2 cycleScale = new Vector2(AngryBird.WORLD_WIDTH/3-this.getWidth(), AngryBird.WORLD_HEIGHT/4); // zone where wasp move from centerPos
    Vector2 centerPos;

    public Wasp(Vector2 position, Vector2 speed){
        super(PICNAME, position, WIDTH, HEIGHT, speed);
        centerPos = position;
    }

    @Override
    public void accelerate(float dt) {
        //Vector2 folly = new Vector2(AngryBird.alea.nextFloat()-(getX()/AngryBird.WORLD_WIDTH), AngryBird.alea.nextFloat()-(getX()/AngryBird.WORLD_HEIGHT));
        //peed = speed.add(folly.scl(AGITATION));
    }

    @Override
    public void move(float deltaTime) {
        long globalCounter = TimeUtils.timeSinceMillis( AngryBird.startTime );
        float x  = (float) Math.sin(globalCounter/cycleLength.x)*cycleScale.x + centerPos.x;
        float y = (float) Math.cos(globalCounter/cycleLength.y)*cycleScale.y + centerPos.y;

        this.setPosition(x,y);
    }
}
