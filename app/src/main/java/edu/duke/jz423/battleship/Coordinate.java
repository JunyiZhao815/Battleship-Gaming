package edu.duke.jz423.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(int r, int c) {
    this.row = r;
    this.column = c;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

}
