package com.example.scr_ani.Kafelek;


import com.example.scr_ani.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class KafelekDrawable extends Drawable implements Drawable.Callback{
	private static Paint DEFAULT_HIGHLIGHT_PAINT;
	private static Paint DEFAULT_POINTS_PAINT;
	private static Paint DEFAULT_TEXT_PAINT;
	private static Paint DEFAULT_WILDCARD_PAINT;
	private int alpha = 255;
	private Rect cachedPointsBounds = new Rect();
	private Rect cachedTextBounds = new Rect();
	private boolean podswietlenie;
	private Paint highlightPaint;
	private Drawable ikona;
	private boolean iconDirty = true;
	private int ikonaWysokosc;
	private int ikonaSzerokosc;
	private int punkty;
	private boolean pointsBoundsDirty = true;
	private Paint pointsPaint;
	private String tekst;
	private boolean textBoundsDirty = true;
	private Paint textPaint;
	
	private boolean blank;
	private Paint wildcardPaint;

	public KafelekDrawable(Context paramContext){
		if (DEFAULT_TEXT_PAINT == null){
			Resources localResources = paramContext.getResources();
			int rozmiarTekstu = localResources.getDimensionPixelSize(R.dimen.tile_text_size);
			
			DEFAULT_TEXT_PAINT = new Paint();
			DEFAULT_TEXT_PAINT.setAntiAlias(true);
			DEFAULT_TEXT_PAINT.setColor(localResources.getColor(R.color.tile_text_normal));
			DEFAULT_TEXT_PAINT.setTextSize(rozmiarTekstu);
			DEFAULT_TEXT_PAINT.setTypeface(Typeface.create("normal", 1));
			
			DEFAULT_WILDCARD_PAINT = new Paint();
			DEFAULT_WILDCARD_PAINT.setAntiAlias(true);
			DEFAULT_WILDCARD_PAINT.setColor(localResources.getColor(R.color.tile_text_normal));//R.color.tile_text_wildcard));
			DEFAULT_WILDCARD_PAINT.setTextSize(rozmiarTekstu);
			DEFAULT_WILDCARD_PAINT.setTypeface(Typeface.create("normal", 1));
			
			DEFAULT_HIGHLIGHT_PAINT = new Paint();
		    DEFAULT_HIGHLIGHT_PAINT.setAntiAlias(true);
		    DEFAULT_HIGHLIGHT_PAINT.setColor(localResources.getColor(R.color.tile_text_highlight));
		    DEFAULT_HIGHLIGHT_PAINT.setTextSize(rozmiarTekstu);
		    DEFAULT_HIGHLIGHT_PAINT.setTypeface(Typeface.create("normal", 1));
		   
		    DEFAULT_POINTS_PAINT = new Paint();
		    DEFAULT_POINTS_PAINT.setAntiAlias(true);
		    DEFAULT_POINTS_PAINT.setColor(localResources.getColor(R.color.tile_text_normal));
		    DEFAULT_POINTS_PAINT.setTextSize(localResources.getDimensionPixelSize(R.dimen.tile_points_size));
		    DEFAULT_POINTS_PAINT.setTypeface(Typeface.create("normal", 1));
	    }
		
		this.textPaint = DEFAULT_TEXT_PAINT;
		this.wildcardPaint = DEFAULT_WILDCARD_PAINT;
		this.highlightPaint = DEFAULT_HIGHLIGHT_PAINT;
		this.pointsPaint = DEFAULT_POINTS_PAINT;
  }

  public void draw(Canvas kafelek){
	  kafelek.save();
	  Rect localRect = getBounds();
	  kafelek.translate(localRect.left, localRect.top);
	  
	  if (this.ikona != null){
		  this.ikona.setAlpha(this.alpha);
		  this.ikona.draw(kafelek);
	  }
	
	  Paint kafelekPaint;
	  
	  	if (this.tekst != null){
		  	if(this.iconDirty){
			  	this.ikonaSzerokosc = getIntrinsicWidth();
			  	this.ikonaWysokosc = getIntrinsicHeight();
			  	this.iconDirty = false;
		  	}
		  	if (!this.blank)
		  		kafelekPaint = this.textPaint;
		  	kafelekPaint = this.wildcardPaint;
		  	if (this.podswietlenie)
		  		kafelekPaint = this.highlightPaint;
		  		
		  	
		  	kafelekPaint.setAlpha(this.alpha);
		  	
		  	if (this.textBoundsDirty){
		  		kafelekPaint.getTextBounds(this.tekst, 0, tekst.length(), this.cachedTextBounds);
		  		this.textBoundsDirty = false;
		  	}
		  	
		  	//rysuje tekst na srodku kafelka
		  	int x = (1 + (this.ikonaSzerokosc - this.cachedTextBounds.right - this.cachedTextBounds.left)) / 2;
		  	int y = (1 + (this.ikonaWysokosc - this.cachedTextBounds.bottom - this.cachedTextBounds.top)) / 2;
		  	kafelek.drawText(this.tekst, x, y, kafelekPaint);
		  
		  
		  	// rysowanie punktow
		  	if(this.punkty!=0){
		  		String pun = Integer.toString(this.punkty);
		  		this.pointsPaint.setAlpha(this.alpha);
			  
		  		if(this.pointsBoundsDirty){
		  			this.pointsPaint.getTextBounds(pun,0, pun.length(), this.cachedPointsBounds);
		  			this.pointsBoundsDirty=false;
		  		}
			  
			  	//Litera W ma na dole liczbe punktow 
			  	if ((this.tekst == null) || (tekst.length() <= 0) || (this.tekst.charAt(0) != 'W'))
			  		kafelek.drawText(pun,8*this.ikonaWysokosc/10-this.cachedPointsBounds.right/2,3*this.ikonaWysokosc/10, this.pointsPaint);
			  	else
			  		kafelek.drawText(pun,16*this.ikonaSzerokosc/20-this.cachedPointsBounds.right/2,16*this.ikonaSzerokosc/20, this.pointsPaint);
		  	}
	  	}
	  
	  	kafelek.restore();
  	}

  	public Drawable pobierzIkone(){
  		return this.ikona;
  	}

  	public int pobierzPunkty(){
  		return this.punkty;
  	}

  	public String pobierzTekst(){
  		return this.tekst;
  	}
  
  	public Paint getTextPaint(){
  		return this.textPaint;
  	}

  	public void invalidateDrawable(Drawable paramDrawable){
  		invalidateSelf();
  	}

  	public boolean czyPodswietlenie(){
  		return this.podswietlenie;
  	}

  	public boolean czyBlank(){
  		return this.blank;
  	}

  	protected void onBoundsChange(Rect paramRect){
  		if (this.ikona != null)
  			this.ikona.setBounds(0, 0, paramRect.width(), paramRect.height());
  	}


  	public void ustawPodswietlenie(boolean podswietlenie){
  		this.podswietlenie = podswietlenie;
  	}

  	public void ustawIkone(Drawable ikona){
  		if (this.ikona != null)
  			this.ikona.setCallback(null);
  		this.ikona = ikona;
  		this.iconDirty = true;
  		if (ikona != null)
  			this.ikona.setCallback(this);
  	}

  	public void ustawPunkty(int punkty){
  		this.punkty = punkty;
  		this.pointsBoundsDirty = true;
  	}

  	public void ustawTekst(String tekst){
  		this.tekst = tekst;
  		this.textBoundsDirty = true;
  	}

  	public void ustawTekstPaint(Paint tekst){
  		this.textPaint = tekst;
  		this.textBoundsDirty = true;
  		this.pointsBoundsDirty = true;
  	}

  	public void ustawBlank(boolean blank){
  		this.blank = blank;
  	}
  	
/* -------------------- metody Drawable ------------------------ */
  	
  	public int getIntrinsicHeight(){
  		if(this.ikona!= null)
  			return this.ikona.getIntrinsicHeight(); 
    	return 32;
  	}

  	public int getIntrinsicWidth(){
  		if (this.ikona != null)
  			return this.ikona.getIntrinsicWidth();
    	return 32;
  	}
  	
  	public int getOpacity(){
  		if (this.alpha != 255)
  			return -3;
  		return -2;
  	}
  	
  	public void setAlpha(int alpha){
  		this.alpha = alpha;
  	}
  	
  	public void setColorFilter(ColorFilter colorFilter){}

  	public void scheduleDrawable(Drawable kto, Runnable co, long kiedy){
  		scheduleSelf(co, kiedy);
  	}
  	
  	public void unscheduleDrawable(Drawable kto, Runnable co){
  		unscheduleSelf(co);
  	}
}