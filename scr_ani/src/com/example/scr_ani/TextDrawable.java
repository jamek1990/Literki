package com.example.scr_ani;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable implements Drawable.Callback{
	private Drawable background;
	private String tekst;

	public void draw(Canvas canvas){}

	public int getOpacity(){return 0;}

  	public String pobierzTekst(){
  		return this.tekst;
  	}
  	
  	public void ustawTekst(String tekst){
  		this.tekst = tekst;
  	}

  	protected void onBoundsChange(Rect rect){
  		if (this.background != null)
  			this.background.setBounds(rect);
  	}

  	//musza byc bo dziedzina po klasie abstrakcyjnej
  	public void setAlpha(int alpha){}
  	public void setColorFilter(ColorFilter paramColorFilter){}
  	public void invalidateDrawable(Drawable drawable){invalidateSelf();}
   	public void scheduleDrawable(Drawable drawable, Runnable runnable, long llong){scheduleSelf(runnable, llong);}
  	public void unscheduleDrawable(Drawable drawable, Runnable runnable){unscheduleSelf(runnable);}
}