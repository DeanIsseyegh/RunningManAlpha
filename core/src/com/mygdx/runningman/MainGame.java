package com.mygdx.runningman;

import com.badlogic.gdx.Game;
import com.mygdx.runningman.managers.GameHUDManager;
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.screens.GameOverScreen;
import com.mygdx.runningman.screens.LevelCompletedScreen;
import com.mygdx.runningman.screens.MainMenu;

public class MainGame extends Game {

	private MainMenu mainMenu;
	private GameOverScreen gameOverScreen;
	private LevelCompletedScreen levelCompletedScreen;
	
	private RunningManLevel1 level1;
	private RunningManLevel2 level2;
	
	private SoundManager soundManager;
	
	private long points;
	
	private GameLevel state;

	@Override
	public void create() {
		soundManager = new SoundManager();
		
		mainMenu = new MainMenu(this, soundManager);
		gameOverScreen = new GameOverScreen(this, soundManager);
		levelCompletedScreen = new LevelCompletedScreen(this, soundManager);
		
		level1 = new RunningManLevel1(this, soundManager);
		level2 = new RunningManLevel2(this, soundManager);
		
		//soundManager.mute();
		state = GameLevel.LEVEL1;
		
		setScreen(mainMenu);
	}

	public enum GameLevel{
		LEVEL1,
		LEVEL2
	}
	
	public GameLevel getNextLevel(){
		switch(state){
		case LEVEL1:
			return GameLevel.LEVEL2;
		default:
			return null;
		}
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	
	public GameOverScreen getGameOverScreen() {
		return gameOverScreen;
	}
	
	public LevelCompletedScreen getLevelCompletedScreen() {
		return levelCompletedScreen;
	}
	
	public RunningManLevel1 getLevel1() {
		return level1;
	}

	public RunningManLevel2 getLevel2() {
		return level2;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
}
