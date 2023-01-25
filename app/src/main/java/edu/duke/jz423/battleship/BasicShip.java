package edu.duke.jz423.battleship;

import java.util.HashMap;
/**
   This BasicShip class is the parent class of RectangleShip, implements Ship, it has myPieces and myDisplayInfo
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;


  /**
     This constructor requires one iterable e.g, map, set, and also a shipDisplayInfo, fill the fields of BasicShip
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    this.myDisplayInfo = myDisplayInfo;
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    if (myPieces.containsKey(where) && myPieces.get(where) != null) {
      return true;
    } else {
      return false;
    }

  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    // TODO this is not right. We need to
    // look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

}
