package com.mygdx.runningman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private static final String LEVEL1_MUSIC = "music/Level1Music.mp3";
	private static final String BOSS1_MUSIC = "music/Boss1Music.mp3";
	
	private static final String JUMP_SOUND = "music/JumpSound.wav";
	private static final String ATTACK_SOUND = "music/AttackSound.wav";
	private static final String DIE_SOUND = "music/DieSound.ogg";
	
	private static final String BOSS1_LANDED = "music/Boss1Landed.ogg";
	private static final String BOSS1_ATTACK = "music/Boss1Attack.ogg";
	
	private Music level1Music;
	private Music boss1Music;
	
	private Sound jumpSound;
	private Sound attackSound;
	private Sound dieSound;
	
	private Sound boss1LandedSound;
	private Sound boss1Attack;
	
	public SoundManager(){
		level1Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL1_MUSIC));
		boss1Music = Gdx.audio.newMusic(Gdx.files.internal(BOSS1_MUSIC));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal(JUMP_SOUND));
		attackSound = Gdx.audio.newSound(Gdx.files.internal(ATTACK_SOUND));
		dieSound = Gdx.audio.newSound(Gdx.files.internal(DIE_SOUND));
		boss1LandedSound = Gdx.audio.newSound(Gdx.files.internal(BOSS1_LANDED));
		boss1Attack = Gdx.audio.newSound(Gdx.files.internal(BOSS1_ATTACK));
	}
	
	public void playLevel1Music(){
		level1Music.setVolume(0.25f);
		level1Music.play();
	}
	
	public void stopLevel1Music(){
		if (level1Music != null){
			level1Music.stop();
			level1Music.dispose();
			level1Music = null;
		}
	}
	
	public void playBoss1Music(){
		boss1Music.setVolume(0.25f);
		boss1Music.play();
	}
	
	public void stopBoss1Music(){
		if (boss1Music != null){
			boss1Music.stop();
		}
	}
	
	
	public void playJumpSound(){
		jumpSound.play();
	}
	
	public void playAttackSound(){
		attackSound.play();
	}
	
	public void playDieSound(){
		dieSound.play();
	}
	
	public void playBoss1LandedSound(){
		boss1LandedSound.play();
	}
	
	public void playBoss1AttackSound(){
		boss1Attack.play();
	}
	
}
