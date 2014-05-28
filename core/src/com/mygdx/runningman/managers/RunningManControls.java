package com.mygdx.runningman.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.runningman.AbstractRunningManListener;
import com.mygdx.runningman.RunningManLevel1;

public class RunningManControls implements GestureListener{

	AbstractRunningManListener runningMan;
	float midWayPoint = Gdx.graphics.getWidth()/2;
	
	public RunningManControls(AbstractRunningManListener runningMan){
		this.runningMan = runningMan;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float posX, float arg1) {
		handleControls(posX);
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
	public boolean tap(float posX, float posY, int consecutiveTaps, int button) {
		handleControls(posX);
		return false;
	}

	@Override
	public boolean touchDown(float posX, float arg1, int arg2, int arg3) {
		handleControls(posX);
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	private void handleControls(float posX){
		if (posX < midWayPoint){
			runningMan.setLeftScreenTouched(true);
			runningMan.getGameHUD().lightUpJumpLabel();
			
		} else {
			runningMan.setRightScreenTouched(true);
			runningMan.getGameHUD().lightUpAttackLabel();
		}
	}
}
