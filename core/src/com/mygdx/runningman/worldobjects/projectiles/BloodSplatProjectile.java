package com.mygdx.runningman.worldobjects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BloodSplatProjectile extends AbstractProjectile {

	private boolean isMultipleSplats;
	
	public BloodSplatProjectile(float posX, float posY){
		super(BLOOD_SPLAT);
		TextureRegion[] aniFrames = animateFromSpriteSheet(1, 1, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
		int scaleFactor = 2;
		width = 50 * scaleFactor;
		height = 43 * scaleFactor;
		velocity = new Vector2(0, -100);
		position = new Vector2(posX, posY);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		position.y += velocity.y * deltaTime;
		if (!isMultipleSplats){
			batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
		} else {
			batch.draw(animation.getKeyFrame(time, true), position.x + 10, position.y + 10, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 20, position.y + 30, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 35, position.y + 20, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 60 , position.y + 60, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 30 , position.y + 100, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 10 , position.y + 150, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 130 , position.y + 150, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 10 , position.y + 200, width, height);
			batch.draw(animation.getKeyFrame(time, true), position.x  + 39 , position.y + 175, width, height);
		}
	}
	
	public void initMultipleSplats(){
		isMultipleSplats = true;
	}

}
