package com.alexngai.herring.gameobjects;

import com.alexngai.framework.Graphics;

public class EnemyPool {
	
	private Enemy[] objarray;
	private int maxNumObjects;
	
	public EnemyPool(){
		
	}
	
	public EnemyPool(int maxNumObjects){
		this.maxNumObjects = maxNumObjects;
		
		objarray = new Enemy[maxNumObjects]; 
		for (int i=0; i<maxNumObjects; i++){
			objarray[i] = new Enemy();
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
	
	public int addEnemy(int enemyTypeID, float scale, int posX, int posY, float velX, float velY){
		for (int i=0; i<maxNumObjects; i++){
			if (!objarray[i].isInUse()){
				objarray[i].setInUse(true);
				int tpX = posX;
				//shift object if it was spawned on the left of the screen
				if (posX <= 0) tpX = (0 - objarray[i].getIm_width());
				objarray[i].reconstruct(enemyTypeID, scale, tpX, posY, velX, velY);
				return i;
			}
		}
		return -1;
	}
	
	public Enemy[] getObjarray(){
		return objarray;
	}

	public int getMaxNumObjects() {
		return maxNumObjects;
	}

	public void setMaxNumObjects(int maxNumObjects) {
		this.maxNumObjects = maxNumObjects;
		objarray = new Enemy[maxNumObjects]; 
		for (int i=0; i<maxNumObjects; i++){
			objarray[i] = new Enemy();
		}	
	}
	
	public void clearAllObjects(){
		for (int i=0; i<maxNumObjects; i++){
			objarray[i].setInUse(false);
		}
	}
	

}
