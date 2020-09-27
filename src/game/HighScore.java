
package game; //Not necessary in eclipse
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HighScore {
  //declares variables for the highscore class
  private static String[] name;
  private static long[] highScore;
  ArrayList < String > names = new ArrayList < String > ();
  ArrayList < Long > highScores = new ArrayList < Long > ();
  BufferedReader br = null;

  //constructor for a highscore instance
  public HighScore() {

    try {
      //creates a file reader to read the text file containing the highscores
      String currentDirectory = System.getProperty("user.dir");
      br = new BufferedReader(new FileReader(currentDirectory + "/res/HighScores.txt"));
      String word = br.readLine();
      long scores;
      while (word != null) {
        //adds the names and scores to array lists
        names.add(word);
        scores = Long.parseLong(br.readLine());
        highScores.add(scores);
        word = br.readLine();
      }

      //prints out errors
    } catch(IOException e) {
      System.out.println("Error: " + e);
    } finally {
      try {
        br.close();
      } catch(IOException ex) {}
    }
    //puts both arraylists into arrays
    name = new String[names.size()];
    names.toArray(name);

    highScore = new long[highScores.size()];
    for (int e = 0; e < highScores.size(); e++) {
      highScore[e] = highScores.get(e);

    }

  }
  //returns highscores array
  public static long[] getHighScores() {
    return highScore;
  }

  //returns name array
  public static String[] getNames() {
    return name;
  }

  //writes current highscores to the document with their paired names
  public static void writeToDocument(long[] a, String b[]) throws FileNotFoundException,
  UnsupportedEncodingException {
    try (PrintWriter writer = new PrintWriter("HighScores.txt", "UTF-8")) {
      for (int c = 0; c < a.length; c++) {
        writer.println(b[c]);
        writer.println(a[c]);

      }
    }
  }

  public static void quickSorting(long[] a, String[] b, int left, int right) {
    //saves the min and max of indexes being sorted
    int i = left;
    int j = right;
    // Get the pivot element from the middle of the list
    long pivot = a[((left + right) / 2)];
    //checks sort order selected
    //if left and right are the same or left is greater the sort is done
    if (left >= right) {
      return;
    }
    //repeat while the left is less than the right
    while (i < j) {

      //repeat while the number at index i is smaller than the pivot
      while (a[i] > pivot) {
        //i is increased, this number is on proper side
        i++;

      }
      //repeat while the number at index j is larger than the pivot
      while (a[j] < pivot) {
        //j is decreased
        j--;

      }
      //if i<=j then number is in the wrong spot and moves the name
      //with it
      if (i <= j) {
        //saves number at index i
        long temp = a[i];
        String temporary = b[i];
        //moves number at index j to index i
        a[i] = a[j];
        b[i] = b[j];
        //moves the temp to index j
        a[j] = temp;
        b[j] = temporary;
        //i increased and j decreased
        i++;
        j--;

      }
      // Recursion, repeats method till entire list is sorted
      quickSorting(a, b, left, j); //sort the left side of the list
      quickSorting(a, b, i, right); //sort the right side of the list
    }
  }
  //alerts the user to a highscore and asks for their names
  public static void updateHighScore(long[] a, String[] b, long p) {
    //if their score is higher then the lowest highscore then replace it
    if (p >= a[9]) {
      a[9] = p;
      String name = JOptionPane.showInputDialog(null, "What is Your Name Space Defender! \nYou have a High Score!");
      if (name != null) {
        b[9] = name;
      } else {
        b[9] = "Mysterious Stranger";
      }
    }
  }
  //resets the highscores back to 0 with no name
  public static void resetHighscore() throws FileNotFoundException,
  UnsupportedEncodingException {
    try (PrintWriter writer = new PrintWriter("HighScores.txt", "UTF-8")) {
      for (int c = 0; c < 10; c++) {
        writer.println("Unknown");
        writer.println(0);

      }
    }
  }
  //displays all the highscores for the user to see
  public static void displayHighScores() {
    //instantiates new highscore
    HighScore a = new HighScore();
    //creates a string to store all of the highscores
    String Output = "";
    for (int q = 0; q < a.highScores.size(); q++) {
      //puts the names and highscores together in the string
      Output = Output + a.names.get(q) + "\n" + a.highScores.get(q) + "\n";
    }
    //displays a popup which tells them the highscores with the title of 
    //"HighScores"
    JOptionPane.showMessageDialog(null, Output, "HighScores", JOptionPane.INFORMATION_MESSAGE);

    //Displays a popup which asks them if they wish to reset the highscores,
    //saves their reply in the variable
    int reply = JOptionPane.showConfirmDialog(null, "Do You wish to reset highscore?", "Choose 1", JOptionPane.YES_NO_OPTION);

    //if the variable reply matches the number for the yes option then run the
    //reset highscore method.
    if (reply == JOptionPane.YES_OPTION) {
      try {
        HighScore.resetHighscore();

        //Declares any errors that occured while trying to change the highscores.
      } catch(FileNotFoundException ex) {
        Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
      } catch(UnsupportedEncodingException ex) {
        Logger.getLogger(LoopOfGame.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }
}