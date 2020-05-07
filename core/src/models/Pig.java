package models;

import com.badlogic.gdx.graphics.Texture;

import java.awt.geom.Point2D;

public class Pig extends TextualObject {
    private int Points;

    public Pig(Texture texture, int x, int y, int points) {
        super(texture, x, y);
        Points = points;
    }

    public void sayWord(){

    }
}
