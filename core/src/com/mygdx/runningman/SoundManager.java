package com.mygdx.runningman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private static final String LEVEL1_MUSIC = "music/Level1Music.mp3";
	private static final String JUMP_SOUND = "music/JumpSound.wav";
	private static final String ATTACK_SOUND = "music/AttackSound.wav";
	private static final String DIE_SOUND = "music/DieSound.ogg";
	
	private Music level1Music;
	private Sound jumpSound;
	private Sound attackSound;
	private Sound dieSound;
	
	public void playLevel1Music(){
		level1Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL1_MUSIC));
		level1Music.setVolume(0.25f);
		level1Music.play();
	}
	
	public void stopLevel1Music(){
		if (level1Music != null){
			level1Music.stop();
			level1Music.dispose();
		}
	}
	
	public void playJumpSound(){
		jumpSound = Gdx.audio.newSound(Gdx.files.internal(JUMP_SOUND));
		jumpSound.play();
	}
	
	public void playAttackSound(){
		attackSound = Gdx.audio.newSound(Gdx.files.internal(ATTACK_SOUND));
		attackSound.play();
	}
	
	public void playDieSound(){
		dieSound = Gdx.audio.newSound(Gdx.files.internal(DIE_SOUND));
		dieSound.play();
	}
}
