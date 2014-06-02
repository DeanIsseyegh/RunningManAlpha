package com.mygdx.runningman;

import java.util.ArrayList;

import com.mygdx.runningman.managers.GameHUDManager;
import com.mygdx.runningman.managers.SoundManager;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.characters.Enemy5;
import com.mygdx.runningman.worldobjects.characters.IEnemy;

public class RunningManLevel2 extends AbstractRunningManListener  {
	
	public RunningManLevel2(MainGame game, SoundManager soundManager) {
		super(game, soundManager);
	}

	private ArrayList<IEnemy> enemy3Array;
	private ArrayList<IEnemy> enemy4Array;
	private ArrayList<IWorldObject> bird1Array;
	
	private Level2State state;

	private int numOfEnemy3;
	private int numOfEnemy4;

	public void show() {
		System.out.println("Show called..");
		super.show();
		System.out.println("Super show called :)");
		this.backgroundWidth = IWorldObject.BG2_WIDTH;
		initBackground(IWorldObject.BG2_IMAGE, IWorldObject.BG2_FLOOR, backgroundWidth);
		
		numOfEnemy3 = 4;
		numOfEnemy4 = 4;
		enemy3Array = initRandomEnemies(IWorldObject.ENEMY3, numOfEnemy3, 3000, 0, 600);
		enemy4Array = initRandomEnemies(IWorldObject.ENEMY4, numOfEnemy4, 3000, 0, 1600);
		
		bird1Array = initRandomCharacters(IWorldObject.BIRD1, 15, 1600, 2000, 1100);
		arrayOfCharacters.addAll(enemy3Array);
		arrayOfCharacters.addAll(enemy4Array);
		arrayOfCharacters.addAll(bird1Array);
		IEnemy enemy5 = new Enemy5(mainChar, this);
		arrayOfCharacters.add(enemy5);
	
		collisionManager.setToLevel2State(enemy3Array, enemy4Array, enemy5);
		
		soundManager.initLevel2Resources();
		soundManager.playLevel2Music();
		
		state = Level2State.Part1;
	}
	
	/**
	 * Level has multiple states or 'parts' to them. Different from level 1 that pretty much only has one
	 * state with only a boss appearing and standard enemies following same patterns.
	 * 
	 * Note that this state does not change or affect logic in this particular class, but affects the position
	 * and attack behaviors of certain enemies.
	 * 
	 * @author DeanIsseyegh
	 */
	public enum Level2State{
		Part1,
		Part2;
	}
	
	public void enemy3Killed(){
		numOfEnemy3 = numOfEnemy3 -1;
	}
	
	public void enemy4Killed(){
		numOfEnemy4 = numOfEnemy4 - 1;
	}
	
	public Level2State getState() {
		return state;
	}
	public void setState(Level2State state) {
		this.state = state;
	}
	public int getNumOfEnemy3() {
		return numOfEnemy3;
	}
	public int getNumOfEnemy4() {
		return numOfEnemy4;
	}
}