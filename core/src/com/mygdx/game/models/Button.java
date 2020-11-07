package com.mygdx.game.models;


import com.badlogic.gdx.math.Vector2;

public class Button extends PhysicalObject {
    private String name;

    public Button(String picname, String name, Vector2 position, float width, float height) {
        super(picname, position, width, height);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}