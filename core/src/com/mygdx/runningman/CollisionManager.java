package com.mygdx.runningman;

import java.util.ArrayList;

import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;

public class CollisionManager {
	
	private IWorldObject weapon1;
	private AbstractProjectile boss1Projectile1;
	private AbstractProjectile boss1Projectile2;

	public void checkCollisions(IWorldObject mainChar, ArrayList<IEnemy> enemy1Array, ArrayList<IEnemy> enemy2Array, RunningMan runningMan){
		
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
		
		handleBossCollisions(mainChar, runningMan.getBossFightManager().getBoss1(), runningMan);
	}
	
	private void handleBossCollisions(IWorldObject mainChar, IEnemy boss1, RunningMan runningMan){
		
		/* NOTE REFACTOR TO PUT PROJECTILES IN ARRAY TO AVOID COPY AND PASTE CODE! */
		
		if (boss1Projectile1 != null && mainChar.getBoundingBox().overlaps(boss1Projectile1.getBoundingBox())){
			runningMan.setGameOver(true);
		}
		
		if (boss1Projectile1 != null 
				&& weapon1 != null 
				&& weapon1.getBoundingBox().overlaps(boss1Projectile1.getBoundingBox())){
			boss1Projectile1.reflect();
		}
		
		if (boss1 != null 
				&& boss1Projectile1 != null
				&& boss1Projectile1.isReflected() 
				&& boss1.getBoundingBox().overlaps(boss1Projectile1.getBoundingBox())){
			boss1.loseHealth();
			boss1Projectile1.destroy();
			boss1Projectile1 = null;
		}
		
		////////////////////////////////////////////////////////////////////////////////
		
		if (boss1Projectile2 != null && mainChar.getBoundingBox().overlaps(boss1Projectile2.getBoundingBox())){
			runningMan.setGameOver(true);
		}
		
		if (boss1Projectile2 != null 
				&& weapon1 != null 
				&& weapon1.getBoundingBox().overlaps(boss1Projectile2.getBoundingBox())){
			boss1Projectile2.reflect();
		}
		
		if (boss1 != null 
				&& boss1Projectile2 != null
				&& boss1Projectile2.isReflected() 
				&& boss1.getBoundingBox().overlaps(boss1Projectile2.getBoundingBox())){
			boss1.loseHealth();
			boss1Projectile2.destroy();
			boss1Projectile2 = null;
		}
	}
	
	public void setWeapon1(IWorldObject weapon1) {
		this.weapon1 = weapon1;
	}
	
	public void setBoss1Projectile1(AbstractProjectile boss1Projectile1){
		this.boss1Projectile1 = boss1Projectile1;
	}
	
	public void setBoss1Projectile2(AbstractProjectile boss1Projectile2){
		this.boss1Projectile2 = boss1Projectile2;
	}
}
