package com.mygdx.runningman.worldobjects.projectiles;

public interface IProjectile {
	
	public void reflect();

	public boolean isReflected();

	public void setReflected(boolean isReflected);
	
	public void destroy();
}
