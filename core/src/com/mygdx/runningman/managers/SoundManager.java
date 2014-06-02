package com.mygdx.runningman.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	
	private static final String MAINMENU_MUSIC = "music/MainMenuMusic.wav";
	private static final String GAMEOVER_SOUND = "music/GameOverSound.mp3";
	private static final String LEVELCOMPLETE_MUSIC = "music/LevelCompletedMusic.wav";
	private static final String LEVEL1_MUSIC = "music/Level1Music.mp3";
	private static final String LEVEL2_MUSIC = "music/Level2Music.mp3";
	private static final String BOSS1_MUSIC = "music/Boss1Music.mp3";
	
	private static final String JUMP_SOUND = "music/JumpSound.wav";
	private static final String ATTACK_SOUND = "music/AttackSound.wav";
	private static final String DIE_SOUND = "music/DieSound.ogg";
	
	private static final String BOSS1_LANDED = "music/Boss1Landed.ogg";
	private static final String BOSS1_ATTACK = "music/Boss1Attack.ogg";
	private static final String BOSS1_HURT = "music/Boss1Hurt.ogg";
	private static final String BOSS1_RAGE = "music/Boss1Rage.ogg";
	private static final String BOSS1_DEATH = "music/Boss1Death.ogg";
	
	private static final String ENEMY5_HURT = "music/Enemy5Hurt.wav";
	
	private Music mainMenuMusic;
	private Sound gameOverSound;
	private Music levelCompleteMusic;
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
	
	private boolean mute;
	
	public SoundManager(){
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal(GAMEOVER_SOUND));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal(JUMP_SOUND));
		attackSound = Gdx.audio.newSound(Gdx.files.internal(ATTACK_SOUND));
		dieSound = Gdx.audio.newSound(Gdx.files.internal(DIE_SOUND));
		mute = false;
	}
	
	public void mute(){
		mute = true;
	}
	
	public void playMainMenuMusic(){
		if (!mute){
			mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal(MAINMENU_MUSIC));
			mainMenuMusic.setVolume(0.25f);
			mainMenuMusic.play();
		}
	}
	
	public void stopMainMenuMusic(){
		if (!mute){
			if (mainMenuMusic != null){
				mainMenuMusic.stop();
				mainMenuMusic.dispose();
				mainMenuMusic = null;
			}
		}
	}
	
	public void playLevelCompletedMusic(){
		System.out.println("PLAYING LEVEL COMPLETED MUSIC");
		if (!mute){
			levelCompleteMusic = Gdx.audio.newMusic(Gdx.files.internal(LEVELCOMPLETE_MUSIC));
			levelCompleteMusic.setVolume(0.25f);
			levelCompleteMusic.play();
		}
	}
	
	public void stopLevelCompletedMusic(){
		System.out.println("STOPPING IT");
		if (!mute){
			if (levelCompleteMusic != null){
				levelCompleteMusic.stop();
				levelCompleteMusic.dispose();
			}
		}
	}
	
	public void playGameOverSound(){
		if (!mute){
			gameOverSound.play();
		}
	}
	public void stopGameOverSound(){
		if (!mute){
			gameOverSound.stop();
		}
	}
	
	public void playLevel1Music(){
		if (!mute){
			level1Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL1_MUSIC));
			level1Music.setVolume(0.25f);
			level1Music.play();
		}
	}
	
	public void stopLevel1Music(){
		if (!mute){
			if (level1Music != null){
				level1Music.stop();
				level1Music.dispose();
			}
		}
	}
	
	public void playLevel2Music(){
		if (!mute){
			leve2Music = Gdx.audio.newMusic(Gdx.files.internal(LEVEL2_MUSIC));
			leve2Music.setVolume(0.25f);
			leve2Music.play();
		}
	}
	
	public void stopLevel2Music(){
		if (!mute){
			if (leve2Music != null){
				leve2Music.stop();
				leve2Music.dispose();
				leve2Music = null;
			}
		}
	}
	
	public void initLevel2Resources(){
		if (!mute){
			enemy5Hurt = Gdx.audio.newSound(Gdx.files.internal(ENEMY5_HURT));
		}
	}
	
	public void playEnemy5HurtSound(){
		if (!mute){
			enemy5Hurt.play();
		}
	}
	
	public void playJumpSound(){
		if (!mute){
			jumpSound.play();
		}
	}
	
	public void playAttackSound(){
		if (!mute){
			attackSound.play();
		}
	}
	
	public void playDieSound(){
		if (!mute){
			dieSound.play();
		}
	}
	
	public void initBoss1Resources(){
		if (!mute){
			boss1Music = Gdx.audio.newMusic(Gdx.files.internal(BOSS1_MUSIC));
			boss1LandedSound = Gdx.audio.newSound(Gdx.files.internal(BOSS1_LANDED));
			boss1Attack = Gdx.audio.newSound(Gdx.files.internal(BOSS1_ATTACK));
			boss1Hurt = Gdx.audio.newSound(Gdx.files.internal(BOSS1_HURT));
			boss1Rage = Gdx.audio.newSound(Gdx.files.internal(BOSS1_RAGE));
			boss1Death = Gdx.audio.newSound(Gdx.files.internal(BOSS1_DEATH));
		}
	}
	
	public void playBoss1Music(){
		if (!mute){
			if ( boss1Music == null)
				boss1Music = Gdx.audio.newMusic(Gdx.files.internal(BOSS1_MUSIC));;
			boss1Music.setVolume(0.25f);
			boss1Music.play();
		}
	}
	
	public void stopBoss1Music(){
		if (!mute){
			if (boss1Music != null){
				boss1Music.stop();
				boss1Music.dispose();
				boss1Music = null;
			}
		}
	}
	
	public void destroyLevelResources() {
		if (!mute){
			try{
				System.out.println("DESTORYING");
			//Level1 Resources
			if (level1Music != null){
				level1Music.stop();
				level1Music.dispose();
			}
			
			if (boss1Music != null){
				boss1Music.stop();
				boss1Music.dispose();
			}
			
			if (boss1LandedSound != null){
				boss1LandedSound.stop();
				boss1LandedSound.dispose();
			}
			
			if (boss1Attack != null){
				boss1Attack.stop();
				boss1Attack.dispose();
			}
			
			if (boss1Hurt != null){
				boss1Hurt.stop();
				boss1Hurt.dispose();
			}
			
			if (boss1Rage != null){	
				boss1Rage.stop();
				boss1Rage.dispose();
			}
			
			if (boss1Death != null){
				boss1Death.stop();
				boss1Death.dispose();
			}
			
			//Level 2 resources
			if (enemy5Hurt != null){
				enemy5Hurt.stop();
				enemy5Hurt.dispose();
			}
			
			if (leve2Music != null){
				leve2Music.stop();
				leve2Music.dispose();
			}
			} catch (Exception e){
				System.out.println("CAUGHT");
			}
		}
	}
	
	public void playBoss1LandedSound(){
		if (!mute){
			boss1LandedSound.play();
		}
	}
	
	public void playBoss1AttackSound(){
		if (!mute){
			boss1Attack.play();
		}
	}

	public void playBoss1HurtSound(){
		if (!mute){
			boss1Hurt.play();
		}
	}
	
	public void playBoss1RageSound(){
		if (!mute){
			boss1Rage.play();
		}
	}
	
	public void playBoss1DeathSound(){
		if (!mute){
			boss1Death.play();
		}
	}
	
}
