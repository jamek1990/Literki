package dnd;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

public class DropTarget {

   private OnDropListener currentDragListener;
   private Map dropTargetMap;


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
                  if(var9 != null && this.containsOnDropListener(var9)) {
                     var7 = var9;
                     break;
                  }
               }

               if(this.containsOnDropListener(var8)) {
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

   public boolean containsOnDropListener(View var1) {
      boolean var2;
      if(this.dropTargetMap == null) {
         var2 = false;
      } else {
         var2 = this.dropTargetMap.containsKey(var1);
      }

      return var2;
   }

   public OnDropListener getOnDropListener(View var1) {
      OnDropListener var2;
      if(this.dropTargetMap == null) {
         var2 = null;
      } else {
         var2 = (OnDropListener)this.dropTargetMap.get(var1);
      }

      return var2;
   }

   public void onDrag(DragRecognizer var1, View var2, MotionEvent var3) {
      View var4 = this.findViewForEvent(var1, var2, var3);
      View var5 = var1.getActive();
      OnDropListener var6 = this.getOnDropListener(var4);
      if(this.currentDragListener != var6) {
         if(this.currentDragListener != null) {
            this.currentDragListener.onDragExit(var4, var5, var3);
         }

         if(var6 != null) {
            var6.onDragEnter(var4, var5, var3);
         }

         this.currentDragListener = var6;
      }

      if(this.currentDragListener != null) {
         this.currentDragListener.onDrag(var4, var5, var3);
      }

   }

   public void onDragCancel(DragRecognizer var1, View var2, MotionEvent var3) {
      View var4 = this.findViewForEvent(var1, var2, var3);
      View var5 = var1.getActive();
      if(this.currentDragListener != null) {
         this.currentDragListener.onDragCancel(var4, var5, var3);
      }

   }

   public void onDragExit(DragRecognizer var1, View var2, MotionEvent var3) {
      View var4 = this.findViewForEvent(var1, var2, var3);
      View var5 = var1.getActive();
      if(this.currentDragListener != null) {
         this.currentDragListener.onDragExit(var4, var5, var3);
      }

   }

   public boolean onDrop(DragRecognizer var1, View var2, MotionEvent var3) {
      View var4 = this.findViewForEvent(var1, var2, var3);
      OnDropListener var5 = this.getOnDropListener(var4);
      boolean var6;
      if(var5 != null) {
         var6 = var5.onDrop(var4, var1.getActive(), var3);
      } else {
         var6 = false;
      }

      return var6;
   }

   public void register(View var1, OnDropListener var2) {
      if(this.dropTargetMap == null) {
         this.dropTargetMap = new HashMap();
      }

      this.dropTargetMap.put(var1, var2);
   }

   public boolean unregister(View var1, OnDropListener var2) {
      boolean var3;
      if(this.dropTargetMap == null) {
         var3 = false;
      } else if((OnDropListener)this.dropTargetMap.get(var1) == var2) {
         this.dropTargetMap.remove(var1);
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
