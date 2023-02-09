package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_create_ship() {
    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c = new Coordinate(1, 1);
    Placement p1 = new Placement(c, 'V');
    Placement p2 = new Placement(c, 'H');
    Placement p3 = new Placement(c, 'O');
    Ship<Character> s1 = shipFactory.createShip(p1, 1, 3, 'c', null);
    Ship<Character> s2 = shipFactory.createShip(p2, 1, 3, 'c', null);
    assertTrue(s1.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(s1.occupiesCoordinates(new Coordinate(2, 1)));
    assertTrue(s1.occupiesCoordinates(new Coordinate(3, 1)));
    assertTrue(s2.occupiesCoordinates(new Coordinate(1, 1)));
    assertTrue(s2.occupiesCoordinates(new Coordinate(1, 2)));
    assertTrue(s2.occupiesCoordinates(new Coordinate(1, 3)));

    assertThrows(IllegalArgumentException.class, () -> shipFactory.createShip(p3, 2, 2, 'g', null));
  }

  @Test
  public void test_create_four_ships() {
    V1ShipFactory shipFactory = new V1ShipFactory();
    Coordinate c1 = new Coordinate(1, 1);
    Placement p1 = new Placement(c1, 'V');
    shipFactory.makeSubmarine(p1);
    Coordinate c2 = new Coordinate(2, 2);
    Placement p2 = new Placement(c2, 'V');
    shipFactory.makeDestroyer(p2);
    Coordinate c3 = new Coordinate(3, 3);
    Placement p3 = new Placement(c3, 'U');
    shipFactory.makeBattleship(p3);
    Coordinate c4 = new Coordinate(4, 4);
    Placement p4 = new Placement(c4, 'D');
    shipFactory.makeCarrier(p4);
    Coordinate c5 = new Coordinate(4, 4);
    Placement p5 = new Placement(c5, 'V');
    assertThrows(IllegalArgumentException.class, () -> shipFactory.makeCarrier(p5));
    Coordinate c6 = new Coordinate(4, 4);
    Placement p6 = new Placement(c6, 'V');
    assertThrows(IllegalArgumentException.class, () -> shipFactory.makeBattleship(p6));
  }
}
