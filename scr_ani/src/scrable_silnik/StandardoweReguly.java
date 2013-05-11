package scrable_silnik;
import com.example.scr_ani.Kafelek.Kafelek;
import com.example.scr_ani.Board.Plansza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class StandardoweReguly implements Reguly {
	private Punktacja punktacja;

	public StandardoweReguly(Punktacja var1) {
		this.setScoring(var1);
	}
	
	protected boolean sprawdzDostep(Plansza plansza, Kafelek[] kafelki) {
		boolean dostepny=true;
	    for(int i=0;i<kafelki.length;i++)
	        if(!plansza.isCellOpen(kafelki[i].wiersz,kafelki[i].kolumna)){
	        	dostepny=false;
	        	break;
	        }
	     return dostepny;
	}

   	protected void constructStrings(Plansza plansza, Kafelek kafelek, Kafelek[] kafelki, Set<Slowo>slowa) {
   		Slowo slowo=this.constructWord(plansza,kafelek,kafelki,true);
   		if(slowo!=null)slowa.add(slowo);
   		Slowo slowo2=this.constructWord(plansza,kafelek,kafelki,false);
   		if(slowo2!=null)slowa.add(slowo2);
   	}
   	
   	protected Slowo constructWord(Plansza plansza, Kafelek kafelek, Kafelek[] var3, boolean kierunek) {
   		int var5;
   		if(kierunek)
   			var5 = kafelek.kolumna;
   		else
   			var5 = kafelek.wiersz;
   		ArrayList<Kafelek> var6 = new ArrayList<Kafelek>();
   		for(int i=var5-1;i>0;--i) {
   			Kafelek var8;
   			if(kierunek)
   				var8 = this.pobierzKafelek(plansza, var3, kafelek.wiersz, i);
   			else
   				var8 = this.pobierzKafelek(plansza, var3, i, kafelek.kolumna);
   			if(var8 == null) 
   				break;
   			var6.add(var8);
   		}
   		Collections.reverse(var6);
   		var6.add(kafelek);
   		for(int i=var5+1;i<=15;++i) {
   			Kafelek var12;
   			if(kierunek)
   				var12 = this.pobierzKafelek(plansza, var3, kafelek.wiersz,i);
   			else
   				var12 = this.pobierzKafelek(plansza, var3, i,kafelek.kolumna);
   			if(var12 == null)
   				break;
   			var6.add(var12);
   		}
   		Slowo var14;
   		if(var6.size() <= 1)
   			var14 = null;
   		else 
   			var14 = new Slowo(var6);
   		return var14;
   	}
   	
   	protected DobryRuch getFirstMove(Plansza board,Slownik slownik,Kafelek[] kafelki) {
   		DobryRuch dobryRuch=null;
   		if(!this.isValidFirstMove(kafelki, 15)) {
   			dobryRuch = null;
   		} else {
   			DobryRuch dobryRuch2 = new DobryRuch(kafelki);
   			HashSet<Slowo> var5 = new HashSet<Slowo>();
   			this.constructStrings(board, kafelki[0], kafelki, var5);
   			dobryRuch2.slowa = new Slowo[var5.size()];
   			var5.toArray(dobryRuch2.slowa);
   			if(!this.validateAndScore(board, slownik, dobryRuch2)) {
   				dobryRuch = null;
   			} else {
   				dobryRuch= dobryRuch2;
   			}
   		}
   		return dobryRuch;
   	}
   	public DobryRuch getPlayableMove(Plansza board, Slownik var2, Kafelek[] kafelki) {
   		DobryRuch var4;
   		if(kafelki!= null && kafelki.length != 0) {
   			if(!this.czyWJednymKierunku(kafelki)) {
   				var4 = null;
   			} else {
   				DobryRuch var5=null;
   				if(board.isFirstPlay()) 
   					var5 = this.getFirstMove(board, var2, kafelki);
   				else
   					this.getSubsequentMove(board, var2, kafelki);
   				if(var5 != null) {
   					Slowo var6 = var5.pobierzPodstawoweSlowo();
   					if(var6 == null ){//|| !var6.containsAll(kafelki)) {
   						var4 = null;
   						return var4;
   					}
   				}
   				var4 = var5;
   			}
   		} else {
   			var4 = null;
   		}
   		return var4;
   	}
   	

   public Punktacja pobierzPunktacje() {
      return this.punktacja;
   }
   
   	protected boolean hasAdjacentTile(Plansza plansza, Kafelek[] kafelki) {
	    boolean var6=false;
	    for(int i=0;i<kafelki.length;i++){
	        if(kafelki[i].wiersz > 1 && !plansza.isCellOpen(kafelki[i].wiersz - 1, kafelki[i].kolumna)) {var6 = true;break;}
	        if(kafelki[i].wiersz < 15 && !plansza.isCellOpen(1 + kafelki[i].wiersz, kafelki[i].kolumna)) {var6 = true;break;}
	        if(kafelki[i].kolumna> 1 && !plansza.isCellOpen(kafelki[i].wiersz, kafelki[i].kolumna - 1)) {var6 = true;break;}
	        if(kafelki[i].kolumna < 15 && !plansza.isCellOpen(kafelki[i].wiersz, 1 + kafelki[i].kolumna)){var6 = true;break;}
	     }
	     return var6;
   	}
   
   	protected DobryRuch getSubsequentMove(Plansza plansza, Slownik slownik, Kafelek[] kafelki) {
   		DobryRuch dobryRuch;
   		if(!this.sprawdzDostep(plansza, kafelki))
   			dobryRuch = null;
   		else if(!this.hasAdjacentTile(plansza, kafelki))
   			dobryRuch= null;
   		else {
   			DobryRuch var5 = new DobryRuch(kafelki);
   			HashSet<Slowo> var6 = new HashSet<Slowo>();
   			for(int i=0;i<kafelki.length;++i)
   				this.constructStrings(plansza,kafelki[i],var5.kafelki,var6);
   			var5.slowa = new Slowo[var6.size()];
   			var6.toArray(var5.slowa);
   			if(!this.validateAndScore(plansza,slownik,var5)) 
   				dobryRuch  = null;
   			else 
   				dobryRuch = var5;
   		}
	   	return dobryRuch;
   	}

   protected Kafelek pobierzKafelek(Plansza plansza, Kafelek[] kafelki, int var3, int var4) {
      Kafelek kafelek=plansza.pobierzKafelek(var3,var4);
      if(kafelek==null) 
         for(int i=0;i<kafelki.length;++i)
            if(kafelki[i].wiersz==var3 && kafelki[i].kolumna==var4){
               kafelek=kafelki[i];
               break;
            }
      return kafelek;
   }


   	protected boolean czyWJednymKierunku(Kafelek[] kafelki){
   		if(kafelki.length == 1)
   			return true;
   		else {
   			int wie=kafelki[0].wiersz;
   			int kol=kafelki[0].kolumna;
   			if(wie==kafelki[1].wiersz){
   				for(int i=2;i<kafelki.length;++i)
   					if(wie!=kafelki[i].wiersz)
   						return false;
   			} else {
   				if(kol!=kafelki[1].kolumna) 
   					return false;
   				for(int i=2;i<kafelki.length;++i)
   					if(kol!=kafelki[i].kolumna)
   						return false;
            }
   			return true;
   		}
   }

   	protected boolean isValidFirstMove(Kafelek[] kafelki, int var2) {
   		int var3 = 1 + var2 / 2;
   		boolean var7=false;
   		for(int i=0;i<kafelki.length;i++){
   			Kafelek var6 =kafelki[i];
   			if(var6.wiersz== var3 && var6.kolumna == var3) {
   				var7=true;
   				break;
   			}
   		}
   		return var7;
   	}

   public void setScoring(Punktacja var1) {
      this.punktacja= var1;
   }
   
   	protected boolean validateAndScore(Plansza plansza,Slownik slownik,DobryRuch dobryRuch) {
   		Slowo[] slowa=dobryRuch.slowa;
   		boolean var7=true;
   		for(int i=0;i<slowa.length;i++){
   			/* if(false){//!slownik.jestSlowem(slowa[i].pobierzTekst())){
            	var7 = false;
            	break;
         	}*/
   			if(i==slowa.length-1)
   	   			this.punktacja.obliczWynik(dobryRuch,plansza);
   		}
   		return var7;
   	}
}
