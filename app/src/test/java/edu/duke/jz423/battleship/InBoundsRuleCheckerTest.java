package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    InBoundsRuleChecker<Character> check = new InBoundsRuleChecker<>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5);

    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p1 = new Placement(c, 'V');
    Ship<Character> ship = shipFactory.createShip(p1, 1, 3, 'c', null);
    check.checkMyRule(ship, board);
    check.checkPlacement(ship, board);

    BattleShipBoard<Character> board2 = new BattleShipBoard<>(5, 5);

    Coordinate c2 = new Coordinate(1, 1);
    Placement p2 = new Placement(c2, 'V');
    Ship<Character> ship2 = shipFactory.createShip(p2, 6, 3, 'c', null);
    check.checkMyRule(ship2, board2);
    check.checkPlacement(ship2, board2);

  }

}
