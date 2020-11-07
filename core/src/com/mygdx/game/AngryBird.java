package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.mygdx.game.screens.GameScreen;

import java.util.Stack;

public class AngryBird1 extends ApplicationAdapter {
    public static Stack<ApplicationAdapter> stackScreens;

    @Override
    public void create() {
        stackScreens = new Stack<>();
        GameScreen gameScreen = new GameScreen();
        gameScreen.create();
        stackScreens.push(gameScreen);
    }

    @Override
    public void render() {
        stackScreens.peek().render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
