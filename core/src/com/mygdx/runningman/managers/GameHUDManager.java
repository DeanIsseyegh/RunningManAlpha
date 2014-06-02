package com.mygdx.runningman.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameHUDManager {

	private BitmapFont bitFont;
	private Stage stage;
	private LabelStyle fontStyle;
	private Label pointsLabel;
	private Label jumpLabel;
	private Label attackLabel;
	private Label bossIncomingLabel;
	
	public GameHUDManager(){
		bitFont = new BitmapFont(Gdx.files.internal("converted.fnt"), false);
		fontStyle = new LabelStyle(bitFont, Color.WHITE);
		
		pointsLabel = new Label("Points: 0", fontStyle);
		pointsLabel.setFontScale(2);
		pointsLabel.setPosition(Gdx.graphics.getWidth() - bitFont.getBounds("Points: 0").width * 3f, Gdx.graphics.getHeight() - bitFont.getBounds("Points: 0").height * 4f);
		
		jumpLabel = new Label("Jump!", fontStyle);
		jumpLabel.setFontScale(3);
		jumpLabel.setPosition(0, Gdx.graphics.getHeight()/2 * 1f);
		
		attackLabel = new Label("Attack!", fontStyle);
		attackLabel.setFontScale(3);
		attackLabel.setPosition(Gdx.graphics.getWidth() - bitFont.getBounds("Attack!").width * 3, Gdx.graphics.getHeight()/2 * 1f);
		
		bossIncomingLabel = new Label("BOSS INCOMING!", fontStyle);
		bossIncomingLabel.setFontScale(3);
		bossIncomingLabel.setPosition(Gdx.graphics.getWidth()/2 * 0.7f, Gdx.graphics.getHeight()/2);
		bossIncomingLabel.setColor(Color.RED);
		
		stage = new Stage();
		stage.addActor(pointsLabel);
		stage.addActor(jumpLabel);
		stage.addActor(attackLabel);
	}
	
	public void dispose(){
		stage.dispose();
		bitFont.dispose();
	}
	public void showBossLabel(){
		stage.addActor(bossIncomingLabel);
	}
	
	public void removeBossLabel(){
		stage.getRoot().removeActor(bossIncomingLabel);
	}
	
	public void lightUpJumpLabel(){
		jumpLabel.setColor(Color.ORANGE);
	}
	
	public void lightUpAttackLabel(){
		attackLabel.setColor(Color.ORANGE);
	}
	
	public void returnJumpLabelToNormal(){
		jumpLabel.setColor(Color.WHITE);
	}
	
	public void returnAttackLabelToNormal(){
		attackLabel.setColor(Color.WHITE);
	}
	
	//GETTERS AND SETTERS
	
	public BitmapFont getBitFont() {
		return bitFont;
	}

	public void setBitFont(BitmapFont bitFont) {
		this.bitFont = bitFont;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public LabelStyle getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(LabelStyle fontStyle) {
		this.fontStyle = fontStyle;
	}

	public Label getPointsLabel() {
		return pointsLabel;
	}

	public void setPointsLabel(Label pointsLabel) {
		this.pointsLabel = pointsLabel;
	}

	public Label getJumpLabel() {
		return jumpLabel;
	}

	public void setJumpLabel(Label jumpLabel) {
		this.jumpLabel = jumpLabel;
	}

	public Label getAttackLabel() {
		return attackLabel;
	}

	public void setAttackLabel(Label attackLabel) {
		this.attackLabel = attackLabel;
	}
}
