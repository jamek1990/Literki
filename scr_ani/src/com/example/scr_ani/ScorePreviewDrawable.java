package com.example.scr_ani;
import android.content.Context;

public class ScorePreviewDrawable extends TextDrawable{
  private int score;
  public ScorePreviewDrawable(Context paramContext, int paramInt, boolean paramBoolean){}
  public int getScore(){
	  return this.score;
  }
  public void setScore(int paramInt){
    this.score = paramInt;
    ustawTekst(Integer.toString(paramInt));
  }
}