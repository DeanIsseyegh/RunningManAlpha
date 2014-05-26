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
import com.badlogic.gdx.math.Vector2;

public class LeftMan implements ApplicationListener
{
	Texture level1Background;
	final int level1BackgroundWidth = 1200;
	
	Texture enemy1;
	Animation walkingEnemy1;
	Vector2 enemy1Velocity;
	Vector2 enemy1Position;

	SpriteBatch batch;
	
	OrthographicCamera camera;
	
	Vector2 manVelocity;
	Vector2 manPosition;
	Vector2 manJumpVelocity;
	Texture walkingManSheet;
	Animation walkingManAnimation;
	
	float animationTime;
	float time;
	float hardCodedJumpHeight = 125;
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
		initBackground();
		initMan();
		initEnemy1();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		actualHeight = 800;
		configureCamera();
	}
	
	private void initBackground(){
		level1Background = new Texture(Gdx.files.internal("skybackground.png"));
		list1 = new ArrayList<Integer>(); 
		
		for (int i = 0; i < 3; i++)
			list1.add(level1BackgroundWidth * i);
		Log.v(TAG, "FIRST INDEX IS: " + list1.get(0));
	}
	
	/**
	 * initMan()
	 * 
	 * Private method responsible for setting up the main character sprite and animation.
	 * Also defines his starting position, walk speed, jump speed and sets the initial jump state to false (isMainInAir = false).
	 */
	private void initMan(){
		manPosition = new Vector2(0, 0);
		manVelocity = new Vector2(200, 0);
		manJumpVelocity = new Vector2(0, 300);
		isManInAir = false;
		
		walkingManSheet = new Texture(Gdx.files.internal("adjustedMan.png")); //608 x 240 pixels - 8 COLS, 2 ROWS
		int FRAME_COLS = 8;
		int FRAME_ROWS = 2;
		TextureRegion[][] tmpWalkMan = TextureRegion.split(walkingManSheet, walkingManSheet.getWidth() / FRAME_COLS, walkingManSheet.getHeight() / FRAME_ROWS);
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				walkFrames[index++] = tmpWalkMan[i][j];
		
		walkingManAnimation = new Animation(0.05f, walkFrames);
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
		enemy1 = new Texture(Gdx.files.internal("Turtles.png"));
		enemy1Velocity = new Vector2(-200, 0);
		enemy1Position = new Vector2(1000, 0);

		int FRAME_COLS = 5;
		int FRAME_ROWS = 1;
		TextureRegion[][] tmpWalkDuck = TextureRegion.split(enemy1, enemy1.getWidth() / FRAME_COLS, enemy1.getHeight() / FRAME_ROWS);
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				walkFrames[index++] = tmpWalkDuck[i][j];
	
		ArrayUtils.reverse(walkFrames);
		walkingEnemy1 = new Animation(0.1f, walkFrames);
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
	ArrayList<Integer> list1; 
	private static final String TAG = "MyActivity";
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
		if (Gdx.input.isTouched() && manPosition.y <= 0  && time > 1 || isManInAir == true){
			animationTime = 0;
			isManInAir = true;
			manJumpVelocity.y -= 300 * deltaTime;
			manPosition.y += manJumpVelocity.y * deltaTime;
			if (manPosition.y > hardCodedJumpHeight) 
				manJumpVelocity.y = -manJumpVelocity.y ;
		} 
		
		if (manPosition.y <= 0){
			manPosition.y = 0;
			manJumpVelocity.y = 300;
			isManInAir = false;
		}
		
		//Change character positions based on their velocity and deltaTime (time between last render call)
		manPosition.x += + manVelocity.x * deltaTime;
		enemy1Position.x += enemy1Velocity.x * deltaTime;
		
		//Draw the characters and 'animate' them - basically show their next frame based on time passed between last render call.
	 	batch.draw(walkingManAnimation.getKeyFrame(animationTime, true), manPosition.x , manPosition.y, 200, 200);
	 	batch.draw(walkingEnemy1.getKeyFrame(time, true), enemy1Position.x , enemy1Position.y, 150, 150);
	 	
	 	//If man goes off the screen, return to start of screen.
	 /*	if (manPosition.x > actualWidth) 
	 		manPosition.x = -200;*/
	 		
		batch.end();		
		
		camera.translate(200 * deltaTime, 0);
	}

	private void renderInfiniteBackground(){
		if (manPosition.x > list1.get(0) * 2 && list1.get(0) != 0){
			list1.remove(0);
			list1.add( list1.get(list1.size() - 1) + level1BackgroundWidth );
		} else if (list1.get(0) == 0) {
			if (manPosition.x > level1BackgroundWidth * 2)
				list1.remove(0);
				list1.add( list1.get(list1.size() - 1) + level1BackgroundWidth );
		}
		
		for (int i = 0; i < list1.size(); i++)
			batch.draw(level1Background, list1.get(i), 0);
	}
	@Override
	public void dispose()
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
	
}