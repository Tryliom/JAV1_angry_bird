package models;

import com.badlogic.gdx.graphics.Texture;

public class Bird extends MovingObject {
    private static String textureName = "bird.png";
    public Bird(int x, int y){
        super(new Texture(textureName), x, y);
    }
    public void accelerate(int dt)
    {

    }
}
