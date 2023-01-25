package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("no name on constructor", c, 1, 3, null);
    HashSet<Coordinate> set = rs.makeCoords(c, 1, 3);
    assertTrue(set.size() == 3);
    assertTrue(set.contains(new Coordinate(1, 2)));
    assertTrue(set.contains(new Coordinate(2, 2)));
    assertTrue(set.contains(new Coordinate(3, 2)));

    Coordinate c2 = new Coordinate(1, 2);
    RectangleShip<Character> rs2 = new RectangleShip<Character>("no name on constructor", c2, 2, 2, null);
    HashSet<Coordinate> set2 = rs2.makeCoords(c2, 2, 2);
    assertTrue(set2.size() == 4);
    assertTrue(set2.contains(new Coordinate(1, 2)));
    assertTrue(set2.contains(new Coordinate(1, 3)));
    assertTrue(set2.contains(new Coordinate(2, 2)));
    assertTrue(set2.contains(new Coordinate(2, 3)));

    Coordinate c3 = new Coordinate(1, 2);
    RectangleShip<Character> rs3 = new RectangleShip<Character>("no name on constructor", c3, 2, 2,
        new SimpleShipDisplayInfo<Character>('s', '*'));
    assertTrue(rs3.myPieces.size() == 4);
  }

  @Test
  public void test_correct_coordinate() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("no name on constructor", c, 1, 3, null);
    Coordinate c2 = new Coordinate(10, 10);
    rs.checkCoordinateInThisShip(c);
    assertThrows(IllegalArgumentException.class, () -> rs.checkCoordinateInThisShip(c2));
  }

  @Test
  public void test_recordHitAt_and_wasHitAt() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("no name on constructor", c, 1, 3, null);
    Coordinate hit1 = new Coordinate(1, 2);
    Coordinate miss1 = new Coordinate(2, 2);
    rs.recordHitAt(c);
    assertTrue(rs.wasHitAt(hit1));
    assertTrue(!rs.wasHitAt(miss1));
  }

  @Test
  public void test_isSunk() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("no name on constructor", c, 1, 3, null);
    for (Coordinate coordinate : rs.myPieces.keySet()) {
      rs.myPieces.put(coordinate, true);
    }
    assertTrue(rs.isSunk());
    rs.myPieces.put(c, false);
    assertTrue(!rs.isSunk());

  }

  @Test
  public void test_getDisplayInfoAt() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>("no name on constructor", c, 1, 3, 'M', 'H');
    for (Coordinate coordinate : rs.myPieces.keySet()) {
      rs.myPieces.put(coordinate, true);
    }
    assertEquals(rs.getDisplayInfoAt(c), 'H');
    rs.myPieces.put(c, false);
    assertEquals(rs.getDisplayInfoAt(c), 'M');

  }

  @Test
  void test_checkGetName() {
    Coordinate c1 = new Coordinate(1, 2);
    RectangleShip<Character> rs = new RectangleShip<Character>(c1, 'a', 'b');
    assertEquals("testship", rs.getName());

    RectangleShip<Character> rs2 = new RectangleShip<Character>("sam", c1, 0, 0, null, null);
    assertEquals("sam", rs2.getName());

    RectangleShip<Character> rs3 = new RectangleShip<>("Tom", c1, 0, 0, null);
    assertEquals("Tom", rs3.getName());
  }

}
