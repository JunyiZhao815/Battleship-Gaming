
package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, 0, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, 0, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, 20, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(0, -20, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class,
        () -> new BattleShipBoard<Character>(-8, -20, 'X'));

  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    if (b.getHeight() != expected.length || b.getWidth() != expected[0].length || b.getHeight() == 0
        || b.getWidth() == 0) {
      throw new IllegalArgumentException("Invalid size of board");
    }

    for (int i = 0; i < b.getHeight(); i++) {
      for (int j = 0; j < b.getWidth(); j++) {
        Coordinate where = new Coordinate(i, j);
        if (b.whatIsAtForSelf(where) != expected[i][j]) {
          throw new IllegalArgumentException("The corrdinate on the board is not correct!");
        }
      }
    }
  }

  @Test
  public void test_tryAddShip() {
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p, 1, 3, 'c', null);
    assertEquals(null, board.tryAddShip(ship));

    Ship<Character> ship2 = shipFactory.createShip(new Placement(new Coordinate(2, 1),
        'V'), 2, 2, 'c', null);
    assertEquals("That placement is invalid: the ship overlaps another ship.\n", board.tryAddShip(ship2));
    Ship<Character> ship3 = shipFactory.createShip(new Placement(new Coordinate(2, 2),
        'V'), 2, 2, 'c', null);
    assertEquals(null, board.tryAddShip(ship3));

  }

  @Test
  public void test_whatIsAt() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');

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

  @Test
  public void test_FireAt() {
    Board<Character> board = new BattleShipBoard<>(3, 3, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> ship1 = shipFactory.makeSubmarine(new Placement("A0H"));
    board.tryAddShip(ship1);
    assertEquals(null, board.fireAt(new Coordinate(2, 2)));
    assertSame(ship1, board.fireAt(new Coordinate(0, 0)));
    assertSame(ship1, board.fireAt(new Coordinate(0, 1)));
    assertTrue(ship1.isSunk());
    assertEquals(null, board.fireAt(new Coordinate(0, 2)));
  }



  
  @Test
  public void test_whatIsAtForEnemy() {
    Board<Character> board = new BattleShipBoard<>(3, 3, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> ship1 = shipFactory.makeSubmarine(new Placement("A0H"));
    assertEquals(null, board.tryAddShip(ship1));
    board.fireAt(new Coordinate(0, 1));
    board.fireAt(new Coordinate(2, 2));
    board.fireAt(new Coordinate(0, 0));
    assertEquals('s', board.whatIsAtForEnemy(new Coordinate(0, 0)));
    assertEquals('X', board.whatIsAtForEnemy(new Coordinate(2, 2)));
    assertEquals('s', board.whatIsAtForEnemy(new Coordinate(0, 1)));
    board.fireAt(new Coordinate(0, 0));
    assertEquals('s', board.whatIsAtForEnemy(new Coordinate(0, 0)));

  }

  @Test
  public void test_moveShip() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> ship1 = shipFactory.makeSubmarine(new Placement("A0H"));
    board.tryAddShip(ship1);
    assertTrue(board.whatIsAtForSelf(new Coordinate("A0")) == 's');
    board.removeShip(ship1);
    assertTrue(board.whatIsAtForSelf(new Coordinate("A0")) == null);
  }

  @Test
  public void test_getShip() {
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> ship1 = shipFactory.makeSubmarine(new Placement("A0H"));
    Ship<Character> ship2 = shipFactory.makeDestroyer(new Placement("E0H"));
    board.tryAddShip(ship1);
    board.tryAddShip(ship2);
    assertTrue(board.getShip(new Coordinate("E0")) == ship2);
    assertTrue(board.getShip(new Coordinate("F0")) == null);
  }

}
