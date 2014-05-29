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

public class Enemy4 extends AbstractWorldObject implements IEnemy {
	
	private EnemyState state;
	private int scaleFactor = 5;
	private int emptyPxls = 2*scaleFactor;
	private final int jumpingSpeed = 350;
	private final int hardCodedJumpHeight = 350;
	private IWorldObject mainChar;
	private AbstractRunningManListener runningMan;
	
	public Enemy4(int posX, IWorldObject mainChar, AbstractRunningManListener runningMan){
		super(ENEMY4_IMAGE);
		velocity = new Vector2(-200, jumpingSpeed);
		position = new Vector2(posX, 0);
		
		width = 31 * scaleFactor;
		height = 32 * scaleFactor;
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.15f, aniFrames);
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
				state = EnemyState.JUMPING;
			break;
			
		case JUMPING:
			boundsBox.set(position.x + emptyPxls, position.y, width, height);
			position.x += velocity.x * deltaTime;
			position.y += velocity.y * deltaTime;
			batch.draw(animation.getKeyFrame(0, true), position.x , position.y, width, height);
			
			velocity.y -= 300 * deltaTime; //Make jumping more realistic/smoother emulate gravity
			if (position.y > hardCodedJumpHeight) 
				velocity.y = -velocity.y;
			if (position.y < 0)
				state = EnemyState.NORMAL;
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
			((RunningManLevel2) runningMan).enemy4Killed();
	}

	@Override
	public void loseHealth() {
		// TODO Auto-generated method stub
		
	}

}
