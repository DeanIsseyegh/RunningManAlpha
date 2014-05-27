package com.mygdx.runningman.worldobjects.characters;

import com.mygdx.runningman.worldobjects.IWorldObject;

public interface IEnemy extends IWorldObject{
	
	public void kill();
	public void loseHealth();
}
