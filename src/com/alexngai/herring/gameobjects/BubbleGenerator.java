package com.alexngai.herring.gameobjects;

import android.util.Log;

import com.alexngai.framework.Image;
import com.alexngai.herring.Assets;

public class BubbleGenerator {
	
	BubblePool bubblepool;
	private final double spawnChance = 0.02;
	private final double spawnChance2 = 0.005;
	private final float bubbleSpeed = -1;
	
	public BubbleGenerator(BubblePool bubblepool){
		this.bubblepool=bubblepool;
	}
	
	public void update(GameCharacter gchar){
		int posY = gchar.getPosY();
		int posX = gchar.isLeft() ? gchar.getPosX():(gchar.getPosX()+gchar.getIm_width());
		
		if (Math.random() < spawnChance){
			
			bubblepool.addBubble((float)(.05), posX, posY, 0f, bubbleSpeed);
			//Log.d("Enemy", "created enemy " + created + ", pos:" + posX);
		}
		if (Math.random() < spawnChance2){
			bubblepool.addBubble((float)(.05), 410, 450, 0f, bubbleSpeed);
		}
		if (Math.random() < spawnChance2){
			bubblepool.addBubble((float)(.05), 790, 450, 0f, bubbleSpeed);
		}
		
		//x:410, y:450
		//x:790, y:450
	}
	
	public void update(){

		if (Math.random() < spawnChance2){		
			bubblepool.addBubble((float)(.05), 410, 450, 0f, bubbleSpeed);
			Log.d("Bubble", "created bubble ");
		}
		if (Math.random() < spawnChance2){
			bubblepool.addBubble((float)(.05), 790, 450, 0f, bubbleSpeed);
			Log.d("Bubble", "created bubble ");
		}
		
		//x:410, y:450
		//x:790, y:450
	}
}
