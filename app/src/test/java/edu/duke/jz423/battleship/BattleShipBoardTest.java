
package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, 0));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, 20));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, -20));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, -20));

  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    if (b.getHeight() != expected.length || b.getWidth() != expected[0].length || b.getHeight() == 0
        || b.getWidth() == 0) {
      throw new IllegalArgumentException("Invalid size of board");
    }

    for (int i = 0; i < b.getHeight(); i++) {
      for (int j = 0; j < b.getWidth(); j++) {
        Coordinate where = new Coordinate(i, j);
        if (b.whatIsAt(where) != expected[i][j]) {
          throw new IllegalArgumentException("The corrdinate on the board is not correct!");
        }
      }
    }
  }

  @Test
  public void test_tryAddShip() {
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5);
    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p, 1, 3, 'c', null);
    assertTrue(board.tryAddShip(ship));

    Ship<Character> ship2 = shipFactory.createShip(new Placement(new Coordinate(2, 1),
        'V'), 2, 2, 'c', null);
    assertFalse(board.tryAddShip(ship2));
    Ship<Character> ship3 = shipFactory.createShip(new Placement(new Coordinate(2, 2),
        'V'), 2, 2, 'c', null);
    assertTrue(board.tryAddShip(ship3));
  }

  @Test
  public void test_whatIsAt() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 2);

    Character[][] e1 = new Character[6][5];
    Character[][] e2 = new Character[2][1];
    Character[][] e3 = new Character[1][2];
    Character[][] e4 = new Character[][] { { 's', 's' }, { 's', 's' } };
    Character[][] e5 = new Character[2][2];
    assertThrows(IllegalArgumentException.class, () -> checkWhatIsAtBoard(b1, e1));
    assertThrows(IllegalArgumentException.class, () -> checkWhatIsAtBoard(b1, e2));
    assertThrows(IllegalArgumentException.class, () -> checkWhatIsAtBoard(b1, e3));
    checkWhatIsAtBoard(b1, e5);

    Coordinate c = new Coordinate(1, 1);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');

    // BasicShip s = new BasicShip(c);
    b1.tryAddShip(s);
    assertThrows(IllegalArgumentException.class, () -> checkWhatIsAtBoard(b1, e4));
    Character[][] e6 = new Character[2][2];
    assertThrows(IllegalArgumentException.class, () -> checkWhatIsAtBoard(b1, e6));

  }

}
