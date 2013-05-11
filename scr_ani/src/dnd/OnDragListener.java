package dnd;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
public abstract interface OnDragListener{
  public abstract void onDragCancel(View paramView1, View paramView2, MotionEvent paramMotionEvent);
  public abstract void onDrop(View paramView1, View paramView2, MotionEvent paramMotionEvent);
  public abstract View onStartDrag(View paramView, MotionEvent paramMotionEvent, Point paramPoint);
}
