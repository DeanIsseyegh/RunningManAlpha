package com.mygdx.runningman.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MainCharWeapon1 extends AbstractWorldObject implements IWorldObject{
	
	public MainCharWeapon1(float x, float y){
		spriteSheet = new Texture(Gdx.files.internal(MAIN_CHAR_WEAPON1));
		position = new Vector2(x, y);
		boundsBox = new Rectangle();
		velocity = new Vector2(600, 0);
		width = 114;
		height = 32;
		int FRAME_COLS = 1;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
	}
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.x += velocity.x * deltaTime;
		boundsBox.set(position.x , position.y, width, height);
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return position.x;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return position.y;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return boundsBox;
	}

}
