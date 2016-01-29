package com.alexngai.herring;

import java.util.List;

import android.R.integer;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Message;
import android.util.Log;

import com.alexngai.framework.Audio;
import com.alexngai.framework.Game;
import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;
import com.alexngai.framework.Music;
import com.alexngai.framework.Screen;
import com.alexngai.framework.Input.TouchEvent;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }
    GameState state = GameState.Ready;
    
    // Variable Setup
    GameInstance gameInstance;
    Paint paint, paint2, paint3, paint4, paint5;
    Music bg_song;
    GameButton settings_button;
    
    GameButton resume_button;
    GameButton mainmenu_button;
    GameButton returnmenu_button;
    GameButton volume_button;
    GameButton volume_sfx_button;
    GameButton joystick_button;
    GameButton easy_button, medium_button, hard_button;
    
    ScreenAdapter adapter;
    float ratioX;
    
    public GameScreen(Game game) {
        super(game);
        
        adapter = new ScreenAdapter(game.getWidth(), game.getHeight());
        ratioX = adapter.getRatio();
        // Initialize game objects here
        gameInstance = new GameInstance(game);
  
        setupPaint();
        
        settings_button = new GameButton(969, 5, 50, 50, Assets.ic_settings);
        resume_button = new GameButton(340, 350, 180, 50, "Resume", 30, Assets.ic_button, ratioX);
        mainmenu_button = new GameButton(540, 350, 220, 50, "Main Menu", 30, Assets.ic_button, ratioX);
        returnmenu_button = new GameButton(362, 350, 300, 50, "Return to Menu", 30, Assets.ic_button, ratioX);
        
        easy_button = new GameButton(650, 150, 300, 75, "Easy (x1)", 40, Assets.ic_button, ratioX);
        medium_button = new GameButton(650, 250, 300, 75, "Medium (x1.3)", 40, Assets.ic_button, ratioX);
        hard_button = new GameButton(650, 350, 300, 75, "Hard (x2)", 40, Assets.ic_button, ratioX);
        
        importSettings(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        
    	//updates ready screen
    	int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (easy_button.selected(x0, y0)){
                    state = GameState.Running;
                    bg_song.play();
                    Difficulty.setDifficulty(Difficulty.EASY, gameInstance);
                }
                else if (medium_button.selected(x0, y0)){
                    state = GameState.Running;
                    bg_song.play();
                    Difficulty.setDifficulty(Difficulty.MEDIUM, gameInstance);
                }
                else if (hard_button.selected(x0, y0)){
                    state = GameState.Running;
                    bg_song.play();
                    Difficulty.setDifficulty(Difficulty.HARD, gameInstance);
                }
                if (state == GameState.Running){ //game.getAdManager().notifyLoad(true);
                	Message completeMessage = game.getHandler().obtainMessage(1);
    				completeMessage.sendToTarget();
                }
            }        
        }
    }

    //update game while it is running
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        gameInstance.update(touchEvents, deltaTime);
        
        // 1. Check settings button pressed
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
            	if (settings_button.selected(x0, y0)){
            		state = GameState.Paused;
            		bg_song.pause();
            	}
            }        
        }
        
        // 2. Check miscellaneous events like death:
        
        if (gameInstance.getlivesLeft() == 0) {
            state = GameState.GameOver;
            bg_song.stop();
        }
        
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_UP) {
            	
            	//resume game
            	if (resume_button.selected(x0, y0)){
            		state = GameState.Running;
            		bg_song.play();
            	}
            	//Set game to game over
            	else if (mainmenu_button.selected(x0, y0)){
            		state = GameState.GameOver;
            		bg_song.stop();
            	}
            	else if (volume_button.selected(x0, y0)){
            		if (!GameSettings.isSoundEnabled()){
            			unmute();
            		} else mute();
            		GameSettings.save(game.getFileIO());
            	}
            	else if (volume_sfx_button.selected(x0, y0)){
            		if (!GameSettings.isSFXEnabled()){
            			unmuteSFX();
            		} else muteSFX();
            		GameSettings.save(game.getFileIO());
            	}
            	else if (joystick_button.selected(x0, y0)){
            		toggleJoystick();
            		GameSettings.save(game.getFileIO());
            	}
            }
            
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
    	
    	if (gameInstance.getScore() > GameSettings.getHighScore()){
    		GameSettings.setHighScore(gameInstance.getScore());
    		GameSettings.save(game.getFileIO());
    	}
    	
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_UP) {
            	if (returnmenu_button.selected(x0, y0)) {
                	nullify();
                    //game.getAdManager().notifyShow(true);
                	Message completeMessage = game.getHandler().obtainMessage(2);
        			completeMessage.sendToTarget();
                	
                	game.setScreen(new MainMenuScreen(game));
                    
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        //draw the game elements.
        gameInstance.paint(g, deltaTime);
        
        //draw the UI above the game elements.
        settings_button.paint(g);
        
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void nullify() {

        // Set all variables to null, they will be recreated in the constructor.
        paint = null;

        // Call garbage collector 
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("How to Play:", 300, 100, paint2);
        g.drawString("1. Eat smaller fish", 300, 160, paint4);
        g.drawString("2. Tap to move", 300, 220, paint4);
        g.drawString("(Joystick in settings)", 300, 260, paint3);
        g.drawScaledImage(Assets.avatar, 100, 300, 240, 80);
        g.drawScaledImage(Assets.sardine, 380, 322, 100, 35);
        g.drawString("3. Become the biggest fish!", 300, 440, paint4);
        
        g.drawString("Difficulty", 800, 120, paint2);
        easy_button.paint(g);
        medium_button.paint(g);
        hard_button.paint(g);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused/Settings screen.
        g.drawARGB(155, 0, 0, 0);
        
        g.drawString("Paused", 512, 70, paint2);
        resume_button.paint(g);
        mainmenu_button.paint(g);
        
        g.drawString("Music:", 450, 140, paint4);
        g.drawString("SFX:", 450, 220, paint4);
        g.drawString("Control:", 450, 300, paint4);
        volume_button.paint(g);
        volume_sfx_button.paint(g);
        joystick_button.paint(g);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawARGB(190, 0, 0, 0);
        g.drawString("GAME OVER", 510, 150, paint2);
        g.drawString("Score: " + gameInstance.getScore(), 510, 220, paint);
        g.drawString("High Score: " + GameSettings.getHighScore(), 510, 270, paint);
        returnmenu_button.paint(g);

    }

    @Override
    public void pause() {
        if (state == GameState.Running){
            state = GameState.Paused;
        	bg_song.pause();
        }

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void backButton() {
        pause();
    }
    
    public void mute(){
    	GameSettings.setSoundEnabled(false);
    	bg_song.setVolume(0);
    	volume_button.setIcon(Assets.ic_volumeOff);
    }
    
    public void unmute(){
    	GameSettings.setSoundEnabled(true);
    	bg_song.setVolume(.5f);
    	volume_button.setIcon(Assets.ic_volumeOn);
    }
    
    public void muteSFX(){
    	GameSettings.setSFXEnabled(false);
    	gameInstance.setVolumeLevel(0);
    	volume_sfx_button.setIcon(Assets.ic_volumeOff);
    }
    
    public void unmuteSFX(){
    	GameSettings.setSFXEnabled(true);
    	gameInstance.setVolumeLevel(1f);
    	volume_sfx_button.setIcon(Assets.ic_volumeOn);
    }
    
    public void toggleJoystick(){
    	if (GameSettings.getControlState() == 3){
    		//control 1 left joystick
    		gameInstance.joystickMode(true);
    		gameInstance.setJoystickPosition(870, 370);
    		GameSettings.setControlState(1);
    		joystick_button.setTitle("Joystick: Right");
    	} else if (GameSettings.getControlState() == 1) {
    		//control 2 right joystick
    		gameInstance.joystickMode(true);
    		gameInstance.setJoystickPosition(150, 370);
    		GameSettings.setControlState(2);
    		joystick_button.setTitle("Joystick: Left");
    	} else if (GameSettings.getControlState() == 2){
    		//control 3 tap mode
    		gameInstance.joystickMode(false);
    		GameSettings.setControlState(3);
    		joystick_button.setTitle("Tap Screen");
    	}
    }
    
    private void setupPaint(){
    	Typeface tf = Typeface.create("Futura", Typeface.BOLD);
        // Defining a paint object
        paint = new Paint();
        paint.setTextSize((int)(40*adapter.getRatio()));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        
        Typeface tf2 = Typeface.create("Futura", Typeface.NORMAL);
        paint2 = new Paint();
        paint2.setTextSize((int)(50*adapter.getRatio()));
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);
        paint2.setTypeface(tf2);

        paint3 = new Paint();
        paint3.setTextSize((int)(25*adapter.getRatio()));
        paint3.setTextAlign(Paint.Align.CENTER);
        paint3.setAntiAlias(true);
        paint3.setColor(Color.WHITE);
        paint3.setTypeface(tf);
        
        Typeface tf3 = Typeface.create("Helvetica", Typeface.NORMAL);
        paint4 = new Paint();
        paint4.setTextSize((int)(30*adapter.getRatio()));
        paint4.setTextAlign(Paint.Align.CENTER);
        paint4.setAntiAlias(true);
        paint4.setColor(Color.WHITE);
        paint4.setTypeface(tf3);
    
        paint5 = new Paint();
        paint5.setTextSize((int)(30*adapter.getRatio()));
        paint5.setAntiAlias(true);
        paint5.setColor(Color.WHITE);
        paint5.setTypeface(tf3);
    }
    
    private void importSettings(Game game){
        //load in file settings
        GameSettings.load(game.getFileIO());
        
        bg_song = Assets.bg_music;
        bg_song.setLooping(true);
        if (GameSettings.isSoundEnabled()){
        	bg_song.setVolume(.5f);
        	gameInstance.setVolumeLevel(1f);
            volume_button = new GameButton(600, 90, 64, 64, Assets.ic_volumeOn);
        } else{
        	bg_song.setVolume(0);
            volume_button = new GameButton(600, 90, 64, 64, Assets.ic_volumeOff);
        }
        
        if (GameSettings.isSFXEnabled()){
        	gameInstance.setVolumeLevel(1f);
            volume_sfx_button = new GameButton(600, 170, 64, 64, Assets.ic_volumeOn);
        } else{
        	gameInstance.setVolumeLevel(0);
            volume_sfx_button = new GameButton(600, 170, 64, 64, Assets.ic_volumeOff);
        }
        
        //load control settings
        switch (GameSettings.getControlState()) {
		case 1: 
			gameInstance.joystickMode(true);
			gameInstance.setJoystickPosition(870, 370);
			joystick_button = new GameButton(480, 270, 300, 50, "Joystick: Right", 30, ratioX);
			break;
		
		case 2:
			gameInstance.joystickMode(true);
    		gameInstance.setJoystickPosition(150, 370);
    		joystick_button = new GameButton(480, 270, 300, 50, "Joystick: Left", 30, ratioX);
			break;
			
		case 3:
			gameInstance.joystickMode(false);
			joystick_button = new GameButton(480, 270, 300, 50, "Tap Screen", 30, ratioX);
			break;
		

		default:
			break;
		}
    }
    
}