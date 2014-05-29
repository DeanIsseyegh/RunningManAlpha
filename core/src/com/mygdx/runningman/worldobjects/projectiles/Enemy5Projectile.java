package com.mygdx.runningman.worldobjects.projectiles;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class Enemy5Projectile extends AbstractProjectile{
	
	private E5ProjectileState state;

	private Animation redAnimation;
	private Animation orangeAnimation;
	
	public Enemy5Projectile(float x, float y, E5ProjectileState orangeOrRed){
		super(ENEMY5_PROJECTILE_RED);
		position = new Vector2(x, y);
		boundsBox = new Rectangle();
		velocity = new Vector2(-300, 0);
		
		int scaleFactor = 1;
		width = 60 * scaleFactor;
		height = 60 * scaleFactor;
		int FRAME_COLS = 1;
		int FRAME_ROWS = 1;
		
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.3f, aniFrames);
		redAnimation = animation;
		
		spriteSheet = new Texture(Gdx.files.internal(IWorldObject.ENEMY5_PROJECTILE_ORANGE));
		aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		orangeAnimation = new Animation(0.3f, aniFrames);
		
		state = orangeOrRed;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.x += velocity.x * deltaTime;
		boundsBox.set(position.x, position.y, width, height);
		
		switch(state){
		case RED:
			batch.draw(redAnimation.getKeyFrame(time, false), position.x , position.y, width, height);
			break;
		case ORANGE:
			batch.draw(orangeAnimation.getKeyFrame(time, false), position.x , position.y, width, height);
			break;
		default: 
			break;
		}

	}

	public enum E5ProjectileState{
		RED,
		ORANGE;
	}
	
	public E5ProjectileState getState() {
		return state;
	}
}
