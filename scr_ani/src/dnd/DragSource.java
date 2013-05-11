package dnd;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

public class DragSource {

   private OnDragListener currentSource;
   private Map dragSourceMap;


   private View findDescendantView(View var1, ViewGroup var2, int var3, int var4, Rect var5) {
      int var6 = 0;

      View var7;
      while(true) {
         if(var6 >= var2.getChildCount()) {
            var7 = null;
            break;
         }

         View var8 = var2.getChildAt(var6);
         if(var8 != var1) {
            var8.getHitRect(var5);
            if(var5.contains(var3, var4)) {
               if(var8 instanceof ViewGroup) {
                  View var9 = this.findDescendantView(var1, (ViewGroup)var8, var3 - var8.getLeft(), var4 - var8.getTop(), var5);
                  if(var9 != null && this.containsOnDragListener(var9)) {
                     var7 = var9;
                     break;
                  }
               }

               if(this.containsOnDragListener(var8)) {
                  var7 = var8;
                  break;
               }
            }
         }

         ++var6;
      }

      return var7;
   }

   private View findViewForEvent(DragRecognizer var1, View var2, MotionEvent var3) {
      int var4 = (int)var3.getRawX();
      int var5 = (int)var3.getRawY();
      Rect var6 = new Rect();
      View var7 = var1.getDragLayer().getRootView();
      View var8;
      if(!(var7 instanceof ViewGroup)) {
         var8 = null;
      } else {
         var8 = this.findDescendantView(var1.getActive(), (ViewGroup)var7, var4, var5, var6);
      }

      return var8;
   }

   public boolean containsOnDragListener(View var1) {
      boolean var2;
      if(this.dragSourceMap == null) {
         var2 = false;
      } else {
         var2 = this.dragSourceMap.containsKey(var1);
      }

      return var2;
   }

   public DragHandler getDragHandler(View var1) {
      DragHandler var3;
      if(this.dragSourceMap == null) {
         var3 = null;
      } else {
         OnDragListener var2 = (OnDragListener)this.dragSourceMap.get(var1);
         if(var2 instanceof DragHandler) {
            var3 = (DragHandler)var2;
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   public OnDragListener getOnDragListener(View var1) {
      OnDragListener var2;
      if(this.dragSourceMap == null) {
         var2 = null;
      } else {
         var2 = (OnDragListener)this.dragSourceMap.get(var1);
      }

      return var2;
   }

   public void onDrag(DragRecognizer var1, View var2, MotionEvent var3) {
      DragHandler var4 = this.getDragHandler(var2);
      if(var4 != null) {
         var4.onDrag(var1, var2, var3);
      } else {
         var1.getDefaultDragHandler().onDrag(var1, var2, var3);
      }

   }

   public void onDragCancel(DragRecognizer var1, View var2, MotionEvent var3) {
      if(this.currentSource != null) {
         this.currentSource.onDragCancel(var2, var1.getActive(), var3);
         this.currentSource = null;
      } else {
         View var4 = this.findViewForEvent(var1, var2, var3);
         OnDragListener var5 = this.getOnDragListener(var4);
         if(var5 != null) {
            var5.onDragCancel(var4, var1.getActive(), var3);
         }
      }

   }

   public void onDrop(DragRecognizer var1, View var2, MotionEvent var3) {
      if(this.currentSource != null) {
         this.currentSource.onDrop(var2, var1.getActive(), var3);
         this.currentSource = null;
      } else {
         View var4 = this.findViewForEvent(var1, var2, var3);
         OnDragListener var5 = this.getOnDragListener(var4);
         if(var5 != null) {
            var5.onDrop(var4, var1.getActive(), var3);
         }
      }

   }

   public View onStartDrag(DragRecognizer var1, View var2, MotionEvent var3) {
      View var4 = this.findViewForEvent(var1, var2, var3);
      OnDragListener var5 = this.getOnDragListener(var4);
      View var6;
      if(var5 != null) {
         if(!(var5 instanceof DragHandler) && var3.getAction() != 0) {
            var6 = null;
         } else {
            View var7 = var5.onStartDrag(var4, var3, var1.getOffset());
            if(var7 != null) {
               this.currentSource = var5;
            }

            var6 = var7;
         }
      } else {
         var6 = null;
      }

      return var6;
   }

   public void register(View var1, OnDragListener var2) {
      if(this.dragSourceMap == null) {
         this.dragSourceMap = new HashMap();
      }

      this.dragSourceMap.put(var1, var2);
   }

   public boolean unregister(View var1, OnDragListener var2) {
      boolean var3;
      if(this.dragSourceMap == null) {
         var3 = false;
      } else if((OnDragListener)this.dragSourceMap.get(var1) == var2) {
         this.dragSourceMap.remove(var1);
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
