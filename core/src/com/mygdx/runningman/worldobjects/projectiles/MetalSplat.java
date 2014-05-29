package com.mygdx.runningman.worldobjects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MetalSplat extends AbstractProjectile {
	
	public MetalSplat(float posX, float posY){
		super(METAL_SPLAT);
		TextureRegion[] aniFrames = animateFromSpriteSheet(1, 1, spriteSheet);
		animation = new Animation(0.1f, aniFrames);
		int scaleFactor = 2;
		width = 45 * scaleFactor;
		height = 40 * scaleFactor;
		velocity = new Vector2(0, -100);
		position = new Vector2(posX, posY);
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		position.y += velocity.y * deltaTime;
		batch.draw(animation.getKeyFrame(time, true), position.x , position.y, width, height);
	}

}
