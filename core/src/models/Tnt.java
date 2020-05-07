package models;

import com.badlogic.gdx.graphics.Texture;

public class Tnt extends PhysicalObject {
    private int negativePoints;

    public Tnt(Texture texture, int x, int y, int negativePoints) {
        super(texture, x, y);
        this.negativePoints = negativePoints;
    }
}
