package com.example.scr_ani.Kafelek;

public class Kafelek{
	
	public static final char BLANK_LITERA = '?';
	public int kolumna;
	public char litera;
	public int wiersz;
	protected boolean blank;

	public Kafelek(char litera, int wiersz, int kolumna){
		this.litera = litera;
		this.wiersz = wiersz;
		this.kolumna = kolumna;
	}

	public Kafelek(char litera, int wiersz, int kolumna, boolean blank){
		this.litera = litera;
		this.wiersz = wiersz;
		this.kolumna = kolumna;
		this.blank= blank;
	}
	public boolean czyBlank(){
		return this.blank;
	}
	
	public char pobierzLitere(){
		return this.litera;
	}

	public int pobierzKolumne(){
		return this.kolumna;
	}

	public int pobierzWiersz(){
		return this.wiersz;
  	}

	public void ustawLitere(char litera){
		this.litera = litera;
	}

	public void ustawKolumne(int kolumna){
		this.kolumna = kolumna;
	}

	public void ustawWiersz(int wiersz){
		this.wiersz = wiersz;
	}
}
