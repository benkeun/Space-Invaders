package game;
//This is the screen controlling part of program
//Required Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing. * ;
import java.awt.image.BufferedImage;

public class Game extends LoopOfGame {
  private Menu menu;
  public int hit = 3;
  public int loopCounter = 0;

  BufferedImage bf = new BufferedImage(845, 530, BufferedImage.TYPE_INT_RGB);

  public static enum STATE {
    GAME,
    MENU
  };

  public static STATE State = STATE.MENU;
  public static void main(String[] args) {

    Game app = new Game();
    app.addWindowListener(app);
    app.setSize(845, 500);
    app.setVisible(true);
    app.setLayout(new BorderLayout());
    app.addKeyListener(app);
    app.addKeyListener(new Menu());
  }
  // Initializing components
  public Game() {
    Graphics gr = bf.getGraphics();
    gr.setColor(Color.white);
    gr.fillRect(0, 10, 845, 545);
    // if (State == STATE.GAME){
    setSize(845, 545);
    Thread th = new Thread(this);
    //if (State == STATE.GAME) {
    th.start();
    //}

    HighScore h = new HighScore();
    // } else {
    menu = new Menu();
    // }
  }

  // Creating of images on screen
  public void paint(Graphics g) {
    paintBuffered(bf.getGraphics()); //bf is the BufferedImage object
    g.drawImage(bf, 0, 10, null);
  }
  public void paintBuffered(Graphics g) {

    if (State == STATE.GAME) {
      g.clearRect(0, 10, 1000, 600);
      g.drawImage(background, 0, 10, this);
      if (hit > p.getHealth() || loopCounter > 0) {
        g.drawImage(explosion, p.getXLoc(), p.getYLoc(), this);
        if (hit > p.getHealth()) {
          hit -= 1;
          loopCounter = 10;
        } else {
          loopCounter--;
        }
      } else {
        g.drawImage(ship, p.getXLoc(), p.getYLoc(), this);
      }
      for (int i = 0; i < invaders.size(); i++) {
        g.drawImage(alien, (int) invaders.get(i).getXLoc(), (int) invaders.get(i).getYLoc(), this);
      }
      for (int i = 0; i < bullets.size(); i++) {
        g.drawImage(missile, (int) bullets.get(i).getXLoc(), (int) bullets.get(i).getYLoc(), this);
      }
      g.drawImage(offScreen, 0, 0, this);

    } else if (State == STATE.MENU) {

      try {
        menu.render(g);
      } catch(IOException ex) {
        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }

  public void update(Graphics g) {

    paint(g);
  }

  public static STATE getGameState() {
    return State;

  }
}