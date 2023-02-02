package edu.duke.jz423.battleship;

public class Coordinate {
  private final int row;
  private final int column;


  /**
     This constructor receive a string, whose length is 2, the first char is for the row, the second one is for the column
     @param descr: the string
     the row is 0 - 9, column is A - Z, both lowercase and uppercase are acceptable
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Invalid size of string!");
    }

    char rowLetter = Character.toUpperCase(descr.charAt(0));
    char columnLetter = descr.charAt(1);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("Invalid input for row alphabet, must be less then U!");
    }
    if (columnLetter < '0' || columnLetter > '9') {
      throw new IllegalArgumentException("Invalid input for column number, must between 0 and 9!");
    }
    this.column = Character.getNumericValue(columnLetter);
    this.row = rowLetter - 'A';
  }

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
