package com.mygdx.runningman.worldobjects.characters;

import org.apache.commons.lang3.ArrayUtils;

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
import com.mygdx.runningman.worldobjects.projectiles.AbstractProjectile;
import com.mygdx.runningman.worldobjects.projectiles.Enemy3Projectile;

public class Enemy3 extends AbstractWorldObject implements IEnemy {
	
	private int scaleFactor = 5;
	private int emptyPxls = 2*scaleFactor;
	private EnemyState state;
	private IWorldObject mainChar;
	private AbstractProjectile projectile;
	private AbstractRunningManListener runningMan;
	
	public Enemy3(int posX, IWorldObject mainChar, AbstractRunningManListener runningMan){
		super(ENEMY3_IMAGE);
		velocity = new Vector2(-200, 0);
		position = new Vector2(posX, 0);
		
		width = 31 * scaleFactor;
		height = 32 * scaleFactor;
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.1f, aniFrames);
		state = EnemyState.NORMAL;
		this.mainChar = mainChar;
		this.runningMan = runningMan;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		
		if (runningMan instanceof RunningManLevel2)
			if (((RunningManLevel2) runningMan).getState() == Level2State.Part2)
				state = EnemyState.OFFSCREEN;
		
		switch (state){
		case NORMAL:
			boundsBox.set(position.x + emptyPxls, position.y, width, height);
			position.x += velocity.x * deltaTime;
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			if (position.x < mainChar.getX() + 800)
				state = EnemyState.SHOOTING;
			break;
		
		case SHOOTING:
			boundsBox.set(position.x + emptyPxls, position.y, width, height);
			projectile = new Enemy3Projectile(position.x, height/2);
			state = EnemyState.NOMOVE;
			runningMan.getCollisionManager().setEnemy3Projectile(projectile);
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			break;
			
		case NOMOVE:
			boundsBox.set(position.x + emptyPxls, position.y, width, height);
			batch.draw(animation.getKeyFrame(time, false), position.x , position.y, width, height);
			break;
			
		case DEAD:
			boundsBox.set(0,0,0,0);
			position.y += velocity.y * deltaTime;
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
			break;
		
		case OFFSCREEN:
			boundsBox.set(0,0,0,0);
			position.x = -100;
			position.y = -100;
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
		spriteSheet = new Texture(Gdx.files.internal(METAL_SPLAT));
		TextureRegion[] aniFrames = animateFromSpriteSheet(1, 1, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
		width = 50 * 2;
		height = 43 * 2;
		velocity.x = 0;
		velocity.y = -100;
		
		if (runningMan instanceof RunningManLevel2)
			((RunningManLevel2) runningMan).enemy3Killed();
	}

	@Override
	public void loseHealth() {
		// TODO Auto-generated method stub
		
	}

}
