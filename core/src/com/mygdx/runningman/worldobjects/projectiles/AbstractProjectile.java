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
	
	/**
	 * If a projectile is reflected it will invert its speed speed/slow it up
	 * by the additionalSpeed parameter taken in.
	 * 
	 * @param additionalSpeed
	 */
	public void reflect(int additionalSpeed){ 
		if (!isReflected){
			velocity.x = -velocity.x + additionalSpeed;
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
	
	/**
	 * Ensures that the projectile does not interact with anything else. Note that
	 * the projectile should be set to null outside of this method to properly destroy itself,
	 * but this provides some defensive coding.
	 */
	public void destroy(){
		position.x = -1;
		position.y = -1;
		velocity.x = 0;
		velocity.y = 0;
	}

}
