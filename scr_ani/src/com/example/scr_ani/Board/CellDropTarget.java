package com.example.scr_ani.Board;

import com.example.scr_ani.MainActivity;
import com.example.scr_ani.R;
import com.example.scr_ani.Kafelek.KafelekDrawable;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import dnd.OnDropListener;


public class CellDropTarget implements OnDropListener {
	private Komorka activeCell;
	private MainActivity activity;
	private BoardView boardView;
	private ViewGroup dragLayer;
	private Drawable dropTargetDrawable;
	private View dropTargetView;

	public CellDropTarget(MainActivity var1, ViewGroup var2, BoardView var3, View var4) {
		this.boardView = var3;
		this.dragLayer = var2;
		this.dropTargetView = var4;
		this.activity = var1;
		this.dropTargetDrawable = ((ImageView)var4).getDrawable();
	}

	public void onDrag(View var1, View var2, MotionEvent var3) {
		Rect var4 = new Rect();
		this.boardView.getGlobalVisibleRect(var4);
		Komorka var6 = this.boardView.getCellAt(var3.getRawX() - (float)var4.left, var3.getRawY() - (float)var4.top);
		if(var6 != null) {
			if(this.activeCell != var6) {
				if(this.activeCell != null) {
					this.activeCell.invalidateSelf();
					this.activeCell.setOverlay((Drawable)null);
				}
				this.activeCell = var6;
				this.activeCell.setOverlay(this.dropTargetDrawable);
			}
			this.dropTargetDrawable.setBounds(this.activeCell.getBounds());
			this.activeCell.invalidateSelf();
			this.boardView.invalidate();
		// this.activity.hideScorePreview();
		}
	}

	public void onDragCancel(View var1, View var2, MotionEvent var3) {
		// this.activity.refreshScorePreview();
	}

	public void onDragEnter(View var1, View var2, MotionEvent var3) {}

	public void onDragExit(View var1, View var2, MotionEvent var3) {
		if(this.activeCell != null) {
			this.activeCell.setOverlay((Drawable)null);
			this.activeCell.invalidateSelf();
			this.boardView.invalidate();
			this.activeCell = null;
		}
	}

	public boolean onDrop(View var1, View var2, MotionEvent var3) {
		this.onDragExit(var1, var2, var3);
		Rect var4 = new Rect();
		this.boardView.getGlobalVisibleRect(var4);
		Komorka var6 = this.boardView.getCellAt(var3.getRawX() - (float)var4.left, var3.getRawY() - (float)var4.top);
		boolean var7=false;
		if(var6 != null && var6.getForeground() == null) {
			KafelekDrawable var8 = (KafelekDrawable)((ImageView)var2).getDrawable();
			((ImageView)var2).setAlpha(255);
			((ImageView)var2).setImageDrawable((Drawable)null);
			Drawable var9 = this.activity.getResources().getDrawable(R.drawable.tile3);
			var9.setBounds(0, 0, var9.getIntrinsicWidth(), var9.getIntrinsicHeight());
			var8.ustawIkone(var9);
			var8.setBounds(var6.getBounds());
			var6.setForeground(var8);
			var6.invalidateSelf();
			//if(var8.isWildcard() && var8.getText() == null) {
            	//this.activity.showWildcardAssignmentDialog(var6);
			//}
			this.activity.refreshScorePreview();
			var7 = true;
		} 
		return var7;
	}
}
