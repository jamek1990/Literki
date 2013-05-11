package scrable_silnik;
import com.example.scr_ani.Kafelek.Kafelek;

public class DobryRuch{
	protected int bonus;
  	protected Slowo[] slowa;
  	protected Kafelek[] kafelki;
  	
  	
	public DobryRuch(Kafelek[] kafelki){
    	this.kafelki = kafelki;
  	}
	
	public Slowo pobierzPodstawoweSlowo(){
		Slowo slowo;
	    if(this.slowa!=null && this.slowa.length>0){
	    	int j=0;
	    	while(true) {
		    	if(j>=this.slowa.length) {
		    		Slowo sl = this.slowa[0];
		    		for(int i= 0;i<this.slowa.length;++i) {
		    			if(sl.pobierzWynik()<this.slowa[i].pobierzWynik())
		    				sl=this.slowa[i];
		    		}
		    		slowo=sl;
		    		break;
		    	}
		    	int ile = 0;
		    	for(int i=0;i<this.kafelki.length;++i) {
		    		if(this.slowa[j].pobierzKafelki().contains(this.kafelki[i]))
		    			++ile;
		    		if(ile>1) {
		    			slowo = this.slowa[j];;
		    			return slowo;
		    		}
		    	}
		    	++j;
		    }
	    } else
	    	slowo = null;
	    return slowo;
	}

  	/*Pobiera Calkowity wynik*/
 	public int pobierzCalkowityWynik(){
	      int calkowityWynik=0;
	      if(this.slowa!=null)
	         for(int i=0;i<this.slowa.length;++i)
	        	 calkowityWynik+=this.slowa[i].pobierzWynik();
	      return calkowityWynik+this.bonus;
  	}
 	
	public int pobierzLiczbeKafelkow(){
    	if(this.kafelki!=null)
    		return 0;
    	return this.kafelki.length;
  	}
  	public Kafelek[] 	pobierzKafelki(){	return this.kafelki;}
  	public Slowo[] 		pobierzSlowa(){		return this.slowa;}
	public int 			pobierzBonus(){		return this.bonus;}
	
	public void ustawBonus(int bonus){	this.bonus = bonus;	}
}