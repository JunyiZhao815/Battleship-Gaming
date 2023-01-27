package edu.duke.jz423.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
  private final int height;
  private final int width;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */

  public BattleShipBoard(int w, int h) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.myShips = new ArrayList<Ship<T>>();
    this.width = w;
    this.height = h;
    this.placementChecker = new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<>(null)); // later: new
                                                                                            // NoCollisionRuleChecker<>(new
    // InBoundsRuleChecker<T>(null))
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
   * @return It return true and add the ship to the ship list
   */
  public boolean tryAddShip(Ship<T> toAdd) {
    if (placementChecker.checkPlacement(toAdd, this)) {
      myShips.add(toAdd);
      return true;
    }

    return false;
  }

  /**
   * 
   * @param where is the parameter which is a Coordinate and represent the row and
   *              height
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
