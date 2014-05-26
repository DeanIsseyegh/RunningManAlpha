package com.mygdx.runningman.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GenericCharacter {

	protected Vector2 velocity;
	protected Vector2 position;
	protected Texture spriteSheet;
	protected Animation animation;
	protected Rectangle boundsBox;
	
	protected int width;
	protected int height;
	protected float time;
	
	/**
	 * Will take in a spriteSheet image and return it as an Animation object.
	 * 
	 * @param int cols - Number of columns in spriteSheet
	 * @param int rows = Number of rows in spriteSheet
	 * @param Texture spriteSheet - The Texture object containing the spriteSheet
	 * @return
	 */
	protected TextureRegion[] animateFromSpriteSheet(int cols, int rows, Texture spriteSheet){
		TextureRegion[][] tmpAniFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);
		TextureRegion[] aniFrames = new TextureRegion[cols * rows];
		
		int index = 0;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				aniFrames[index++] = tmpAniFrames[i][j];
		
		return aniFrames;
	}
}
