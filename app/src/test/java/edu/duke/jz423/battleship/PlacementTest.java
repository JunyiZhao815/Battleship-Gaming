package edu.duke.jz423.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
    @Test
  public void testConstructer1() {
      assertThrows(IllegalArgumentException.class, ()->new Placement("AAAAA"));
      assertThrows(IllegalArgumentException.class, ()->new Placement("A2O"));
       
  }
    @Test
  public void testConstructor2() {
    Placement a = new Placement("A2H");
    Coordinate a1 = new Coordinate("A2");
    char o = 'H';
    Placement b = new Placement(a1, o);
    assertEquals(a, b);
  }


  
  @Test
  public void testgetWhere() {
    Placement a = new Placement("A0V");
    Coordinate a1 = new Coordinate("A0");
    Coordinate a2 = new Coordinate("A2");
    assertEquals(a.getWhere(), a1);
    assertNotEquals(a.getWhere(), a2);
  }

  @Test
  public void testgetOrientation() {
    Placement a = new Placement("A0V");
    Placement b = new Placement("A0H");
    Placement c = new Placement("B2V");
    assertNotEquals(a.getOrientation(), b.getOrientation());
    assertEquals(a.getOrientation(), c.getOrientation());
    assertEquals(a.getOrientation(), 'V');
    assertEquals(b.getOrientation(), 'H');
    assertNotEquals(a.getOrientation(), 'A');
  }

  @Test
  public void testEqual() {
    Placement a = new Placement("A0V");
    Placement b = new Placement("A0V");
    Placement c = new Placement("A0v");
    Placement d = new Placement("A0H");
    Placement e = new Placement("B0H");
    Coordinate f = new Coordinate("A2");
    assertFalse( a.equals(f));
    assertEquals(a, b);
    assertEquals(b, c);
    assertNotEquals(a, d);
    assertNotEquals(d, e);
  }

  @Test
  public void testHashCode() {
    Placement a = new Placement("A0V");
    Placement b = new Placement("A0V");
    Placement c = new Placement("A0v");
    Placement d = new Placement("A0H");
    Placement e = new Placement("B0H");
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(b.hashCode(), c.hashCode());
    assertNotEquals(a.hashCode(), d.hashCode());
    assertNotEquals(d.hashCode(), e.hashCode());
  }

      @Test
  public void testToString() {
    Placement a = new Placement("A0V");
    Placement b = new Placement("A0V");
    Placement c = new Placement("A0v");
    Placement d = new Placement("A0H");
     Placement e = new Placement("B0H");
    assertEquals(a.toString(), b.toString());
    assertEquals(b.toString(), c.toString());
    assertNotEquals(a.toString(), d.toString());
    assertNotEquals(d.toString(), e.toString());
  }
}
