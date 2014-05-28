package com.mygdx.runningman.worldobjects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public abstract class AbstractProjectile extends AbstractWorldObject implements IProjectile{
	
	private boolean isReflected = false;
	
	public AbstractProjectile(String spriteSheetPath) {
		super(spriteSheetPath);
	}
	
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
