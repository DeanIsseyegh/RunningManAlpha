package com.mygdx.runningman;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.apache.commons.lang3.RandomUtils;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.runningman.managers.BossFightManager;
import com.mygdx.runningman.managers.CollisionManager;
import com.mygdx.runningman.managers.GameHUDManager;
import com.mygdx.runningman.managers.RunningManControls;
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.Enemy1;
import com.mygdx.runningman.worldobjects.characters.Enemy2;
import com.mygdx.runningman.worldobjects.characters.Enemy3;
import com.mygdx.runningman.worldobjects.characters.Enemy4;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.characters.MainCharacter;
import com.mygdx.runningman.worldobjects.characters.npc.Bird1;

public abstract class AbstractRunningManListener implements Screen
{
	public static final String TAG = "MyLog";
	
	private Texture background;
	protected int backgroundWidth;
	private ArrayList<Integer> backgroundPositions; 
	
	private SpriteBatch batch;
	
	private OrthographicCamera camera;
	
	protected IWorldObject mainChar;
	protected float posOfLastEnemy;
	protected ArrayList<IWorldObject> arrayOfCharacters;
	
	private float time;
	private int actualScreenWidth;
	private int actualScreenHeight = 800;
	protected int scrollSpeed = 250;

	private long points;
	
	private boolean leftScreenTouched = false;
	private boolean rightScreenTouched = false;
	
	private boolean isGameOver = false;
	
	protected CollisionManager collisionManager;
	protected GameHUDManager gameHUDManager;
	protected SoundManager soundManager;
	protected BossFightManager bossFightManager;

	private MainGame game;
	
	public AbstractRunningManListener(MainGame game, SoundManager soundManager){
		this.game = game;
		this.soundManager = soundManager;
	}
	
	/**
	 * show()
	 * 
	 * Method that is called when instance of game is first created. Sets up camera, sprites, animations, background
	 * and batch.
	 */
	@Override
	public void show()
	{
		Log.v(TAG, "Game created. . .");
		isGameOver = false;
		points = 0;
		time = 0;
		
		mainChar = new MainCharacter(scrollSpeed, this);
		arrayOfCharacters = new ArrayList<IWorldObject>();
		arrayOfCharacters.add(mainChar);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		configureCamera();
		
		Gdx.input.setInputProcessor(new GestureDetector(new RunningManControls(this))); //setup custom controls
		collisionManager = new CollisionManager(this, mainChar);
		bossFightManager = new BossFightManager(this, mainChar);
		gameHUDManager = new GameHUDManager();
	}
	
	
	/**
	 * initRandomEnemy1()
	 * 
	 * Creates an array of enemies and will generate them at a random position
	 * based on a RandomUtils.nextInt call.
	 * 
	 */
	protected ArrayList<IEnemy> initRandomEnemies(String typeOfEnemy, int numOfEnemy, int minDistBetweenEnemies, int possibleMaxDistBetweenEnemies, int initialStartingMinDistance){
		ArrayList<IEnemy> arrayOfEnemies = new ArrayList<IEnemy>();
		int randomInt = initialStartingMinDistance;
		
		boolean isEnemy1 = (typeOfEnemy.equals(IWorldObject.ENEMY1));
		boolean isEnemy2 = (typeOfEnemy.equals(IWorldObject.ENEMY2));
		boolean isEnemy3 = (typeOfEnemy.equals(IWorldObject.ENEMY3));
		boolean isEnemy4 = (typeOfEnemy.equals(IWorldObject.ENEMY4));
		
		for (int i = 0; i < numOfEnemy; i++){
			randomInt = RandomUtils.nextInt(randomInt, randomInt + possibleMaxDistBetweenEnemies) + minDistBetweenEnemies; 
			if (isEnemy1)
				arrayOfEnemies.add(new Enemy1(randomInt, mainChar));
			else if (isEnemy2)
				arrayOfEnemies.add(new Enemy2(randomInt));
			else if (isEnemy3)
				arrayOfEnemies.add(new Enemy3(randomInt));
			else if (isEnemy4)
				arrayOfEnemies.add(new Enemy4(randomInt));
		}
		
		
		if (!isEnemy1) posOfLastEnemy = arrayOfEnemies.get(arrayOfEnemies.size() - 1).getX();
		bossFightManager.setPosOfLastEnemy(posOfLastEnemy);
		return arrayOfEnemies;
	}
	
