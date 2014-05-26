package com.mygdx.leftman;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import org.apache.commons.lang3.ArrayUtils;

import android.util.Log;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.leftman.characters.MainCharacter;

public class LeftMan implements ApplicationListener
{
	private static final String TAG = "MyActivity";
	private static final String ENEMY1_IMAGE = "Enemy1.png";
	private static final String ENEMY2_IMAGE = "Enemy2_1.png";
	private static final String MAIN_CHAR_IMAGE = "adjustedMan.png";
	private static final String BG1_IMAGE = "skybackground.png";
	
	Texture level1Background;
	final int level1BackgroundWidth = 1200;
	ArrayList<Integer> backgroundPositions; 
	
	Texture enemy1;
	Animation walkingEnemy1;
	Vector2 enemy1Velocity;
	Vector2 enemy1Position;

	Texture enemy2;
	Animation animatingEnemy2;
	Vector2 enemy2Position;
	Rectangle enemy2Box;
	
	SpriteBatch batch;
	
	OrthographicCamera camera;
	
	MainCharacter mainChar;
	
	float animationTime;
	float time;
	float hardCodedJumpHeight = 150;
	int actualWidth;
	int actualHeight;
	boolean isManInAir;
	
	/**
	 * create()
	 * 
	 * Method that is called when instance of game is first created. Sets up camera, sprites, animations, background
	 * and batch.
	 */
	@Override
	public void create()
	{
		mainChar = new MainCharacter();
		initBackground();
		initEnemy1();
		initEnemy2();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		actualHeight = 800;
		configureCamera();
	}
	
	private void initBackground(){
		level1Background = new Texture(Gdx.files.internal(BG1_IMAGE));
		backgroundPositions = new ArrayList<Integer>(); 
		
		for (int i = 0; i < 3; i++)
			backgroundPositions.add(level1BackgroundWidth * i);
	}
	
	/**
	 * initEnemy1()
	 * 
	 * Private method responsible for setting up the first enemy character sprite and animation.
	 * Also defines his starting position and walk speed.).
	 * 
	 * Note that the frames are reversed so the enemy does not having a walking backwards animation (spriteSheet has images in wrong order).
	 */
	private void initEnemy1(){
		enemy1 = new Texture(Gdx.files.internal(ENEMY1_IMAGE));
		enemy1Velocity = new Vector2(-200, 0);
		enemy1Position = new Vector2(1000, 0);

		int FRAME_COLS = 5;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, enemy1);
		ArrayUtils.reverse(aniFrames);
		walkingEnemy1 = new Animation(0.1f, aniFrames);
	}
	
	private void initEnemy2(){
		enemy2 = new Texture(Gdx.files.internal(ENEMY2_IMAGE));
		enemy2Position = new Vector2(1000, 0);
		enemy2Box = new Rectangle();

		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, enemy2);
		animatingEnemy2 = new Animation(0.4f, aniFrames);
	}
	/**
	 * Will take in a spriteSheet image and return it as an Animation object.
	 * 
	 * @param int cols - Number of columns in spriteSheet
	 * @param int rows = Number of rows in spriteSheet
	 * @param Texture spriteSheet - The Texture object containing the spriteSheet
	 * @return
	 */
	private TextureRegion[] animateFromSpriteSheet(int cols, int rows, Texture spriteSheet){
		TextureRegion[][] tmpAniFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);
		TextureRegion[] aniFrames = new TextureRegion[cols * rows];
		
		int index = 0;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				aniFrames[index++] = tmpAniFrames[i][j];
		
		return aniFrames;
	}
	
	/**
	 * render()
	 * 
	 * A method that is called everytime the screen is rendered - basically everytime a frame is created.
	 * Includes the bulk of the game logic.
	 * 
	 * Important to take note of the Gdx.graphics.getDeltaTime() - this returns the amount of time passed between
	 * the last render call in float format.
	 */
	@Override
	public void render()
	{        
		
	    Gdx.gl.glClearColor(0, 159, 205, 0);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    float deltaTime = Gdx.graphics.getDeltaTime();
	    animationTime += deltaTime;
	    time += Gdx.graphics.getDeltaTime();
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
		batch.begin();
	 	
		renderInfiniteBackground();

		enemy1Position.x += enemy1Velocity.x * deltaTime;
		
		enemy2Box.set(enemy2Position.x, enemy2Position.y, 100, 100);
		mainChar.update(deltaTime, batch);

	 	batch.draw(walkingEnemy1.getKeyFrame(time, true), enemy1Position.x , enemy1Position.y, 100, 150);
	 	
	 	batch.draw(animatingEnemy2.getKeyFrame(time, true), enemy2Position.x , enemy2Position.y, 100, 100);
	 		
		batch.end();		
		
		camera.translate(200 * deltaTime, 0);
		gameOverConditions();
	}

	/**
	 * renderInfiniteBackground()
	 * 
	 * A helper method used to render an infinite background so when the game scrolls, backgrounds keep getting rendered.
	 * 
	 * We could essentially render 1000 backgrounds and there would be no need for this method, but this has an impact
	 * on performance. This method means only 3 backgrounds will be rendered at a given time!
	 * 
	 */
	private void renderInfiniteBackground(){
		if (mainChar.getX() > backgroundPositions.get(0) + level1BackgroundWidth && backgroundPositions.get(0) != 0){
			backgroundPositions.remove(0);
			backgroundPositions.add( backgroundPositions.get(backgroundPositions.size() - 1) + level1BackgroundWidth );
		} else if (backgroundPositions.get(0) == 0 
					&& mainChar.getX() > level1BackgroundWidth * 2) {
			backgroundPositions.remove(0);
			backgroundPositions.add( backgroundPositions.get(backgroundPositions.size() - 1) + level1BackgroundWidth );
		}
		
		for (int i = 0; i < backgroundPositions.size(); i++)
			batch.draw(level1Background, backgroundPositions.get(i), 0);
	}
	@Override
	public void dispose()
	{
	}
	
	@Override
	public void resize(int width, int height)
	{
		configureCamera();
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
	
	private void configureCamera(){
		if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth()) {
			actualWidth = actualHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualWidth, actualHeight);
		} else {
			actualWidth = actualHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualHeight, actualWidth);
		}
	}
	
	private void resetGame(){
		configureCamera();
		create();
	}
	private boolean gameOverConditions(){
		boolean isGameOver = false;
		
		if (mainChar.getBoundingBox().overlaps(enemy2Box))
			resetGame();
		
		return isGameOver;
	}
}