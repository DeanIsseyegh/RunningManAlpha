package com.mygdx.runningman.worldobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IWorldObject {
	
	static final String TAG = "MyLog";
	static final String ENEMY1 = "Enemy1";
	static final String ENEMY2 = "Enemy2";
	static final String ENEMY3 = "Enemy3";
	static final String ENEMY4 = "Enemy4";
	static final String BIRD1  = "Bird1";
	
	static final String BG1_IMAGE = "levels/skybackground.png";
	static final String BG1_FLOOR = "levels/level1Floor.png";
	static final int BG1_WIDTH 	  = 1920;
	
	static final String BG2_IMAGE = "levels/level2Background.jpg";
	static final String BG2_FLOOR = "levels/level2Floor.png";
	static final int BG2_WIDTH 	  = 1600;
	
	static final String MAIN_CHAR_IMAGE	  = "adjustedMan.png";
	static final String MAIN_CHAR_WEAPON1 = "weapon1.png";
	
	static final String ENEMY1_IMAGE = "enemies/Enemy1.png";
	static final String ENEMY2_IMAGE = "enemies/Enemy2_1.png";
	static final String ENEMY3_IMAGE = "enemies/Enemy3.png";
	static final String ENEMY4_IMAGE = "enemies/Enemy4.png";
	static final String ENEMY5_IMAGE = "enemies/Enemy5.png";
	
	static final String BOSS1_IMAGE		 = "boss1/BowserWalkingSpriteSheetFlipped.png";
	static final String BOSS1_SHOOT 	 = "boss1/Boss1Shooting.png";
	static final String BOSS1_PROJECTILE = "boss1/Boss1Projectile1.png";
	static final String BOSS1_DYING 	 = "boss1/Boss1Dying.png";
	
	static final String BLOOD_SPLAT 	  		 = "bloodsplat.png";
	static final String METAL_SPLAT 	  		 = "MetalSplat2.png";
	static final String STARS					 = "StarCircle.png";
	static final String BIRD1_IMAGE 	 		 = "Bird1.png";
	static final String ENEMY3_PROJECTILE		 =	 "enemies/Enemy3_Projectile.png";
	static final String ENEMY5_PROJECTILE_RED	 = "enemies/Enemy5ProjectileRed.png";
	static final String ENEMY5_PROJECTILE_ORANGE = "enemies/Enemy5ProjectileOrange.png";
	
	public void update(float deltaTime, SpriteBatch batch);
	
	public float getX();
	public float getY();
	
	public float getWidth();
	public float getHeight();
	
	public Rectangle getBoundingBox();
	
	public void stopMoving();
	public void stopMovingAndAnimation();
	public void die();
	
	public void dispose();
}
