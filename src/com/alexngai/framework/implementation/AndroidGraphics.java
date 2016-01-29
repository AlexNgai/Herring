package com.alexngai.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;

import com.alexngai.framework.Game;
import com.alexngai.framework.Graphics;
import com.alexngai.framework.Image;
import com.alexngai.herring.ScreenAdapter;
import com.google.android.gms.drive.internal.p;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    
    private Game game;
    private ScreenAdapter adapter;
    
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, Game game) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        this.game = game;
        adapter = new ScreenAdapter(game.getWidth(), game.getHeight());
    }

    @Override
    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }


    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
    	//modified
    	Point p1 = adapter.translateValues(x, y);
    	Point p2 = adapter.translateValues(x2, y2);
    	//modified
        paint.setColor(color);
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        Point p1 = adapter.translateValues(x, y);
    	Point p2 = adapter.translateValues(width, height);
        canvas.drawRect(p1.x, p1.y, p1.x + p2.x - 1, p1.y + p2.y - 1, paint);
    }
    
    @Override
    public void drawCircle(int x, int y, float radius, Paint p){
    	Point p1 = adapter.translateValues(x, y);
    	float r = adapter.translateVal(radius);
    	canvas.drawCircle(p1.x, p1.y, r, p);
    }
    
    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }
    
    @Override
    public void drawString(String text, int x, int y, Paint paint){
    	Point p1 = adapter.translateValues(x, y);
        canvas.drawText(text, p1.x, p1.y, paint);

        
    }
    
    //draw only portion of image
    public void drawImage(Image Image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
    	//needs modification
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;
      
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,
                null);
    }
    
    //draw full image at location
    @Override
    public void drawImage(Image Image, int x, int y) {
    	Point p1 = adapter.translateValues(x, y);
        canvas.drawBitmap(((AndroidImage)Image).bitmap, p1.x, p1.y, null);
    }
    
    //draw scaled image at x,y with width "width" and height "height from subset of image src
    public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
        
    	srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
   
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
        
    }
   
    //draw entire scaled image at x,y with width "width" and height "height 
    public void drawScaledImage(Image Image, int x, int y, int width, int height){
    	Point p1 = adapter.translateValues(x, y);
    	Point p2 = adapter.translateValues(width, height);
        dstRect.left = p1.x;
        dstRect.top = p1.y;
        dstRect.right = p1.x + p2.x;
        dstRect.bottom = p1.y + p2.y;
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, null, dstRect, null);  
    }
    
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
