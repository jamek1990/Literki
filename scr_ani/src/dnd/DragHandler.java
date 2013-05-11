package dnd;

import android.view.MotionEvent;
import android.view.View;

public abstract interface DragHandler
{
  public abstract void onDrag(DragRecognizer paramDragRecognizer, View paramView, MotionEvent paramMotionEvent);
}
