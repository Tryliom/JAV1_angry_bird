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
	private Rubber rubberBack;
	private Rubber rubberFront;

	private OrthographicCamera camera;

	private SpriteBatch batch;
	private Scenery scenery;
	private Texture slingshotBack;
	private Texture slingshotFront;

	private Vector3 touchPos;
	private Vector3 dragPos;
	@Override
	public void create () {
		batch = new SpriteBatch();
		alea = new Random();
		scenery = new Scenery();
		touchPos = new Vector3();
		dragPos = new Vector3();

		slingshotBack = new Texture(Gdx.files.internal("slingshot1.png")) ;
		slingshotFront = new Texture(Gdx.files.internal("slingshot2.png")) ;

		background = new Texture(Gdx.files.internal("background.jpg"));

		birdStart = new Vector2(190, 320);
		bird = new Bird(birdStart);

		rubberBack = new Rubber(birdStart.x + bird.getWidth() - 10, birdStart.y + bird.getHeight()/2);
		rubberFront = new Rubber(birdStart.x + bird.getWidth()/2 - 10, birdStart.y + bird.getHeight()/2);

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
				bird.isFlying = false;
			}
		}
		waspy.accelerate(dt);
		waspy.move(dt);
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
		Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
		if(bird.hitbox(new Vector2(actualPos.x, actualPos.y)) && !bird.isFlying) {
			touchPos = camera.unproject(new Vector3(screenX - bird.getWidth() / 2, screenY + bird.getHeight() / 2, 0));
			bird.isDragged = true;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(bird.isDragged && !bird.isFlying)
		{
			Vector2 speed = calculatePosition();

			bird.setSpeed( new Vector2(-speed.x, -speed.y) );
			bird.isFlying = true;
			bird.isDragged = false;
			rubberBack.reset();
			rubberFront.reset();
			bird.freeze();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
		if(bird.isDragged && !bird.isFlying)
		{
            dragPos = actualPos;
            Vector2 position = new Vector2(birdStart.x + calculatePosition().x - bird.getWidth() / 2, birdStart.y + calculatePosition().y - bird.getHeight() / 2);
            bird.setPosition(position.x, position.y);


			rubberBack.setDestination( position.x + bird.getWidth() / 2, position.y + bird.getHeight()/2);
			rubberFront.setDestination(position.x + bird.getWidth() / 2, position.y + bird.getHeight()/2);
		}
		return false;
	}

	private Vector2 calculatePosition()
	{

		if(dragPos == null) dragPos = new Vector3(0,0, 0);
		float posX = dragPos.x-touchPos.x;
		float posY = dragPos.y-touchPos.y;

		if(posX >= birdStart.x)
			posX = birdStart.x;
		else if(posX < -150)
			posX = -150;

		if(posY >= birdStart.y)
			posY = birdStart.y;
		else if(posY < -150)
			posY = -150;

		return  new Vector2(posX, posY);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void render () {
		update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		scenery.draw(batch);
		waspy.draw(batch);
		batch.draw(slingshotBack, 200,FLOOR_HEIGHT);
		//rubberBack.draw(batch);
		bird.draw(batch);
		rubberFront.draw(batch);
		batch.draw(slingshotFront, 200,FLOOR_HEIGHT);
		batch.end();
	}

}
