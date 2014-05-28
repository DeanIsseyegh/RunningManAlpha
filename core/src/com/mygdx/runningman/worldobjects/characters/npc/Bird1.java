package com.mygdx.runningman.worldobjects.characters.npc;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;

public class Bird1 extends AbstractWorldObject{

	public Bird1(int randomInt) {
		super(BIRD1_IMAGE);
		
		velocity = new Vector2(-200, 0);
		int randomPosY = Gdx.graphics.getHeight() - 250 - RandomUtils.nextInt(0, 350);
		position = new Vector2(randomInt, randomPosY);
		int scaleFac = 3;
		width = 37 * scaleFac;
		height = 29 * scaleFac;
		int FRAME_COLS = 4;
		int FRAME_ROWS = 1;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		ArrayUtils.reverse(aniFrames);
		animation = new Animation(0.1f, aniFrames);
	}

	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		position.x += velocity.x * deltaTime;
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
	}

}
