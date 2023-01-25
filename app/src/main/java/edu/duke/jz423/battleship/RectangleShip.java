package edu.duke.jz423.battleship;

import java.util.HashSet;
/**
   This class extends BasicShip class, which means it also has the fields and functions that BasicShip has:
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
 */
public class RectangleShip<T> extends BasicShip<T>{

  /**
     There are three kinds of constructors, which represent three different kinds of initialization.
   */
  public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> sdi) {
    super(makeCoords(upperLeft, width, height), sdi);
  }
  

  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }

  /**
   * This funtion generates the set of coordinates for a rectangle starting
   * at upperLeft whose width and height are as specified
   * is to generate
   * e.g.,
   * upperLeft = (row=1,col=2)
   * width = 1
   * height = 3
   * Because it is static, only the functions inside of this class can access to it.
   *
   * You would return the set {(row=1,col=2), (row=2,col=2), (row=3,col=2)}
   * 
   * 
   * @param upperLeft: the coordinate of upperLeft
   * @param width:     the width of the ship
   * @param height:    the height of ship
   * @return a HashSet that contains all coordinates that the ship possesses.
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> set = new HashSet<>();
    int baseRow = upperLeft.getRow();
    int baseCol = upperLeft.getColumn();
    for (int i = baseRow; i < baseRow + height; i++) {
      for (int j = baseCol; j < baseCol + width; j++) {
        Coordinate c = new Coordinate(i, j);
        set.add(c);
      }
    }
    return set;

  }
}
