package com.mygdx.runningman.worldobjects.projectiles;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public class Boss1Projectile extends AbstractProjectile{

	public Boss1Projectile(float x, float y, float velocityX, float velocityY){
		super(BOSS1_PROJECTILE);
		int scaleFactor = 2;
		position = new Vector2(x, y);
		boundsBox = new Rectangle();
		velocity = new Vector2(velocityX, velocityY);
		width = 36 * scaleFactor;
		height = 44 * scaleFactor;
		int FRAME_COLS = 7;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.25f, aniFrames);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		boundsBox.set(position.x + 7f, position.y, width, height);
		batch.draw(animation.getKeyFrame(time, false), position.x , position.y, width, height);
	}

}
