package com.mygdx.runningman.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.runningman.MainGame;
import com.mygdx.runningman.MainMenu;
import com.mygdx.runningman.RunningManLevel1;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new RunningManLevel1(), config);
		initialize(new MainGame(), config);
	}
}
