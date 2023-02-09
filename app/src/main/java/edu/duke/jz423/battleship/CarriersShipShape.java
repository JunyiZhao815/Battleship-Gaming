package edu.duke.jz423.battleship;

import java.util.HashSet;

/**
 * This class extends BasicShip class, which means it also has the fields and
 * functions that BasicShip has:
 * protected HashMap<Coordinate, Boolean> myPieces;
 * protected ShipDisplayInfo<T> myDisplayInfo;
 */
public class CarriersShipShape<T> extends BasicShip<T> {

  final String name;

  /**
   * There are three kinds of constructors, which represent three different kinds
   * of initialization.
   */
  public CarriersShipShape(String name, Coordinate upperLeft, ShipDisplayInfo<T> sdi,
      ShipDisplayInfo<T> enemyDisplayInfo, Character orientation) {
    super(makeCoords(upperLeft, orientation), sdi, enemyDisplayInfo);
    this.name = name;
  }

  public CarriersShipShape(String name, Coordinate upperLeft, T data, T onHit, Character orientation) {
    this(name, upperLeft, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data), orientation);
  }

  public CarriersShipShape(Coordinate upperLeft, T data, T onHit, Character orientation) {
    this("testship", upperLeft, data, onHit, orientation);
  }

  /**
   * This funtion generates the set of coordinates for a rectangle starting
   * at upperLeft whose width and height are as specified
   * is to generate
   * e.g.,
   * upperLeft = (row=1,col=2)
   * width = 1
   * height = 3
   * Because it is static, only the functions inside of this class can access to
   * it.
   *
   * You would return the set {(row=1,col=2), (row=2,col=2), (row=3,col=2)}
   * 
   * 
   * @param upperLeft: the coordinate of upperLeft
   * @param width:     the width of the ship
   * @param height:    the height of ship
   * @return a HashSet that contains all coordinates that the ship possesses.
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, Character orientation) {
    if (orientation == 'U' || orientation == 'u') {
      return makeCoordsUp(upperLeft);
    } else if (orientation == 'R' || orientation == 'r') {
      return makeCoordsRight(upperLeft);
    } else if (orientation == 'D' || orientation == 'd') {
      return makeCoordsDown(upperLeft);
    } else {
      return makeCoordsLeft(upperLeft);
    }
  }

  /**
     The following are the functions that generates the Up, Down, Left, Right direction of coordiates
     @param: the input is the upper left coordinate
     @return: the function returns the set of coordinates that a ship occupies
   */
  static HashSet<Coordinate> makeCoordsUp(Coordinate upperLeft) {
    HashSet<Coordinate> set = new HashSet<>();
    int baseRow = upperLeft.getRow();
    int baseCol = upperLeft.getColumn();
    set.add(new Coordinate(baseRow, baseCol));
    set.add(new Coordinate(baseRow + 1, baseCol));
    set.add(new Coordinate(baseRow + 2, baseCol));
    set.add(new Coordinate(baseRow + 3, baseCol));
    set.add(new Coordinate(baseRow + 2, baseCol + 1));
    set.add(new Coordinate(baseRow + 3, baseCol + 1));
    set.add(new Coordinate(baseRow + 4, baseCol + 1));
    return set;
  }

  static HashSet<Coordinate> makeCoordsRight(Coordinate upperLeft) {
    HashSet<Coordinate> set = new HashSet<>();
    int baseRow = upperLeft.getRow();
    int baseCol = upperLeft.getColumn();
    set.add(new Coordinate(baseRow, baseCol + 1));
    set.add(new Coordinate(baseRow, baseCol + 2));
    set.add(new Coordinate(baseRow, baseCol + 3));
    set.add(new Coordinate(baseRow, baseCol + 4));
    set.add(new Coordinate(baseRow + 1, baseCol));
    set.add(new Coordinate(baseRow + 1, baseCol + 1));
    set.add(new Coordinate(baseRow + 1, baseCol + 2));
    return set;
  }

  static HashSet<Coordinate> makeCoordsDown(Coordinate upperLeft) {
    HashSet<Coordinate> set = new HashSet<>();
    int baseRow = upperLeft.getRow();
    int baseCol = upperLeft.getColumn();
    set.add(new Coordinate(baseRow, baseCol));
    set.add(new Coordinate(baseRow + 1, baseCol));
    set.add(new Coordinate(baseRow + 2, baseCol));
    set.add(new Coordinate(baseRow + 1, baseCol + 1));
    set.add(new Coordinate(baseRow + 2, baseCol + 1));
    set.add(new Coordinate(baseRow + 3, baseCol + 1));
    set.add(new Coordinate(baseRow + 4, baseCol + 1));
    return set;
  }

  static HashSet<Coordinate> makeCoordsLeft(Coordinate upperLeft) {
    HashSet<Coordinate> set = new HashSet<>();
    int baseRow = upperLeft.getRow();
    int baseCol = upperLeft.getColumn();
    set.add(new Coordinate(baseRow, baseCol + 2));
    set.add(new Coordinate(baseRow, baseCol + 3));
    set.add(new Coordinate(baseRow, baseCol + 4));
    set.add(new Coordinate(baseRow + 1, baseCol));
    set.add(new Coordinate(baseRow + 1, baseCol + 1));
    set.add(new Coordinate(baseRow + 1, baseCol + 2));
    set.add(new Coordinate(baseRow + 1, baseCol + 3));

    return set;
  }

  @Override
  public String getName() {
    return name;
  }
}
