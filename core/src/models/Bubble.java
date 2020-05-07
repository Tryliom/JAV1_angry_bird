package models;

import com.badlogic.gdx.graphics.Texture;

public class Bubble extends TextualObject {
    public Bubble(Texture texture, int x, int y, int duration) {
        super(texture, x, y);
        this.duration = duration;
    }

    private int duration;
}
