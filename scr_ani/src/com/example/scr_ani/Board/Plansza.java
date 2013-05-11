package com.example.scr_ani.Board;
import com.example.scr_ani.Kafelek.Kafelek;

public class Plansza{
	public static final int DL = 2;//b
	public static final int DW = -2;//c
	public static final int SL = 1;//e
	public static final int TL = 3;//d
	public static final int TW = -3;//a
	public static final int MAX_LENGTH = 15;
  	private boolean firstPlay;
  	private Kafelek[][] kafelki;
  	public char[][] bor;
  	public char b01[] = {'a','e','e','b','e','e','e','a','e','e','e','b','e','e','a'};
  	public char b02[] = {'e','e','c','e','e','e','b','e','b','e','e','e','c','e','e'};
  	public char b03[] = {'e','c','e','e','e','c','e','e','e','c','e','e','e','c','e'};
  	public char b04[] = {'b','e','e','e','d','e','e','b','e','e','d','e','e','e','b'};
  	public char b05[] = {'e','e','e','b','e','e','e','e','e','e','e','b','e','e','e'};
  	public char b06[] = {'e','e','d','e','e','e','d','e','d','e','e','e','d','e','e'};
  	public char b07[] = {'e','b','e','e','e','c','e','e','e','c','e','e','e','b','e'};
  	public char b08[] = {'a','e','e','b','e','e','e','f','e','e','e','b','e','e','a'};
  	public char b09[] = {'e','b','e','e','e','c','e','e','e','c','e','e','e','b','e'};
  	public char b10[] = {'e','e','d','e','e','e','d','e','d','e','e','e','d','e','e'};
  	public char b11[] = {'e','e','e','b','e','e','e','e','e','e','e','b','e','e','e'};
  	public char b12[] = {'b','e','e','e','d','e','e','b','e','e','d','e','e','e','b'};
  	public char b13[] = {'e','c','e','e','e','c','e','e','e','c','e','e','e','c','e'};
  	public char b14[] = {'e','e','c','e','e','e','b','e','b','e','e','e','c','e','e'};
  	public char b15[] = {'a','e','e','b','e','e','e','a','e','e','e','b','e','e','a'};

  	public Plansza(){
  		bor = new char[15][];
  		for(int i=0;i<15;i++){
  			bor[i]=new char[15];
  		}
  		for(int i=0;i<15;i++)bor[0][i]=b01[i];
  		for(int i=0;i<15;i++)bor[1][i]=b02[i];
  		for(int i=0;i<15;i++)bor[2][i]=b03[i];
  		for(int i=0;i<15;i++)bor[3][i]=b04[i];
  		for(int i=0;i<15;i++)bor[4][i]=b05[i];
  		for(int i=0;i<15;i++)bor[5][i]=b06[i];
  		for(int i=0;i<15;i++)bor[6][i]=b07[i];
  		for(int i=0;i<15;i++)bor[7][i]=b08[i];
  		for(int i=0;i<15;i++)bor[8][i]=b09[i];
  		for(int i=0;i<15;i++)bor[9][i]=b10[i];
  		for(int i=0;i<15;i++)bor[10][i]=b11[i];
  		for(int i=0;i<15;i++)bor[11][i]=b12[i];
  		for(int i=0;i<15;i++)bor[12][i]=b13[i];
  		for(int i=0;i<15;i++)bor[13][i]=b14[i];
  		for(int i=0;i<15;i++)bor[14][i]=b15[i];
  		
  		Kafelek[][] kafelki=new Kafelek[15][];
  		for(int i=0;i<15;i++)
  			kafelki[i] = new Kafelek[15];
  		this.kafelki=kafelki;
  		this.firstPlay = true;
  	}
  	public void clear(){
  		for(int i=0;i<15;i++)
  			for(int j=0;j<15;j++)
  				this.kafelki[i][j] = null;
  		this.firstPlay = true;
  	}
  
  	public Kafelek pobierzKafelek(int x,int y){
  		return this.kafelki[x-1][y-1];
  	}

  	public boolean isCellOpen(int paramInt1, int paramInt2){
  		if (pobierzKafelek(paramInt1, paramInt2) == null)
  			return true;
  		return false;
  	}
    public int getAward(int var1, int var2) {
        return 1;//AWARDS[var1 - 1][var2 - 1];
     }

  	public boolean isFirstPlay(){
  		return this.firstPlay;
  	}
}