package com.mygdx.runningman.worldobjects.projectiles;

public interface IProjectile {
	
	public void reflect(int additionalSpeed);

	public boolean isReflected();

	public void setReflected(boolean isReflected);
	
	public void destroy();
}
