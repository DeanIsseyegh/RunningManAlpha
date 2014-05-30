package com.mygdx.runningman;

import java.util.ArrayList;

import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;

public class RunningManLevel1 extends AbstractRunningManListener  {
	
	public RunningManLevel1(MainGame game, SoundManager soundManager) {
		super(game, soundManager);
	}

	private ArrayList<IEnemy> enemy1Array;
	private ArrayList<IEnemy> enemy2Array;
	private ArrayList<IWorldObject> bird1Array;
	
	public void show() {
		super.show();
		this.backgroundWidth = IWorldObject.BG1_WIDTH;
		initBackground(IWorldObject.BG1_IMAGE, IWorldObject.BG1_FLOOR, backgroundWidth);
		
		int numOfEnemy1 = 12;
		int numOfEnemy2 = 8;
		enemy1Array = initRandomEnemies(IWorldObject.ENEMY1, numOfEnemy1, 600, 700, 200);
		enemy2Array = initRandomEnemies(IWorldObject.ENEMY2, numOfEnemy2, 600, 700, 200);
		
		bird1Array = initRandomCharacters(IWorldObject.BIRD1, 15, 1600, 2000, 1100);
		arrayOfCharacters.addAll(enemy1Array);
		arrayOfCharacters.addAll(enemy2Array);
		arrayOfCharacters.addAll(bird1Array);
	
		collisionManager.setToLevel1State(enemy1Array, enemy2Array);
		soundManager.playLevel1Music();
	}
	
}