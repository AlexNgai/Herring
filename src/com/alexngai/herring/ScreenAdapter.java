package com.alexngai.herring;

import android.graphics.Point;

public class ScreenAdapter {
	
	private final int gameMaxX = 1024;
	private final int gameMaxY = 512;
	private final int realMaxX;
	private final int realMaxY;
	private final float ratioX;
	private final float ratioY;
	
	public ScreenAdapter(int x, int y){
		realMaxX = x;
		realMaxY = y;
		ratioX = ((float)realMaxX/(float)gameMaxX);
		ratioY = ((float)realMaxY/(float)gameMaxY);
	}
	
	public Point translateValues(int posX, int posY){
		Point point = new Point();
		int x = (int) (posX * ratioX);
		int y = (int) (posY * ratioY);
		point.set(x, y);
		return point;
	}
	
	public float translateVal(float posX){
		return (posX * ratioX);
	}
	
	public int translateX(int posX){
		return (int) (posX * ratioX);
	}
	
	public int translateY(int posY){
		return (int) (posY * ratioY);
	}
	
	public float getRatio(){
		return ratioX;
	}
}
