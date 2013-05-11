package com.example.scr_ani;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.example.scr_ani.Board.Plansza;
import com.example.scr_ani.Board.BoardView;
import com.example.scr_ani.Board.Komorka;
import com.example.scr_ani.Board.CellDragSource;
import com.example.scr_ani.Board.CellDropTarget;
import com.example.scr_ani.Kafelek.Kafelek;
import com.example.scr_ani.Kafelek.KafelekDrawable;
import com.example.scr_ani.Kafelek.TileRackDragSource;
import com.example.scr_ani.Kafelek.TileRackDropTarget;

import scrable_silnik.DobryRuch;
import scrable_silnik.Slowo;
import scrable_silnik.Slownik;
import scrable_silnik.Reguly;
import scrable_silnik.Punktacja;
import scrable_silnik.StandardowaPunktacja;
import scrable_silnik.StandardoweReguly;

import dnd.DragRecognizer;
import dnd.DragSource;
import dnd.DropTarget;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ArrayList<Bundle> playerDataList;
	private ViewGroup stojakNaKafelki;
	private BoardView planszaWidok;
	private LinearLayout.LayoutParams stojakNaKafelki_LayoutParams;
	private Punktacja wynik;
	private Plansza plansza;
	
	private Slownik slownik;
	private Punktacja punktacja;
	private Reguly reguly;
	
	
	//Klasy potrzebne do obs³ugi zdarzen drag and drop2
	
	private ViewGroup dragLayer;
	private DragRecognizer dragRecognizer;
	private ImageView dropTargetView;
	
	private ImageButton wymieszajPrzycisk;
	private ImageButton grajPrzycisk;
	
	private TextView tilesRemainingView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.slownik;//= new AcceptAllDictionary();
        this.punktacja = new StandardowaPunktacja();
        this.reguly = new StandardoweReguly(this.punktacja);
		
		//FULL SCREEN 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.stojakNaKafelki_LayoutParams = new LinearLayout.LayoutParams(-2, -2);
        int rozmiar = getResources().getDimensionPixelSize(R.dimen.tilerack_tile_size);
        this.stojakNaKafelki_LayoutParams.width = rozmiar;
        this.stojakNaKafelki_LayoutParams.height = rozmiar;
        this.stojakNaKafelki=((ViewGroup)findViewById(R.id.tile_rack));
        this.planszaWidok = ((BoardView)findViewById(R.id.board));
        this.plansza = new Plansza();
        this.planszaWidok.setBoard(this.plansza, this.wynik);
        this.planszaWidok.invalidate();
        aktualizujKafelki(SlowaPomocne.tekstNaKafelki("KOSZULA"));
        initDragDrop();
        this.planszaWidok.setOnCellClickListener(new BoardView.OnCellClickListener(){
          public void onClick(Komorka paramAnonymousCell){}
        });
        
        
        this.wymieszajPrzycisk = ((ImageButton)findViewById(R.id.shuffle));
        this.tilesRemainingView = ((TextView)findViewById(R.id.tiles_remaining));
        if(this.wymieszajPrzycisk != null)
            this.wymieszajPrzycisk.setOnClickListener(new View.OnClickListener(){
              public void onClick(View paramAnonymousView){
                MainActivity.this.wymieszaj();
              }
            });
        
        this.grajPrzycisk = ((ImageButton)findViewById(R.id.play));
        if(this.grajPrzycisk != null)
            this.grajPrzycisk.setOnClickListener(new View.OnClickListener(){
              public void onClick(View paramAnonymousView){
                MainActivity.this.graj();
              }
            });
       
        
        
        this.planszaWidok.setPinchZoomEnabled(true);
        this.planszaWidok.setAutoZoomContraints(true);
        this.planszaWidok.setClickable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addRackTile(char paramChar){
		ImageView localImageView = new ImageView(this);
	    KafelekDrawable localTileDrawable = new KafelekDrawable(this);
	    localTileDrawable.ustawIkone(getResources().getDrawable(R.drawable.tile2));
	    localTileDrawable.ustawTekst(SlowaPomocne.literaNaTekst(paramChar));
	    localTileDrawable.ustawPunkty(this.wynik.getLetterPoints(paramChar));
	    if (paramChar == '?')
	    	localTileDrawable.ustawBlank(true);
	    else
	    	localTileDrawable.ustawBlank(false);
	    localImageView.setImageDrawable(localTileDrawable);
	    this.stojakNaKafelki.addView(localImageView, this.stojakNaKafelki_LayoutParams);
	    localImageView.invalidate();
	    return;
	}
	
	public void setRackVisible(boolean widzialny){
		ViewGroup stojak=this.stojakNaKafelki;
	    if (widzialny)
	    	stojak.setVisibility(0);
	    else
	    	stojak.setVisibility(8);
	    return;
	}
	public boolean isRackVisible(){
	    if (this.stojakNaKafelki.getVisibility() == 0)
	    	return true;
	    return false;

	}
	private void initDragDrop(){
	    this.dragLayer = ((ViewGroup)findViewById(R.id.drag_layer));
	    this.dropTargetView = new ImageView(this);
	    this.dropTargetView.setImageResource(R.drawable.drop_target);
	    this.dragRecognizer = new DragRecognizer(this.dragLayer);
	    DropTarget dropTarget = new DropTarget();
	    DragSource dragSource = new DragSource();
	    this.dragRecognizer.setDragSource(dragSource);
	    this.dragRecognizer.setDropTarget(dropTarget);
	    CellDragSource localCellDragSource = new CellDragSource(this);
	    CellDropTarget localCellDropTarget = new CellDropTarget(this, this.dragLayer, this.planszaWidok, this.dropTargetView);
	    localCellDragSource.setDragAlpha(192);
	    TileRackDragSource localTileRackDragSource = new TileRackDragSource(this.dragLayer, this.stojakNaKafelki);
	    TileRackDropTarget localTileRackDropTarget = new TileRackDropTarget(this.dragRecognizer, this.dragLayer, this.stojakNaKafelki, this.stojakNaKafelki_LayoutParams);
	    localTileRackDragSource.setDragAlpha(192);
	    dragSource.register(this.planszaWidok, localCellDragSource);
	    this.planszaWidok.setDragRecognizer(this.dragRecognizer);
	    dropTarget.register(this.planszaWidok, localCellDropTarget);
	    dragSource.register(this.stojakNaKafelki, localTileRackDragSource);
	    dropTarget.register(this.stojakNaKafelki, localTileRackDropTarget);
	    this.stojakNaKafelki.setOnTouchListener(this.dragRecognizer);
	}
	
	
	/*Funkcja losuje kafelki znajdujace sie w stojaku*/
	public void wymieszaj(){
		if(this.stojakNaKafelki.getChildCount()!=0){
			Random losuj = new Random();
			ArrayList kafelki = new ArrayList(this.stojakNaKafelki.getChildCount());
			for(int i=0;i<this.stojakNaKafelki.getChildCount();++i){
				int wylosowanaLiczba=losuj.nextInt(this.stojakNaKafelki.getChildCount());
				ImageView kaf=(ImageView)this.stojakNaKafelki.getChildAt(wylosowanaLiczba);
				kafelki.add((KafelekDrawable)kaf.getDrawable());
				this.stojakNaKafelki.removeView(kaf);
			}
			Iterator iterator=kafelki.iterator();
	     	while(iterator.hasNext()){
	     		KafelekDrawable kaf=(KafelekDrawable)iterator.next();
	     		ImageView kafWidok=new ImageView(this);
	     		kafWidok.setImageDrawable(kaf);
	     		this.stojakNaKafelki.addView(kafWidok,this.stojakNaKafelki_LayoutParams);
	     		kafWidok.invalidate();
	     	}
		}
	}
	 public void zmien(){
		 Toast.makeText(this, "Zmieniaj!", 1).show();
	 }
	/*Funkcja wstawia slowo jesli jest poprawne*/
	public void graj(){
		DobryRuch dobryRuch = createMove();
		if (dobryRuch== null)
		      if (this.plansza.isFirstPlay())
		        Toast.makeText(this, "Niestety to nie Twoja kolej!", 1).show();
	}
	
	 public void submitMove(){}
	 
	//odswiza wynik w gornym panelu
	public void odswiezWynik(String s){
	}
	private DobryRuch createMove(){
	    List localList = this.planszaWidok.getTransientCells();
	    Kafelek[] arrayOfTile = new Kafelek[localList.size()];
	    for (int i = 0; i < arrayOfTile.length; i++){
	    	Komorka localCell = (Komorka)localList.get(i);
	    	KafelekDrawable localTileDrawable = (KafelekDrawable)localCell.getForeground();
	    	arrayOfTile[i] = new Kafelek(Character.toLowerCase(SlowaPomocne.tekstNaLitera(localTileDrawable.pobierzTekst())), localCell.getRow(), localCell.getCol(),false);// localTileDrawable.isWildcard());
	    }
	    DobryRuch localPlayableMove=null;
	    if (this.plansza != null)
	    	localPlayableMove = this.reguly.getPlayableMove(this.plansza, this.slownik, arrayOfTile); 
	    return localPlayableMove;
	}
	  
	public void refreshScorePreview(){
		DobryRuch localPlayableMove = createMove();
		if ((localPlayableMove == null))//|| (this.gameOver))
	    	this.planszaWidok.clearScorePreview();
	      else{
		      Slowo localWord = localPlayableMove.pobierzPodstawoweSlowo();
		      Kafelek localTile = localWord.pobierzOstatniKafelek();
		      int w=localPlayableMove.pobierzCalkowityWynik();
		      odswiezWynik(Integer.toString(w));
		     // this.planszaWidok.setScorePreview(localTile.getRow(), localTile.getCol(), localPlayableMove.getTotalScore(), localWord.isHorizontal());
		  }

	  }

	/*Aktualizuje kafelki i dodaje je do stojaka*/
	public void aktualizujKafelki(Kafelek[] kafelki){
		this.stojakNaKafelki.removeAllViews();
	    if(kafelki!=null){
	    	for(int i=0;i<kafelki.length;++i){
	    		Kafelek kafelek=kafelki[i];
	    		ImageView kafWidok = new ImageView(this);
	    		KafelekDrawable kafRys = new KafelekDrawable(this);
	    		kafRys.ustawIkone(getResources().getDrawable(R.drawable.tile3));
	    		char litera = kafelek.pobierzLitere();
	    		kafRys.ustawTekst(SlowaPomocne.literaNaTekst(litera));
	    		kafRys.ustawPunkty(2);//this.scoring.getLetterPoints(c));
	    		if (litera == '?')
	    			kafRys.ustawBlank(true);
	    		else
	    			kafRys.ustawBlank(false);
	    		kafWidok.setImageDrawable(kafRys);
	    		this.stojakNaKafelki.addView(kafWidok, this.stojakNaKafelki_LayoutParams);
	    		kafWidok.invalidate();
	        }
	    }
	}
}
