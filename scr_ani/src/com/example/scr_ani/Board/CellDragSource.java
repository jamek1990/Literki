package com.example.scr_ani.Board;

import com.example.scr_ani.MainActivity;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import dnd.OnDragListener;


public class CellDragSource implements OnDragListener {
	protected Komorka activeCell;
	private MainActivity activity;
	protected int dragAlpha = 255;

	public CellDragSource(MainActivity mainActivity) {
		this.activity = mainActivity;
	}

	public int getDragAlpha() {
		return this.dragAlpha;
	}

	public void onDragCancel(View var1, View var2, MotionEvent var3) {
		if(this.activeCell != null) {
			Drawable var4 = ((ImageView)var2).getDrawable();
			((ImageView)var2).setAlpha(255);
			this.activeCell.setForeground(var4);
			var4.setBounds(this.activeCell.getBounds());
			this.activeCell = null;
		}
	}

	public void onDrop(View var1, View var2, MotionEvent var3) {
		this.activity.refreshScorePreview();
	}

   	public View onStartDrag(View var1, MotionEvent var2, Point var3) {
   		BoardView var4 = (BoardView)var1;
   		this.activeCell = var4.getCellAt(var2.getX(), var2.getY());
   		ImageView var5;
   		if(this.activeCell != null && !this.activeCell.isFrozen()) {
   			Drawable var6 = this.activeCell.getForeground();
   			if(var6 != null) {
   				ImageView var7 = new ImageView(var4.getContext());
   				var7.setAlpha(this.dragAlpha);
   				var7.setImageDrawable(var6);
   				float var8 = var4.getZoom();
   				int var9 = Math.max((int)(var8 * (float)var4.getCellWidth()), var6.getIntrinsicWidth());
   				int var10 = Math.max((int)(var8 * (float)var4.getCellHeight()), var6.getIntrinsicHeight());
   				var7.measure(var9, var10);
   				var7.layout(0, 0, var9, var10);
   				Rect var11 = var4.getCellBounds(this.activeCell);
   				var3.x = (int)(-var2.getX()) + var11.left - (var9 - var11.width()) / 2;
            	var3.y = (int)(-var2.getY()) + var11.top - (var10 - var11.height()) / 2;
            	this.activeCell.setForeground((Drawable)null);
            	var7.setTag(var4.pobierzPlansze().pobierzKafelek(this.activeCell.getRow(), this.activeCell.getCol()));
            	var5 = var7;
            	return var5;
   			}
      	}
   		var5 = null;
   		return var5;
   	}

   	public void setDragAlpha(int var1) {
   		this.dragAlpha = var1;
   	}
}
