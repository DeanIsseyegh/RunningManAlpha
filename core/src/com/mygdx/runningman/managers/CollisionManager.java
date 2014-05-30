package com.mygdx.runningman.managers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.mygdx.runningman.AbstractRunningManListener;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.IEnemy;
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;
import com.mygdx.runningman.worldobjects.projectiles.Enemy5Projectile;
import com.mygdx.runningman.worldobjects.projectiles.Enemy5Projectile.E5ProjectileState;

public class CollisionManager {
	
	private LevelState state;
	
	private AbstractRunningManListener runningMan;
	private IWorldObject mainChar;
	private IWorldObject weapon1;
	
	private ArrayList<IEnemy> enemy1Array;
	private ArrayList<IEnemy> enemy2Array;
	private AbstractProjectile boss1Projectile1;
	private AbstractProjectile boss1Projectile2;
	
	private ArrayList<IEnemy> enemy3Array;
	private ArrayList<IEnemy> enemy4Array;
	private IEnemy enemy5;
	private AbstractProjectile enemy3Projectile;
	private Enemy5Projectile enemy5Projectile;
	
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
	
	public void setToLevel2State(ArrayList<IEnemy> enemy3Array, ArrayList<IEnemy> enemy4Array, IEnemy enemy5){
		this.state = LevelState.Level2;
		this.enemy3Array = enemy3Array;
		this.enemy4Array = enemy4Array;
		this.enemy5 = enemy5;
	}
	
	/**
	 * Main public method that is getting constantly called. It will check for collisions relevant to its current state (i.e. Level1 - Enemy1&2 and Boss1).
	 */
	public void handleCollisions(){
		switch(state){
		case Level1:
			checkLevel1Collisions();
			break;
		case Level2:
			checkLevel2Collisions();
			break;
		default:
			break;
		
		}
	}
	
	/**
	 * Handles relevant collisions for level 2.
	 * 
	 * Includes: Enemy4, Enemy5, Enemy5Projectile....etc.
	 */
	public void checkLevel2Collisions(){
		for (IEnemy e : enemy3Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox())){
				runningMan.setGameOver(true);
			} else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox())){
				e.kill();
				runningMan.setPoints(runningMan.getPoints() + 200);
			}
		}
		
		for (IEnemy e : enemy4Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox())){
				runningMan.setGameOver(true);
			} else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox())){
				e.kill();
				runningMan.setPoints(runningMan.getPoints() + 200);
			}
		}
		
		if (enemy3Projectile != null 
				&& mainChar.getBoundingBox().overlaps(enemy3Projectile.getBoundingBox()))
			runningMan.setGameOver(true);
			
		handleEnemy5Projectiles();
	}
		
	private void handleEnemy5Projectiles(){
		
		// Handle the projectile hitting the mainChar or his weapon
		if (enemy5Projectile != null 
				&& mainChar.getBoundingBox().overlaps(enemy5Projectile.getBoundingBox())){
			runningMan.setGameOver(true);
		} else if ( weapon1 != null 
				&& enemy5Projectile != null
				&& enemy5Projectile.getState() == E5ProjectileState.ORANGE
				&& weapon1.getBoundingBox().overlaps(enemy5Projectile.getBoundingBox())){
			enemy5Projectile.reflect(400);
		}	
		
		if ( enemy5Projectile != null 
				&& enemy5 != null
				&& enemy5Projectile.isReflected()
				&& enemy5Projectile.getBoundingBox().overlaps(enemy5.getBoundingBox()) ){
			enemy5.loseHealth();
			runningMan.getSoundManager().playEnemy5HurtSound();
			enemy5Projectile = null;
		}
	}
	
	/**
	 * Handles relevant collisions for level 1.
	 * 
	 * Includes: Enemy1, Enemy2 and Boss1....etc.
	 */
	public void checkLevel1Collisions(){
		
		for (IEnemy e : enemy1Array){
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox())){
				runningMan.setGameOver(true);
			} else if (weapon1 != null && weapon1.getBoundingBox().overlaps(e.getBoundingBox())){
				Gdx.input.vibrate(2);
				e.kill();
				runningMan.setPoints(runningMan.getPoints() + 200);
			}
		}
		
		for (IEnemy e : enemy2Array)
			if (mainChar.getBoundingBox().overlaps(e.getBoundingBox())){
				runningMan.setGameOver(true);
			}
		handleBoss1ProjectileCollisions(boss1Projectile1, runningMan.getBossFightManager().getBoss1());
		handleBoss1ProjectileCollisions(boss1Projectile2, runningMan.getBossFightManager().getBoss1());
	}
	
	/**
	 * A private helper method for handling projectile collisions for the first boss fight. Put in a separate private method
	 * as the boss can shoot up to two projectiles and can hurt himself if reflected, making the logic slightly more complex.
	 *  
	 * @param projectiles
	 * @param boss1
	 */
	private void handleBoss1ProjectileCollisions(AbstractProjectile projectiles, IEnemy boss1){
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

	public void setEnemy3Projectile(AbstractProjectile enemy3Projectile) {
		this.enemy3Projectile = enemy3Projectile;
		
	}
	
	public void setEnemy5Projectile(Enemy5Projectile enemy5Projectile) {
		this.enemy5Projectile = enemy5Projectile;
	}
}
