package com.alexngai.herring.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class CollisionDetector {
	
	//compare collision between game character and all members of enemy list
	public static Enemy checkCollision(GameCharacter c, EnemyPool enemypool){
		for (Enemy enemy : enemypool.getObjarray()){
			if (enemy.isInUse() && overlap(c, enemy)){
				Log.d("Collision", "overlap");

				if (collides(c, enemy)){
					Log.d("Collision", "detected");
					return enemy;
				}
			}
		}
		return null;
	}
	
	private static boolean overlap(GameCharacter c, Enemy e){
		//check for collisions between two objects

		int right1 = c.getPosX()+c.getIm_width();
		int left1 = c.getPosX();
		int top1 = c.getPosY();
		int bottom1 = c.getPosY()+c.getIm_height();
		
		int right2 = e.getPosX()+e.getIm_width();
		int left2 = e.getPosX();
		int top2 = e.getPosY();
		int bottom2 = e.getPosY()+e.getIm_height();
		
		Rect r1 = new Rect(left1, top1, right1, bottom1);
		Rect r2 = new Rect(left2, top2, right2, bottom2);
		
		return Rect.intersects(r1, r2);
        
	}
	
	//compare collision between two objects
	private static boolean collides(GameCharacter c, Enemy e){
		//check for collisions between two objects
		int x1 = c.getPosX(); int y1 = c.getPosY();
		//scaled bitmap
		Bitmap b1 = c.getCurImage().scaleBitmap(c.getScale());
		
		int x2 = e.getPosX(); int y2 = e.getPosY();
		Bitmap b2 = e.getCurImage().scaleBitmap(e.getScale());
		
		//Log.d("Collision", "b1:" + b1.getWidth() + ", width:" + c.getIm_width());
		

		  double width1 = x1 + c.getIm_width() -1,
		         height1 = y1 + c.getIm_height() -1,
		         width2 = x2 + e.getIm_width() -1,
		         height2 = y2 + e.getIm_height() -1;
		  
		  //find intersecting box
		  int xstart = (int) Math.max(x1, x2),
		      ystart = (int) Math.max(y1, y2),
		      xend   = (int) Math.min(width1, width2),
		      yend   = (int) Math.min(height1, height2);

		  // intersection rect
		  int recty = Math.abs(yend - ystart);
		  int rectx = Math.abs(xend - xstart);

		  //get index of each bitmap
		  for (int y=1;y < recty-1;y++){
		    int ny1 = Math.abs(ystart - (int) y1) + y;
		    int ny2 = Math.abs(ystart - (int) y2) + y;

		    for (int x=1;x < rectx-1;x++) {
		      
		      int nx1 = Math.abs(xstart - (int) x1) + x;
		      int nx2 = Math.abs(xstart - (int) x2) + x;
		      try {
		    	  if (((b1.getPixel(nx1,ny1) & 0xFF000000) != 0x00) &&
		    			  ((b2.getPixel(nx2,ny2) & 0xFF000000) != 0x00)) {
		    		  // collision detected
		    		  return true;
		    	  }
		      } catch (Exception error){
		    	  Log.d("Collision", "s1:"+nx1+","+ny1+","+b1.getWidth()+","+b1.getHeight());
		      }
		    }
		  }
		  return false;
	}

	
}
