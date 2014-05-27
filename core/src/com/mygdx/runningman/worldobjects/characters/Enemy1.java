package com.mygdx.runningman.worldobjects.characters;

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
	private IWorldObject mainChar;
	
	public Enemy1(int posX, IWorldObject mainChar){
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
		this.mainChar = mainChar;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		if (isKilled){				
			boundsBox.set(0,0,0,0);
			position.y += velocity.y * deltaTime;
		} else {
			boundsBox.set(position.x , position.y, width, height);
			position.x += velocity.x * deltaTime;
			position.y = mainChar.getY();
		}
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
		
	}

	@Override
	public void kill() {
		isKilled = true;
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
		// TODO Auto-generated method stub
		
	}

}
