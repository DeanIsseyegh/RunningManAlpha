package com.mygdx.runningman.worldobjects.projectiles;

import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public abstract class AbstractProjectile extends AbstractWorldObject{
	
	private boolean isReflected = false;
	
	public void reflect(){ 
		if (!isReflected){
			velocity.x = -velocity.x + 600;
			velocity.y = -velocity.y;
			isReflected = true;
		}
	}

	public boolean isReflected() {
		return isReflected;
	}

	public void setReflected(boolean isReflected) {
		this.isReflected = isReflected;
	}
	
	public void destroy(){
		position.x = -1;
		position.y = -1;
		velocity.x = 0;
		velocity.y = 0;
	}

}
