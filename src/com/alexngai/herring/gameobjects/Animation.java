package com.alexngai.herring.gameobjects;

import java.util.ArrayList;
import com.alexngai.framework.Image;

public class Animation {
	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;
	
	public Animation(){
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;
		
		synchronized(this){
			animTime = 0;
			currentFrame = 0;
		}
	
	}
	
	public synchronized void addFrame(Image image, long duration){
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	//update time elapsed, frame number
	public synchronized boolean update(long elapsedTime) {
		boolean temp = false;
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;

			}
			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;
				temp = true;
			}
		}
		return temp;
	}
	
	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}
	
	//called to retrieve current animation image
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	} 
	
	//helper class stores image time pairs
	public class AnimFrame{
		Image image;
		long endTime;
		
		public AnimFrame(Image image, long endTime){
			this.image = image;
			this.endTime = endTime;
		}
	}
}
