package com.mygdx.runningman.worldobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IWorldObject {
	
	static final String TAG = "MyActivity";
	
	static final String MAIN_CHAR_IMAGE = "adjustedMan.png";
	static final String ENEMY2_IMAGE = "Enemy2_1.png";
	static final String ENEMY1_IMAGE = "Enemy1.png";
	static final String BG1_IMAGE = "skybackground.png";
	static final String MAIN_CHAR_WEAPON1 = "weapon1.png";
	static final String BLOOD_SPLAT = "bloodsplat.png";
	static final String BOSS1_IMAGE = "BowserWalkingSpriteSheetFlipped.png";
	
	public void update(float deltaTime, SpriteBatch batch);
	
	public float getX();
	public float getY();
	
	public float getWidth();
	public float getHeight();
	
	public Rectangle getBoundingBox();
}
