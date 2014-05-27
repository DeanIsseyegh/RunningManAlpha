package com.mygdx.runningman.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.RunningMan;
import com.mygdx.runningman.worldobjects.AbstractWorldObject;
import com.mygdx.runningman.worldobjects.IWorldObject;
import com.mygdx.runningman.worldobjects.MainCharWeapon1;

public class MainCharacter extends AbstractWorldObject implements IWorldObject {
	
	private boolean isInAir;
	private boolean isAttacking;
	private int hardCodedJumpHeight = 175;
	
	private float animationTime;
	private float lastSwordAttack;
	private float attackingTimeLength;
	private RunningMan runningMan;

	public MainCharacter(int scrollSpeed, RunningMan runningMan){
		width = 125;
		height = 200;
		spriteSheet = new Texture(Gdx.files.internal(MAIN_CHAR_IMAGE)); //608 x 240 pixels - 8 COLS, 2 ROWS
		position = new Vector2(0, 0);
		velocity = new Vector2(scrollSpeed, 0);
		isInAir = false;
		boundsBox = new Rectangle();
		width = 120;
		height = 200;
		int FRAME_COLS = 8;
		int FRAME_ROWS = 2;
		TextureRegion[] aniFrames = animateFromSpriteSheet(FRAME_COLS, FRAME_ROWS, spriteSheet);
		animation = new Animation(0.05f, aniFrames);
		this.runningMan = runningMan;
		lastSwordAttack = 2;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		animationTime = time;
		lastSwordAttack += deltaTime;
		
		//If user touches screen and mainChar is touching the ground
		if (runningMan.isLeftScreenTouched() && position.y <= 0  && time > 1){
			isInAir = true;
			velocity.y = 300;
		} 
		
		if (runningMan.isRightScreenTouched() && lastSwordAttack > 2){
			isAttacking = true;
			lastSwordAttack = 0;
		} 
		
		//If mainChar is in attack frame
		if (isAttacking){
			animationTime = 0.65f;
			attackingTimeLength += deltaTime;
			MainCharWeapon1 weapon1 = new MainCharWeapon1(position.x + width - 19, position.y + (height * 0.43f));
			weapon1.update(deltaTime, batch);
			runningMan.getCollisionManager().setWeapon1(weapon1);
			
			if (attackingTimeLength > 0.75f){
				isAttacking = false;
				attackingTimeLength = 0;
			}
		}
		//If mainChar is jumping/in the air
		if (isInAir){
			animationTime = 0.65f;
			velocity.y -= 300 * deltaTime; //Make jumping more realistic/smoother emulate gravity
			if (position.y > hardCodedJumpHeight) 
				velocity.y = -velocity.y;
		}
		
		//Update mainChar position and bounds
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		boundsBox.set(position.x + 8, position.y, width, height); //Actual image is 60 wide, 100 tall with 8 empty pixels each side of him - image is stretched 2x when drawn
		
		//Draw the mainChar
		batch.draw(animation.getKeyFrame(animationTime, true), position.x , position.y, 156, 200); //78px wide, 100 tall, 8 empty pixels each side of him
		
		//Post check if man has landed - if so reset jumping state
		if (position.y <= 0){
			position.y = 0;
			velocity.y = 0;
			isInAir = false;
		}
		
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
