package scrable_silnik;
import com.example.scr_ani.Kafelek.Kafelek;
import com.example.scr_ani.Board.Plansza;
public abstract interface Reguly{
  public abstract DobryRuch getPlayableMove(Plansza plansza, Slownik slownik, Kafelek[] kafelki);
}
