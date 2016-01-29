package com.alexngai.framework.implementation;

import android.graphics.Bitmap;

import com.alexngai.framework.Image;
import com.alexngai.framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
    
    @Override
    public Bitmap scaleBitmap(float scale){
    	
        int newWidth = (int) (scale*bitmap.getWidth());
        int newHeight = (int) (scale*bitmap.getHeight());
        
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);	
    }

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}
}