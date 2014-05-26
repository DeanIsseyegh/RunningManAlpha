package com.mygdx.leftman.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface ICharacter {
	
	public void update(float delta, SpriteBatch batch);
	
	public float getX();
	public float getY();
	
	public float getWidth();
	public float getHeight();
	
	public Rectangle getBoundingBox();
}
