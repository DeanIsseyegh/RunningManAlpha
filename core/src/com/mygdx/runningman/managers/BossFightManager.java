package com.mygdx.runningman.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.runningman.AbstractRunningManListener;
import com.mygdx.runningman.RunningManLevel1;
import com.mygdx.runningman.RunningManLevel2;
import com.mygdx.runningman.MainGame.GameLevel;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.Boss1;
import com.mygdx.runningman.worldobjects.characters.IEnemy;

public class BossFightManager {
	
	private IWorldObject mainChar;
	private IEnemy boss1;

	private AbstractRunningManListener runningMan;
	private BossManagerState bossState;
	
	private float timeSinceBossStart;
	private float posOfLastEnemy;

	private boolean isBoss1Dead;
	private boolean isBoss2Dead;
	
	public BossFightManager(AbstractRunningManListener runningMan, IWorldObject mainChar){
		this.runningMan = runningMan;
		this.mainChar = mainChar;
	
		bossState = BossManagerState.NoBossFight;
	}

	/**
	 * Handle boss states and what occurs during them. Note that switch statement takes in BossManagerState enums.
	 * 
	 * @param deltaTime
	 * @param batch
	 */
	public void handle(float deltaTime, SpriteBatch batch){
		switch(bossState){
		
			case NoBossFight: 
				noBossFightLogic();
				break;
				
			case BossFight1:
				bossFight1Logic(deltaTime, batch);
				break;
				
			case BossFight2:
				bossFight2Logic(deltaTime, batch);
				break;
				
			default: break;
		}	
	}

	public enum BossManagerState{
		NoBossFight,
		BossFight1,
		BossFight2;
	}
	
	/**
	 * Handles the no boss state - will trigger boss fights (basically set state to a bossfight) if certain
	 * conditions are met.
	 */
	private void noBossFightLogic(){
	
		if (runningMan instanceof RunningManLevel1) 
			noBossFightLogicLevel1();
		else if (runningMan instanceof RunningManLevel2)
			noBossFightLogicLevel2();
	}
	
	/**
	 * Handles the boss fight logic for level1
	 */
	private void noBossFightLogicLevel1(){
		if (mainChar.getX() > posOfLastEnemy
				&& bossState == BossManagerState.NoBossFight
				&& !isBoss1Dead){
			runningMan.getSoundManager().stopLevel1Music();
		}
		
		if (mainChar.getX() > posOfLastEnemy + Gdx.graphics.getWidth()
				&& !isBoss1Dead){
			runningMan.getGameHUD().showBossLabel();
			timeSinceBossStart = 0;
			
			boss1 = new Boss1(mainChar.getX() + 1000, runningMan);
			bossState = BossManagerState.BossFight1;
	 	}
	}
	
	/**
	 * Handles the boss fight logic for level2
	 */
	private void noBossFightLogicLevel2(){
		//EMPTY FOR NOW
	}
	
	private void bossFight2Logic(float deltaTime, SpriteBatch batch) {
		//EMPTY FOR NOW
	}
	
	/**
	 * Handles the first boss fight conditions. Adds/removes the
	 * "Boss Incoming" label and handles music resources.
	 * 
	 * Also sets boss1 to null when boss fight is over.
	 * 
	 * @param deltaTime
	 * @param batch
	 */
	private void bossFight1Logic(float deltaTime, SpriteBatch batch){
		timeSinceBossStart += deltaTime;
		if (timeSinceBossStart > 4f)
			runningMan.getGameHUD().removeBossLabel();
			
		boss1.update(deltaTime, batch);
		
		if (boss1.getX() > mainChar.getX() + Gdx.graphics.getWidth() + (Gdx.graphics.getWidth() * 0.05f) && !runningMan.isGameOver()){
			boss1 = null;
			runningMan.getSoundManager().destroyLevelResources();
			isBoss1Dead = true;
			
			bossState = BossManagerState.NoBossFight;
			runningMan.getGame().setPoints(runningMan.getPoints());
			runningMan.getGame().setScreen(runningMan.getGame().getLevelCompletedScreen());
		}
	}
	
	public IEnemy getBoss1(){
		return boss1;
	}
	
	public void setPosOfLastEnemy(float posOfLastEnemy) {
		this.posOfLastEnemy = posOfLastEnemy;
	}
}
