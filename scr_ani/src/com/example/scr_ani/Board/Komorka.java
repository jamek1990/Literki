package com.example.scr_ani.Board;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public class Komorka extends LayerDrawable{
	private static final Drawable EMPTY_DRAWABLE = new Drawable(){
		public void draw(Canvas paramAnonymousCanvas){}
		
    	public int getOpacity(){
    		return 0;
    	}

    	public void setAlpha(int paramAnonymousInt){}
    	public void setColorFilter(ColorFilter paramAnonymousColorFilter){}
	};
  	private boolean backgroundNull = true;
  	private int col;
  	private boolean foregroundNull = true;
  	private boolean frozen;
  	private boolean overlayNull = true;
  	private int row;

  	public Komorka(Drawable paramDrawable, int paramInt1, int paramInt2){
  		super(new Drawable[] { paramDrawable,EMPTY_DRAWABLE,EMPTY_DRAWABLE});
  		for (int i = 0; i < 3; i++)
  			setId(i, i);
  		this.row = paramInt1;
  		this.col = paramInt2;
  	}

  	private Drawable nonNull(Drawable paramDrawable){
  		Drawable localDrawable = EMPTY_DRAWABLE;
  		if (paramDrawable != null)
  			localDrawable = paramDrawable;
  		return localDrawable;
  	}

  	public void czysc(){
    	setForeground(null);
    	setOverlay(null);
    	this.frozen = false;
  	}

  	public Drawable getBackground(){
  		Drawable localDrawable = null;
  		if (!this.backgroundNull);
  			localDrawable = getDrawable(0);
  		return localDrawable;
  	}

  	public int getCol(){
  		return this.col;
  	}

  	public Drawable getForeground(){
  		Drawable localDrawable = null;
    	if (!this.foregroundNull)
    		localDrawable = getDrawable(1);
    	return localDrawable;
  	}

  	public int getLeft(){
  		return (this.col - 1) * getBackground().getIntrinsicWidth();
  	}

  	public Drawable getOverlay(){
  		Drawable localDrawable = null;
  		if (!this.overlayNull);
  			localDrawable = getDrawable(2);
  		return localDrawable;
  	}

  	public int getRow(){
  		return this.row;
  	}

  	public int getTop(){
  		return (this.row - 1) * getBackground().getIntrinsicHeight();
  	}

  	public boolean isFrozen(){
  		return this.frozen;
  	}

  	protected void onBoundsChange(Rect paramRect){
  		super.onBoundsChange(paramRect);
  		Rect localRect = new Rect(getBounds());
  		if (getBackground() != null){
  			localRect.bottom = (1 + localRect.bottom);
  			localRect.right = (1 + localRect.right);
  			getBackground().setBounds(localRect);
  		}
  	}

  /*	public void setBackground(Drawable paramDrawable){
  		setDrawableByLayerId(0, nonNull(paramDrawable));
  		boolean bool = true;
  		if (paramDrawable != null);
  			bool = false;
  		this.backgroundNull = bool;
  	}*/

  	public void setForeground(Drawable paramDrawable){
  		Drawable localDrawable = getForeground();
  		if (localDrawable != null)
  			localDrawable.setCallback(null);
  		setDrawableByLayerId(1, nonNull(paramDrawable));
  		boolean bool = true; 
  		if (paramDrawable != null)
  			bool = false;
  		this.foregroundNull = bool;
  		if (paramDrawable != null)
  			paramDrawable.setCallback(this);
  	}

  	public void setFrozen(boolean paramBoolean){
  		this.frozen = paramBoolean;
  	}

  	public void setOverlay(Drawable paramDrawable){
  		Drawable localDrawable = getOverlay();
	  	if (localDrawable != null)
	  		localDrawable.setCallback(null);
	  	setDrawableByLayerId(2, nonNull(paramDrawable));
	  	boolean bool = true;
	  	if (paramDrawable != null)
	  		bool = false;
	  	this.overlayNull = bool;
	  	if (paramDrawable != null)
	  		paramDrawable.setCallback(this);
  	}
}