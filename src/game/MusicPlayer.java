/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io. * ;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class MusicPlayer {
  Clip clip;

  AudioInputStream audioInputStream;
  static String filePath;
  /**
     * @param args the command line arguments
     */
  public static void main(String[] args) {
    try {
      Music();
    } catch(Exception e) {
      System.out.println("ERROR");
    }
  }
  public static void Music() throws FileNotFoundException {
    String currentDirectory = System.getProperty("user.dir");
    filePath = currentDirectory + "/res/music.wav";
    MusicPlayer audioPlayer = new MusicPlayer();

    audioPlayer.clip.start();

  }
  public MusicPlayer() {
    try {
      audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

      // create clip reference 
      clip = AudioSystem.getClip();

      // open audioInputStream to the clip 
      clip.open(audioInputStream);

      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch(Exception ex) {
      Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}