package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {

  /**
   * This is a helper to test the empty board, if it is correct.
   * 
   * @param w:              width or board
   * @param h:              height of board
   * @param expectedHeader: the header of board
   * @param body:           the body of board, without header
   */
  private void emptyBoardHelper(int w, int h, String expectedHeader, String body) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');

    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader +
        "A  |  A\n" +
        "B  |  B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader +
        "A  | |  A\n" +
        "B  | |  B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String body = "A  | |  A\n" +
        "B  | |  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, body);
  }

  @Test
  public void test_display_empty_3by5_withShips() {
    Coordinate c = new Coordinate(1, 1);
    RectangleShip<Character> bs = new RectangleShip<Character>(c, 's', '*');

    // BasicShip bs = new BasicShip(c);
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    b1.tryAddShip(bs);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    String body = "A  | |  A\n" +
        "B  |s|  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n";
    String expected = expectedHeader + body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  
  @Test
  public void test_display_enemy_3by5_withShips(){ 
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeBattleship(new Placement("A1V"));
    Ship<Character> ship2 = factory.makeSubmarine(new Placement("A0V"));
    // BasicShip bs = new BasicShip(c);
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(b1);
    b1.tryAddShip(ship1);
    b1.tryAddShip(ship2);
    b1.fireAt(new Coordinate(2, 1));
    b1.fireAt(new Coordinate(3, 0));
    b1.fireAt(new Coordinate(1, 0));
    String expectedHeader = "  0|1|2\n";
    String body = "A  | |  A\n" +
        "B s| |  B\n" +
        "C  |b|  C\n" +
        "D X| |  D\n" +
        "E  | |  E\n";
    String expected = expectedHeader + body + expectedHeader;
    assertEquals(expected, view.displayEnemyBoard());
  }
}
