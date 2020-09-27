package game;

import java.util.ArrayList;

public class Invader {

  private double xLoc,
  yLoc;
  public int health;
  private int currentDir = 0;
  private int steps = 200;
  private int lastShot = 10;
  private static double numAliens;
  public Invader(double x, double y, int hlth) {
    xLoc = x;
    yLoc = y;
    health = hlth;
  }

  public boolean newBullet() {
    lastShot--;
    if (lastShot < 0) {
      lastShot = 10;
      return true;
    } else {
      return false;
    }
  }

  public void moveAndUpdate() {

    if (currentDir == 0) {
      xLoc += 0.2;
    } else if (currentDir == 90) {
      yLoc += 0.1;
    } else if (currentDir == 180) {
      xLoc -= 0.2;
    } else if (currentDir == 270) {
      yLoc += 0.1;
    }
    if (numAliens > 36) {
      steps--;
    }
    else {
      steps = steps - 2;
    }
    if (steps < 0) {
      steps = 200;
      currentDir += 90;
      if (currentDir > 270) {
        currentDir = 0;
      }
    }
  }

  public double getXLoc() {
    return xLoc;
  }

  public double getYLoc() {
    return yLoc;
  }
  public static double getNumAliens() {
    return numAliens;
  }
  public static void setNumAliens(double a) {
    numAliens = a;
  }
  public static double lowestAlien(ArrayList < Invader > a) {
    double lowestYLoc = a.get(0).getYLoc();
    for (int i = 0; i < a.size(); i++) {
      if (lowestYLoc < a.get(i).getYLoc()) {
        lowestYLoc = a.get(i).getYLoc();
      }
    }
    return lowestYLoc;
  }

}