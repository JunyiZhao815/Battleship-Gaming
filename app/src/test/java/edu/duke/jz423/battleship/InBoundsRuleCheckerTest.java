package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    InBoundsRuleChecker<Character> check = new InBoundsRuleChecker<>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5, 'X');

    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p1 = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p1, 1, 3, 'c', null);
    assertEquals(check.checkMyRule(ship, board), null);
    assertEquals(check.checkPlacement(ship, board), null);

    BattleShipBoard<Character> board2 = new BattleShipBoard<>(5, 5, 'X');
    Coordinate c2 = new Coordinate(1, 1);
    Placement p2 = new Placement(c2, 'V');
    Ship<Character> ship2 = shipFactory.createShip(p2, 6, 3, 'c', null);
    assertEquals("That placement is invalid: the ship goes off the right of the board.\n",
        check.checkMyRule(ship2, board2));
    Ship<Character> ship3 = shipFactory.createShip(p2, 2, 8, 'c', null);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.\n",
        check.checkMyRule(ship3, board2));

    Coordinate c3 = new Coordinate(-1, 1);
    Placement p3 = new Placement(c3, 'V');
    Ship<Character> ship4 = shipFactory.createShip(p3, 2, 2, 'c', null);
    assertEquals("That placement is invalid: the ship goes off the top of the board.\n",
        check.checkMyRule(ship4, board2));

    Coordinate c4 = new Coordinate(1, -1);
    Placement p4 = new Placement(c4, 'V');
    Ship<Character> ship5 = shipFactory.createShip(p4, 2, 2, 'c', null);
    assertEquals("That placement is invalid: the ship goes off the left of the board.\n",
        check.checkMyRule(ship5, board2));
  }

}
