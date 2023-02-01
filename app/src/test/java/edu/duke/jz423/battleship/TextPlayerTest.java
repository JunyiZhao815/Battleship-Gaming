package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.function.Function;
import org.junit.jupiter.api.Test;


public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    // App app = new App(b, sr, ps);
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    String line = "--------------------------------------------------------------------------------\n";

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(line + prompt + "\n" + line, bytes.toString()); // should have printed protmpt and newline
      bytes.reset(); // clear out bytes for next time around
    }
    String a = null;
    assertThrows(IllegalArgumentException.class, () -> player.readPlacement(a));

    StringReader sr2 = new StringReader("");
    TextPlayer player2 = new TextPlayer("A", b, new BufferedReader(sr2), ps, new V1ShipFactory());
    assertThrows(EOFException.class, () -> player2.readPlacement(prompt));

    StringReader sr3 = new StringReader("AAV"); // A2Q is also tested on this line
    TextPlayer player3 = new TextPlayer("A", b, new BufferedReader(sr3), ps, new V1ShipFactory());
    assertThrows(IllegalArgumentException.class, () -> player3.readPlacement(prompt));
  }

  @Test
  void test_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // App app = new App(b, sr, ps);
    TextPlayer player = createTextPlayer(10, 10, "A0V\nA1V\na3v\n", bytes);

    String line = "--------------------------------------------------------------------------------\n";
    for (int i = 0; i < 3; i++) {
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      assertEquals(
          line + "Player " + player.name + " where do you want to place a Destroyer?\n" + line + line
              + "Current ocean:\n" + player.view.displayMyOwnBoard() + line,
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
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);

  }

  /**
   * @Test
   *       public void test_doPlacementPhase() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       TextPlayer player = createTextPlayer(3, 3, "A0V\nA1V\na2v\n", bytes);
   * 
   *       // App app = new App(b, sr, ps);
   *       String instruction =
   *       "--------------------------------------------------------------------------------\n"
   *       +
   *       "Player " + player.name + ": you are going to place the following ships
   *       (which are all\n" +
   *       "rectangular). For each ship, type the coordinate of the upper left\n"
   *       +
   *       "side of the ship, followed by either H (for horizontal) or V (for\n" +
   *       "vertical). For example M4H would place a ship horizontally starting\n"
   *       +
   *       "at M4 and going to the right. You have\n\n" +
   *       "2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n"
   *       +
   *       "3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n" +
   *       "--------------------------------------------------------------------------------\n";
   *       String[] expected = new String[4];
   *       expected[0] = " 0|1|2\n" + "A | | A\n" + "B | | B\n" + "C | | C\n" + "
   *       0|1|2\n";
   *       expected[1] = " 0|1|2\n" + "A s| | A\n" + "B s| | B\n" + "C s| | C\n" +
   *       " 0|1|2\n";
   *       expected[2] = " 0|1|2\n" + "A s|s| A\n" + "B s|s| B\n" + "C s|s| C\n" +
   *       " 0|1|2\n";
   *       expected[3] = " 0|1|2\n" + "A s|s|s A\n" + "B s|s|s B\n" + "C s|s|s
   *       C\n" + " 0|1|2\n";
   *       for (int i = 0; i < expected.length - 1; i++) {
   *       player.doPlacementPhase();
   *       assertEquals(
   *       expected[i] + instruction + "Player A where do you want to place a
   *       Destroyer?\n" + expected[i + 1] + "\n",
   *       bytes.toString());
   *       bytes.reset();
   *       }
   *       }
   */
    @Test
  public void test_displayMyBoardWithEnemyNextToIt() throws IOException {

        // 1. create two board and two players
    // 2. For each board and player, place ships
    // 3. player 1 hit play2
    // 4. display board, and check if equal to expected
    TextPlayer player = createTextPlayer(5, 3, "a0v\n", new ByteArrayOutputStream());
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
       String  ans = 
    "--------------------------------------------------------------------------------\n"+
    "Player A's turn:\n"+
    "     Your ocean                 Player B's ocean\n"+
    "  0|1|2|3|4                    0|1|2|3|4\n"+
    "A d| | | |  A                A  | | | |  A\n"+
    "B d| | | |  B                B s| | | |  B\n"+
    "C d| | | |  C                C X|d| | |  C\n"+
    "  0|1|2|3|4                    0|1|2|3|4\n"+
    "--------------------------------------------------------------------------------\n";
    String display = player.displayMyBoardWithEnemyNextToIt(get_Enemy_view(),"Your ocean","Player B's ocean");
    assertEquals(display,ans);
    

  }
  /**
     It is a helper function to get enemy view for testing test_displayMyBoardWithEnemyNextToIt()
   */
  public BoardTextView  get_Enemy_view(){ 
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeDestroyer(new Placement("A1V"));//3
    Ship<Character> ship2 = factory.makeSubmarine(new Placement("A0V"));//2
    // BasicShip bs = new BasicShip(c);
    Board<Character> b1 = new BattleShipBoard<Character>(5, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    b1.tryAddShip(ship1);
    b1.tryAddShip(ship2);
    b1.fireAt(new Coordinate(2, 1));
    b1.fireAt(new Coordinate(2, 0));
    b1.fireAt(new Coordinate(1, 0));
    return view;
  }

}
