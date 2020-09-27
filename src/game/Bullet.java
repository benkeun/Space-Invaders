package game;
import java.awt. * ;

public class Bullet {
  Rectangle Bullet = new Rectangle();
  private double xLoc,
  yLoc;
  private Color color;
  public boolean player;
  final private int bulletWidth = 14;
  final private int bulletHeight = 28;
  public Bullet(double x, double y, boolean from) {

    xLoc = x;
    yLoc = y;
    player = from;
  }

  public double getXLoc() {
    return xLoc;
  }

  public double getYLoc() {
    return yLoc;
  }

  public boolean checkCollision(Character player) {
    int width = 64;
    int height = 64;
    Rectangle me = new Rectangle();

    me.setBounds(player.getXLoc(), player.getYLoc(), width, height);
    Bullet.setBounds((int) this.getXLoc(), (int) this.getYLoc(), bulletWidth, bulletHeight);

    return Bullet.intersects(me);
  }

  public boolean checkCollision(Invader inv) {
    int width = 30;
    int height = 18;
    Rectangle alien = new Rectangle();

    alien.setBounds((int) inv.getXLoc(), (int) inv.getYLoc(), width, height);
    Bullet.setBounds((int) this.getXLoc(), (int) this.getYLoc(), bulletWidth, bulletHeight);

    return alien.intersects(Bullet);
  }

  public void moveBullet() {
    if (player) {
      yLoc -= 4;
    } else {
      yLoc += 4;
    }
  }
}