package com.mygdx.runningman.worldobjects.characters;

import org.apache.commons.lang3.RandomUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.RunningMan;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;
import com.mygdx.runningman.worldobjects.projectiles.BloodSplatProjectile;
import com.mygdx.runningman.worldobjects.projectiles.Boss1Projectile;

public class Boss1 extends AbstractWorldObject implements IEnemy {

	private RunningMan runningMan;
	
	private Animation walkingAnimation;
	private Animation shootAnimation;
	private Animation dyingAnimation;
	
	private int dyingWidth;
	private int dyingHeight;
	
	private AbstractProjectile projectile1;
	private AbstractProjectile projectile2;
	private AbstractProjectile bloodSplat;
	
	private boolean hasBossLanded;
	private boolean isShootingAnimation;
	private boolean shouldFireVelocityY;
	private boolean isDead;
	
	private float timeSinceLastShoot;
	private float shootingAnimationTime;
	
	//States
	private final int HP_10 = 10;
	private final int HP_09 = 9;
	private final int HP_08 = 8;
	private final int HP_07 = 7;
	private final int HP_06 = 6;
	private final int HP_05 = 5;
	private final int HP_04 = 4;
	private final int HP_03 = 3;
	private final int HP_02 = 2;
	private final int HP_01 = 1;
	private final int HP_00 = 0;
	
	private final float towardsFeetProjectileY = -50;
	private final float towardsHeadProjectileY = 50;
	private final float slowProjectileSpeedX = -350f;
	private final float fastProjectileSpeedX = -550f;
	
	private int currentBossHealth;
	
	public Boss1(float posX, RunningMan runningMan){
		runningMan.getSoundManager().initBoss1Resources();
		this.runningMan = runningMan;
		currentBossHealth = 10;
		spriteSheet = new Texture(Gdx.files.internal(BOSS1_IMAGE)); //actual 127px wide 88px high
		velocity = new Vector2(250, -400);
		position = new Vector2(posX, 1000);
		boundsBox = new Rectangle();
		int scaleFactor = 4;
		width = 127 * scaleFactor;
		height = 86 * scaleFactor;
		numOfXEmptyPxls = 8 * scaleFactor;
		int FRAME_COLS = 16;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
		walkingAnimation = animation;
		
		spriteSheet = new Texture(Gdx.files.internal(BOSS1_SHOOT));
		FRAME_COLS = 9;
		FRAME_ROWS = 1;
		aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		shootAnimation = new Animation(0.1f, aniFrames);
		
		spriteSheet = new Texture(Gdx.files.internal(BOSS1_DYING));
		FRAME_COLS = 11;
		FRAME_ROWS = 1;
		aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		dyingAnimation = new Animation(0.1f, aniFrames);
		dyingWidth = 128 * scaleFactor;
		dyingHeight = 100 * scaleFactor;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		
		time += deltaTime;
		timeSinceLastShoot += deltaTime;
		position.y += velocity.y * deltaTime;
		position.x += velocity.x * deltaTime;
		boundsBox.set(position.x + width/2, position.y, width, height);
		
		if (!isDead){ 
			if (timeSinceLastShoot > 2.35f || isShootingAnimation){
				isShootingAnimation = true;
				shootingAnimationTime += deltaTime;
				batch.draw(shootAnimation.getKeyFrame(shootingAnimationTime, false), position.x , position.y, width, height);
				
				if (shootAnimation.isAnimationFinished(shootingAnimationTime)){
					isShootingAnimation = false;
					shootingAnimationTime = 0;
				}
			} else		
				batch.draw(walkingAnimation.getKeyFrame(time, true), position.x , position.y, width, height);
		} else {
			if (bloodSplat instanceof BloodSplatProjectile) ((BloodSplatProjectile) bloodSplat).initMultipleSplats();
			batch.draw(dyingAnimation.getKeyFrame(time, false), position.x , position.y, dyingWidth, dyingHeight);
		}
		
		//Handle boss states
		switch(currentBossHealth){
		case HP_10: 
		case HP_09: 
		case HP_08: 
		case HP_07: firstBossState(deltaTime, batch);
					break;
		case HP_06: 
		case HP_05: 
		case HP_04: secondBossState(deltaTime, batch);
					break;
		case HP_03: 
		case HP_02: 
		case HP_01: thirdBossState(deltaTime, batch);
					break;
		case HP_00: kill();
					break;
		default: break;
		}
		
		//Handle Projectiles
		if (projectile1 != null)
			projectile1.update(deltaTime, batch);
		if (projectile2 != null)
			projectile2.update(deltaTime, batch);
		
		//Handle boss landing from sky and make sure he doesn't go below ground
		if (position.y <= 0){
			if (!hasBossLanded){
				runningMan.getSoundManager().playBoss1LandedSound();
				runningMan.getSoundManager().playBoss1Music();
			}
			hasBossLanded = true;
			position.y = 0;
			velocity.y = 0;
		}
		
		//Handle blood splat animation/projectiles
		if (bloodSplat != null){
			bloodSplat.update(deltaTime, batch);
			if (bloodSplat.getY() < -50 && !isDead)
				bloodSplat = null;
		}
		
	}
	
