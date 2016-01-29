package com.alexngai.herring.gameobjects;

import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;
import com.alexngai.herring.Assets;

public class Bubble extends GameObject{

	static final Image curImage = Assets.bubble;
	
	public Bubble(float size) {
		super(curImage ,size);
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		checkLifetime();
	}
	
	public void paint(Graphics g, float deltaTime){
		g.drawScaledImage(curImage, getPosX(), getPosY(), getIm_width(), getIm_height());
	}
	
	private void unuse(){
		super.setInUse(false);
		//super.setPosX(500);
		//Log.d("Enemy", "died" + id);
	}
	
	private void checkLifetime(){
		int y = getPosY();
		if (y < 0 || y > 512) unuse();
	}
}
