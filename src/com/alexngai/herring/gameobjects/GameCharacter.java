package com.alexngai.herring.gameobjects;

import java.util.ResourceBundle.Control;

import android.util.Log;

import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;
import com.alexngai.herring.Assets;
import com.alexngai.herring.ControlState;

public class GameCharacter extends GameObject {
	
	private static float decay = (float) 0.95;
	private static float accel = (float) 0.2;
	private static float minSpeed = (float) 0.1;
	private static float velX_max = 4;
	private static float velY_max = 4;
	
	private int goalPosX = -1;
	private int goalPosY = -1;
	//private Animation animation;
	
	private static final Image[] sprites = {Assets.avatar, Assets.avatar2, Assets.avatarflip, Assets.avatar2flip};
    public static final int RIGHT = 0;
    public static final int RIGHT2 = 1;
    public static final int LEFT = 2;
	public static final int LEFT2 = 3;
	private boolean left = false;
	
	private long animTime = 0;
	private long totalDuration = 20;
	
	private boolean joystick_mode = false;
	

	public GameCharacter(float size){
		super();
		setScale(size);
		setCurImage(sprites[RIGHT]);
	}
	
	/*
	public GameCharacter(Animation animation, float size){
		this.animation = animation;
		Image img = Assets.avatar;
		super.setSprite(img);
		super.setScale(size);
	}*/
	
	/*
	public GameCharacter(Image sprite, Image sprite2, float size){
		//super(sprite, sprite2, size);
		super.setScale(size);
	}*/ 
	
	public void update(float deltaTime, ControlState control){
		
		/*if (control.getState() >= 0){
			if (animation.update((long) deltaTime)){
				super.setSprite(animation.getImage());
				Log.d("Animation", "animation changed");
			}
		} else super.setSprite(Assets.avatar);*/
		
		if (joystick_mode){
			if (control.getState() >= 0){
				animTime += deltaTime;
				animTime = animTime % totalDuration;
				
				if (control.getCtrl_x() > 0){
					if (animTime < totalDuration/2) curImage = sprites[RIGHT];
					else curImage = sprites[RIGHT2];
					left = false;
				}
				else{
					if (animTime < totalDuration/2) curImage = sprites[LEFT];
					else curImage = sprites[LEFT2];
					left = true;
				}
			} 
		} else {
			if (control.getState() >= 0){
				animTime += deltaTime;
				animTime = animTime % totalDuration;
					
				if (control.getPosX() - posX > 0 || !left){
					if (animTime < totalDuration/2) curImage = sprites[RIGHT];
					else curImage = sprites[RIGHT2];
					left = false;
				}
				if (control.getPosX() - posX < 0 || left){
					if (animTime < totalDuration/2) curImage = sprites[LEFT];
					else curImage = sprites[LEFT2];
					left = true;
				}
			}
		}

		float x;
		float y;
		
		if (joystick_mode){
			if (control.getState() < 0){
				y = (float) (super.getVelY()*decay);
				if (Math.abs(y) < minSpeed) super.setVelY(0);
				else super.setVelY(y);
				
				x = (float) (super.getVelX()*decay);
				if (Math.abs(x) < minSpeed) super.setVelX(0);
				else super.setVelX(x);
			}
			else{
				y = (float) (super.getVelY()*decay+accel*control.getCtrl_y());
				if (Math.abs(y) < velY_max) super.setVelY(y);
				
				x = (float) (super.getVelX()*decay+accel*control.getCtrl_x());
				if (Math.abs(x) < velX_max) super.setVelX(x);
			}
		}
		
		//tap to move
		else {
			if (control.getState() < 0){
					
					y = (float) (super.getVelY()*decay);
					if (Math.abs(y) < minSpeed) super.setVelY(0);
					else super.setVelY(y);
					
					x = (float) (super.getVelX()*decay);
					if (Math.abs(x) < minSpeed) super.setVelX(0);
					else super.setVelX(x);
				//}
			}
			else{
				goalPosX = control.getPosX();
				goalPosY = control.getPosY();
				
				float vy, vx, tx, ty;
				ty = (goalPosY - posY);
				tx = (goalPosX - posX);
				vy = (float) (accel*ty/(Math.sqrt(ty*ty + tx*tx)));
				vx = (float) (accel*tx/(Math.sqrt(ty*ty + tx*tx)));
				
				y = (float) (super.getVelY()*decay + vy);
				if (Math.abs(y) < velY_max) super.setVelY(y);
				
				x = (float) (super.getVelX()*decay + vx);
				if (Math.abs(x) < velX_max) super.setVelX(x);
			}
			
			
		}
		/*
		float x;
		float y;

		switch (control.getState()) {
		//none selected, slow down
		case ControlState.NONE:
			y = (float) (super.getVelY()*decay);
			if (Math.abs(y) < minSpeed) super.setVelY(0);
			else super.setVelY(y);
			
			x = (float) (super.getVelX()*decay);
			if (Math.abs(x) < minSpeed) super.setVelX(0);
			else super.setVelX(x);
			
			break;
		//increase up speed, slow lateral movement	
		case ControlState.UP:
			y = (float) (super.getVelY()-accel);
			if (Math.abs(y) < velY_max) super.setVelY(y);
			
			x = (float) (super.getVelX()*decay);
			if (Math.abs(x) < minSpeed) super.setVelX(0);
			else super.setVelX(x);
			
			break;
		//increase down speed, slow lateral movement
		case ControlState.DOWN:
			y = (float) (super.getVelY()+accel);
			if (Math.abs(y) < velY_max) super.setVelY(y);
			
			x = (float) (super.getVelX()*decay);
			if (Math.abs(x) < minSpeed) super.setVelX(0);
			else super.setVelX(x);
			
			break;
		//increase left speed, slow vertical movement
		case ControlState.LEFT:
			x = (float) (super.getVelX()-accel);
			if (Math.abs(x) < velX_max) super.setVelX(x);
			
			y = (float) (super.getVelY()*decay);
			if (Math.abs(y) < minSpeed) super.setVelY(0);
			else super.setVelY(y);
			
			break;
		//increase right speed, slow vertical movement
		case ControlState.RIGHT:
			x = (float) (super.getVelX()+accel);
			if (Math.abs(x) < velX_max) super.setVelX(x);
			
			y = (float) (super.getVelY()*decay);
			if (Math.abs(y) < minSpeed) super.setVelY(0);
			else super.setVelY(y);
			
			break;

		default:
			break;
		}
		*/
		super.update(deltaTime);
		
		if (super.getPosX() > (boundX-getIm_width())){
			super.setPosX(boundX-getIm_width());
			super.setVelX(0);
		}
		else if (super.getPosX() < 0){
			super.setPosX(0);
			super.setVelX(0);
		}

		if (super.getPosY() > boundY){ 
			super.setPosY(boundY);
			super.setVelY(0);
		}
		else if (super.getPosY() < 0){
			super.setPosY(0);
			super.setVelY(0);
		}
	}
	
	public void paint(Graphics g, float deltaTime){
		super.paint(g, deltaTime);
		//g.drawScaledImage(Assets.halo_yellow, posX-100+getIm_width()/2, posY-100+getIm_height()/2, 200, 200);
	}

	public void grow(){
		scale = getScale() + .0013f;
		updateSize();
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setJoystickMode(boolean joystick_mode) {
		this.joystick_mode = joystick_mode;
	}

	
}
