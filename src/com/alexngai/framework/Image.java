package com.alexngai.framework;
import android.graphics.Bitmap;

import com.alexngai.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
    public Bitmap scaleBitmap(float scale);
    public Bitmap getBitmap();
}