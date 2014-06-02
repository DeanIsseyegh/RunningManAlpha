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
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class GameOverScreen implements Screen {

	public static final String LOGO = "menu/GameOverLogo.png";
	public static final int LOGO_WIDTH = 671;
	public static final int LOGO_HEIGHT = 110;
	public static final int LOGO_SCALE_F = 2;
	public static final String BUTTON_PACK_IMAGE = "menu/RunningManPack.pack";
	
	private int actualScreenWidth;
	private int actualScreenHeight;
	private float time;
	
	private boolean hasGameOverSoundPlayed = false;
	private boolean hasUserSelectedContinue = false;
	
	private Texture logo;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Animation logoCharacter;
	private int logoCharWidth;
	private int logoCharHeight;
	private Vector2 initialLogoCharStartPos;
	private Vector2 logoCharacterPos;
	private Vector2 logoCharacterVel;
	private float maxJumpHeight;
	private float logoCharJumpSpeed;
	
	private Stage stage;
	
	private TextureAtlas buttonAtlas;
	
	private TextButtonStyle contButtonbuttonStyle;
	private TextButton contButton;
	
	private TextButtonStyle exitButtonbuttonStyle;
	private TextButton exitButton;
	
	private Skin skin;
	
	private MainGame game;
	private SoundManager soundManager;
	
	public GameOverScreen(MainGame game, SoundManager soundManager){
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
		contButton.setPosition(Gdx.graphics.getWidth()/2 - LOGO_WIDTH/2, Gdx.graphics.getHeight()/2 - 200);
		stage.addActor(contButton);
		
		exitButtonbuttonStyle = new  TextButtonStyle();
		exitButtonbuttonStyle.up = skin.getDrawable("ExitGameButton");
		exitButtonbuttonStyle.over = skin.getDrawable("ExitGameButtonPressed");
		exitButtonbuttonStyle.down = skin.getDrawable("ExitGameButtonPressed");
		exitButtonbuttonStyle.font = font;
		
		exitButton = new TextButton("", exitButtonbuttonStyle);
		exitButton.setPosition(Gdx.graphics.getWidth()/2 - 589/2 + 40, Gdx.graphics.getHeight()/2 - 400);
		stage.addActor(exitButton);
		
		Gdx.input.setInputProcessor(stage);
		
		contButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				hasUserSelectedContinue = true;
				stage.addAction(Actions.sequence(Actions.fadeOut(2), Actions.run(new Runnable() {
					@Override
					public void run() {
						continueGame();
					}
				})));
				return true;
			}
		});
		
		exitButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				exitGame();
				return true;
			}
		});
		
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
	}
	
	private void continueGame(){
		game.setPoints(0);
	}
	
	private void exitGame(){
		dispose();
		Gdx.app.exit();
	}
	
	private void initLogoCharacter(){
		int FRAME_COLS = 8;
		int FRAME_ROWS = 2;
		Texture spriteSheet = new Texture(Gdx.files.internal(IWorldObject.MAIN_CHAR_IMAGE));
		TextureRegion[] aniFrames = AbstractWorldObject.animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		logoCharacter = new Animation(0.05f, aniFrames);
		logoCharWidth = 156;
		logoCharHeight = 200;
		logoCharJumpSpeed = 300;
		initialLogoCharStartPos = new Vector2(Gdx.graphics.getWidth()/2 - 100 , Gdx.graphics.getHeight()/2);
		logoCharacterPos = new Vector2(initialLogoCharStartPos.x , initialLogoCharStartPos.y);
		logoCharacterVel = new Vector2(0, logoCharJumpSpeed);
		maxJumpHeight = initialLogoCharStartPos.y + 175;
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
	
	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    stage.act();
	    
	    time += deltaTime;
	    
	    if (time > 1 && !hasGameOverSoundPlayed){
	    	hasGameOverSoundPlayed = true;
	    	soundManager.playGameOverSound();
	    }
	    
	    batch.begin();
	    batch.draw(logo, Gdx.graphics.getWidth()/2 - LOGO_WIDTH, Gdx.graphics.getHeight() - LOGO_HEIGHT*LOGO_SCALE_F, LOGO_WIDTH*LOGO_SCALE_F, LOGO_HEIGHT*LOGO_SCALE_F);
	    if (hasUserSelectedContinue)
	    	makeLogoCharJump(deltaTime);
	    else
	    	batch.draw(logoCharacter.getKeyFrame(time, true), initialLogoCharStartPos.x, initialLogoCharStartPos.y, 156, 200);
	    batch.end();
	    
	    stage.draw();
	}
	
	/**
	 * Makes the logo character jump, make a jumping sound and eventually fall off the screen.
	 */
	private void makeLogoCharJump(float deltaTime){
		logoCharacterPos.y +=  logoCharacterVel.y * deltaTime;
		logoCharacterVel.y -= logoCharJumpSpeed * deltaTime; //Make jumping more realistic/smoother emulate gravity
		batch.draw(logoCharacter.getKeyFrame(time, true), logoCharacterPos.x , logoCharacterPos.y, logoCharWidth, logoCharHeight);
		if (logoCharacterPos.y > maxJumpHeight){
			logoCharacterVel.y = - logoCharacterVel.y;
		}
		if (logoCharacterPos.y < 0 && hasUserSelectedContinue){
			hasUserSelectedContinue = false;
			game.setScreen(game.getLevel1());
		}
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
		soundManager.stopGameOverSound();
		hasGameOverSoundPlayed = false;
		batch.dispose();
		buttonAtlas.dispose();
		logo.dispose();
		skin.dispose();
		stage.dispose();
		
	}


}
