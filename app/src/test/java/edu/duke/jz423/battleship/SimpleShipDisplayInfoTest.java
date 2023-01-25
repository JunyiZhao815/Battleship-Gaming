package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    Character c1 = 'd';
    Character c2 = 'h';
    SimpleShipDisplayInfo<Character> ssdi = new SimpleShipDisplayInfo<>(c1, c2);
    assertEquals(ssdi.getInfo(null, false), 'd');
    assertEquals(ssdi.getInfo(null, true), 'h');
  }

}
