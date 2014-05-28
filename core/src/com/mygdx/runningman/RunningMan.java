package com.mygdx.runningman;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.apache.commons.lang3.RandomUtils;

import android.graphics.Matrix;
import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.Boss1;
import com.mygdx.runningman.worldobjects.characters.Enemy1;
import com.mygdx.runningman.worldobjects.characters.Enemy2;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.characters.MainCharacter;

public class RunningMan implements ApplicationListener
{
	private static final String TAG = "MyLog";
	private static final String BG1_IMAGE = "skybackground.png";
	
	private Texture level1Background;
	private final int level1BackgroundWidth = 1200;
	private ArrayList<Integer> backgroundPositions; 
	
	private SpriteBatch batch;
	
	private OrthographicCamera camera;
	
	private IWorldObject mainChar;
	private IEnemy boss1;
	private ArrayList<IEnemy> enemy1Array;
	private ArrayList<IEnemy> enemy2Array;
	private float posOfLastEnemy1;
	private float posOfLastEnemy2;
	
	private float time;
	private int actualScreenWidth;
	private int actualScreenHeight;
	private int scrollSpeed = 250;
	private long points;
	
	private boolean leftScreenTouched = false;
	private boolean rightScreenTouched = false;
	
	private boolean isGameOver = false;
	
	private CollisionManager collisionManager;
	private GameHUDManager gameHUDManager;
	private SoundManager soundManager;
	private BossFightManager bossFightManager;

	/**
	 * create()
	 * 
	 * Method that is called when instance of game is first created. Sets up camera, sprites, animations, background
	 * and batch.
	 */
	@Override
	public void create()
	{
		isGameOver = false;
		points = 0;
		time = 0;
		mainChar = new MainCharacter(scrollSpeed, this);
		
		enemy1Array = initRandomEnemies(IWorldObject.ENEMY1, 1, 600, 700);
		enemy2Array = initRandomEnemies(IWorldObject.ENEMY2, 1, 600, 700);
		initBackground();
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		actualScreenHeight = 800;
		configureCamera();
		
		Gdx.input.setInputProcessor(new GestureDetector(new RunningManControls(this))); //setup custom controls
		collisionManager = new CollisionManager();
		gameHUDManager = new GameHUDManager();
		soundManager = new SoundManager();
		bossFightManager = new BossFightManager(this, posOfLastEnemy1, posOfLastEnemy1, mainChar);
		
		soundManager.playLevel1Music();
	}
	
	
	/**
	 * initRandomEnemy1()
	 * 
	 * Creates an array of enemies and will generate them at a random position
	 * based on a RandomUtils.nextInt call.
	 * 
	 */
	private ArrayList<IEnemy> initRandomEnemies(String typeOfEnemy, int numOfEnemy, int minDistBetweenEnemies, int possibleMaxDistBetweenEnemies){
		ArrayList<IEnemy> arrayOfEnemies = new ArrayList<IEnemy>();
		int randomInt = 50;
		
		boolean isEnemy1 = (typeOfEnemy.equals(IWorldObject.ENEMY1));
		boolean isEnemy2 = (typeOfEnemy.equals(IWorldObject.ENEMY2));
		
		for (int i = 0; i < numOfEnemy; i++){
			randomInt = RandomUtils.nextInt(randomInt, randomInt + possibleMaxDistBetweenEnemies) + minDistBetweenEnemies; 
			if (isEnemy1)
				arrayOfEnemies.add(new Enemy1(randomInt, mainChar));
			else if (isEnemy2)
				arrayOfEnemies.add(new Enemy2(randomInt));
		}
		
		if (isEnemy1) posOfLastEnemy1 = arrayOfEnemies.get(arrayOfEnemies.size() - 1).getX();
		else if (isEnemy2) posOfLastEnemy2 = arrayOfEnemies.get(arrayOfEnemies.size() - 1).getX();
		
		return arrayOfEnemies;
	}

	
	/**
	 * initBackground()
	 * 
	 * Helper method to create an array of backgroundPositions (array of Integers) which 
	 * hold the x coordinates for where a background will be rendered. Note they are all spaced equally with a different
	 * of X. X = Widths of background in pixels
	 * 
	 */
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
	    time += deltaTime;
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		    
		batch.begin(); 
		 	
		renderInfiniteBackground();
			
		mainChar.update(deltaTime, batch);
		 	
		for (IWorldObject e : enemy1Array)
			e.update(deltaTime, batch);
	
		for (IWorldObject e : enemy2Array)
		 	e.update(deltaTime, batch);
	
		bossFightManager.handle(deltaTime, batch);
		 	
		batch.end();		
	
		camera.translate(scrollSpeed * deltaTime, 0);
			
		collisionManager.checkCollisions(mainChar, enemy1Array, enemy2Array, this);
			
		gameOverConditions();
			
		resetCustomControlState();
			
		points += deltaTime * 80;
		gameHUDManager.getPointsLabel().setText("Points: " + points);
		gameHUDManager.getStage().draw();
	    
	}
	
	/**
	 * resetCustomControlState()
	 * 
	 * A short helper method to reset touched screen states back to false.
	 * 
	 */
	private void resetCustomControlState(){
		leftScreenTouched = false;	
		rightScreenTouched = false;
	}

	/**
	 * renderInfiniteBackground()
	 * 
	 * A helper method used to render an infinite background so when the game scrolls, backgrounds keep getting rendered.
	 * 
	 * We could essentially render 1000 backgrounds and there would be no need for this method, but this has an impact
	 * on performance. This method means only 4 backgrounds will be rendered at a given time!
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
	public void dispose(){}

	@Override
	public void resize(int width, int height){}

	@Override
	public void pause(){}

	@Override
	public void resume(){}
	
	/**
	 * configureCamera()
	 * 
	 * Ensures camera is the correct side up depending on how the user is holding the phone.
	 * 
	 */
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
		soundManager.stopLevel1Music();
		configureCamera();
		create();
	}
	
	/**
	 * gameOverConditions()
	 * 
	 * Includes conditions for a game over. Usually when/if the main character collides with something
	 * 
	 */
	private void gameOverConditions(){
		if (isGameOver){
			soundManager.playDieSound();
			soundManager.destroyBoss1Resources();
			resetGame();
		}
	}
	
	//GETTERS AND SETTERS
	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
	public void setLeftScreenTouched(boolean leftScreenTouched) {
		this.leftScreenTouched = leftScreenTouched;
	}
	
	public boolean isLeftScreenTouched() {
		return leftScreenTouched;
	}
	
	public void setRightScreenTouched(boolean rightScreenTouched) {
		this.rightScreenTouched = rightScreenTouched;
	}
	
	public boolean isRightScreenTouched() {
		return rightScreenTouched;
	}
	
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	public CollisionManager getCollisionManager() {
		return collisionManager;
	}
	
	public GameHUDManager getGameHUD() {
		return gameHUDManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public BossFightManager getBossFightManager() {
		return bossFightManager;
	}
}