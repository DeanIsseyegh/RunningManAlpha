package com.mygdx.runningman.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private static final String MAINMENU_MUSIC = "music/MainMenuMusic.wav";
	private static final String LEVEL1_MUSIC = "music/Level1Music.mp3";
	private static final String LEVEL2_MUSIC = "music/Level2Music.mp3";
	private static final String BOSS1_MUSIC = "music/Boss1Music.mp3";
	
	private static final String JUMP_SOUND = "music/JumpSound.wav";
	private static final String ATTACK_SOUND = "music/AttackSound.wav";
	private static final String DIE_SOUND = "music/DieSound.ogg";
	
	private static final String BOSS1_LANDED = "music/Boss1Landed.ogg";
	private static final String BOSS1_ATTACK = "music/Boss1Attack.ogg";
	private static final String BOSS1_HURT = "music/Boss1Hurt.ogg";
	private static final String BOSS1_RAGE = "music/Boss1Rage.wav";
	private static final String BOSS1_DEATH = "music/Boss1Death.ogg";
	
	private static final String ENEMY5_HURT = "music/Enemy5Hurt.wav";
	
	private Music mainMenuMusic;
	private Music level1Music;
	private Music leve2Music;
	private Music boss1Music;
	
	private Sound jumpSound;
	private Sound attackSound;
	private Sound dieSound;
	
	private Sound boss1LandedSound;
	private Sound boss1Attack;
	private Sound boss1Hurt;
	private Sound boss1Rage;
	private Sound boss1Death;
	
	private Sound enemy5Hurt;
	
	public SoundManager(){
		jumpSound = Gdx.audio.newSound(Gdx.files.internal(JUMP_SOUND));
		attackSound = Gdx.audio.newSound(Gdx.files.internal(ATTACK_SOUND));
		dieSound = Gdx.audio.newSound(Gdx.files.internal(DIE_SOUND));
	}
	
	public void playMainMenuMusic(){
		if (mainMenuMusic == null)
			mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal(MAINMENU_MUSIC));
		mainMenuMusic.setVolume(0.25f);
		mainMenuMusic.play();
	}
	
	public void stopMainMenuMusic(){
		if (mainMenuMusic != null){
			mainMenuMusic.stop();
			mainMenuMusic.dispose();
			mainMenuMusic = null;
		}
	}
	
	public void playLevel1Music(){
		if (level1Music == null)
			level1Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL1_MUSIC));
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
	
	public void playLevel2Music(){
		if (leve2Music == null)
			leve2Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL2_MUSIC));
		leve2Music.setVolume(0.25f);
		leve2Music.play();
	}
	
	public void stopLevel2Music(){
		if (leve2Music != null){
			leve2Music.stop();
			leve2Music.dispose();
			leve2Music = null;
		}
	}
	
	public void initLevel2Resources(){
		enemy5Hurt = Gdx.audio.newSound(Gdx.files.internal(ENEMY5_HURT));
	}
	
	public void destroyLevel2Resources(){
		if (enemy5Hurt != null)
			enemy5Hurt.dispose();
		if (leve2Music != null)
			leve2Music.dispose();
	}
	
	public void playEnemy5HurtSound(){
		enemy5Hurt.play();
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
	
	public void initBoss1Resources(){
		boss1Music = Gdx.audio.newMusic(Gdx.files.internal(BOSS1_MUSIC));
		boss1LandedSound = Gdx.audio.newSound(Gdx.files.internal(BOSS1_LANDED));
		boss1Attack = Gdx.audio.newSound(Gdx.files.internal(BOSS1_ATTACK));
		boss1Hurt = Gdx.audio.newSound(Gdx.files.internal(BOSS1_HURT));
		boss1Rage = Gdx.audio.newSound(Gdx.files.internal(BOSS1_RAGE));
		boss1Death = Gdx.audio.newSound(Gdx.files.internal(BOSS1_DEATH));
	}
	
	public void destroyBoss1Resources(){
		if (boss1Music != null)
			boss1Music.dispose();
		if (boss1LandedSound != null)
			boss1LandedSound.dispose();
		if (boss1Attack != null)
			boss1Attack.dispose();
		if (boss1Hurt != null)	
			boss1Hurt.dispose();
		if (boss1Rage != null)	
			boss1Rage.dispose();
		if (boss1Death != null)	
			boss1Death.dispose();
	}
	
	public void playBoss1Music(){
		if ( boss1Music == null)
			boss1Music = Gdx.audio.newMusic(Gdx.files.internal(BOSS1_MUSIC));;
		boss1Music.setVolume(0.25f);
		boss1Music.play();
	}
	
	public void stopBoss1Music(){
		if (boss1Music != null){
			boss1Music.stop();
			boss1Music.dispose();
			boss1Music = null;
		}
	}
	
	public void playBoss1LandedSound(){
		boss1LandedSound.play();
	}
	
	public void playBoss1AttackSound(){
		boss1Attack.play();
	}

	public void playBoss1HurtSound(){
		boss1Hurt.play();
	}
	
	public void playBoss1RageSound(){
		boss1Rage.play();
	}
	
	public void playBoss1DeathSound(){
		boss1Death.play();
	}
	
}
