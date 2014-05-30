package com.mygdx.runningman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
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
	
	@Override
	public void create() {
		soundManager = new SoundManager();
		
		mainMenu = new MainMenu(this, soundManager);
		gameOverScreen = new GameOverScreen(this, soundManager);
		levelCompletedScreen = new LevelCompletedScreen(this, soundManager);
		
		level1 = new RunningManLevel1(this, soundManager);
		level2 = new RunningManLevel2(this, soundManager);
		
		setScreen(mainMenu);
		//soundManager.mute();
		//setScreen(level1);
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
