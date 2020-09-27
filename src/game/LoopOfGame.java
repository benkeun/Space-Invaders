package game;
//Game Loop (Actual coding of Game)
//Imports required for program
import java.awt. * ;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//Class extending Applet (Creates window) implementing runnable code and keylistener
public class LoopOfGame extends Frame implements WindowListener,
KeyListener,
Runnable {

  //List of program variables
  public int mY, mX, numberOfBullets;
  public int x,y;
  public Image offScreen;
  public Graphics d;
  public boolean left, right, space;
  public BufferedImage background, alien, ship, missile, explosion;
  Character p;
  ArrayList < Bullet > bullets = new ArrayList < Bullet > ();
  ArrayList < Invader > invaders = new ArrayList < Invader > ();
  private int lastShot = 100;
  private int score = 0;
  private long timeofShot = 10000;
  boolean gameRunning = true;
  private int round = 1;
  private boolean music = false;

  public void createBullet() {
    Bullet b = new Bullet(p.getXLoc() + 11, p.getYLoc(), true);
    bullets.add(b);

  }
  public void windowClosing(WindowEvent e) {
    dispose();
    System.exit(0); // normal exit of program
  }

  public void windowOpened(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}

  //Running of code
  public void run() {
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    while (Game.getGameState() == Game.STATE.MENU) {
      try {
        //Causes the thread to sleep. slows everything down to a manageable and visible speed
        Thread.sleep(10);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    Thread thread1 = new Thread() {@Override
      public void run() {
        if (music == false) {
          try {
            MusicPlayer.Music();
          } catch(FileNotFoundException ex) {
            Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
          }
          music = true;
        }
      }
    };
    Thread thread2 = new Thread() {
      public void run() {

        p = new Character(400, 400);

        for (int i = 0; i < 16; i++) {
          for (int j = 0; j < 4; j++) {
            invaders.add(new Invader(46.9 + i * 40, 25 + j * 40, 1));
            Invader.setNumAliens(Invader.getNumAliens() + 1);

          }
        }

        //Following code is required to be in Try/Catch statement to run
        try {

          //Sets the 'BufferedImage variables to the images in the source folder
          background = ImageIO.read(this.getClass().getResource("/res/Background.png"));
          ship = ImageIO.read(this.getClass().getResource("/res/Spaceship.png"));
          alien = ImageIO.read(this.getClass().getResource("/res/Alien.png"));
          missile = ImageIO.read(this.getClass().getResource("/res/Missile.png"));
          explosion = ImageIO.read(this.getClass().getResource("/res/Explosion.png"));

        } catch(IOException e1) {
          e1.printStackTrace();
        }

        //While statement that is always true and continuously loops through the code inside (The Game Loop)
        while (gameRunning == true) {

          if (left == true) {
            p.left();
          }

          if (right == true) {
            p.right();
          }

          if (space == true && lastShot < 0) {
            timeofShot = System.currentTimeMillis();

            createBullet();
            lastShot = 100;
          }

          lastShot -= 1;

          int bulletCount = bullets.size();
          int invaderCount = invaders.size();

          ArrayList < Integer > remBullet = new ArrayList < Integer > ();
          ArrayList < Integer > remInvader = new ArrayList < Integer > ();

          for (int i = 0; i < bulletCount; i++) {
            Bullet b = bullets.get(i);
            b.moveBullet();
            if (b.getYLoc() > 600 || b.getYLoc() < 0) {
              remBullet.add(i);
            }
            for (int j = 0; j < invaderCount; j++) {
              Invader n = invaders.get(j);
              if (b.checkCollision(n) && b.player == true) {
                n.health -= 1;
                score += 50;
                if (n.health <= 0) {
                  remInvader.add(j);
                  score += 100;
                  Invader.setNumAliens(Invader.getNumAliens() - 1);
                }
                remBullet.add(i);
              }
            }
            if (b.checkCollision(p) && b.player == false) {
              p.setHealth(p.getHealth() - 1);
              remBullet.add(i);

            }
          }
          if (Invader.getNumAliens() <= 0) {
            round++;
            for (int i = 0; i < 18; i++) {
              for (int j = 0; j < 3 + round; j++) {
                invaders.add(new Invader(46.9 + i * 40, 25 + (j * 40 / (0.6 * round)), 1));
                Invader.setNumAliens(Invader.getNumAliens() + 1);

                remBullet.clear();
                remInvader.clear();

              }
            }
          }
          if (p.getHealth() <= 0 || Invader.lowestAlien(invaders) >= p.getYLoc()) {
            gameRunning = false;
          }

          for (Invader i: invaders) {
            i.moveAndUpdate();
            if (i.newBullet() && Math.random() < 0.5 / (invaders.size() - 0.04 / invaders.size())) {
              bullets.add(new Bullet(i.getXLoc(), i.getYLoc(), false));
            }
          }
          for (int i: remBullet) {
            if (i < bullets.size()) {
              bullets.remove(i);
            }
          }
          for (int i: remInvader) {
            if (i < invaders.size()) {
              invaders.remove(i);
            }
          }

          //repaint method makes program redraw all the components in the screen. this allows game to flow and 
          //images to move.
          repaint();
          //Following code needs to be surrounded by Try/Catch statement in order to work.
          try {
            //Causes the thread to sleep for 2 seconds. slows everything down to a manageable and visible speed
            Thread.sleep(20);
          } catch(InterruptedException e) {
            e.printStackTrace();
          }

        }
        //Game is no longer running tells the user the game is over.
        JOptionPane.showMessageDialog(null, "Game Over!!!");
        try {
          //updates the highscores by getting the arrays from the highscore
          //class and updating them to include the new score in the proper order. 
          long scores[] = HighScore.getHighScores();
          String names[] = HighScore.getNames();
          HighScore.updateHighScore(scores, names, score);
          HighScore.quickSorting(scores, names, 0, scores.length - 1);
          //updates them to the document to be available when the program is run
          //again
          HighScore.writeToDocument(scores, names);

          //displays errors with the writing of the highscores.
        } catch(FileNotFoundException ex) {
          Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch(UnsupportedEncodingException ex) {
          Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        //displays the user for the player to see
        HighScore.displayHighScores();
      }
    };
    thread1.start();
    thread2.start();
  }

  //Whenever a key is pressed the following is run.
  public void keyPressed(KeyEvent e) {
    boolean paused = false;
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_LEFT) {
      left = true;
    }

    if (code == KeyEvent.VK_RIGHT) {
      right = true;
    }

    if (code == KeyEvent.VK_SPACE) {
      if (System.currentTimeMillis() - timeofShot >= 500) {
        space = true;
      }

    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_LEFT) {
      left = false;
    }

    if (code == KeyEvent.VK_RIGHT) {
      right = false;
    }

    if (code == KeyEvent.VK_SPACE) {
      space = false;
      lastShot = 0;
    }

  }

  public void keyTyped(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_SPACE) {
      createBullet();
    }
  }

}