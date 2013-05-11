package scrable_silnik;
import com.example.scr_ani.Kafelek.Kafelek;
import com.example.scr_ani.Board.Plansza;

import java.util.Iterator;

public class StandardowaPunktacja implements Punktacja{
	static int[] PUNKTY= new int[]{0, 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
	
	public void obliczWynik(DobryRuch dobryRuch, Plansza plansza) {
		for(int i=0;i<dobryRuch.slowa.length;++i)
			this.punktyZaSlowo(dobryRuch.slowa[i],plansza);
		byte bonus=0;
      	if(dobryRuch.kafelki.length==7)
      		bonus=40;
      	dobryRuch.ustawBonus(bonus);
	}

   	public void punktyZaSlowo(Slowo slowo, Plansza plansza) {
   		int var3=0;
   		int var4=1;
   		int var8=0;
   		for(Iterator<Kafelek> it= slowo.kafelki.iterator();it.hasNext();var3+=var8) {
   			Kafelek kafelek = (Kafelek)it.next();
   			boolean blank = false;//var6.isWildcard();
   			var8 = 0;
   			if(!blank)var8 = this.getLetterPoints(kafelek.litera);
   			int var9=1;
   			if(plansza.isCellOpen(kafelek.wiersz,kafelek.kolumna))
   				var9=plansza.getAward(kafelek.wiersz,kafelek.kolumna);
   			if(var9<0)
   				var4*=-var9;
   			else
   				var8*=var9;
   		}
   		slowo.wynik=var3*var4;
   	}

   	public int getLetterPoints(char var1) {
   		int var2 = Math.max(0, 1 + (Character.toUpperCase(var1) - 65));
   		return PUNKTY[var2];
   	}
}
