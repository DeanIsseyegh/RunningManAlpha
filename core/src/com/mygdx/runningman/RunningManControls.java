package com.mygdx.runningman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class RunningManControls implements GestureListener{

	RunningMan runningMan;
	
	public RunningManControls(RunningMan runningMan){
		this.runningMan = runningMan;
	}
	
	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float posX, float arg1) {
		if (posX < 900)
			this.runningMan.setLeftScreenTouched(true);
		else
			this.runningMan.setRightScreenTouched(true);
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float posX, float posY, int consecutiveTaps, int unkown) {
		System.out.println("****TAPPED**");
		System.out.println("posX : " + posX);
		
		if (posX < 900)
			this.runningMan.setLeftScreenTouched(true);
		else
			this.runningMan.setRightScreenTouched(true);
		
		return false;
	}

	@Override
	public boolean touchDown(float posX, float arg1, int arg2, int arg3) {
		if (posX < 900)
			this.runningMan.setLeftScreenTouched(true);
		else
			this.runningMan.setRightScreenTouched(true);
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
