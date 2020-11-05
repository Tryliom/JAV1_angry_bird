package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.customExceptions.OutOfSceneryException;
import com.mygdx.game.models.*;
import com.mygdx.game.models.Vocabulary;
import com.mygdx.game.providers.VocabularyProvider;
import com.mygdx.game.models.Word;

import java.util.ArrayList;
import java.util.Random;


public class AngryBird extends ApplicationAdapter implements InputProcessor {

	//region Declaration
	public static long startTime = TimeUtils.millis();
	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;
	public static final int FLOOR_HEIGHT = 120;

	private Texture background;

	private Bird bird;
	private Vector2 birdStart;
	private Wasp wasp;
	private RubberBand rubberBandBack;
	private RubberBand rubberBandFront;


	private Panel panel;
	private Bubble bubble;
	private float bubbleTime;

	private VocabularyProvider vocabularyProvider;
	private Vocabulary vocabulary;
//	private	ArrayList<String> words;

	private OrthographicCamera camera;

	private SpriteBatch batch;
	private Scenery scenery;
	private Texture slingshotBack;
	private Texture slingshotFront;

	private Score score;
	private static GlyphLayout glyphLayout;
	private BitmapFont font;
	//endregion
	@Override
	public void create () {
		vocabularyProvider = vocabularyProvider.getInstance();
//		words = new ArrayList<String>();
//		words.addAll(Arrays.asList( "Pomme", "Patate", "Fraise", "Cookie", "banane"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

		batch = new SpriteBatch();
		score = new Score();

		background = new Texture(Gdx.files.internal("background.jpg"));
		font = new BitmapFont();
		font.getData().setScale(4);
		glyphLayout = new GlyphLayout();
		glyphLayout.setText(font, "Score: ".concat( String.valueOf(score.getScore()) ) );


		birdStart = new Vector2(190, 320);
		bird = new Bird(birdStart);
		wasp = new Wasp(new Vector2(AngryBird.WORLD_WIDTH/2, AngryBird.WORLD_HEIGHT/2), new Vector2(20,50));
		bubbleTime = 2;

		slingshotBack = new Texture(Gdx.files.internal("slingshot1.png")) ;
		slingshotFront = new Texture(Gdx.files.internal("slingshot2.png")) ;
		rubberBandBack = new RubberBand(birdStart.x + bird.getWidth() - 10, birdStart.y + bird.getHeight()/2);
		rubberBandFront = new RubberBand(birdStart.x + bird.getWidth()/2 - 10, birdStart.y + bird.getHeight()/2);

		createScene();

		Gdx.input.setInputProcessor(this);
	}
	
	private void createScene()
	{
		Gdx.app.log("scenery", "Creating scenery");
		//scenery elements
		try {
			Random generator = new Random();

			Gdx.app.log("scenery", "get vocabulary");
			vocabulary = vocabularyProvider.pickRandomVocabulary();
			ArrayList<Word> voc = new ArrayList<Word>();
			for (int i = 0; i < 6; i++) {
				// i don't know if a best method exists 😂, i use habitually a filter on c#
				Word word;
				do {
					word = vocabulary.pickRandomWord();
				} while (word.allocated);
				word.allocated = true;
				voc.add(word);
			}
			Gdx.app.log("scenery", String.format("Total of words: %s", voc.size()) );

			panel = new Panel( new Vector2(20, WORLD_HEIGHT), voc.get(MathUtils.random(voc.size()-1)));

			//create empty bubble used to all pigs
			bubble = new Bubble(new Vector2(-Bubble.WIDTH,0), voc.get(0), 0);
			bubbleTime = 2;

			scenery = new Scenery();
			scenery.addFloor();
			scenery.addTNT(5);

			Gdx.app.log("scenery", "pigs placed on scenery");
			for (int i = 0; i < voc.size(); i++) {
				try {
					Pig pig = new Pig(new Vector2(generator.nextInt(WORLD_WIDTH-400)+400,FLOOR_HEIGHT), 20);
					scenery.add(pig);
					pig.setWord(voc.get(i));
				} catch (OutOfSceneryException e) {
					Gdx.app.log("OutOfSceneryException", e.getMessage());
					i--;
				}catch (Exception e){
					Gdx.app.log("Angry", e.getMessage());
					i--;
				}
			}
			Gdx.app.log("scenery", "scenery created");
		} catch (OutOfSceneryException e) {
			Gdx.app.log("OutOfSceneryException", e.getMessage());
		}
	}

