package com.alexngai.herring.gameobjects;

import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;

public class GameObject {

	private boolean inUse = false;
	
	public static int boundX = 1024;
	public static int boundY = 412;
	
	protected Image curImage;
	private int im_width;
	private int im_height;
	
	protected float scale;
	protected int posX;
	protected int posY;
	protected float velX;
	protected float velY;
	
	public GameObject(){}
	
	public GameObject(Image sprite, float scale){
		this.curImage = sprite; 
		this.scale = scale;
		updateSize();
	}
	
	public void update(float deltaTime){
		posX = (int) (posX + velX*deltaTime);
		posY = (int) (posY + velY*deltaTime);
	}
	
	public void paint(Graphics g, float deltaTime){
		g.drawScaledImage(curImage, posX, posY, im_width, im_height);
	}

	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	public void updateSize(){
		im_width = Math.round(curImage.getWidth()*scale);
		im_height = Math.round(curImage.getHeight()*scale);
	}
	
	
	public void reconstruct(float scale, int posX, int posY, float velX, float velY){
		this.posX = posX;
		this.posY = posY;
		this.velX = velX;
		this.velY = velY;
		updateSize();
	}
	
	public Image getCurImage() {
		return curImage;
	}

	public float getScale() {
		return scale;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}
	
	public int getIm_width() {
		return im_width;
	}

	public int getIm_height() {
		return im_height;
	}
	
	public boolean isInUse(){
		return inUse;
	}

	public void setCurImage(Image sprite) {
		this.curImage = sprite;
		updateSize();
	}

	public void setSize(float scale) {
		this.scale = scale;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public void setIm_width(int im_width) {
		this.im_width = im_width;
	}

	public void setIm_height(int im_height) {
		this.im_height = im_height;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
}
