package com.mygdx.runningman;

import com.badlogic.gdx.Game;
import com.mygdx.runningman.managers.SoundManager;

public class MainGame extends Game {

	MainMenu mainMenu;
	RunningManLevel1 level1;
	
	private SoundManager soundManager;
	
	@Override
	public void create() {
		soundManager = new SoundManager();
		mainMenu = new MainMenu(this, soundManager);
		level1 = new RunningManLevel1(this, soundManager);
		setScreen(mainMenu);
	}

}
