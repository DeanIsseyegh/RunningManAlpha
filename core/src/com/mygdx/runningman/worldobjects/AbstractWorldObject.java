package com.mygdx.runningman.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractWorldObject implements IWorldObject {

	protected Vector2 velocity;
	protected Vector2 position;
	protected Texture spriteSheet;
	protected Animation animation;
	protected Rectangle boundsBox;
	
	protected int width;
	protected int height;
	protected int numOfXEmptyPxls;
	protected float time;
	
	/**
	 * Default constructor that initialises the boundsBox variable - the only necessary member that requires no parameters
	 * to be initialised.
	 */
	public AbstractWorldObject(){
		boundsBox = new Rectangle();
	}
	
	/**
	 * Minimalist constructor that initialises the bare minimum for an object
	 * 
	 * @param spriteSheetPath
	 */
	public AbstractWorldObject(String spriteSheetPath){
		boundsBox = new Rectangle();
		spriteSheet = new Texture(Gdx.files.internal(spriteSheetPath));
	}
	
	/**
	 * 
	 * Optional constructor that initialises most of the needed/expected variables for a game object.
	 * 
	 * @param spriteSheetPath
	 * @param velocityX
	 * @param velocityY
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 */
	public AbstractWorldObject(String spriteSheetPath, float velocityX, float velocityY, float positionX, float positionY, int width, int height){
		boundsBox = new Rectangle();
		
		spriteSheet = new Texture(Gdx.files.internal(spriteSheetPath));
		
		velocity = new Vector2(velocityX, velocityY);
		position = new Vector2(positionX, positionY);
		
		this.width = width;
		this.height = height;
	}
	
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
