package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BattleShipShapeTest {
  @Test
  public void test_makeCoordsUp() {
    Coordinate c = new Coordinate(0, 0);
    BattleShipShape<Character> rs = new BattleShipShape<Character>("No name on constructor", c, 'c', 'h', 'U');
    HashSet<Coordinate> set = rs.makeCoords(c, 'U');
    assertTrue(set.contains(new Coordinate(0, 1)));
    assertTrue(set.contains(new Coordinate(1, 0)));
    assertTrue(set.contains(new Coordinate(1, 1)));
    assertTrue(set.contains(new Coordinate(1, 2)));
    assertTrue(set.size() == 4);
  }
    @Test
  public void test_makeCoordsRight() {
    Coordinate c = new Coordinate(0, 0);
    BattleShipShape<Character> rs = new BattleShipShape<Character>("No name on constructor", c, 'c', 'h', 'U');
    HashSet<Coordinate> set = rs.makeCoords(c, 'R');
    assertTrue(set.contains(new Coordinate(0, 0)));
    assertTrue(set.contains(new Coordinate(1, 0)));
    assertTrue(set.contains(new Coordinate(1, 1)));
    assertTrue(set.contains(new Coordinate(2, 0)));
    assertTrue(set.size() == 4);
  }
    @Test
  public void test_makeCoordsDown() {
    Coordinate c = new Coordinate(0, 0);
    BattleShipShape<Character> rs = new BattleShipShape<Character>("No name on constructor", c, 'c', 'h', 'U');
    HashSet<Coordinate> set = rs.makeCoords(c, 'D');
    assertTrue(set.contains(new Coordinate(0, 0)));
    assertTrue(set.contains(new Coordinate(0, 1)));
    assertTrue(set.contains(new Coordinate(0, 2)));
    assertTrue(set.contains(new Coordinate(1, 1)));

    assertTrue(set.size() == 4);
  }
    @Test
  public void test_makeCoordsLeft() {
    Coordinate c = new Coordinate(0, 0);
    BattleShipShape<Character> rs = new BattleShipShape<Character>("No name on constructor", c, 'c', 'h', 'U');
    HashSet<Coordinate> set = rs.makeCoords(c, 'l');
    assertTrue(set.contains(new Coordinate(0, 1)));
    assertTrue(set.contains(new Coordinate(1, 1)));
    assertTrue(set.contains(new Coordinate(2, 1)));
    assertTrue(set.contains(new Coordinate(1, 0)));

    assertTrue(set.size() == 4);
  }
     @Test
  public void test_getName() {
    Coordinate c = new Coordinate(0, 0);
    BattleShipShape<Character> rs = new BattleShipShape<Character>(c, 'c', 'h', 'U');
    HashSet<Coordinate> set = rs.makeCoords(c, 'l');
    assertTrue(rs.getName() == "testship");
  }

}
