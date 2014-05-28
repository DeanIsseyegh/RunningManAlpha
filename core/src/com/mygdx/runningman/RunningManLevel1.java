package com.mygdx.runningman;

import java.util.ArrayList;

import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;

public class RunningManLevel1 extends AbstractRunningManListener  {
	
	private ArrayList<IEnemy> enemy1Array;
	private ArrayList<IEnemy> enemy2Array;
	
	public void create() {
		super.create();
		this.backgroundWidth = IWorldObject.BG1_WIDTH;
		initBackground(IWorldObject.BG1_IMAGE, backgroundWidth);
		
		enemy1Array = initRandomEnemies(IWorldObject.ENEMY1, 15, 600, 700);
		enemy2Array = initRandomEnemies(IWorldObject.ENEMY2, 10, 600, 700);
		
		arrayOfCharacters.addAll(enemy1Array);
		arrayOfCharacters.addAll(enemy2Array);
	
		collisionManager.setToLevel1State(enemy1Array, enemy2Array);
		soundManager.playLevel1Music();
	}
	
}