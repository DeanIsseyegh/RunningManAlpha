package com.mygdx.runningman.screens;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.runningman.MainGame;
import com.mygdx.runningman.MainGame.GameLevel;
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class LevelCompletedScreen implements Screen {

	public static final String LOGO = "menu/LevelCompletedLogo.png";
	public static final float LOGO_WIDTH = 1184;
	public static final float LOGO_HEIGHT = 93;
	public static final float LOGO_SCALE_F = 1.5f;
	public static final String BUTTON_PACK_IMAGE = "menu/RunningManPack.pack";
	
	private int actualScreenWidth;
	private int actualScreenHeight;
	private float time;
	
	private Texture logo;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Animation logoCharacter;
	private int logoCharWidth;
	private int logoCharHeight;
	private Vector2 initialLogoCharStartPos;
	
	private Animation stars;
	private int starsWidth;
	private int starsHeight;
	private Vector2 starsPos;
	
	private Stage stage;
	
	private TextureAtlas buttonAtlas;
	
	private TextButtonStyle contButtonbuttonStyle;
	private TextButton contButton;
	
	private Skin skin;
	
	private MainGame game;
	private SoundManager soundManager;
	
	public LevelCompletedScreen(MainGame game, SoundManager soundManager){
		this.game = game;
		this.soundManager = soundManager;
	}
	

	@Override
	public void show() {
		camera = new OrthographicCamera();
		logo = new Texture(Gdx.files.internal(LOGO)); //749px wide, 121 high
		batch = new SpriteBatch();
		stage = new Stage();
		initLogoCharacter();
		initStarts();
		configureCamera();
		
		skin = new Skin();
		buttonAtlas = new TextureAtlas(BUTTON_PACK_IMAGE);
		skin.addRegions(buttonAtlas);
		
		BitmapFont font = new BitmapFont();
		
		contButtonbuttonStyle = new  TextButtonStyle();
		contButtonbuttonStyle.up = skin.getDrawable("ContinueButton");
		contButtonbuttonStyle.over = skin.getDrawable("ContinueButtonPressed");
		contButtonbuttonStyle.down = skin.getDrawable("ContinueButtonPressed");
		contButtonbuttonStyle.font = font;
		
		contButton = new TextButton("", contButtonbuttonStyle);
		contButton.setPosition(Gdx.graphics.getWidth()/2 - 350, Gdx.graphics.getHeight()/2 - 350);
		stage.addActor(contButton);
		
		Gdx.input.setInputProcessor(stage);
		
		soundManager.playLevelCompletedMusic();
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(2)));
		
		contButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				stage.addAction(Actions.sequence(Actions.fadeOut(2), Actions.run(new Runnable() {
					@Override
					public void run() {
						continueGame();
					}
				})));
				continueGame();
				return true;
			}
		});
		
	}
	
	private void continueGame(){
		soundManager.stopLevelCompletedMusic();
		GameLevel gameLevel = game.getNextLevel();
		switch (gameLevel){
		case LEVEL1:
			game.setScreen(game.getLevel1());
			break;
		case LEVEL2:
			game.setScreen(game.getLevel2());
			break;
		default: 
			game.setScreen(game.getMainMenu());
			break;
		}
	}
	

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    stage.act();
	    
	    time += deltaTime;

	    batch.begin();
	    batch.draw(logo, Gdx.graphics.getWidth()/2 - (LOGO_WIDTH/2) * LOGO_SCALE_F, Gdx.graphics.getHeight() - LOGO_HEIGHT*LOGO_SCALE_F, LOGO_WIDTH*LOGO_SCALE_F, LOGO_HEIGHT*LOGO_SCALE_F);
	    batch.draw(logoCharacter.getKeyFrame(time, true), initialLogoCharStartPos.x, initialLogoCharStartPos.y, logoCharWidth, logoCharHeight);
	    batch.draw(stars.getKeyFrame(time, true), starsPos.x, starsPos.y, starsWidth, starsHeight);
	    batch.end();
	    
	    stage.draw();
	}
	
	private void configureCamera(){
		if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth()) {
			actualScreenWidth = actualScreenHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualScreenWidth, actualScreenHeight);
		} else {
			actualScreenWidth = actualScreenHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualScreenHeight, actualScreenWidth);
		}
	}
	
	private void initLogoCharacter(){
		int FRAME_COLS = 8;
		int FRAME_ROWS = 2;
		Texture spriteSheet = new Texture(Gdx.files.internal(IWorldObject.MAIN_CHAR_IMAGE));
		TextureRegion[] aniFrames = AbstractWorldObject.animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		logoCharacter = new Animation(0.05f, aniFrames);
		logoCharWidth = 156;
		logoCharHeight = 200;
		initialLogoCharStartPos = new Vector2(Gdx.graphics.getWidth()/2 - 100 , Gdx.graphics.getHeight()/2);
	}
	
	private void initStarts(){
		int FRAME_COLS = 1;
		int FRAME_ROWS = 1;
		Texture spriteSheet = new Texture(Gdx.files.internal(IWorldObject.STARS));
		TextureRegion[] aniFrames = AbstractWorldObject.animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		stars = new Animation(0.05f, aniFrames);
		starsWidth = 440;
		starsHeight = 440;
		starsPos = new Vector2(Gdx.graphics.getWidth()/2 - 240 , Gdx.graphics.getHeight()/2 - 120);
	}
	
	@Override
	public void dispose() {}

	@Override
	public void pause() {}


	@Override
	public void resize(int arg0, int arg1) {
		configureCamera();
	}

	@Override
	public void resume() {}

	@Override
	public void hide() {
		batch.dispose();
		buttonAtlas.dispose();
		logo.dispose();
		skin.dispose();
		stage.dispose();
	}


}
