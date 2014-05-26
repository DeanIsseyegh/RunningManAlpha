package com.mygdx.runningman;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.apache.commons.lang3.RandomUtils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.runningman.characters.Enemy1;
import com.mygdx.runningman.characters.Enemy2;
import com.mygdx.runningman.characters.ICharacter;
import com.mygdx.runningman.characters.MainCharacter;

public class RunningMan implements ApplicationListener
{
	private static final String TAG = "RunningManActivity";
	private static final String BG1_IMAGE = "skybackground.png";
	
	private Texture level1Background;
	private final int level1BackgroundWidth = 1200;
	private ArrayList<Integer> backgroundPositions; 
	
	private SpriteBatch batch;
	
	private OrthographicCamera camera;
	
	private ICharacter mainChar;
	private ICharacter enemy1;
	private ICharacter enemy2;
	
	private float time;
	private int actualScreenWidth;
	private int actualScreenHeight;
	private int scrollSpeed = 250;
	private int points;
	private BitmapFont bitFont;
	/**
	 * create()
	 * 
	 * Method that is called when instance of game is first created. Sets up camera, sprites, animations, background
	 * and batch.
	 */
	@Override
	public void create()
	{
		bitFont = new BitmapFont();
		points = 0;
		mainChar = new MainCharacter(scrollSpeed);
		enemy1 = new Enemy1();
		initRandomEnemy2();
		initBackground();
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		actualScreenHeight = 800;
		configureCamera();
	}
	
	ArrayList<ICharacter> enemy2Array;
	private void initRandomEnemy2(){
		enemy2Array = new ArrayList<ICharacter>();
		
		int randomInt = 100;
		for (int i = 0; i < 30; i++){
			System.out.println(randomInt);
			randomInt = RandomUtils.nextInt(randomInt, randomInt + 600) + 600; 
			enemy2Array.add(new Enemy2(randomInt));
		}
	}
	
	private void initBackground(){
		level1Background = new Texture(Gdx.files.internal(BG1_IMAGE));
		backgroundPositions = new ArrayList<Integer>(); 
		
		for (int i = 0; i < 4; i++)
			backgroundPositions.add(level1BackgroundWidth * i);
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
	    time += Gdx.graphics.getDeltaTime();
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
	    
		batch.begin(); 
	 	
		renderInfiniteBackground();
		
		mainChar.update(deltaTime, batch);
	 	enemy1.update(deltaTime, batch);
	 	
	 	for (ICharacter e : enemy2Array)
	 		e.update(deltaTime, batch);
	 	
	 	points += 2 * time;
	 	bitFont.draw(batch, "Points: " + points, mainChar.getX() + 1300,  800);
		batch.end();		

		gameOverConditions();
		camera.translate(scrollSpeed * deltaTime, 0);
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
			actualScreenWidth = actualScreenHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualScreenWidth, actualScreenHeight);
		} else {
			actualScreenWidth = actualScreenHeight * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
			camera.setToOrtho(false, actualScreenHeight, actualScreenWidth);
		}
	}
	
	private void resetGame(){
		configureCamera();
		create();
	}
	private boolean gameOverConditions(){
		boolean isGameOver = false;
		
		for (ICharacter e : enemy2Array)
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				resetGame();
		
		/*if (mainChar.getBoundingBox().overlaps(enemy2.getBoundingBox())
			|| mainChar.getBoundingBox().overlaps(enemy1.getBoundingBox()))
			resetGame();*/
		
		return isGameOver;
	}
}