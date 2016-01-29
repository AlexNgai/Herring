package com.alexngai.herring;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

import com.alexngai.framework.Game;
import com.alexngai.framework.Graphics;
import com.alexngai.framework.Sound;
import com.alexngai.framework.Input.TouchEvent;
import com.alexngai.herring.gameobjects.Animation;
import com.alexngai.herring.gameobjects.BubbleGenerator;
import com.alexngai.herring.gameobjects.BubblePool;
import com.alexngai.herring.gameobjects.CollisionDetector;
import com.alexngai.herring.gameobjects.Enemy;
import com.alexngai.herring.gameobjects.EnemyGenerator;
import com.alexngai.herring.gameobjects.EnemyPool;
import com.alexngai.herring.gameobjects.GameCharacter;

public class GameInstance {
	Paint p1, p2, p3, p4;
	
	private Game game;
	private int livesLeft = 1;
	private int score = 0;
	private int curLevel = 1;
	private String LevelString = "Level 1";
	private int clearCounter = 50;
	
	private GameCharacter gchar;
	private EnemyPool enemypool;
	private EnemyGenerator enemyGenerator;
	private BubblePool bubblepool;
	private BubbleGenerator bubbleGenerator;
	
	Sound gulp = Assets.gulp;
	Sound crunch = Assets.crunch;
	Sound explosion = Assets.explosion;
	private float volumeLevel = 1f;
	
	private ControlState control;
	
	private boolean joystick_mode = false;
	public Joystick joystick;
	
	private float difficultyMult = 1;
	
	Animation charAnimation;
	Animation enemyAnimation;
	
	ScreenAdapter adapter;
	
	public GameInstance(Game game){
		this.game = game;
		
		adapter = new ScreenAdapter(game.getWidth(), game.getHeight());
		
		loadSettings();
		
		//setupAnimations();
		
		gchar = new GameCharacter(0.07f);
		gchar.setPosition(525-gchar.getIm_width()/2, 20);
		gchar.setVelY(4);
		
		//object pool
		enemypool = new EnemyPool();
		enemyGenerator = new EnemyGenerator(enemypool);
		
		bubblepool = new BubblePool();
		bubbleGenerator = new BubbleGenerator(bubblepool);
		
		control = new ControlState();
		
		joystick = new Joystick(150, 370, 100); //(870, 370, 100);
		
		setupPaint();
	
	}

	public void update(List<TouchEvent> touchEvents, float deltaTime){
		
		//handle touch events
        handleTouchEvents(touchEvents);
		
        //display level title and halt enemy spawns
        if (clearCounter > 0){
        	clearCounter--;
        	p4.setAlpha(5*clearCounter);
        } else enemyGenerator.update();
		//randomly generate new enemies
		//enemyGenerator.update();
		
		//update game objects
		enemypool.update(deltaTime);
		gchar.update(deltaTime, control);
		bubbleGenerator.update(gchar);
        bubblepool.update(deltaTime);
		
		//check for collisions between objects
		Enemy e = CollisionDetector.checkCollision(gchar, enemypool);
		if (e != null){
			if ( e.getIm_width() < gchar.getIm_width()){
				e.unuse();
				gchar.grow();
				score = (int) (score + e.getIm_width());
				gulp.play(volumeLevel);
			}
			else if ( e.getIm_width() >= gchar.getIm_width()){
				livesLeft--;
				if (livesLeft == 0) {
					crunch.play(volumeLevel);
					score = (int) (score*difficultyMult);
				}
			}
		}
		
		String tempString;
		tempString = Difficulty.increaseLevel(curLevel, score);
		if (tempString != null){
			curLevel++;
			enemyGenerator.setNumTypes(curLevel);
			LevelString = tempString;
			clearCounter = 50;
			enemypool.clearAllObjects();
			explosion.play(volumeLevel);
		}
			
		
		//if (collided) livesLeft--;
		if (gchar.getIm_width() > 1024){
			livesLeft--;
		}
	}
	
	
	public void paint(Graphics g, float deltaTime){
		g.drawScaledImage(Assets.background, 0, 0, 1024, 512);
		enemypool.paint(g, deltaTime);
        bubblepool.paint(g, deltaTime);
		gchar.paint(g, deltaTime);
        
		if (joystick_mode) joystick.paint(g);
        
        //int temp =  (int) (100f/deltaTime);
        //g.drawString("" + temp + "fps", 20, 20, p1);
        g.drawString(""+score, 510, 55, p3);
        if (clearCounter > 0) g.drawString(LevelString, 512, 250, p4);
	}
	
