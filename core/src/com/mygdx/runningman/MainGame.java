package com.mygdx.runningman;

import com.badlogic.gdx.Game;
import com.mygdx.runningman.managers.SoundManager;

public class MainGame extends Game {

	private MainMenu mainMenu;
	private RunningManLevel1 level1;
	private RunningManLevel2 level2;
	
	private SoundManager soundManager;
	
	@Override
	public void create() {
		soundManager = new SoundManager();
		
		mainMenu = new MainMenu(this, soundManager);
		level1 = new RunningManLevel1(this, soundManager);
		level2 = new RunningManLevel2(this, soundManager);
		
		setScreen(mainMenu);
		//setScreen(level2);
	}

	
	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public RunningManLevel1 getLevel1() {
		return level1;
	}

	public RunningManLevel2 getLevel2() {
		return level2;
	}
}
