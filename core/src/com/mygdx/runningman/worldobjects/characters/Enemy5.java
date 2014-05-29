package com.mygdx.runningman.worldobjects.characters;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.AbstractRunningManListener;
import com.mygdx.runningman.RunningManLevel2;
import com.mygdx.runningman.RunningManLevel2.Level2State;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.projectiles.Enemy5Projectile;
import com.mygdx.runningman.worldobjects.projectiles.Enemy5Projectile.E5ProjectileState;

public class Enemy5 extends AbstractWorldObject implements IEnemy {
	
	private int scaleFactor = 5;
	private EnemyState state;
	private IWorldObject mainChar;
	private Enemy5Projectile projectile;
	private AbstractRunningManListener runningMan;
	private int healthP;
	
	public Enemy5(IWorldObject mainChar, AbstractRunningManListener runningMan){
		super(ENEMY5_IMAGE);
		velocity = new Vector2(0, 0);
		position = new Vector2(-100, -100);
		
		width = 32 * scaleFactor;
		height = 32 * scaleFactor;
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.1f, aniFrames);
		
		this.mainChar = mainChar;
		this.runningMan = runningMan;
		
		healthP = 5;
		state = EnemyState.OFFSCREEN;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		
		switch (state){
		
		case OFFSCREEN:
			boundsBox.set(0,0,0,0);
			if (runningMan instanceof RunningManLevel2)
				if ( ((RunningManLevel2) runningMan).getNumOfEnemy3() == 0
				&& ((RunningManLevel2) runningMan).getNumOfEnemy4() == 0 ){
					position.x = mainChar.getX() + Gdx.graphics.getWidth();
					position.y = 0;
					velocity.x = -350;
					state = EnemyState.NORMAL;
					((RunningManLevel2) runningMan).setState(Level2State.Part2);
				}
				
			break;
			
		case NORMAL:
			boundsBox.set(position.x, position.y, width, height);
			position.x += velocity.x * deltaTime;
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			if (position.x < mainChar.getX() + 1200)
				state = EnemyState.SHOOTING;
			break;
		
		case SHOOTING:
			boundsBox.set(position.x, position.y, width, height);
			if (RandomUtils.nextInt(0, 2) == 1)
				projectile = new Enemy5Projectile(position.x, height/2 - 15, E5ProjectileState.ORANGE);
			else
				projectile = new Enemy5Projectile(position.x, height/2 - 15, E5ProjectileState.RED);
			state = EnemyState.MOVE_AT_SCRLL_SPD;
			runningMan.getCollisionManager().setEnemy5Projectile(projectile);
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			break;
			
		case MOVE_AT_SCRLL_SPD:
			boundsBox.set(position.x, position.y, width, height);
			velocity.x = runningMan.getScrollSpeed();
			position.x += velocity.x * deltaTime;
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			if (projectile.getX() < mainChar.getX() || projectile.getX() > position.x)
				state = EnemyState.SHOOTING;
			break;
			
		case DEAD:
			boundsBox.set(0,0,0,0);
			position.y += velocity.y * deltaTime;
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			break;
			
		default:
			break;
		}
		
		if (projectile != null)
			projectile.update(deltaTime, batch);
	}

	@Override
	public void kill() {
		state = EnemyState.DEAD;
		boundsBox.set(0, 0, 0 ,0);
		spriteSheet = new Texture(Gdx.files.internal(BLOOD_SPLAT));
		TextureRegion[] aniFrames = animateFromSpriteSheet(1, 1, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
		width = 50 * 2;
		height = 43 * 2;
		velocity.x = 0;
		velocity.y = -100;
	}

	@Override
	public void loseHealth() {
		Log.v(TAG, "Lost hp: " + healthP);
		if ( healthP < 0)
			kill();
		else
			healthP = healthP - 1;
	}

}
