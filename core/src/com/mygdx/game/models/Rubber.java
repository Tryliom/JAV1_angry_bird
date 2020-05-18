package com.mygdx.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Rubber extends Sprite {
    public static String PICNAME = "rubber.png";
    private Vector2 origin;
    private Vector2 destination;
    private static int THICKNESS = 40;

    public Rubber(float x, float y){
        super(new Texture(PICNAME));
        destination = new Vector2(0, 0);
        setOrigin( x , y);
        origin = new Vector2(x,y);
        setBounds(x, y - THICKNESS, -10, THICKNESS);
        //setBounds(this.getOriginX(), this.getOriginY(), 0, 0);
    }

    public void setDestination(float x, float y)
    {
        this.destination = new Vector2(x, y);
        calculatePosition();
    }

    public void reset() { setDestination(origin.x, origin.y); }

    private void calculatePosition() {
        Vector2 vector = destination.sub(origin);
        setBounds(origin.x, origin.y, vector.len(), THICKNESS);
        setOrigin(0,0);
        setRotation(vector.angle());
    }
}
