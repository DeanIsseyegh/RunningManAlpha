package com.mygdx.runningman.managers;

import java.util.ArrayList;

import android.util.Log;

import com.mygdx.runningman.AbstractRunningManListener;
import com.mygdx.runningman.RunningManLevel1;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;
import com.mygdx.runningman.worldobjects.projectiles.IProjectile;

public class CollisionManager {
	
	private LevelState state;
	
	private AbstractRunningManListener runningMan;
	private IWorldObject mainChar;
	private IWorldObject weapon1;
	
	private ArrayList<IEnemy> enemy1Array;
	private ArrayList<IEnemy> enemy2Array;

	private AbstractProjectile boss1Projectile1;
	private AbstractProjectile boss1Projectile2;
	
	public CollisionManager(AbstractRunningManListener runningMan, IWorldObject mainChar){
		this.runningMan = runningMan;
		this.mainChar = mainChar;
	}
	
	public enum LevelState{
		Level1,
		Level2;
	}
	
	public void setToLevel1State(ArrayList<IEnemy> enemy1Array, ArrayList<IEnemy> enemy2Array){
		this.state = LevelState.Level1;
		this.enemy1Array = enemy1Array;
		this.enemy2Array = enemy2Array;
	}
	
	public void handleCollisions(){
		switch(state){
		case Level1:
			checkLevel1Collisions();
			break;
		case Level2:
			//DoSTUFF
			break;
		default:
			break;
		
		}
	}
	
	public void checkLevel1Collisions(){
		
		for (IEnemy e : enemy1Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox())){
				runningMan.setGameOver(true);
			} else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox())){
				e.kill();
				runningMan.setPoints(runningMan.getPoints() + 200);
			}
		}
		
		for (IEnemy e : enemy2Array)
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox()))
				runningMan.setGameOver(true);
		
		handleBossProjectileCollisions(boss1Projectile1, runningMan.getBossFightManager().getBoss1());
		handleBossProjectileCollisions(boss1Projectile2, runningMan.getBossFightManager().getBoss1());
	}
	
	private void handleBossProjectileCollisions(AbstractProjectile projectiles, IEnemy boss1){
		if (projectiles != null && mainChar.getBoundingBox().overlaps(projectiles.getBoundingBox())){
			runningMan.setGameOver(true);
		}
		
		if (projectiles != null 
				&& weapon1 != null 
				&& weapon1.getBoundingBox().overlaps(projectiles.getBoundingBox())){
			projectiles.reflect(runningMan.getScrollSpeed() + 350);
		}
		
		if (boss1 != null 
				&& projectiles != null
				&& projectiles.isReflected() 
				&& boss1.getBoundingBox().overlaps(projectiles.getBoundingBox())){
			boss1.loseHealth();
			projectiles.destroy();
			if (projectiles == boss1Projectile1)
				boss1Projectile1 = null;
			else 
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
