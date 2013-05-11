package com.example.scr_ani.Board;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ZoomButtonsController;
import dnd.DragRecognizer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.example.scr_ani.R;
import com.example.scr_ani.ScorePreviewDrawable;
import com.example.scr_ani.SlowaPomocne;
import com.example.scr_ani.Kafelek.Kafelek;
import com.example.scr_ani.Kafelek.KafelekDrawable;
import scrable_silnik.Punktacja;

public class BoardView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ZoomButtonsController.OnZoomListener {	
	private boolean autoZoomContraints;
	private Plansza plansza;
	private Paint ramkaPaint;
	private int ramkaSzerokosc;
	private int komorkaWysokosc;
	private int komorkaSzerokosc;
	private  Komorka[][] komorki;
	private DragRecognizer dragRecognizer;
	private int maxPanInset;
	//private float maxZoom;
	private float minZoom;
	private OnCellClickListener onCellClickListener;
	private float pinchRelativeScale;
	private boolean pinchZoomEnabled;
	private ScorePreviewDrawable scorePreview;
	private boolean scrollOnLayout;
	private float zoom;
	private ZoomButtonsController zoomController;
	private GestureDetector gestureDetector;


	public BoardView(Context paramContext){
		super(paramContext);
		int[] arrayOfInt = new int[2];
		arrayOfInt[0] = 15;
		arrayOfInt[1] = 15;
		this.komorki = ((Komorka[][])Array.newInstance(Komorka.class, arrayOfInt));
		this.zoom = 1.0F;
		//this.maxZoom = 1.5F;
		this.minZoom = 1.0F;
		this.autoZoomContraints = true;
		this.ramkaSzerokosc = 15;
		this.scrollOnLayout = true;
		this.pinchZoomEnabled = true;
		init();
	}

	public BoardView(Context paramContext, AttributeSet paramAttributeSet){
		super(paramContext, paramAttributeSet);
		int[] arrayOfInt = new int[2];
		arrayOfInt[0] = 15;
		arrayOfInt[1] = 15;
		this.komorki= ((Komorka[][])Array.newInstance(Komorka.class, arrayOfInt));
		this.zoom = 1.0F;
		//this.maxZoom = 1.5F;
		//this.minZoom = 0.5F;
		this.autoZoomContraints = true;
		this.ramkaSzerokosc = 15;
		this.scrollOnLayout = true;
		this.pinchZoomEnabled = true;
		init();
	}

	private Komorka getViewCellAt(float paramFloat1, float paramFloat2){
		float f1 = screenToView(getScrollX());
		float f2 = screenToView(getScrollY());
		int i = (int)((paramFloat2 + f2) / this.komorkaWysokosc);
		int j = (int)((paramFloat1 + f1) / this.komorkaSzerokosc);
		Komorka komorka = null;
		if ((i < 0) || (j < 0) || (i >= 15) || (j >= 15))
			return komorka;
		komorka= this.komorki[i][j];
		return komorka;
	}

	public void clear(){
		for (int i=0;i<15;i++)
			for (int j=0;j<15;j++)
					this.komorki[i][j].czysc();
	}

	public void clearScorePreview(){
		if (this.scorePreview != null){
			this.scorePreview.invalidateSelf();
			this.scorePreview.setCallback(null);
			this.scorePreview = null;
		}
	}

	protected void dispatchDraw(Canvas paramCanvas){
		paramCanvas.scale(this.zoom, this.zoom);
		paramCanvas.translate(-this.ramkaSzerokosc, -this.ramkaSzerokosc);
		paramCanvas.drawRect(0.0F, 0.0F, 15 * this.komorkaSzerokosc + 2 * this.ramkaSzerokosc, 15 * this.komorkaWysokosc+ 2 * this.ramkaSzerokosc,this.ramkaPaint);
		paramCanvas.translate(this.ramkaSzerokosc, this.ramkaSzerokosc);
		for (int i=0;i<15;i++)
			for (int j=0; j<15;j++)
				this.komorki[i][j].draw(paramCanvas);
		if (this.scorePreview != null)
			this.scorePreview.draw(paramCanvas);
	}

	public Plansza pobierzPlansze(){
		return this.plansza;
	}

	public Paint getBorderPaint(){
		return this.ramkaPaint;
	}

	public int pobierzSzerkoscRamki(){
		return this.ramkaSzerokosc;
	}

