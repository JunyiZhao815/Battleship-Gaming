package edu.duke.jz423.battleship;

import java.util.HashMap;

/**
 * This BasicShip class is the parent class of RectangleShip, implements Ship,
 * it has myPieces and myDisplayInfo
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;

  /**
   * This constructor requires one iterable e.g, map, set, and also a
   * shipDisplayInfo, fill the fields of BasicShip
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!(myPieces.containsKey(c) && myPieces.get(c) != null)) {
      throw new IllegalArgumentException("The coordinate input for the ship, row: " + c.getRow() + " ,col: "
          + c.getColumn() + " does not exists on the Ship!");
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    if (myPieces.get(where) != null) {
      return true;
    } else {
      return false;
    }

  }

  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {

      checkCoordinateInThisShip(c);
      if (!myPieces.get(c)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {

    if (myShip) {
      if (this.wasHitAt(where)) {
        return myDisplayInfo.getInfo(where, true);
      } else {
        return myDisplayInfo.getInfo(where, false);
      }
    } else {
      checkCoordinateInThisShip(where);
      if (this.wasHitAt(where)) {
        return enemyDisplayInfo.getInfo(where, true);
      } else {
        return enemyDisplayInfo.getInfo(where, false);
      }
    }

  }

  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

}
