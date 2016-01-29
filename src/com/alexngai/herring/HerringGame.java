package com.alexngai.herring;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alexngai.framework.Screen;
import com.alexngai.framework.implementation.AdManager;
import com.alexngai.framework.implementation.AdManager.GameAdListener;
import com.alexngai.framework.implementation.AndroidGame;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class HerringGame extends AndroidGame implements GameAdListener{
	
	private static final String AD_UNIT_ID = "ca-app-pub-1171910688919463/6645049432";
	private InterstitialAd interstitialAd;
	private static final String LOG_TAG = "InterstitialSample";
	private AdManager admanager;
	
	private Handler adHandler;
	public static final int AD_LOAD = 1;
	public static final int AD_SHOW = 2;
	
	public AdManager getAdManager(){
		return admanager;
	}
	
	public Handler getHandler(){
		return adHandler;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		interstitialAd = new InterstitialAd(this);
	    interstitialAd.setAdUnitId(AD_UNIT_ID);
	    //loadInterstitial();
	    admanager = new AdManager();
	    admanager.registerListener(this);
	    
	    adHandler = new Handler(Looper.getMainLooper()){
	    	@Override
	        public void handleMessage(Message inputMessage) {
	            switch (inputMessage.what) {
				case AD_LOAD:
					loadInterstitial();
					break;
					
				case AD_SHOW:
					showInterstitial();
					break;
				}	            
	        }
	    };
	    
	}
	
	//@Override
	public void onLoadAd(boolean state){
		if (state){
			loadInterstitial();
			//showInterstitial();
			//Log.d("Adtest", "Event received, load ad");
		}
		//loadInterstitial();
	}
	
	public void onShowAd(boolean state){
		if (state){
			showInterstitial();
		}
	}
	
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this); 
    }

    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
    
    public void loadInterstitial() {

        // Check the logcat output for your hashed device ID to get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
            //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
            .build();

        // Load the interstitial ad.
        interstitialAd.loadAd(adRequest);
      }
    
    public void showInterstitial() {
        // Disable the show button until another interstitial is loaded.
        if (interstitialAd.isLoaded()) {
          interstitialAd.show();
        } else {
          Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
        }
      }
    
    
}