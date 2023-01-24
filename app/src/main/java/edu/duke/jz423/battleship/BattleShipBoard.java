package edu.duke.jz423.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
  private final int height;
  private final int width;
  final ArrayList<Ship<T>> myShips;

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */

  public BattleShipBoard(int w, int h, ArrayList<Ship<T>> myShips) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.myShips = myShips;
    this.width = w;
    this.height = h;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
  /**
   * It add ship to the myShips list, if successful, then return true
   * 
   * 
   * @param toAdd is the ship to add

   */
  public boolean tryAddShip(Ship<T> toAdd) {
    myShips.add(toAdd);
    return true;
  }
  /**
   * 
   * @param where is the parameter which is a Coordinate and represent the row and height
   * @return return the information on the position "where"
   */
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }

}
