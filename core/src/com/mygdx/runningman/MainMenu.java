package com.mygdx.runningman;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.MainCharacter;

public class MainMenu implements Screen {

	public static final String LOGO = "menu/Logo.png";
	public static final int LOGO_WIDTH = 749;
	public static final int LOGO_HEIGHT = 121;
	public static final int LOGO_SCALE_F = 2;
	public static final String BUTTON_PACK_IMAGE = "menu/RunningManPack.pack";
	
	private int actualScreenWidth;
	private int actualScreenHeight = 800;
	private float time;
	
	private Texture logo;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Animation logoCharacter;
	
	private Stage stage;
	
	private TextureAtlas buttonAtlas;
	
	private TextButtonStyle startButtonbuttonStyle;
	private TextButton startButton;
	
	private TextButtonStyle exitButtonbuttonStyle;
	private TextButton exitButton;
	
	private Skin skin;
	
	private MainGame game;
	private SoundManager soundManager;
	
	public MainMenu(MainGame game, SoundManager soundManager){
		this.game = game;
		this.soundManager = soundManager;
	}
	

	@Override
	public void show() {
		soundManager.playMainMenuMusic();
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
		
		startButtonbuttonStyle = new  TextButtonStyle();
		startButtonbuttonStyle.up = skin.getDrawable("StartGameButton");
		startButtonbuttonStyle.over = skin.getDrawable("StartGameButtonPressed");
		startButtonbuttonStyle.down = skin.getDrawable("StartGameButtonPressed");
		startButtonbuttonStyle.font = font;
		
		startButton = new TextButton("", startButtonbuttonStyle);
		startButton.setPosition(Gdx.graphics.getWidth()/2 - 589/2, Gdx.graphics.getHeight()/2 - 200);
		stage.addActor(startButton);
		
		exitButtonbuttonStyle = new  TextButtonStyle();
		exitButtonbuttonStyle.up = skin.getDrawable("ExitGameButton");
		exitButtonbuttonStyle.over = skin.getDrawable("ExitGameButtonPressed");
		exitButtonbuttonStyle.down = skin.getDrawable("ExitGameButtonPressed");
		exitButtonbuttonStyle.font = font;
		
		exitButton = new TextButton("", exitButtonbuttonStyle);
		exitButton.setPosition(Gdx.graphics.getWidth()/2 - 589/2 + 40, Gdx.graphics.getHeight()/2 - 400);
		stage.addActor(exitButton);
		
		Gdx.input.setInputProcessor(stage);
		
		startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				startGame();
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
	}
	
	private void startGame(){
		game.setScreen(game.level1);
	}
	
	private void exitGame(){
		dispose();
		System.exit(1);
	}
	
	private void initLogoCharacter(){
		int FRAME_COLS = 8;
		int FRAME_ROWS = 2;
		Texture spriteSheet = new Texture(Gdx.files.internal(IWorldObject.MAIN_CHAR_IMAGE));
		TextureRegion[] aniFrames = AbstractWorldObject.animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		logoCharacter = new Animation(0.05f, aniFrames);
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
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    stage.act();
	    
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    time += deltaTime;
	    
	    batch.begin();
	    batch.draw(logo, Gdx.graphics.getWidth()/2 - LOGO_WIDTH, Gdx.graphics.getHeight() - LOGO_HEIGHT*LOGO_SCALE_F, LOGO_WIDTH*LOGO_SCALE_F, LOGO_HEIGHT*LOGO_SCALE_F);
	    batch.draw(logoCharacter.getKeyFrame(time, true), Gdx.graphics.getWidth()/2 - 100 , Gdx.graphics.getHeight()/2 + 100, 156, 200);
	    batch.end();
	    
	    stage.draw();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(int arg0, int arg1) {
		configureCamera();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		soundManager.stopMainMenuMusic();
	}


}
