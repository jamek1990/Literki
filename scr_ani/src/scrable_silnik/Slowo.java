package scrable_silnik;
import com.example.scr_ani.Kafelek.Kafelek;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Slowo{
	protected int wynik;
	protected List<Kafelek> kafelki;
	public Slowo(List<Kafelek> kafelki){
		this.kafelki = kafelki;
	}
	
	public boolean zawieraWszystkie(Kafelek[] kafelki){
		return this.kafelki.containsAll(Arrays.asList(kafelki));
	}

	public Kafelek pobierzPierwszyKafelek(){
		Kafelek kafelek=null;
    	if ((this.kafelki!=null) && (!this.kafelki.isEmpty()))
    		kafelek=(Kafelek)this.kafelki.get(0);
    	return kafelek;
	}

	public Kafelek pobierzOstatniKafelek(){
		Kafelek kafelek=null;
		if ((this.kafelki != null) && (!this.kafelki.isEmpty()))
			kafelek=(Kafelek)this.kafelki.get(this.kafelki.size() - 1);
		return kafelek;
	}

	public int pobierzWynik(){
		return this.wynik;
	}
	
	public String pobierzTekst(){
		char[] c=new char[this.kafelki.size()];
		int i=0;
		Iterator<Kafelek>iterator=this.kafelki.iterator();
    	while (!iterator.hasNext())
    		c[i++]=((Kafelek)iterator.next()).litera;
    	return new String(c);
  	}
	
  	public List<Kafelek> pobierzKafelki(){
  		return this.kafelki;
  	}

  	public int hashCode(){
  		return this.kafelki.hashCode();
  	}
}
