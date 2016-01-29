package com.alexngai.herring;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

import com.alexngai.framework.Game;
import com.alexngai.framework.Graphics;
import com.alexngai.framework.Screen;
import com.alexngai.framework.Input.TouchEvent;
import com.alexngai.herring.GameScreen.GameState;
import com.alexngai.herring.gameobjects.BubbleGenerator;
import com.alexngai.herring.gameobjects.BubblePool;
import com.alexngai.herring.gameobjects.EnemyGenerator;
import com.alexngai.herring.gameobjects.EnemyPool;


public class MainMenuScreen extends Screen {

	enum GameState {
        Main, Settings
    }
	GameState state = GameState.Main;
	
	Paint paint, paint2, paint3, paint4;

	GameButton play_button;
	GameButton settings_button;
    
    GameButton returnmenu_button;
    GameButton volume_button;
    GameButton volume_sfx_button;
    GameButton joystick_button;
	
	
	private BubblePool bubblepool;
	private BubbleGenerator bubbleGenerator;
	
	//private EnemyPool enemypool;
	//private EnemyGenerator enemyGenerator;
	
	private ScreenAdapter adapter;
	private float ratioX;
	
	public MainMenuScreen(Game game) {
        super(game);
        
        adapter = new ScreenAdapter(game.getWidth(), game.getHeight());
        ratioX = adapter.getRatio();
        setupPaint();
        importSettings(game);
        
        play_button = new GameButton(350, 300, 140, 50, "Play", 30, Assets.ic_button, ratioX);
        settings_button = new GameButton(510, 300, 190, 50, "Settings", 30, Assets.ic_button, ratioX);
        returnmenu_button = new GameButton(400, 350, 220, 50, "Main Menu", 30, Assets.ic_button, ratioX);
        
		//enemypool = new EnemyPool(20);
		//enemyGenerator = new EnemyGenerator(enemypool);
		bubblepool = new BubblePool();
		bubbleGenerator = new BubbleGenerator(bubblepool);
    }


    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (state == GameState.Main)
            updateMain(touchEvents, deltaTime);
        if (state == GameState.Settings)
            updateSettings(touchEvents);
    }

    private void updateMain(List<TouchEvent> touchEvents, float deltaTime) {
    	
    	int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 = event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_UP) {
                if (play_button.selected(x0, y0)) {
                    //START GAME
                    game.setScreen(new GameScreen(game));               
                }
                else if (settings_button.selected(x0, y0)){
                	state = GameState.Settings;
                }
            }
        }
        
		//enemyGenerator.update();
		//enemypool.update(deltaTime);
		bubbleGenerator.update();
        bubblepool.update(deltaTime);
    }
    
    private void updateSettings(List<TouchEvent> touchEvents) {
    	//updates ready screen
    	int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            //Point p1 = adapter.translateValues(event.x, event.y);
            int x0 =event.x;
            int y0 = event.y;
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (returnmenu_button.selected(x0, y0)){
                	state = GameState.Main;
                }
                
                else if (joystick_button.selected(x0, y0)){
                	toggleJoystick();
                	GameSettings.save(game.getFileIO());
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
            }        
        }
    }
    
    
    @Override
    public void paint(float deltaTime) {
    	
        if (state == GameState.Main)
            drawMainUI(deltaTime);
        if (state == GameState.Settings)
            drawSettingsUI();
    }
    
    private void drawMainUI(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawScaledImage(Assets.menu, 0, 0, 1024, 512);
        g.drawScaledImage(Assets.avatar, 400, 100, 220, 60);
        
        //enemypool.paint(g, deltaTime);
        bubblepool.paint(g, deltaTime);
        
        g.drawString("Herring", 512, 250, paint);
        play_button.paint(g);
        settings_button.paint(g);
    }
    
    private void drawSettingsUI(){
    	Graphics g = game.getGraphics();
    	g.drawScaledImage(Assets.menu, 0, 0, 1024, 512);
        g.drawARGB(155, 0, 0, 0);
        g.drawRect(300, 0, 500, 512, 0x32000000);
        
        g.drawString("Settings", 512, 70, paint2);
        returnmenu_button.paint(g);
        
        g.drawString("Music:", 450, 140, paint3);
        g.drawString("SFX:", 450, 220, paint3);
        g.drawString("Control:", 450, 300, paint3);
        volume_button.paint(g);
        volume_sfx_button.paint(g);
        joystick_button.paint(g);
        
        g.drawString("Credits", 150, 70, paint2);
        g.drawString("Created by Alex Ngai", 50, 140, paint4);
        g.drawString("SFX from freeSFX.com", 50, 180, paint4);
        g.drawString("Music 'H2O' from DST", 50, 220, paint4);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void backButton() {
        //Display "Exit Game?" Box
    }
    
    private void importSettings(Game game){
        //load in file settings
        GameSettings.load(game.getFileIO());
        
        if (GameSettings.isSoundEnabled()){
            volume_button = new GameButton(600, 90, 64, 64, Assets.ic_volumeOn);
        } else{
            volume_button = new GameButton(600, 90, 64, 64, Assets.ic_volumeOff);
        }
        if (GameSettings.isSFXEnabled()){
            volume_sfx_button = new GameButton(600, 170, 64, 64, Assets.ic_volumeOn);
        } else{
            volume_sfx_button = new GameButton(600, 170, 64, 64, Assets.ic_volumeOff);
        }
        
        //load control settings
        switch (GameSettings.getControlState()) {
		case 1:
			joystick_button = new GameButton(480, 270, 300, 50, "Joystick: Right", 30, ratioX);
			break;
		
		case 2:
    		joystick_button = new GameButton(480, 270, 300, 50, "Joystick: Left", 30, ratioX);
			break;
			
		case 3:
			joystick_button = new GameButton(480, 270, 300, 50, "Tap Screen", 30, ratioX);
			break;
		default:
			break;
		}
    }
    
    public void toggleJoystick(){
    	if (GameSettings.getControlState() == 3){
    		//control 1 left joystick
    		GameSettings.setControlState(1);
    		joystick_button.setTitle("Joystick: Right");
    	} else if (GameSettings.getControlState() == 1) {
    		//control 2 right joystick
    		GameSettings.setControlState(2);
    		joystick_button.setTitle("Joystick: Left");
    	} else if (GameSettings.getControlState() == 2){
    		//control 3 tap mode
    		GameSettings.setControlState(3);
    		joystick_button.setTitle("Tap Screen");
    	}
    	
    }
    
    public void mute(){
    	GameSettings.setSoundEnabled(false);
    	volume_button.setIcon(Assets.ic_volumeOff);
    }
    
    public void unmute(){
    	GameSettings.setSoundEnabled(true);
    	volume_button.setIcon(Assets.ic_volumeOn);
    }
    public void muteSFX(){
    	GameSettings.setSFXEnabled(false);
    	volume_sfx_button.setIcon(Assets.ic_volumeOff);
    }
    
    public void unmuteSFX(){
    	GameSettings.setSFXEnabled(true);
    	volume_sfx_button.setIcon(Assets.ic_volumeOn);
    }

    public void setupPaint(){
        Typeface tf = Typeface.create("Georgia", Typeface.BOLD_ITALIC);
        Typeface tf2 = Typeface.create("Helvetica", Typeface.NORMAL);
        paint = new Paint();
        paint.setTextSize((int)(100*adapter.getRatio()));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        
        Typeface tf3 = Typeface.create("Futura", Typeface.NORMAL);
        paint2 = new Paint();
        paint2.setTextSize((int)(40*adapter.getRatio()));
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);
        paint2.setTypeface(tf3);
        
        paint3 = new Paint();
        paint3.setTextSize((int)(30*adapter.getRatio()));
        paint3.setTextAlign(Paint.Align.CENTER);
        paint3.setAntiAlias(true);
        paint3.setColor(Color.WHITE);
        paint3.setTypeface(tf2);
        
        paint4 = new Paint();
        paint4.setTextSize((int)(20*adapter.getRatio()));
        paint4.setAntiAlias(true);
        paint4.setColor(Color.WHITE);
        paint4.setTypeface(tf2);
    }
}