	protected ArrayList<IWorldObject> initRandomCharacters(String typeOfChar, int numOfChars, int minDistBetweenEnemies, int possibleMaxDistBetweenEnemies, int initialStartingMinDistance){
		int randomInt = initialStartingMinDistance;
		ArrayList<IWorldObject> arrayOfChars = new ArrayList<IWorldObject>();
		
		boolean isBird1 = (typeOfChar.equals(IWorldObject.BIRD1));
		
		for (int i = 0; i < numOfChars; i++){
			randomInt = RandomUtils.nextInt(randomInt, randomInt + possibleMaxDistBetweenEnemies) + minDistBetweenEnemies; 
			if (isBird1)
				arrayOfChars.add(new Bird1(randomInt));
		}
		
		return arrayOfChars;
	}
	
	/**
	 * initBackground()
	 * 
	 * Helper method to create an array of backgroundPositions (array of Integers) which 
	 * hold the x coordinates for where a background will be rendered. Note they are all spaced equally with a different
	 * of X. X = Widths of background in pixels
	 * 
	 */
	protected void initBackground(String backgroundImagePath, int backgroundWidth){
		background = new Texture(Gdx.files.internal(backgroundImagePath));
		backgroundPositions = new ArrayList<Integer>(); 
		
		for (int i = 0; i < 4; i++)
			backgroundPositions.add(backgroundWidth * i);
	}
	
	/**
	 * render()
	 * 
	 * A method that is called everytime the screen is rendered - basically everytime a frame is created.
	 * Includes the bulk of the game logic.
	 * 
	 * Important to take note of the Gdx.graphics.getDeltaTime() - this returns the amount of time passed between
	 * the last render call in float format.
	 * 
	 * @param deltaTime - time between each call of this method
	 */
	@Override
	public void render(float deltaTime)
	{        
	    Gdx.gl.glClearColor(0, 159, 205, 0);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    time += deltaTime;
	    camera.update();
		batch.setProjectionMatrix(camera.combined);
		    
		batch.begin(); 
		 	
		renderInfiniteBackground(batch);
	
		for (IWorldObject e : arrayOfCharacters)
		 	e.update(deltaTime, batch);
	
		bossFightManager.handle(deltaTime, batch);
		 	
		batch.end();		
	
		camera.translate(scrollSpeed * deltaTime, 0);
		
		collisionManager.handleCollisions();
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
	private void renderInfiniteBackground(SpriteBatch batch){
		if (mainChar.getX() > backgroundPositions.get(0) + backgroundWidth && backgroundPositions.get(0) != 0){
			backgroundPositions.remove(0);
			backgroundPositions.add( backgroundPositions.get(backgroundPositions.size() - 1) + backgroundWidth );
		} else if (backgroundPositions.get(0) == 0 
					&& mainChar.getX() > backgroundWidth * 2) {
			backgroundPositions.remove(0);
			backgroundPositions.add( backgroundPositions.get(backgroundPositions.size() - 1) + backgroundWidth );
		}
		
		for (int i = 0; i < backgroundPositions.size(); i++)
			batch.draw(background, backgroundPositions.get(i), 0);
	}
	
	@Override
	public void dispose(){}

	@Override
	public void resize(int width, int height){
		Log.v(TAG, "Gametime: " + time + " Game resized. . . ");
	}

	@Override
	public void pause(){
		Log.v(TAG, "Gametime: " + time + " Game paused. . .");
	}

	@Override
	public void resume(){
		Log.v(TAG, "Gametime: " + time + " Game resumed. . .");
	}
	
	@Override
	public void hide(){
		Log.v(TAG, "Gametime: " + time + " Game hiding. . .");
		batch.dispose();
		background.dispose();
	}
	
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
		game.setScreen(game.mainMenu);
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
	
	public int getScrollSpeed() {
		return scrollSpeed;
	}

	public void setScrollSpeed(int scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
	}
}