package com.alexngai.herring.gameobjects;

import com.alexngai.framework.Graphics;

public class ObjectPool {

	protected GameObject[] objarray;
	protected int maxNumObjects;
	
	public ObjectPool(){}
	
	public ObjectPool(int maxNumObjects){
		this.maxNumObjects = maxNumObjects;
		objarray = new GameObject[maxNumObjects]; 
		
		for (int i=0; i<maxNumObjects; i++){
			objarray[i] = new GameObject();
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
				Enemy temp = (Enemy) objarray[i];
				objarray[i].setInUse(true);
				objarray[i].reconstruct(scale, posX, posY, velX, velY);
				return i;
			}
		}
		
		return -1;
	}
	
	public GameObject[] getObjarray(){
		return objarray;
	}
}
