package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_NoCollisionRule() {
    NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5, 'X');

    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p1 = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p1, 1, 3, 'c', null);
    board.tryAddShip(ship);
    Ship<Character> ship2 = shipFactory.createShip(new Placement(new Coordinate(2, 2),
        'H'), 1, 3, 'c', null);
    assertEquals(null, checker.checkMyRule(ship2, board));
    Ship<Character> ship3 = shipFactory.createShip(new Placement(new Coordinate(2, 1),
        'V'), 1, 2, 'c', null);
    assertEquals("That placement is invalid: the ship overlaps another ship.\n", checker.checkMyRule(ship3, board));
  }

  @Test
  public void test_twoRules() {
    // InBoundsRuleChecker inside of NoCollisionRuleChecker
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null));
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p1 = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p1, 1, 3, 'c', null);
    board.tryAddShip(ship);
    Ship<Character> ship2 = shipFactory.createShip(new Placement(new Coordinate(2, 2),
        'H'), 1, 3, 'c', null);
    assertEquals(null, checker.checkPlacement(ship2, board));
    Ship<Character> ship3 = shipFactory.createShip(new Placement(new Coordinate(3, 3),
        'V'), 1, 6, 'c', null);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n",
        checker.checkPlacement(ship3, board));
    // Vice versa
    PlacementRuleChecker<Character> checker2 = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));
    BattleShipBoard<Character> board2 = new BattleShipBoard<>(5, 5, 'X');
    V1ShipFactory shipFactory2 = new V1ShipFactory();
    Coordinate c2 = new Coordinate(1, 1);
    Placement p2 = new Placement(c2, 'V');
    Ship<Character> ship4 = shipFactory2.createShip(p2, 1, 3, 'c', null);
    board2.tryAddShip(ship4);

    Ship<Character> ship5 = shipFactory.createShip(new Placement(new Coordinate(2, 1),
        'V'), 2, 2, 'c', null);
    assertEquals("That placement is invalid: the ship overlaps another ship.\n", checker2.checkPlacement(ship5, board2));
  }
}