	public void update() {
		float dt = Gdx.graphics.getDeltaTime(); // number of milliseconds elapsed since last render
		if (bird.isFrozen()) {
			bird.accelerate(dt);
			bird.move(dt);
			if (bird.getX() > WORLD_WIDTH || (bird.getX()) < 0-bird.getWidth() || bird.getY() < FLOOR_HEIGHT || bird.overlaps(wasp))
			{
				resetBird();
			}
			else if (scenery.overlaps(bird) != null){
				PhysicalObject touchedObject = scenery.overlaps(bird);
				if(touchedObject.getClass() == Pig.class)
				{
					Pig pig = (Pig) touchedObject;
					if(pig.getWord() == panel.getWord()){
						score.calculate(pig);
						createScene();
					}
					else {
						scenery.removeObject(pig);
					}
				}
				else {
					score.calculate(touchedObject);
					scenery.removeObject(touchedObject);
				}

				glyphLayout.setText(font, String.format("Score: %s", score.getScore()));
				resetBird();
			}
		}
		if (bubble.getDuration() > 0) bubble.countdown(dt);
		wasp.accelerate(dt);
		wasp.move(dt);
	}

	private void resetBird()
	{
		bird.freeze();
		bird.setPosition(birdStart.x, birdStart.y);
		bird.isFlying = false;
	}

	private Vector2 shootZone(Vector3 position)	{
		return new Vector2( Math.min( Math.max(0, position.x), birdStart.x),
				Math.min( Math.max(FLOOR_HEIGHT, position.y), birdStart.y));
	}

	//region Touch Events
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
		if(bird.getBoundingRectangle().contains(actualPos.x, actualPos.y) && !bird.isFlying) {
			bird.isDragged = true;
		}

		for ( Pig pig : scenery.getPigs() ) {
			if(pig.getBoundingRectangle().contains(actualPos.x, actualPos.y)) {
				bubble = new Bubble(new Vector2(pig.getX() , pig.getY() + pig.getHeight()), pig.getWord(),  bubbleTime);
				bubble.translateX(-bubble.getWidth()/1.8f);
			}
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
		if(bird.isDragged && !bird.isFlying)
		{
			Vector2 speed = shootZone(actualPos);

			bird.setSpeed( new Vector2((bird.getOriginX() + bird.WIDTH / 2 - speed.x)* rubberBandFront.power, (bird.getOriginY() + bird.HEIGHT / 2 - speed.y)* rubberBandFront.power));
			bird.isFlying = true;
			bird.isDragged = false;
			rubberBandBack.reset();
			rubberBandFront.reset();
			bird.freeze();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 actualPos = camera.unproject(new Vector3(screenX, screenY, 0));
		if(bird.isDragged && !bird.isFlying)
		{
            Vector2 dragPos = shootZone(actualPos);
            bird.setPosition(dragPos.x, dragPos.y);

			rubberBandBack.setDestination( dragPos.x + bird.getWidth() / 2, dragPos.y + bird.getHeight()/2);
			rubberBandFront.setDestination(dragPos.x + bird.getWidth() / 2, dragPos.y + bird.getHeight()/2);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	//endregion

	//region Render
	@Override
	public void render () {
		update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		wasp.draw(batch);
		panel.draw(batch);
		scenery.draw(batch);
		if(bubble.getDuration() > 0) {
			bubble.drawWord(batch);
		}
		batch.draw(slingshotBack, 200,FLOOR_HEIGHT);
		rubberBandBack.draw(batch);
		bird.draw(batch);
		rubberBandFront.draw(batch);
		batch.draw(slingshotFront, 200, FLOOR_HEIGHT);
		font.draw(batch, glyphLayout,  WORLD_WIDTH-glyphLayout.width-20, WORLD_HEIGHT-glyphLayout.height);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	//endregion
}
