package com.example.scr_ani;
import com.example.scr_ani.Kafelek.Kafelek;
public class SlowaPomocne{
	
	
	public static final char tekstNaLitera(String tekst){
		if((tekst==null) || (tekst.length()==0))
	    	return '?';
	    return Character.toLowerCase(tekst.charAt(0));
	}

	/*zamienamy literke na tekst jesli nie jest blankiem*/
	public static final String literaNaTekst(char litera){
		char c=Character.toUpperCase(litera);
		String tekst=null;
    	if (c!='?')
    		tekst=Character.toString(litera);
    	return tekst;
	}
	
	/*Tworzymy tablice kafelkow z literkami z pobranego tesktu*/
	public static final Kafelek[] tekstNaKafelki(String tekst){
	    Kafelek[] kafelki=null;
	    if(tekst!=null){
	    	kafelki=new Kafelek[tekst.length()];
	    	for (int i=0;i<tekst.length();++i)
	    		kafelki[i]=new Kafelek(tekst.charAt(i),0,0);
	    }
	    return kafelki;
	}
}