	public Komorka pobierzKomorke(int x, int y){
		return this.komorki[x-1][y-1];
	}

	public Komorka getCellAt(float x, float y){
		return getViewCellAt(screenToView(x), screenToView(y));
	}

	public Rect getCellBounds(float paramFloat1, float paramFloat2){
		return toScreenRect(getViewCellBounds(screenToView(paramFloat1), screenToView(paramFloat2)));
	}

	public Rect getCellBounds(Komorka komorka){
		return toScreenRect(getViewCellBounds(komorka));
	}

	public int getCellHeight(){
		return this.komorkaWysokosc;
	}
	
	public int getCellWidth(){
		return this.komorkaSzerokosc;
	}

	public DragRecognizer getDragRecognizer(){
		return this.dragRecognizer;
	}

	//public float getMaxZoom(){
	//	return this.maxZoom;
	//}

	//public float getMinZoom(){
	//	return this.minZoom;
	//}


	
	public OnCellClickListener getOnCellClickListener(){
		return this.onCellClickListener;
	}

	protected int pobierzSugerowanaWysokosc(){
		return 15*this.komorkaWysokosc+2*this.ramkaSzerokosc;
	}

	protected int pobierzSugerowanaSzerokosc(){
		return 15*this.komorkaSzerokosc+2*this.ramkaSzerokosc;
	}

	protected Rect getViewCellBounds(float x,float y){
		float f = screenToView(getScrollX());
		int i = (int)((y+ screenToView(getScrollY())) / this.komorkaWysokosc);
		int j = (int)((x+ f) / this.komorkaSzerokosc);
		Rect localRect = null;
		if ((i < 0) || (j < 0) || (i >= 15) || (j >= 15)){
			return localRect;
		}
		int k = j * this.komorkaSzerokosc;
		int m = i * this.komorkaWysokosc;
		localRect = new Rect(k, m, k + this.komorkaSzerokosc- 1, m + this.komorkaWysokosc- 1);
		return localRect;
    }
	
	public Rect getViewCellBounds(Komorka komorka){
		int i = (komorka.getCol() - 1) * this.komorkaSzerokosc;
		int j = (komorka.getRow() - 1) * this.komorkaWysokosc;
		return new Rect(i, j, i + this.komorkaSzerokosc - 1, j + this.komorkaWysokosc- 1);
	}

	public float getZoom(){
		return this.zoom;
	}


