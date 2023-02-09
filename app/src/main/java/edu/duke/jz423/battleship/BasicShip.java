package edu.duke.jz423.battleship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

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

  /**
   * It is a protected helper function that check if the coordinate belongs to
   * this ship
   * 
   * @param Coordinate c: the coordinate that is check if belonged to this ship
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!(myPieces.containsKey(c) && myPieces.get(c) != null)) {
      throw new IllegalArgumentException("The coordinate input for the ship, row: " + c.getRow() + " ,col: "
          + c.getColumn() + " does not exists on the Ship!");
    }
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    if (myPieces.get(where) != null) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      checkCoordinateInThisShip(c);
      if (myPieces.get(c) == null || myPieces.get(c) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  /**
   * This function returns true if the coordinate "where" has been hit, otherwise
   * return false.
   * 
   * @param where: the coordinate that needs to be check if it has been hit
   * @return true if the coordinate "where" has been hit, otherwise return false.
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where) == true;
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

  /**
   * This functions serves to get the relative position of fired coordinate on the
   * ship when moving the ship
   * 
   * @param: the character o, which is orientation
   * @return: it returns the ordered arraylist that may/may not contains the
   *          coordinate of this ship.
   */
  @Override
  public ArrayList<Coordinate> getCoordinates_inorder(Character o) {
    Character O = Character.toUpperCase(o);
    if (O == 'U') {
      return getCoordinates_inorder_Up();
    } else if (O == 'R') {
      return getCoordinates_inorder_Right();
    } else if (O == 'D') {
      return getCoordinates_inorder_Down();
    } else if (O == 'L') {
      return getCoordinates_inorder_Left();
    } else {
      return getCoordinates_inorder_H_or_V();
    }
  }

  /**
   * This functions serves to get ordered arraylist of the ship that has H or V
   * orientation
   * 
   * @return: an arraylist that is ordered based on the left to right, up to down.
   */
  @Override
  public ArrayList<Coordinate> getCoordinates_inorder_H_or_V() {
    PriorityQueue<Coordinate> pq = new PriorityQueue<>(new Comparator<Coordinate>() {
      @Override
      public int compare(Coordinate c1, Coordinate c2) {
        if (c1.getColumn() == c2.getColumn()) {
          return c1.getRow() - c2.getRow();
        }
        return c1.getColumn() - c2.getColumn();
      }
    });
    for (Coordinate c : this.getCoordinates()) {
      pq.add(c);
    }
    int size = pq.size();
    ArrayList<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(pq.poll());
    }
    return list;
  }

  /**
   * This functions serves to get ordered arraylist of the ship that has UDLR
   * orientation
   * 
   * @return: an arraylist that is ordered based on the left to right, up to down.
   */
  @Override
  public ArrayList<Coordinate> getCoordinates_inorder_Up() {
    PriorityQueue<Coordinate> pq = new PriorityQueue<>(new Comparator<Coordinate>() {
      @Override
      public int compare(Coordinate c1, Coordinate c2) {
        if (c1.getColumn() == c2.getColumn()) {
          return c1.getRow() - c2.getRow();
        }
        return c1.getColumn() - c2.getColumn();
      }
    });
    for (Coordinate c : this.getCoordinates()) {
      pq.add(c);
    }
    int size = pq.size();
    ArrayList<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(pq.poll());
    }
    return list;
  }

  /**
   * This functions serves to get ordered arraylist of the ship that has UDLR
   * orientation
   * 
   * @return: an arraylist that is ordered based on the up to down, right to left.
   */
  @Override
  public ArrayList<Coordinate> getCoordinates_inorder_Right() {
    PriorityQueue<Coordinate> pq = new PriorityQueue<>(new Comparator<Coordinate>() {
      @Override
      public int compare(Coordinate c1, Coordinate c2) {
        if (c1.getRow() == c2.getRow()) {
          return c2.getColumn() - c1.getColumn();
        }
        return c1.getRow() - c2.getRow();
      }
    });
    for (Coordinate c : this.getCoordinates()) {
      pq.add(c);
    }
    int size = pq.size();
    ArrayList<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(pq.poll());
    }
    return list;
  }

  /**
   * This functions serves to get ordered arraylist of the ship that has UDLR
   * orientation
   * 
   * @return: an arraylist that is ordered based on the right to left, down to
   *          left.
   */

  @Override
  public ArrayList<Coordinate> getCoordinates_inorder_Down() {
    PriorityQueue<Coordinate> pq = new PriorityQueue<>(new Comparator<Coordinate>() {
      @Override
      public int compare(Coordinate c1, Coordinate c2) {
        if (c1.getColumn() == c2.getColumn()) {
          return c2.getRow() - c1.getRow();
        }
        return c2.getColumn() - c1.getColumn();
      }
    });
    for (Coordinate c : this.getCoordinates()) {
      pq.add(c);
    }
    int size = pq.size();
    ArrayList<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(pq.poll());
    }
    return list;
  }

  /**
   * This functions serves to get ordered arraylist of the ship that has UDLR
   * orientation
   * 
   * @return: an arraylist that is ordered based on the down to up, left to right.
   */
  @Override
  public ArrayList<Coordinate> getCoordinates_inorder_Left() {
    PriorityQueue<Coordinate> pq = new PriorityQueue<>(new Comparator<Coordinate>() {
      @Override
      public int compare(Coordinate c1, Coordinate c2) {
        if (c1.getRow() == c2.getRow()) {
          return c1.getColumn() - c2.getColumn();
        }
        return c2.getRow() - c1.getRow();
      }
    });
    for (Coordinate c : this.getCoordinates()) {
      pq.add(c);
    }
    int size = pq.size();
    ArrayList<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(pq.poll());
    }
    return list;
  }

  /**
   * This functions make all of the coordinate of the ship null except the
   * coordinates that has been hit
   */
  @Override
  public void makeNULL() {
    for (Coordinate c : this.getCoordinates()) {
      if (this.myPieces.get(c) != true) {
        myPieces.put(c, null);
      }
    }
  }

}
