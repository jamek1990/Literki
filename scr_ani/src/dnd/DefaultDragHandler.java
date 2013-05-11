package dnd;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

public class DefaultDragHandler
  implements DragHandler
{
  public void onDrag(DragRecognizer paramDragRecognizer, View paramView, MotionEvent paramMotionEvent)
  {
    Point localPoint = paramDragRecognizer.getOffset();
    ViewGroup localViewGroup = paramDragRecognizer.getDragLayer();
    View localView = paramDragRecognizer.getActive();
    DropTarget localDropTarget = paramDragRecognizer.getDropTarget();
    if (localView.getParent() != localViewGroup)
    {
      if (localView.getParent() != null)
        ((ViewManager)localView.getParent()).removeView(localView);
      localViewGroup.addView(localView);
    }
    paramDragRecognizer.setActiveLocation((int)paramMotionEvent.getRawX() + localPoint.x - localViewGroup.getLeft(), (int)paramMotionEvent.getRawY() + localPoint.y - localViewGroup.getTop());
    localView.invalidate();
    if (localDropTarget != null)
      localDropTarget.onDrag(paramDragRecognizer, paramView, paramMotionEvent);
  }
}