	protected void init(){
		Resources zrodla=getResources();
		if (zrodla!=null){
			this.plansza=new Plansza();
			this.ramkaSzerokosc = zrodla.getDimensionPixelSize(R.dimen.board_border_width);
			this.ramkaPaint = new Paint();
		  	this.ramkaPaint.setColor(zrodla.getColor(R.color.board_border_color));
		  	this.komorkaSzerokosc = zrodla.getDimensionPixelSize(R.dimen.board_cell_size);
		  	this.komorkaWysokosc = this.komorkaSzerokosc;
		  	this.maxPanInset = (2 * this.komorkaSzerokosc);
		  	for(int i=0;i<15;i++){
		  		for(int j=0;j<15;j++){
		  			Komorka komorka;
		  			if(this.plansza.bor[i][j]=='f')
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_star), i + 1, j + 1);
		  			else if(this.plansza.bor[i][j]=='a')
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_tw), i + 1, j + 1);
		  			else if(this.plansza.bor[i][j]=='b')
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_dl), i + 1, j + 1);
		  			else if(this.plansza.bor[i][j]=='c')
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_dw), i + 1, j + 1);
		  			else if(this.plansza.bor[i][j]=='d')
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_tl), i + 1, j + 1);
		  			else 
		  				komorka= new Komorka(zrodla.getDrawable(R.drawable.cell_fr2), i + 1, j + 1);
		  			komorka.setCallback(this);
		  			komorka.setBounds(j*this.komorkaSzerokosc,i*this.komorkaWysokosc,(j+1)*this.komorkaSzerokosc,(i+1)*this.komorkaWysokosc);
				  this.komorki[i][j]=komorka;
			  }
		  }
		  this.invalidate();
		  this.gestureDetector = new GestureDetector(getContext(), this);
		  this.gestureDetector.setIsLongpressEnabled(false);
		  this.zoomController = new ZoomButtonsController(this);
		  this.zoomController.setOnZoomListener(this);
	  }
  }

	public boolean isAutoZoomContraints(){
		return this.autoZoomContraints;
	}
	public boolean isPinchZoomEnabled(){
		return this.pinchZoomEnabled;
	}
	public boolean isScrollOnLayout(){
		return this.scrollOnLayout;
	}
	protected void onDetachedFromWindow(){
		super.onDetachedFromWindow();
		this.zoomController.setVisible(false);
	}

	public boolean onDoubleTap(MotionEvent paramMotionEvent){
		if (getZoom() == 1.0F){
			setZoom(this.minZoom);
			scrollTo(0, 0);
		}else
			setZoom(1.0F);
		return true;
	}
	public boolean onDoubleTapEvent(MotionEvent paramMotionEvent){
		return false;
	}
	public boolean onDown(MotionEvent paramMotionEvent){
		return true;
	}
	public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2){
		return true;
	}
	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4){
		if (this.scrollOnLayout){
			scrollToCell(8, 8);
			this.scrollOnLayout = false;
		}
		if (this.autoZoomContraints){
			this.minZoom = Math.max(0.4F, Math.min(getWidth(), getHeight()) / 15 / this.komorkaSzerokosc);
		}
	}

	public void onLongPress(MotionEvent paramMotionEvent){}

	public void onPinchZoom(float paramFloat){
		//float f = Math.min(this.maxZoom, paramFloat * this.pinchRelativeScale);
		//setZoom(Math.max(this.minZoom, f));
	}

	public void onPinchZoomStart(){
		this.pinchRelativeScale = getZoom();
	}

	public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2){
		if (!this.dragRecognizer.isInDrag()){
			scrollBy((int)paramFloat1, (int)paramFloat2);
			invalidate();
		}
		return true;
	}

	public void onShowPress(MotionEvent paramMotionEvent){}

	public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent){
		return false;
	}

  public boolean onSingleTapUp(MotionEvent paramMotionEvent){
    if (this.onCellClickListener != null){
      Komorka localCell = getCellAt(paramMotionEvent.getX(), paramMotionEvent.getY());
      if (localCell != null)
        this.onCellClickListener.onClick(localCell);
    }
    return true;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!this.pinchZoomEnabled)
      this.zoomController.setVisible(true);
    this.dragRecognizer.onTouch(this, paramMotionEvent);
    boolean bool = true;
    if (!this.gestureDetector.onTouchEvent(paramMotionEvent));
 bool = super.onTouchEvent(paramMotionEvent);
      return bool;
  }

  public void onVisibilityChanged(boolean paramBoolean)
  {
  }

  public void onZoom(boolean paramBoolean)
  {
    /*if (paramBoolean)
      setZoom(Math.min(this.maxZoom, this.zoom + this.zoomInterval));
    else
      setZoom(Math.max(this.minZoom, this.zoom - this.zoomInterval));*/
  }

  public float screenToView(float paramFloat)
  {
    return paramFloat / this.zoom;
  }

  	public void scrollTo(int paramInt1, int paramInt2){
  		int i = 10;//(int)viewToScreen(this.maxPanInset);
  		int j = (int)viewToScreen(pobierzSugerowanaSzerokosc());
  		int k = (int)viewToScreen(pobierzSugerowanaWysokosc());
  		Log.d("Wysokosc",Integer.toString(getHeight()));
  		Log.d("Szerokosc",Integer.toString(getWidth()));
  		//getHeight() - > 350;
  		//getWidth() - > 320
	  	int m=0;
	  	int n = Math.min(Math.max(paramInt2, k - i - getHeight()), -i);
	  	m = Math.min(Math.max(paramInt1, -i), j-i  - getWidth());
	  	if (getWidth() - i * 2 > j)
	  		m = Math.min(Math.max(paramInt1, j + i - getWidth()), -i);
	  	else
	  		n = Math.min(Math.max(paramInt2, -i), k - i - getHeight());
	  	
	  	super.scrollTo(m, n);
	  	return;
	}

  	public void scrollToCell(int x, int y){
  		Rect localRect = getCellBounds(this.komorki[x-1][y-1]);
  		scrollBy((int)(this.ramkaSzerokosc * this.zoom) + localRect.centerX() - getWidth() / 2 + getScrollX(), (int)(this.ramkaSzerokosc* this.zoom) + localRect.centerY() - getHeight() / 2 + getScrollY());
  	}

  	public void setAutoZoomContraints(boolean paramBoolean){
  		this.autoZoomContraints = paramBoolean;
  	}

  	public void setBoard(Plansza paramBoard, Punktacja paramScoring){
  		setBoard(paramBoard, paramScoring, null);
  	}


  	public void setBoard(Plansza plansza, Punktacja paramScoring, List<Komorka> paramList){
  		if (this.plansza!=plansza){
  			this.plansza=plansza;
  			Resources zrodla=getResources();
  			for(int i=0;i<15;i++)
  				for(int j=0;j<15;j++){
  					Kafelek kafelek=plansza.pobierzKafelek(i+1,j+1);
  					Komorka komorka= this.komorki[i][j];
  					if ((paramList == null) || (!paramList.contains(komorka)))
  						komorka.czysc();
  					if (kafelek!= null){
			            Drawable localDrawable =zrodla.getDrawable(R.drawable.tile2);
			            localDrawable.setBounds(0, 0, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
			            KafelekDrawable localTileDrawable = new KafelekDrawable(getContext());
			            localTileDrawable.ustawIkone(localDrawable);
			            localTileDrawable.ustawTekst(SlowaPomocne.literaNaTekst(kafelek.pobierzLitere()));
			            localTileDrawable.ustawBlank(kafelek.czyBlank());
			            if (!kafelek.czyBlank())
			              localTileDrawable.ustawPunkty(paramScoring.getLetterPoints(kafelek.pobierzLitere()));
			            localTileDrawable.setBounds(komorka.getBounds());
			           //komorka.setForeground(localTileDrawable);
			            komorka.setFrozen(true);
			        }
  				}
  				invalidate();
  			}
  		}

  	public void setBorderPaint(Paint paramPaint){
  		this.ramkaPaint = paramPaint;
  	}

  	public void setBorderWidth(int paramInt){
  		this.ramkaSzerokosc= paramInt;
  	}

  	public void setDragRecognizer(DragRecognizer paramDragRecognizer){
  		this.dragRecognizer = paramDragRecognizer;
  	}

 

 	public void setMaxZoom(float paramFloat){
  		//this.maxZoom = paramFloat;
  	}

  	public void setMinZoom(float paramFloat){
  		//this.minZoom = paramFloat;
  	}

	public void setOnCellClickListener(OnCellClickListener paramOnCellClickListener){
	    this.onCellClickListener = paramOnCellClickListener;
	 }

  	public void setPinchZoomEnabled(boolean paramBoolean){
  		this.pinchZoomEnabled = paramBoolean;
  	}

  	public void setScrollOnLayout(boolean paramBoolean){
  		this.scrollOnLayout = paramBoolean;
  	}

  	public void setZoom(float tzoom){
  		if (this.zoom !=  tzoom){
  			int i = getScrollX();
  			int j = getScrollY();
  			float f =  tzoom / this.zoom;
  			int k = getWidth();
  			int m = getHeight();
  			int n = (int)((f * k - k) / 2.0F + f * i);
  			int i1 = (int)((f * m - m) / 2.0F + f * j);
  			this.zoom =  tzoom;
  			scrollTo(n, i1);
  			invalidate();
  		}
  	}

  	protected Rect toScreenRect(Rect rect){
  		if (rect != null){
  			rect.left = ((int)(rect.left * this.zoom) - getScrollX());
  			rect.right = ((int)(rect.right * this.zoom) - getScrollX());
  			rect.top = ((int)(rect.top * this.zoom) - getScrollY());
  			rect.bottom = ((int)(rect.bottom * this.zoom) - getScrollY());
  		}
  		return rect;
  	}
  	
  	public List<Komorka> getTransientCells(){
  		ArrayList<Komorka>komorki=new ArrayList<Komorka>(7);
  		for (int i=0;i<15;i++)
  			for (int j=0;j<15;j++){
  				Komorka komorka= this.komorki[i][j];
  				if ((!komorka.isFrozen()) && (komorka.getForeground() != null))
  					komorki.add(komorka);
  			}
  		return komorki;
    }

  	public float viewToScreen(float x){
  		return x* this.zoom;
  	}
  	public void setZoomInterval(float paramFloat){}
  	public static abstract interface OnCellClickListener{
  		public abstract void onClick(Komorka komorka);
  	}
}