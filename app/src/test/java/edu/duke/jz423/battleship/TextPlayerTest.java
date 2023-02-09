package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("A0V\nA1V\na3H\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(6, 6, 'X');
    // App app = new App(b, sr, ps);
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(0, 0), 'V');
    expected[1] = new Placement(new Coordinate(0, 1), 'V');
    expected[2] = new Placement(new Coordinate(0, 3), 'H');
    String line = "--------------------------------------------------------------------------------\n";

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(line + prompt + "\n" + line, bytes.toString()); // should have printed protmpt and newline
      bytes.reset(); // clear out bytes for next time around
    }
    String a = null;
    assertThrows(IllegalArgumentException.class, () -> player.readPlacement(a));

    StringReader sr3 = new StringReader("AAV\nA0V\n"); // A2Q is also tested on this line
    TextPlayer player3 = new TextPlayer("A", b, new BufferedReader(sr3), ps, new V1ShipFactory());
    assertEquals(new Placement("A0V"), player3.readPlacement(prompt));
  }

  /**
   * // @Disabled
   * 
   * @Test
   *       void test_read_coordinate() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
   *       StringReader sr3 = new StringReader("B8\nA1\n"); // A2Q is also tested
   *       on this line
   *       PrintStream ps = new PrintStream(bytes, true);
   *       TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr3), ps,
   *       new V1ShipFactory());
   *       assertEquals(new Coordinate("A1"), player.readCoordinate());
   *       }
   */
  @Test
  void test_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // App app = new App(b, sr, ps);
    TextPlayer player = createTextPlayer(10, 10, "A0V\na3h\nd3r\n", bytes);

    String line = "--------------------------------------------------------------------------------\n";
    for (int i = 0; i < 2; i++) {
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));

      // assertEquals(
      // line + "Player " + player.name + " where do you want to place a Destroyer?\n"
      // + line + line
      // + "Current ocean:\n" + player.view.displayMyOwnBoard() + line,
      // bytes.toString());
      bytes.reset(); // clear out bytes for next time around
    }
    player.doOnePlacement("Battleship", player.shipCreationFns.get("Battleship"));

    Set<Character> set = new HashSet<>();
    set.add('V');
    set.add('H');
    set.add('R');
    // set.add('D');
    HashMap<Ship<Character>, Character> map = player.map_getCoordinate;
    for (Ship<Character> s : map.keySet()) {
      assertTrue(set.contains(map.get(s)));
    }
  }

  @Test
  void test_one_placement2() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // App app = new App(b, sr, ps);
    TextPlayer player = createTextPlayer(10, 10, "A9V\nZ7H\nZ2H\nA0V\nA1V\nD3H\nE0H\nF0H\n", bytes);

    for (int i = 0; i < 6; i++) {
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
    }
    Set<Character> set = new HashSet<>();
    set.add('V');
    set.add('H');
    // set.add('D');
    HashMap<Ship<Character>, Character> map = player.map_getCoordinate;
    for (Ship<Character> s : map.keySet()) {
      assertTrue(set.contains(map.get(s)));
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
    String ans = "--------------------------------------------------------------------------------\n" +
        "Player A's turn:\n" +
        "     Your ocean                 Player B's ocean\n" +
        "  0|1|2|3|4                    0|1|2|3|4\n" +
        "A d| | | |  A                A  | | | |  A\n" +
        "B d| | | |  B                B s| | | |  B\n" +
        "C d| | | |  C                C X|d| | |  C\n" +
        "  0|1|2|3|4                    0|1|2|3|4\n" +
        "--------------------------------------------------------------------------------\n";
    TextPlayer enemy = get_Enemy();
    String display = player.displayMyBoardWithEnemyNextToIt(enemy.view, "Your ocean", "Player B's ocean");
    assertEquals(display, ans);
    enemy.theBoard.fireAt(new Coordinate(0, 0));
    enemy.theBoard.fireAt(new Coordinate(1, 1));
    enemy.theBoard.fireAt(new Coordinate(0, 1));
    assertTrue(enemy.isLose());
    ans = "--------------------------------------------------------------------------------\n" +
        "Player A's turn:\n" +
        "     Your ocean                 Player B's ocean\n" +
        "  0|1|2|3|4                    0|1|2|3|4\n" +
        "A d| | | |  A                A s|d| | |  A\n" +
        "B d| | | |  B                B s|d| | |  B\n" +
        "C d| | | |  C                C X|d| | |  C\n" +
        "  0|1|2|3|4                    0|1|2|3|4\n" +
        "--------------------------------------------------------------------------------\n";
    display = player.displayMyBoardWithEnemyNextToIt(enemy.view, "Your ocean", "Player B's ocean");
    assertEquals(display, ans);
  }

  /**
   * It is a helper function to get enemy view for testing
   * test_displayMyBoardWithEnemyNextToIt()
   */
  public TextPlayer get_Enemy() {
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeDestroyer(new Placement("A1V"));// 3
    Ship<Character> ship2 = factory.makeSubmarine(new Placement("A0V"));// 2
    // BasicShip bs = new BasicShip(c);
    Board<Character> b1 = new BattleShipBoard<Character>(5, 3, 'X');
    TextPlayer player = new TextPlayer("sam", b1, null, null, null);
    b1.tryAddShip(ship1);
    b1.tryAddShip(ship2);
    b1.fireAt(new Coordinate(2, 1));
    b1.fireAt(new Coordinate(2, 0));
    b1.fireAt(new Coordinate(1, 0));
    assertFalse(player.isLose());
    return player;
  }

  // @Disabled
  @Test
  public void test_Sonar() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer enemy = createTextPlayer(10, 20, "", bytes);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeBattleship(new Placement("f5u"));// 3
    Ship<Character> ship2 = factory.makeBattleship(new Placement("j4u"));// 2
    Ship<Character> ship3 = factory.makeBattleship(new Placement("h2r"));// 2
    Ship<Character> ship4 = factory.makeCarrier(new Placement("d5l"));// 2
    enemy.theBoard.tryAddShip(ship1);
    enemy.theBoard.tryAddShip(ship2);
    enemy.theBoard.tryAddShip(ship3);
    enemy.theBoard.tryAddShip(ship4);
    String line = "--------------------------------------------------------------------------------\n";
    String expected = "Where do you want to use the sonar?\n" + "Please enter a correct Coordinate!\n" + line +
        "Submarines occupy 0 squares\n" +
        "Destroyers occupy 0 squares\n" +
        "Battleships occupy 0 squares\n" +
        "Carriers occupy 5 squares\n" +
        line;
    TextPlayer self = createTextPlayer(10, 20, "d99\nd9\n", bytes);
    self.useSonar(enemy.theBoard);
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_choose_steps() throws IOException {
    String line = "--------------------------------------------------------------------------------\n";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "f99\nh7\n", bytes);
    String expected = line + "Possible actions for Player A:\n" + "\n" +
        "F Fire at a square\n" +
        "M Move a ship to another square (3 remaining)\n" +
        "S Sonar scan (3 remaining)\n" +
        "\n" +
        "Player A, what would you like to do?\n" +
        line;
    player.chooseSteps();
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_getCoordinate_inorder() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer enemy = createTextPlayer(10, 20, "E6\nO4U\n", bytes);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeBattleship(new Placement("f5u"));// 3
    Ship<Character> ship2 = factory.makeBattleship(new Placement("j4u"));// 2
    Ship<Character> ship3 = factory.makeBattleship(new Placement("h2r"));// 2
    Ship<Character> ship4 = factory.makeCarrier(new Placement("d5l"));// 2
    enemy.theBoard.tryAddShip(ship1);
    enemy.theBoard.tryAddShip(ship2);
    enemy.theBoard.tryAddShip(ship3);
    enemy.theBoard.tryAddShip(ship4);
    ArrayList<Coordinate> list = ship4.getCoordinates_inorder('l');
    ArrayList<Coordinate> expected = new ArrayList<>();
    expected.add(new Coordinate("e5"));
    expected.add(new Coordinate("e6"));
    expected.add(new Coordinate("e7"));
    expected.add(new Coordinate("e8"));
    expected.add(new Coordinate("d7"));
    expected.add(new Coordinate("d8"));
    expected.add(new Coordinate("d9"));
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), expected.get(i));
    }

    ArrayList<Coordinate> list2 = ship1.getCoordinates_inorder('u');
    ArrayList<Coordinate> expected2 = new ArrayList<>();
    expected2.add(new Coordinate("g5"));
    expected2.add(new Coordinate("f6"));
    expected2.add(new Coordinate("g6"));
    expected2.add(new Coordinate("g7"));
    for (int i = 0; i < list2.size(); i++) {
      assertEquals(list2.get(i), expected2.get(i));
    }

  }

  @Test
  public void test_transforHit() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer enemy = createTextPlayer(10, 20, "A5D\nG2R\n", bytes);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeBattleship(new Placement("A5D"));// 3
    Set<Coordinate> set = new HashSet<>();

    set.add(new Coordinate("A7"));
    set.add(new Coordinate("B6"));
    set.add(new Coordinate("A6"));
    set.add(new Coordinate("A5"));
    for (Coordinate c : ship1.getCoordinates()) {
      set.add(c);
    }
    assertTrue(set.size() == 4);

    ArrayList<Coordinate> list = ship1.getCoordinates_inorder('D');
    assertEquals(new Coordinate("A7"), list.get(0));
    assertEquals(new Coordinate("B6"), list.get(1));
    assertEquals(new Coordinate("A6"), list.get(2));
    assertEquals(new Coordinate("A5"), list.get(3));

  }


  /**
   * It is a helper function that generate a player, inorder to check the random
   * function
   */

  public String generateAPlayer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String place = "a0h\n" +
        "b0h\n" +
        "c0h\n" +
        "d0h\n" +
        "e0h\n" +
        "a5d\n" +
        "c5d\n" +
        "e5d\n" +
        "h3r\n" +
        "o3L\n";
    TextPlayer player = createTextPlayer(10, 20, place, bytes);
    player.doPlacementPhase();
    return player.view.displayMyOwnBoard();
  }
  

  /**
     Here I create another player that is not robot to test with the robot one
   */
  @Test
  public void test_oRobot_Placement_and_Fire() throws IOException {

    StringReader sr = new StringReader("");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    TextPlayer player_robot = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory(), true);
    player_robot.doPlacementPhase();
    assertEquals(player_robot.view.displayMyOwnBoard(), generateAPlayer());
    player_robot.playOneTurn(player_robot.theBoard, null, null);
    assertSame(player_robot.theBoard.whatIsAtForEnemy(new Coordinate("A0")),'s');
  }

}
