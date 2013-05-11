package dnd;
import android.view.MotionEvent;
import android.view.View;
public abstract interface OnDropListener{
	public abstract void onDrag(View paramView1, View paramView2, MotionEvent paramMotionEvent);
	public abstract void onDragCancel(View paramView1, View paramView2, MotionEvent paramMotionEvent);
	public abstract void onDragEnter(View paramView1, View paramView2, MotionEvent paramMotionEvent);
	public abstract void onDragExit(View paramView1, View paramView2, MotionEvent paramMotionEvent);
	public abstract boolean onDrop(View paramView1, View paramView2, MotionEvent paramMotionEvent);
}
