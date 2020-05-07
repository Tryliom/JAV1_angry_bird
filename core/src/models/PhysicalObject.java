package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PhysicalObject extends Sprite {
  public PhysicalObject(Texture texture, int x, int y) {
      super(texture, x, y, texture.getWidth(), texture.getHeight());
  }
}
