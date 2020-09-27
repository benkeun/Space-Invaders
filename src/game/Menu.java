package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Menu extends LoopOfGame implements KeyListener {
  private Font titleFont = new Font("Arial", Font.PLAIN, 64);
  private Font startFont = new Font("Arial", Font.PLAIN, 32);
  private String title = "Space Invaders";
  private String start = "Press Enter";

  public void render(Graphics g) throws IOException {

    g.setColor(Color.white);
    g.drawRect(0, 0, 845, 480);
    g.setFont(titleFont);
    int titleWidth = g.getFontMetrics().stringWidth(title);
    g.setColor(Color.green);
    g.drawString(title, ((845 / 2) - (titleWidth / 2)) - 2, (480 / 2) - 123);
    g.setColor(Color.blue);
    g.drawString(title, (845 / 2) - (titleWidth / 2), (480 / 2) - 125);

    g.setFont(startFont);
    g.setColor(Color.red);
    int startWidth = g.getFontMetrics().stringWidth(start);
    g.drawString(start, (845 / 2) - (startWidth / 2), (480 / 2) + 75);
    g.setColor(Color.white);
    g.drawRect(0, 0, 845, 480);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      Game.State = Game.STATE.GAME;
    }

  }

}