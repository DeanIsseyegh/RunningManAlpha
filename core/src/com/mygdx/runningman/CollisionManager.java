package com.mygdx.runningman;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runningman.characters.IEnemy;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class CollisionManager {
	
	private IWorldObject weapon1;

	public void checkCollisions(IWorldObject mainChar, ArrayList<IEnemy> enemy1Array, ArrayList<IEnemy> enemy2Array, RunningMan runningMan){
		
		for (IEnemy e : enemy1Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				runningMan.setGameOver(true);
			else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox()))
				e.kill();
		}
		
		for (IEnemy e : enemy2Array)
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				runningMan.setGameOver(true);
	}
	
	
	public void setWeapon1(IWorldObject weapon1) {
		this.weapon1 = weapon1;
	}
}
