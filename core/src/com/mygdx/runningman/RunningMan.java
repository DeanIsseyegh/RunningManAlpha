package com.mygdx.runningman;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.apache.commons.lang3.RandomUtils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.runningman.characters.Boss1;
import com.mygdx.runningman.characters.Enemy1;
import com.mygdx.runningman.characters.Enemy2;
import com.mygdx.runningman.characters.IEnemy;
import com.mygdx.runningman.characters.MainCharacter;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class RunningMan implements ApplicationListener
{
	private static final String BG1_IMAGE = "skybackground.png";
	
	private Texture level1Background;
	private final int level1BackgroundWidth = 1200;
	private ArrayList<Integer> backgroundPositions; 
	
	private SpriteBatch batch;
	
	private CollisionManager collisionManager;
	
	private OrthographicCamera camera;
	
	private IWorldObject mainChar;
	private IWorldObject boss1;
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
	private boolean isBossFight = false;
	private float timeSinceBossStart;
	
	private GameHUDManager gameHUDManager;
	private SoundManager soundManager;

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
		
		initRandomEnemy1();
		initRandomEnemy2();
		initBackground();
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		actualScreenHeight = 800;
		configureCamera();
		
		collisionManager = new CollisionManager();
		Gdx.input.setInputProcessor(new GestureDetector(new RunningManControls(this)));
		gameHUDManager = new GameHUDManager();
		soundManager = new SoundManager();
		soundManager.playLevel1Music();
	}
	
	private void initRandomEnemy1(){
		enemy1Array = new ArrayList<IEnemy>();
		
		int randomInt = 250;
		int numOfEnemy = 10;
		for (int i = 0; i < numOfEnemy; i++){
			randomInt = RandomUtils.nextInt(randomInt, randomInt + 610) + 600; 
			enemy1Array.add(new Enemy1(randomInt, mainChar));
		}
		posOfLastEnemy1 = enemy1Array.get(enemy1Array.size() - 1).getX();
	}
	
	private void initRandomEnemy2(){
		enemy2Array = new ArrayList<IEnemy>();
		
		int randomInt = 250;
		int numOfEnemy = 10;
		for (int i = 0; i < numOfEnemy; i++){
			randomInt = RandomUtils.nextInt(randomInt, randomInt + 600) + 600; 
			enemy2Array.add(new Enemy2(randomInt));
		}
		posOfLastEnemy2 = enemy2Array.get(enemy2Array.size() - 1).getX();
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

	 	bossFightHandle(deltaTime);
	 	
		batch.end();		

		camera.translate(scrollSpeed * deltaTime, 0);
		
		collisionManager.checkCollisions(mainChar, enemy1Array, enemy2Array, this);
		
		gameOverConditions();
		
		resetCustomControlState();
		
	 	points += deltaTime * 80;
	 	gameHUDManager.getPointsLabel().setText("Points: " + points);
	 	gameHUDManager.getStage().draw();
	}
	
	private void bossFightHandle(float deltaTime){
		
		if (mainChar.getX() + 1200 > posOfLastEnemy1 && mainChar.getX() + 1200> posOfLastEnemy2 && !isBossFight){
			gameHUDManager.showBossLabel();
			isBossFight = true;
			timeSinceBossStart = 0;
			boss1 = new Boss1(mainChar.getX() + 1000);
	 	}	
		
		
		if (isBossFight){
			timeSinceBossStart += deltaTime;
			if (timeSinceBossStart > 4f)
				gameHUDManager.removeBossLabel();
			
			boss1.update(deltaTime, batch);
		}
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
}