	private void handleTouchEvents(List<TouchEvent> touchEvents){
		
		//handle touch events
        int len = touchEvents.size();
        //if (len == 0) control.setState(-1);
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
            	/*
            	if (upButton.selected(event.x, event.y)) control.setState(ControlState.UP);
            	else if (downButton.selected(event.x, event.y)) control.setState(ControlState.DOWN);
            	else if (leftButton.selected(event.x, event.y)) control.setState(ControlState.LEFT);
            	else if (rightButton.selected(event.x, event.y)) control.setState(ControlState.RIGHT);
            	*/
            	if (joystick_mode){
	            	if (joystick.selected(x0, y0)){
	            		joystick.setStickPosition(x0, y0);
	            		control.setCtrl(joystick.getCtrl_x(), joystick.getCtrl_y(), joystick.getMag());
	            	}	
            	}
            	//tap mode
            	else{
            		control.setCtrl(x0, y0);
            		
            	}
            }

            if (event.type == TouchEvent.TOUCH_UP) {
            	control.setState(ControlState.NONE);
            	if (joystick_mode) joystick.centerStick();

            }
            
            if (event.type == TouchEvent.TOUCH_DRAGGED){
            	/*
            	if (upButton.selected(event.x, event.y)) control.setState(ControlState.UP);
            	else if (downButton.selected(event.x, event.y)) control.setState(ControlState.DOWN);
            	else if (leftButton.selected(event.x, event.y)) control.setState(ControlState.LEFT);
            	else if (rightButton.selected(event.x, event.y)) control.setState(ControlState.RIGHT);
            	*/
            	if (joystick_mode){
            		if (joystick.selected(x0, y0)){
            			joystick.setStickPosition(x0, y0);
            			control.setCtrl(joystick.getCtrl_x(), joystick.getCtrl_y(), joystick.getMag());
            		}
            	//tap mode
            	} else{
            		control.setCtrl(x0, y0);
            	}
            }            
        }
	}
	
	/*
	public void setupAnimations(){
		charAnimation = new Animation();
		charAnimation.addFrame(Assets.avatar, 10);
		charAnimation.addFrame(Assets.avatar2, 10);
		
		enemyAnimation = new Animation();
		enemyAnimation.addFrame(Assets.enemy, 10);
		enemyAnimation.addFrame(Assets.enemy2, 10);
	}*/
	
	public int getlivesLeft() {
		return livesLeft;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setlivesLeft(int lives) {
		livesLeft = lives;
	}
	
	public void setJoystickPosition(int x, int y){
		joystick.setCenterPosition(x, y);
	}

	public void setVolumeLevel(float f) {
		volumeLevel = f;
	}

    public Joystick getJoystick(){
    	return joystick;
    }
    
    public void joystickMode(boolean mode){
    	joystick_mode = mode;
    	gchar.setJoystickMode(mode);
    }
	
    private void loadSettings(){
    	GameSettings.load(game.getFileIO());
    }

	public void setEnemies(int numenemies) {
		enemypool.setMaxNumObjects(numenemies);
	}

	public void setSpawnRate(double spawnChance) {
		enemyGenerator.setSpawnChance(spawnChance);
	}

	public void setSpeedMultiplier(double enemySpeed) {
		enemyGenerator.setEnemySpeed(enemySpeed);
	}
	
	public void setSizeMultiplier(double enemySize){
		enemyGenerator.setEnemySize(enemySize);
	}
	
	public void setDifficultyMult(float easyMult){
		difficultyMult = easyMult;
	}
	
	private void setupPaint(){
		Typeface tf = Typeface.create("Futura", Typeface.NORMAL);
		p1 = new Paint();
	    p1.setTextSize((int)(12*adapter.getRatio()));
	    p1.setColor(Color.WHITE);
	    
	    p2 = new Paint();
	    p2.setTextSize((int)(40*adapter.getRatio()));
	    p2.setColor(Color.WHITE);
	    p2.setTextAlign(Paint.Align.CENTER);
	    p2.setTypeface(tf);
	    
	    Typeface tf2 = Typeface.create("Futura", Typeface.NORMAL);
	    p3 = new Paint();
	    p3.setTextSize((int)(40*adapter.getRatio()));
	    p3.setColor(Color.WHITE);
	    p3.setTextAlign(Paint.Align.CENTER);
	    p3.setTypeface(tf2);
	    
	    p4 = new Paint();
	    p4.setTextSize((int)(40*adapter.getRatio()));
	    p4.setColor(Color.WHITE);
	    p4.setTextAlign(Paint.Align.CENTER);
	    p4.setTypeface(tf2);
	}
}
