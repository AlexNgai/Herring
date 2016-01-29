package com.alexngai.herring;

import android.graphics.Color;
import android.graphics.Paint;

import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;

public class Joystick {

	public static int LEFT = 1;
	public static int RIGHT = 2;
	
	private int c_posX;
	private int c_posY;
	private float radius;
	private int posX;
	private int posY;
	
	private final int padcolor = 0x50FFFFFF;
	private final int stickcolor = 0x96F59C20; 
	Paint padPaint;
	Paint stickPaint;
	
	private float ctrl_x;
	private float ctrl_y;
	private float mag;
	
	private Image icon;
	
	public Joystick(int c_posX, int c_posY, float radius){
		this.c_posX = c_posX;
		this.c_posY = c_posY;
		this.radius = radius;
		posX = c_posX;
		posY = c_posY;
		
		padPaint = new Paint();
		stickPaint = new Paint();
		padPaint.setColor(padcolor);
		stickPaint.setColor(stickcolor);
		
		ctrl_x = 0;
		ctrl_y = 0;
		mag = 0;
	}
	
	public void paint(Graphics g){
		g.drawCircle(c_posX, c_posY, radius, padPaint);
		g.drawCircle(posX, posY, radius/3, stickPaint);
	}
	
	public boolean selected(int x, int y){
		return (Math.sqrt(Math.pow(x-c_posX, 2) + Math.pow(y-c_posY, 2)) < radius);
	}
	
	public void setStickPosition(int x, int y){
		posX = x;
		posY = y;
		updateCtrl();
	}
	
	public void setCenterPosition(int x, int y){
		c_posX = x;
		c_posY = y;
		posX = x;
		posY = y;
		updateCtrl();
	}
	
	private void updateCtrl(){
		float t = (float) (Math.sqrt(Math.pow(posX-c_posX, 2) + Math.pow(posY-c_posY,2)));
		ctrl_x = (posX - c_posX)/radius;
		ctrl_y = (posY - c_posY)/radius;
		mag = t/radius;
	}
	
	public void centerStick(){
		posX = c_posX;
		posY = c_posY;
	}

	public float getCtrl_x() {
		return ctrl_x;
	}

	public float getCtrl_y() {
		return ctrl_y;
	}

	public float getMag() {
		return mag;
	}
	
}
