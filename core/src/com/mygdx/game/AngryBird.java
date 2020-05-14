package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AngryBird extends ApplicationAdapter implements InputProcessor {
	public static Random alea; // random generator object. Static for app-wide use
	public static long startTime = TimeUtils.millis();
	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;
	public static final int FLOOR_HEIGHT = 120;

	private Texture background;

	private Bird bird;
	private Vector2 birdStart;
	private Wasp waspy;

	private OrthographicCamera camera;

	private SpriteBatch batch;
	private Scenery scenery;
	private Texture slingshotBack;
	private Texture slingshotFront;

	private Vector2 touchPos;
	private Vector2 dragPos;
	@Override
	public void create () {
		batch = new SpriteBatch();
		alea = new Random();
		scenery = new Scenery();
		touchPos = new Vector2();
		dragPos = new Vector2();

		slingshotBack = new Texture(Gdx.files.internal("slingshot1.png")) ;
		slingshotFront = new Texture(Gdx.files.internal("slingshot2.png")) ;

		background = new Texture(Gdx.files.internal("background.jpg"));

		birdStart = new Vector2(200, 300);
		bird = new Bird(birdStart);//new Vector2(900,500));

		waspy = new Wasp(new Vector2(AngryBird.WORLD_WIDTH/2, AngryBird.WORLD_HEIGHT/2), new Vector2(20,50));

		scenery.addFloor();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

		Gdx.input.setInputProcessor(this);
	}
	public void update() {
		float dt = Gdx.graphics.getDeltaTime(); // number of milliseconds elapsed since last render
		if (bird.isFrozen()) {
			bird.accelerate(dt);
			bird.move(dt);
			if (bird.getX() > WORLD_WIDTH || (bird.getX()) < 0-bird.getWidth() || bird.getY() < FLOOR_HEIGHT)
			{
				bird.freeze();
				bird.setPosition(birdStart.x, birdStart.y);
			}
		}
		waspy.accelerate(dt);
		waspy.move(dt);
		// --------- Bird
		// Apply changes to the bird. The magnitude of the changes depend on the time elapsed since last update !!!

		// --------- Wasp
		// Apply changes to the wasp...
	}

	@Override
	public void render () {
		update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		batch.draw(slingshotBack, 200,FLOOR_HEIGHT);
		bird.draw(batch);
		waspy.draw(batch);
		scenery.draw(batch);
		batch.draw(slingshotFront, 200,FLOOR_HEIGHT);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchPos.set(screenX, Gdx.graphics.getHeight() - screenY);
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 speed = calculatePosition();
		System.out.print(speed);
		bird.setSpeed( new Vector2(-speed.x*5, -speed.y*5) );
		bird.freeze();
		return false;
	}

	private float   radius      = 200f;
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		dragPos.set(screenX, Gdx.graphics.getHeight() - screenY);

		return false;
	}

	private Vector2 calculatePosition()
	{
		if(dragPos == null) dragPos = new Vector2(0,0);
		float posX = dragPos.x-touchPos.x;
		float posY = dragPos.y-touchPos.y;

		if(posX > 200)
			posX = 200;
		else if(posX < -200)
			posX = -200;

		if(posY > 200)
			posY = 200;
		else if(posY < -200)
			posY = -200;

		return new Vector2(posX, posY);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
