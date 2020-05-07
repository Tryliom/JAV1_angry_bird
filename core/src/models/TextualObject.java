package models;

import com.badlogic.gdx.graphics.Texture;

public abstract class TextualObject extends PhysicalObject {
    protected String word;

    public TextualObject(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    protected String getWord() {
        return word;
    }
}
