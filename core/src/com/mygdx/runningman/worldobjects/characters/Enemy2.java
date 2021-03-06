package com.mygdx.runningman.worldobjects.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;

public class Enemy2 extends AbstractWorldObject implements IEnemy {
	
	private IWorldObject mainChar;
	
	public Enemy2(int posX, IWorldObject mainChar){
		super(ENEMY2_IMAGE);
		this.mainChar = mainChar;
		position = new Vector2(posX, 0);
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
		
		if (!isDisposed)
			if (position.x < mainChar.getX() - 600 )
				dispose();
	}

	@Override
	public void kill() {
		//Cannot be killed.
	}

	@Override
	public void loseHealth() {
		//Cannot lose health.
	}

}