	/**
	 * firstBossStates()
	 * 
	 * This the first bosses state. Should go into his "shooting" animation every 2.35 seconds, and then produce a projectile1
	 * at 3 seconds.
	 * 
	 * Every time he shoots a random int is produce (0 or 1) using the ApacheCommons RandomUtils class.
	 * 
	 * If the int is a 1, then he will shoot a slow moving projectile that heads down towards the main character. The only
	 * way he can avoid this is to jump.
	 * 
	 * If the int is a 0, then he will shoot a fast moving projectile towards the main character which he must deflect using his
	 * sword.
	 * 
	 */
	private void firstBossState(float deltaTime, SpriteBatch batch){
		
		if (timeSinceLastShoot > 3){ //start shooting
			timeSinceLastShoot = 0;
			shouldFireVelocityY = RandomUtils.nextInt(0,2) == 1;
			float velocityY = shouldFireVelocityY ? towardsFeetProjectileY : 0;
			float velocityX = shouldFireVelocityY ? slowProjectileSpeedX : fastProjectileSpeedX;
			projectile1 = new Boss1Projectile(position.x + 20, 65, velocityX, velocityY);
			runningMan.getCollisionManager().setBoss1Projectile1(projectile1);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
	}
	
	/**
	 * secondBossState()
	 * 
	 * This the second boss state. Should go into his "shooting" animation every 2.35 seconds, and then produce a projectile1
	 * at 3 seconds AND a projectile2.
	 * 
	 * projectile1 will be slow towards his feet - must be jumped to avoid damage
	 * projectile2 will be slow towards his head - must be jumped AND attacked/reflected to avoid damage
	 * 
	 * 
	 * 
	 */
	private void secondBossState(float deltaTime, SpriteBatch batch){
		
		if (timeSinceLastShoot > 3){
			projectile1 = new Boss1Projectile(position.x + 20, 65, slowProjectileSpeedX, towardsFeetProjectileY);
			runningMan.getCollisionManager().setBoss1Projectile1(projectile1);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
		
		if (timeSinceLastShoot > 3){
			timeSinceLastShoot = 0;
			projectile2 = new Boss1Projectile(position.x + 20, 65, slowProjectileSpeedX, towardsHeadProjectileY);
			runningMan.getCollisionManager().setBoss1Projectile2(projectile2);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
	}
	
	private void thirdBossState(float deltaTime, SpriteBatch batch){

		if (timeSinceLastShoot > 3){
			projectile1 = new Boss1Projectile(position.x + 20, 65, fastProjectileSpeedX, towardsFeetProjectileY);
			runningMan.getCollisionManager().setBoss1Projectile1(projectile1);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
		
		if (timeSinceLastShoot > 3){
			timeSinceLastShoot = 0;
			projectile2 = new Boss1Projectile(position.x + 20, 65, fastProjectileSpeedX, towardsHeadProjectileY);
			runningMan.getCollisionManager().setBoss1Projectile2(projectile2);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
	}
	
	@Override
	public void kill() {
		isDead = true;
		bloodSplat = new BloodSplatProjectile(position.x + width/2 - 50, position.y);
		runningMan.getSoundManager().playBoss1DeathSound();
		velocity.x += 100;
		currentBossHealth = -1;
	}

	@Override
	public void loseHealth() {
		currentBossHealth = currentBossHealth - 1;
		runningMan.getSoundManager().playBoss1HurtSound();
		if (currentBossHealth == 6 || currentBossHealth == 3) runningMan.getSoundManager().playBoss1RageSound();
		bloodSplat = new BloodSplatProjectile(position.x + width/2 - 50, position.y);
	}

}
