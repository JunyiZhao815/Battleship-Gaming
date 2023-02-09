package edu.duke.jz423.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  T myData;
  T onHit;

  public SimpleShipDisplayInfo(T myData, T onHit) {
    this.myData = myData;
    this.onHit = onHit;
  }

  /**
   * It is to get the information about the coordinate, if this coordinate has
   * been hit or not.
   * 
   * @param: where; the coordinate to check
   * @param: hit:   hit or not
   * @return: onHit if the coordinate is hit, otherwise return myData, i.e. the
   *          symbol of the ship
   */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    } else {
      return myData;
    }
  }

}
