package dnd;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class DragRecognizer implements View.OnTouchListener{
	protected View active;
	protected int activeHeight;
	protected int activeWidth;
	private DragHandler defaultDragHandler = new DefaultDragHandler();
	protected ViewGroup dragLayer;
	protected Rect dragLayerOffset;
	protected DragSource dragSource;
	protected DropTarget dropTarget;
	private boolean enabled = true;
	protected MotionEvent lastMotionEvent;
	private Point offset;

	public DragRecognizer(ViewGroup paramViewGroup){
		this.dragLayer = paramViewGroup;
	}

	public void cancel(){
		if (this.active != null){
			onDragCancel(this.dragLayer, this.lastMotionEvent);
			this.active = null;
		}
	}

	public View getActive(){
		return this.active;
	}

	public int getActiveHeight(){
		return this.activeHeight;
	}

	public int getActiveWidth(){
		return this.activeWidth;
	}	

	public DragHandler getDefaultDragHandler(){
		return this.defaultDragHandler;
	}

	public ViewGroup getDragLayer(){
		return this.dragLayer;
	}

	public DragSource getDragSource(){
		return this.dragSource;
	}

	public DropTarget getDropTarget(){
		return this.dropTarget;
	}

	public Point getOffset(){
		return this.offset;
	}

	public boolean isEnabled(){
		return this.enabled;
	}

	public boolean isInDrag(){
		boolean bool = true;
		if (this.active == null)
			bool = false;
		return bool;
	}

	protected void onDrag(View paramView, MotionEvent paramMotionEvent){
		if (this.dragSource != null)
			this.dragSource.onDrag(this, paramView, paramMotionEvent);
		if (this.dropTarget != null)
			this.dropTarget.onDrag(this, paramView, paramMotionEvent);
	}

	protected void onDragCancel(View paramView, MotionEvent paramMotionEvent){
	    if (this.dropTarget != null)
	    	this.dropTarget.onDragExit(this, paramView, paramMotionEvent);
	    if (this.dragSource != null)
	    	this.dragSource.onDragCancel(this, paramView, paramMotionEvent);
	    if (this.dropTarget != null)
	    	this.dropTarget.onDragCancel(this, paramView, paramMotionEvent);
	}

	//cos zmienione
	 protected boolean onDrop(View var1, MotionEvent var2) {
	      boolean var3;
	      if(this.dropTarget != null && !this.dropTarget.onDrop(this, var1, var2)) {
	         var3 = false;
	      } else {
	         if(this.dragSource != null) {
	            this.dragSource.onDrop(this, var1, var2);
	         }

	         var3 = true;
	      }

	      return var3;
	   }
	protected View onStartDrag(View paramView, MotionEvent paramMotionEvent){
		View localView = null;
		if (this.dragSource != null)
			localView = this.dragSource.onStartDrag(this, paramView, paramMotionEvent);
		return localView;
	}
	public boolean onTouch(View var1, MotionEvent var2) {
	      boolean var4;
	      if(!this.enabled) {
	         var4 = false;
	      } else {
	         if(this.dragLayerOffset == null) {
	            this.dragLayerOffset = new Rect();
	            this.dragLayer.getGlobalVisibleRect(this.dragLayerOffset);
	         }

	         boolean var3;
	         if(var2.getAction() == 0) {
	            var3 = true;
	         } else {
	            var3 = false;
	         }

	         if(this.active == null) {
	            if(this.offset == null) {
	               this.offset = new Point();
	            } else {
	               this.offset.x = 0;
	               this.offset.y = 0;
	            }

	            this.active = this.onStartDrag(var1, var2);
	            if(this.active != null) {
	               if(Log.isLoggable("DragRecognizer", 3)) {
	                  Log.d("DragRecognizer", "Starting drag. " + var2 + ",active=" + this.active);
	               }

	               this.activeWidth = this.active.getWidth();
	               this.activeHeight = this.active.getHeight();
	               var3 = true;
	            }
	         }

	         if(this.active != null) {
	            this.lastMotionEvent = var2;
	            this.onDrag(var1, var2);
	            if(var2.getAction() == 1) {
	               this.dragLayer.removeView(this.active);
	               if(!this.onDrop(var1, var2)) {
	                  this.onDragCancel(var1, var2);
	               }

	               this.active = null;
	               this.lastMotionEvent = null;
	            }

	            var3 = true;
	         }

	         var4 = var3;
	      }

	      return var4;
	   }

	
  	@Deprecated
  	public void setActive(View paramView){
  		this.active = paramView;
  	}

  	public void setActiveLocation(int paramInt1, int paramInt2){
  		int i = this.dragLayer.getWidth();
  		int j = this.dragLayer.getHeight();
    	this.active.setPadding(paramInt1, paramInt2, i - (paramInt1 + this.activeWidth), j - (paramInt2 + this.activeHeight));
  	}

  	public void setDefaultDragHandler(DragHandler paramDragHandler){
  		this.defaultDragHandler = paramDragHandler;
  	}

  	public void setDragSource(DragSource paramDragSource){
  		this.dragSource = paramDragSource;
  	}

  	public void setDropTarget(DropTarget paramDropTarget){
  		this.dropTarget = paramDropTarget;
  	}

  	public void setEnabled(boolean paramBoolean){
  		this.enabled = paramBoolean;
  	}
}
