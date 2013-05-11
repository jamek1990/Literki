package scrable_silnik;
import com.example.scr_ani.Board.Plansza;
public abstract interface Punktacja
{
  public abstract void obliczWynik(DobryRuch paramPlayableMove, Plansza paramBoard);

  public abstract void punktyZaSlowo(Slowo paramWord, Plansza paramBoard);

  public abstract int getLetterPoints(char paramChar);
}