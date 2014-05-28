package com.mygdx.runningman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.Boss1;
import com.mygdx.runningman.worldobjects.characters.IEnemy;

public class BossFightManager {
	
	private IWorldObject mainChar;
	private IEnemy boss1;

	private RunningMan runningMan;
	private BossManagerState bossState;
	
	private float timeSinceBossStart;
	private float posOfLastEnemy1;
	private float posOfLastEnemy2;
	
	private boolean isBoss1Dead;
	private boolean isBoss2Dead;
	
	public BossFightManager(RunningMan runningMan, float posOfLastEnemy1, float posOfLastEnemy2, IWorldObject mainChar){
		this.runningMan = runningMan;
		this.posOfLastEnemy1 = posOfLastEnemy1;
		this.posOfLastEnemy2 = posOfLastEnemy2;
		this.mainChar = mainChar;
	
		bossState = BossManagerState.NoBossFight;
	}
	
	public void handle(float deltaTime, SpriteBatch batch){
		
		switch(bossState){
		
			case NoBossFight: 
				noBossFightLogic();
				break;
				
			case BossFight1:
				bossFight1Logic(deltaTime, batch);
				break;
				
			default: break;
		}	
		
	}
	
	public enum BossManagerState{
		NoBossFight,
		BossFight1,
		BossFight2;
	}
	
	private void noBossFightLogic(){
		if (mainChar.getX() > posOfLastEnemy1 
				&& mainChar.getX() > posOfLastEnemy2 
				&& bossState == BossManagerState.NoBossFight
				&& !isBoss1Dead){
			runningMan.getSoundManager().stopLevel1Music();
		}
		
		if (mainChar.getX() + -1200 > posOfLastEnemy1 
				&& mainChar.getX() - 1600> posOfLastEnemy2 
				&& !isBoss1Dead){
			runningMan.getGameHUD().showBossLabel();
			timeSinceBossStart = 0;
			boss1 = new Boss1(mainChar.getX() + 1000, runningMan);
			
			bossState = BossManagerState.BossFight1;
	 	}	
	}
	
	private void bossFight1Logic(float deltaTime, SpriteBatch batch){
		timeSinceBossStart += deltaTime;
		if (timeSinceBossStart > 4f)
			runningMan.getGameHUD().removeBossLabel();
			
		boss1.update(deltaTime, batch);
		if (boss1.getX() > mainChar.getX() + Gdx.graphics.getWidth() + (Gdx.graphics.getWidth() * 0.05f)){
			boss1 = null;
			runningMan.getSoundManager().destroyBoss1Resources();
			isBoss1Dead = true;
			
			bossState = BossManagerState.NoBossFight;
		}
	}
	
	public IEnemy getBoss1(){
		return boss1;
	}
	
}
