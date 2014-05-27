package com.mygdx.runningman.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public class Boss1 extends AbstractWorldObject implements IEnemy {

	private Animation walkingAnimation;
	private Animation shootAnimation;
	
	public Boss1(float posX){
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
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.y += velocity.y * deltaTime;
		position.x += velocity.x * deltaTime;
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
		//boundsBox.set(position.x + 6, position.y, width, height);
		
		if (position.y <= 0){
			position.y = 0;
			velocity.y = 0;
		}
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

}
