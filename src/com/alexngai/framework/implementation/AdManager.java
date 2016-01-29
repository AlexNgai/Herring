package com.alexngai.framework.implementation;

import com.alexngai.framework.Game;

public class AdManager {
	
	GameAdListener mListener;
	Game game;
	
	public interface GameAdListener {
        public void onShowAd(boolean state);
        public void onLoadAd(boolean state);
    }
    
    public void registerListener (GameAdListener listener) {
        mListener = listener;
    }
    
    public void notifyLoad(boolean b) {
        // now notify if someone is interested.
        if (mListener != null && b)
            mListener.onLoadAd(true);
    }
    
    public void notifyShow(boolean b) {
        // now notify if someone is interested.
        if (mListener != null && b)
            mListener.onShowAd(true);
    }
    
}
