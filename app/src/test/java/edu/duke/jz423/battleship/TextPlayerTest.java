package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20);
    // App app = new App(b, sr, ps);
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed protmpt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test
  void test_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // App app = new App(b, sr, ps);
    TextPlayer player = createTextPlayer(3, 3, "A0V\nA1V\na3v\n", bytes);
    Placement[] expected = new Placement[3];

    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      player.doOnePlacement();
      assertEquals(
          "Player " + player.name + " where do you want to place a Destroyer?\n" + player.view.displayMyOwnBoard()
              + "\n",
          bytes.toString());
      bytes.reset(); // clear out bytes for next time around
    }
  }

  /**
   * change the App declaration
   * and construction to a TextPlayer
   */
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);

  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "A0V\nA1V\na2v\n", bytes);

    // App app = new App(b, sr, ps);
    String instruction = "--------------------------------------------------------------------------------\n" +
        "Player " + player.name + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" +
        "2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n" +
        "--------------------------------------------------------------------------------\n";
    String[] expected = new String[4];
    expected[0] = "  0|1|2\n" + "A  | |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "  0|1|2\n";
    expected[1] = "  0|1|2\n" + "A s| |  A\n" + "B s| |  B\n" + "C s| |  C\n" + "  0|1|2\n";
    expected[2] = "  0|1|2\n" + "A s|s|  A\n" + "B s|s|  B\n" + "C s|s|  C\n" + "  0|1|2\n";
    expected[3] = "  0|1|2\n" + "A s|s|s A\n" + "B s|s|s B\n" + "C s|s|s C\n" + "  0|1|2\n";
    for (int i = 0; i < expected.length - 1; i++) {
      player.doPlacementPhase();
      assertEquals(expected[i] + instruction + "Player A where do you want to place a Destroyer?\n" + expected[i + 1] + "\n",
          bytes.toString());
      bytes.reset();
    }
  }
}
