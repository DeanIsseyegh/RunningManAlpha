package com.mygdx.runningman;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;

public class CollisionManager {
	
	private IWorldObject weapon1;
	private AbstractProjectile boss1Projectile;

	public void checkCollisions(IWorldObject mainChar, ArrayList<IEnemy> enemy1Array, ArrayList<IEnemy> enemy2Array, IEnemy boss1, RunningMan runningMan){
		
		for (IEnemy e : enemy1Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				runningMan.setGameOver(true);
			else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox())){
				e.kill();
				runningMan.setPoints(runningMan.getPoints() + 200);
			}
		}
		
		for (IEnemy e : enemy2Array)
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				runningMan.setGameOver(true);
	
		if (boss1Projectile != null && mainChar.getBoundingBox().overlaps(boss1Projectile.getBoundingBox())){
			//runningMan.setGameOver(true);
		}
		
		if (boss1Projectile != null 
				&& weapon1 != null 
				&& weapon1.getBoundingBox().overlaps(boss1Projectile.getBoundingBox())){
			boss1Projectile.reflect();
			System.out.println("COLLISION MANAGER HAS DETECTED IT**");
		}
		
		if (boss1 != null 
				&& boss1Projectile.isReflected() 
				&& boss1.getBoundingBox().overlaps(boss1Projectile.getBoundingBox())){
			boss1.loseHealth();
		}
	}
	
	
	public void setWeapon1(IWorldObject weapon1) {
		this.weapon1 = weapon1;
	}
	
	public void setBoss1Projectile(AbstractProjectile boss1Projectile){
		this.boss1Projectile = boss1Projectile;
	}
}
