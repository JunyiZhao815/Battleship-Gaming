package edu.duke.jz423.battleship;

/**
 * It is the class that check the bounds rules
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      int ShipRow = c.getRow();
      int shipCol = c.getColumn();
      if (ShipRow < 0) {
        return "That placement is invalid: the ship goes off the top of the board.\n";
      } else if (ShipRow >= theBoard.getHeight()) {
        return "That placement is invalid: the ship goes off the bottom of the board.\n";
      } else if (shipCol < 0) {
        return "That placement is invalid: the ship goes off the left of the board.\n";
      } else if (shipCol >= theBoard.getWidth()) {
        return "That placement is invalid: the ship goes off the right of the board.\n";
      }
    }
    return null;
  }
}
