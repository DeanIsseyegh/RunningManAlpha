package com.mygdx.runningman.worldobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IWorldObject {
	
	static final String TAG = "MyActivity";
	static final String ENEMY1 = "Enemy1";
	static final String ENEMY2 = "Enemy2";
	static final String ENEMY3 = "Enemy3";
	static final String ENEMY4 = "Enemy4";
	
	static final String BG1_IMAGE = "skybackground.png";
	static final int BG1_WIDTH = 1200;
	
	static final String MAIN_CHAR_IMAGE = "adjustedMan.png";
	static final String MAIN_CHAR_WEAPON1 = "weapon1.png";
	
	static final String ENEMY1_IMAGE = "Enemy1.png";
	static final String ENEMY2_IMAGE = "Enemy2_1.png";
	
	static final String BOSS1_IMAGE = "BowserWalkingSpriteSheetFlipped.png";
	static final String BOSS1_SHOOT = "Boss1Shooting.png";
	static final String BOSS1_PROJECTILE = "Boss1Projectile1.png";
	static final String BOSS1_DYING = "Boss1Dying.png";
	
	static final String BLOOD_SPLAT = "bloodsplat.png";
	
	public void update(float deltaTime, SpriteBatch batch);
	
	public float getX();
	public float getY();
	
	public float getWidth();
	public float getHeight();
	
	public Rectangle getBoundingBox();
}
