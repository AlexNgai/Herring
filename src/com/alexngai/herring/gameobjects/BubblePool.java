package com.alexngai.herring.gameobjects;

import com.alexngai.framework.Graphics;
import com.alexngai.herring.Assets;

public class BubblePool {
	private Bubble[] objarray;
	private final int maxNumObjects = 8;
	
	
	public BubblePool(){
		objarray = new Bubble[maxNumObjects]; 
		for (int i=0; i<maxNumObjects; i++){
			objarray[i] = new Bubble((float)(0.15));
		}
	}
	
	public void paint(Graphics g, float deltaTime){
		
		for (int i=0; i<maxNumObjects; i++){
			if(objarray[i].isInUse()){
				objarray[i].paint(g, deltaTime);
			}
		}
	}
	
	public void update(float deltaTime){

		for (int i=0; i<maxNumObjects; i++){
			if(objarray[i].isInUse()){
				objarray[i].update(deltaTime);
			}
		}
	}
	
	public int addBubble(float scale, int posX, int posY, float velX, float velY){

		for (int i=0; i<maxNumObjects; i++){
			if (!objarray[i].isInUse()){
				objarray[i].setInUse(true);
				objarray[i].reconstruct(scale, posX, posY, velX, velY);
				return i;
			}
		}
		
		return -1;
	}
	
	public Bubble[] getObjarray(){
		return objarray;
	}
}
