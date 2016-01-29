package com.alexngai.herring.gameobjects;

import java.math.RoundingMode;

import android.util.Log;

import com.alexngai.framework.Image;
import com.alexngai.herring.Assets;

public class EnemyGenerator {

	EnemyPool enemypool;
	private double spawnChance = 0.03;
	private int numTypes = 1;
	private final int maxNumTypes = 7;
	private double enemySpeed = 2.5;
	private double minEnemySpeed = .5;
	private double enemySize = 1;
	
	public EnemyGenerator(EnemyPool enemypool){
		this.enemypool=enemypool;
	}
	
	public void update(){
		float x = (float) Math.random();
		if (x < spawnChance){
			float direction = (float) Math.random();
			float speed = (float) (enemySpeed*Math.random());
			float spawnPosition = (float) (412*Math.random());
			int typeID = (int) Math.floor(numTypes*Math.random());
			float scaleMultiplier = (float) (enemySize*(0.7 + .6*Math.random())); //range .7 to 1.3
			
			//limit minimum speed
			speed = (float) ((speed < minEnemySpeed) ? minEnemySpeed:speed);
			
			int posX; int posY; float velX;
			if (direction < 0.5){
				//right
				posX = 0;
				velX = speed;
			} else{
				//left
				posX = 1024;
				velX = -speed;
			}
			
			posY = (int) spawnPosition;
			
			int created = enemypool.addEnemy(typeID, scaleMultiplier, posX, posY, velX, 0);
			//Log.d("Enemy", "created enemy " + created + ", pos:" + posX);
		}
	}

	public double getSpawnChance() {
		return spawnChance;
	}

	public double getEnemySpeed() {
		return enemySpeed;
	}

	public double getMinEnemySpeed() {
		return minEnemySpeed;
	}

	public void setSpawnChance(double spawnChance) {
		this.spawnChance = spawnChance;
	}

	public void setEnemySpeed(double enemySpeed) {
		this.enemySpeed = enemySpeed;
	}
	
	public void setEnemySize(double enemySize) {
		this.enemySize = enemySize;
	}

	public void setMinEnemySpeed(double minEnemySpeed) {
		this.minEnemySpeed = minEnemySpeed;
	}

	public int getNumTypes() {
		return numTypes;
	}

	public void setNumTypes(int numTypes) {
		if (numTypes <= maxNumTypes) this.numTypes = numTypes;
	}
	
	
}
