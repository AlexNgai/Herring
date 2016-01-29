package com.alexngai.herring;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;

public class GameButton {

	private int posX;
	private int posY;
	private int sizeX;
	private int sizeY;
	private int color;

	
	private Image icon;
	private String title;
	
	private Paint paint;
	
	public GameButton(int posX, int posY, int sizeX, int sizeY){
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public GameButton(int posX, int posY, int sizeX, int sizeY, Image icon){
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.icon = icon;
	}
	
	public GameButton(int posX, int posY, int sizeX, int sizeY, String title, int textSize, float ratio){
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.title = title;
		
		Typeface tf = Typeface.create("Georgia", Typeface.BOLD_ITALIC);
        paint = new Paint();
        paint.setTextSize((int)(ratio*textSize));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
	}
	
	public GameButton(int posX, int posY, int sizeX, int sizeY, String title, int textSize, Image icon, float ratio){
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.title = title;
		this.icon = icon;
		
		Typeface tf = Typeface.create("Georgia", Typeface.BOLD_ITALIC);
        paint = new Paint();
        paint.setTextSize((int)(ratio*textSize));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
	}
	
	
	public void paint(Graphics g){
		if (icon != null){
			g.drawScaledImage(icon, posX, posY, sizeX, sizeY);
		}
		if ( title != null){
			g.drawString(title, posX+sizeX/2, posY+sizeY*3/4, paint);
		}
		//else g.drawRect(posX, posY, sizeX, sizeY, 0x32FFFFFF);
	}
	
	public boolean selected(int x, int y){
		
		return (x > posX && x < posX+sizeX && y > posY && y < posY+sizeY) ? true:false;

	}
	
	public void setIcon(Image image){
		icon = image;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
}
