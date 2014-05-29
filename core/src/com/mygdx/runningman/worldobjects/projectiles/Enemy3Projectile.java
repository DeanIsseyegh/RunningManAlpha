package com.mygdx.runningman.worldobjects.projectiles;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy3Projectile extends AbstractProjectile{

	private int emptyPixels;
	
	public Enemy3Projectile(float x, float y){
		super(ENEMY3_PROJECTILE);
		position = new Vector2(x, y);
		boundsBox = new Rectangle();
		velocity = new Vector2(-200, 0);
		
		int scaleFactor = 6;
		emptyPixels = 2 * 3;
		width = 10 * scaleFactor;
		height = 5 * scaleFactor;
		int FRAME_COLS = 5;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.3f, aniFrames);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.x += velocity.x * deltaTime;
		boundsBox.set(position.x + emptyPixels/2, position.y, width - emptyPixels, height);
		batch.draw(animation.getKeyFrame(time, false), position.x , position.y, width, height);
	}

}
