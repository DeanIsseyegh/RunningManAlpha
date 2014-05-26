package com.mygdx.runningman.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy2 extends GenericCharacter implements ICharacter {

	public Enemy2(int posX){
		spriteSheet = new Texture(Gdx.files.internal(ENEMY2_IMAGE)); //Actual each sprite 33px wide, 3 empty on left side
		position = new Vector2(posX, 0);
		boundsBox = new Rectangle();
		width = 99; //3x stretch
		height = 90; //3x stretch
		int FRAME_COLS = 3;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		animation = new Animation(0.4f, aniFrames);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
		boundsBox.set(position.x + 6, position.y, width, height);
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

}
