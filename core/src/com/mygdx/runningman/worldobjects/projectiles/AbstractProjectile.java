package com.mygdx.runningman.worldobjects.projectiles;

import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public abstract class AbstractProjectile extends AbstractWorldObject{
	
	private boolean isReflected = false;
	
	public void reflect(){ 
		if (!isReflected){
			System.out.println("BEFORE velocity.x : " + velocity.x);
			velocity.x = -velocity.x + 600;
			velocity.y = -velocity.y;
			isReflected = true;
			System.out.println("REFLECTED velocity.x : " + velocity.x);
		} else {
			//doNothing
		}
	}

	public boolean isReflected() {
		return isReflected;
	}

	public void setReflected(boolean isReflected) {
		this.isReflected = isReflected;
	}

}
