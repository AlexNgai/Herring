package com.alexngai.herring;

import android.R.integer;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.alexngai.framework.Game;
import com.alexngai.framework.Graphics;
import com.alexngai.framework.Screen;
import com.alexngai.framework.Graphics.ImageFormat;
import com.alexngai.framework.Audio;

public class LoadingScreen extends Screen {
    
	Audio androidaudio;
	
	Paint paint;
	
	int count = 0;
	
	public LoadingScreen(Game game) {
        super(game);  
        
        Typeface tf = Typeface.create("Georgia", Typeface.BOLD_ITALIC);
        paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        
        Assets.loadscreenchar = game.getGraphics().newImage("fishy_load.png", ImageFormat.RGB565);
    }


    @Override
    public void update(float deltaTime) {
        
    	addAssets();
        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void paint(float deltaTime) {
    	Graphics g = game.getGraphics();
        g.drawImage(Assets.loadscreenchar, 310, 200);
        g.drawString("Herring", 510, 300, paint);

    }

    private void addAssets(){
    	Graphics g = game.getGraphics();
        Audio audio = game.getAudio();
        
        Assets.menu = g.newImage("ocean_background.png", ImageFormat.RGB565);
        
        Assets.avatar = g.newImage("fishy.png", ImageFormat.RGB565);
        Assets.avatar2 = g.newImage("fishy2.png", ImageFormat.RGB565);
        Assets.avatarflip = g.newImage("fishy_flip.png", ImageFormat.RGB565);
        Assets.avatar2flip = g.newImage("fishy2_flip.png", ImageFormat.RGB565);
        
        Assets.background = g.newImage("ocean_background.png", ImageFormat.RGB565);
        Assets.bubble = g.newImage("bubble.png", ImageFormat.RGB565);
        
        Assets.bg_music = audio.createMusic("game_music.mp3");
        Assets.gulp = audio.createSound("gulp.mp3");
        Assets.crunch = audio.createSound("crunch.mp3");
        Assets.explosion = audio.createSound("explosion.mp3");
        
        Assets.ic_settings = g.newImage("ic_action_settings.png", ImageFormat.RGB565);
        Assets.ic_button = g.newImage("ic_button.png", ImageFormat.RGB565);
        Assets.ic_volumeOn = g.newImage("ic_action_volume_on.png", ImageFormat.RGB565);
        Assets.ic_volumeOff = g.newImage("ic_action_volume_muted.png", ImageFormat.RGB565);
        
        Assets.halo_yellow = g.newImage("halo_yellow2.png", ImageFormat.RGB565);
        
        AddEnemyAssets();
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


    }
    
    private void AddEnemyAssets(){
    	Graphics g = game.getGraphics();
    	Assets.bluefish = g.newImage("bluefish.png", ImageFormat.RGB565);
        Assets.bluefish2 = g.newImage("bluefish2.png", ImageFormat.RGB565);
        Assets.bluefishflip = g.newImage("bluefish_flip.png", ImageFormat.RGB565);
        Assets.bluefish2flip = g.newImage("bluefish2_flip.png", ImageFormat.RGB565);
        
        Assets.sardine = g.newImage("sardine.png", ImageFormat.RGB565);
        Assets.sardine2 = g.newImage("sardine2.png", ImageFormat.RGB565);
        Assets.sardineflip = g.newImage("sardine_flip.png", ImageFormat.RGB565);
        Assets.sardine2flip = g.newImage("sardine2_flip.png", ImageFormat.RGB565);
        
        Assets.bass = g.newImage("bass.png", ImageFormat.RGB565);
        Assets.bass2 = g.newImage("bass2.png", ImageFormat.RGB565);
        Assets.bassflip = g.newImage("bass_flip.png", ImageFormat.RGB565);
        Assets.bass2flip = g.newImage("bass2_flip.png", ImageFormat.RGB565);
        
        Assets.seabream = g.newImage("seabream.png", ImageFormat.RGB565);
        Assets.seabream2 = g.newImage("seabream2.png", ImageFormat.RGB565);
        Assets.seabreamflip = g.newImage("seabream_flip.png", ImageFormat.RGB565);
        Assets.seabream2flip = g.newImage("seabream2_flip.png", ImageFormat.RGB565);
        
        Assets.tuna = g.newImage("tuna.png", ImageFormat.RGB565);
        Assets.tuna2 = g.newImage("tuna2.png", ImageFormat.RGB565);
        Assets.tunaflip = g.newImage("tuna_flip.png", ImageFormat.RGB565);
        Assets.tuna2flip = g.newImage("tuna2_flip.png", ImageFormat.RGB565);
        
        Assets.swordfish = g.newImage("swordfish.png", ImageFormat.RGB565);
        Assets.swordfish2 = g.newImage("swordfish2.png", ImageFormat.RGB565);
        Assets.swordfishflip = g.newImage("swordfish_flip.png", ImageFormat.RGB565);
        Assets.swordfish2flip = g.newImage("swordfish2_flip.png", ImageFormat.RGB565);
        
        Assets.carp = g.newImage("carp.png", ImageFormat.RGB565);
        Assets.carp2 = g.newImage("carp2.png", ImageFormat.RGB565);
        Assets.carpflip = g.newImage("carp_flip.png", ImageFormat.RGB565);
        Assets.carp2flip = g.newImage("carp2_flip.png", ImageFormat.RGB565);
    }
}