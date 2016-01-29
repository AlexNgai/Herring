package com.alexngai.framework;

import android.os.Handler;

import com.alexngai.framework.implementation.AdManager;

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();
    
    public int getHeight();
    
    public int getWidth();

    public void loadInterstitial();
    public void showInterstitial();
    public AdManager getAdManager();
    public Handler getHandler();

	//void onStateChange(boolean state);
    
}