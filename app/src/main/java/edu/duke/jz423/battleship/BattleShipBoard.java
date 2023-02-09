package edu.duke.jz423.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int height;
  private final int width;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  HashSet<Coordinate> enemyMisses;
  final T missInfo;
  HashMap<Coordinate, T> enemyGotHit;

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */

  public BattleShipBoard(int w, int h, T missInfo) {
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
    this.enemyMisses = new HashSet<>();
    this.missInfo = missInfo;
    this.enemyGotHit = new HashMap<>();
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  /**
   * It add ship to the myShips list, if successful, then return null
   * 
   * 
   * @param toAdd is the ship to add
   * @return It return null and add the ship to the ship list, else return the
   *         error
   */
  public String tryAddShip(Ship<T> toAdd) {
    String placementProblem = this.placementChecker.checkPlacement(toAdd, this);
    if (placementProblem == null) {
      this.myShips.add(toAdd);
    }
    return placementProblem;
  }

  /**
   * 
   * @param where is the parameter which is a Coordinate and represent the row and
   *              height
   * @return return the information on the position "where"
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * 
   * @param where is the parameter which is a Coordinate and represent the row and
   *              height, isSelf is to tell where this function returns for self
   * @return return the information on the position "where"
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        if (!isSelf) {
          if (s.wasHitAt(where) && !enemyGotHit.containsKey(where)) {
            return null;
          }
        }
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    return null;
  }

  /**
   * This method should search for any ship that occupies coordinate c
   * (you already have a method to check that)
   * If one is found, that Ship is "hit" by the attack and should
   * record it (you already have a method for that!). Then we
   * should return this ship.
   *
   * @param: c: the coordinate to fire at
   * @return: it returns the ship that hit, else record
   *          the miss in the enemyMisses HashSet that we just made,
   *          and return null.
   * 
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> ship : myShips) {
      if (ship.occupiesCoordinates(c)) {
        ship.recordHitAt(c);
        enemyGotHit.put(c, ship.getDisplayInfoAt(c, false));
        if (enemyMisses.contains(c)) {
          enemyMisses.remove(c);
        }
        return ship;
      }
    }
    enemyMisses.add(c);
    enemyGotHit.remove(c);
    return null;
  }

  /**
   * 
   * @param where is the parameter which is a Coordinate and represent the row and
   *              height
   * @return return the information on the position "where" for the enemy
   */
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    if (enemyMisses.contains(where)) {
      return missInfo;
    }
    if (enemyGotHit.containsKey(where)) {
      return enemyGotHit.get(where);
    }

    return whatIsAt(where, false);
  }

  /**
   * This function checks if all ships in myShips are all sunk
   * 
   * @return: it returns true if all ships are sunk, else return false;
   */
  @Override
  public boolean isAllSunk() {
    for (Ship<T> ship : myShips) {
      if (!ship.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * getShip function serves useMove() in TextPlayer class, we use the function
   * here because we cannot deliver the field: myShips to other class.
   * 
   * @param: Coordinate c, the coordinate where the ships has
   * @return the required ship
   */
  @Override
  public Ship<T> getShip(Coordinate c) {
    for (Ship<T> s : this.myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }

  /**
   * This functions removes the ship from myShips.
   * 
   * @param: the ship that I am going to remove
   */
  @Override
  public void removeShip(Ship<T> s) {
    myShips.remove(s);
  }
}
