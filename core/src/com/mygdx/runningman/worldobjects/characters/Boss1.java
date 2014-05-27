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
import com.mygdx.runningman.worldobjects.projectiles.Boss1Projectile;

public class Boss1 extends AbstractWorldObject implements IEnemy {

	private Animation walkingAnimation;
	private Animation shootAnimation;
	private AbstractProjectile projectile1;
	private RunningMan runningMan;
	private boolean hasBossLanded;
	private boolean isShootingAnimation;
	private boolean shouldFireVelocityY = true;
	
	private float timeSinceLastShoot;
	private float shootingAnimationTime;
	public Boss1(float posX, RunningMan runningMan){
		this.runningMan = runningMan;
		spriteSheet = new Texture(Gdx.files.internal(BOSS1_IMAGE)); //actual 127px wide 88px high
		velocity = new Vector2(250, -400);
		position = new Vector2(posX, 1000);
		boundsBox = new Rectangle();
		width = 509;
		height = 344;
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
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		timeSinceLastShoot += deltaTime;
		position.y += velocity.y * deltaTime;
		position.x += velocity.x * deltaTime;
		
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
		
		if (timeSinceLastShoot > 3){
			timeSinceLastShoot = 0;
			shouldFireVelocityY = RandomUtils.nextInt(0,2) == 1;
			float velocityY = shouldFireVelocityY ? -50f : 0;
			projectile1 = new Boss1Projectile(position.x + 20, 65, velocityY);
			runningMan.getCollisionManager().setBoss1Projectile(projectile1);
			runningMan.getSoundManager().playBoss1AttackSound();
		}
		
		if (projectile1 != null)
			projectile1.update(deltaTime, batch);
		//boundsBox.set(position.x + 6, position.y, width, height);
		
		if (position.y <= 0){
			if (!hasBossLanded){
				runningMan.getSoundManager().playBoss1LandedSound();
				runningMan.getSoundManager().playBoss1Music();
			}
			hasBossLanded = true;
			position.y = 0;
			velocity.y = 0;
		}
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loseHealth() {
		// TODO Auto-generated method stub
		
	}

}
