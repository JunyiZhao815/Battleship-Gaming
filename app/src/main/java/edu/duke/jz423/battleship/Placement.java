package edu.duke.jz423.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  /**
   * This constructor takes a coordinate and char.
   */
  public Placement(Coordinate where, char orientation) {
    this.where = where;
    this.orientation = orientation;
  }

  /**
   * This constructor takes a string, and then place each field.
   */
  public Placement(String str) {
    if (str.length() != 3) {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    String coordinateLetter = str.substring(0, str.length() - 1);
    this.where = new Coordinate(coordinateLetter);

    
    char orientationLetter = Character.toUpperCase(str.charAt(str.length() - 1));
    if ( !(orientationLetter == 'V' || orientationLetter == 'H')) {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    this.orientation = orientationLetter;
  }

  public Coordinate getWhere() {
    return this.where;
  }

  public char getOrientation() {
    return this.orientation;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement c = (Placement) o;
      return c.orientation == orientation && c.where.getRow() == where.getRow()
        && c.where.getColumn() == where.getColumn() && c.where.equals(this.where);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "It is at row " + this.where.getRow() + " and column " + this.where.getColumn() + ", orienting at "
        + this.orientation;
  }
}
