package com.mygdx.runningman.characters;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class Enemy1 extends AbstractWorldObject implements IEnemy {
	
	boolean isKilled = false;
	
	public Enemy1(int posX){
		spriteSheet = new Texture(Gdx.files.internal(ENEMY1_IMAGE)); //actual 24px wide 37px high
		velocity = new Vector2(-200, 0);
		position = new Vector2(posX, 0);
		boundsBox = new Rectangle();
		width = 96;
		height = 148;
		int FRAME_COLS = 5;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.1f, aniFrames);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		if (isKilled)
			boundsBox.set(0,0,0,0);
		else {
			position.x += velocity.x * deltaTime;
			boundsBox.set(position.x , position.y, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
		}
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public Rectangle getBoundingBox() {
		return boundsBox;
	}

	@Override
	public void kill() {
		isKilled = true;
		boundsBox.set(0, 0, 0 ,0);
		System.out.println("**KILLED");
	}

}
