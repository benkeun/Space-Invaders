package game;
import java.awt. * ;

public class Character {

  private int xLoc,
  yLoc,
  health;
  private Color color = Color.BLUE;

  public Character(double x, double y) {
    xLoc = (int) x;
    yLoc = (int) y;
    health = 3;
  }

  public int getXLoc() {
    return xLoc;
  }

  public int getYLoc() {
    return yLoc;
  }

  public void left() {
    xLoc -= 5;
    if (xLoc < 0) {
      xLoc = 0;
    }
  }

  public void right() {
    xLoc += 5;
    if (xLoc > 775) {
      xLoc = 775;
    }
  }
  public void space() {

}

  public int getHealth() {
    return health;
  }

  public void setHealth(int a) {
    health = a;
  }
}