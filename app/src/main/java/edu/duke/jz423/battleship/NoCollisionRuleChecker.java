package edu.duke.jz423.battleship;

/**
 * This class should check the rule that theShip does not collide with anything
 * else
 * on theBoard (that all the squares it needs are empty).
 * 
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * It is the functions that overrides from {@link PlacementRuleChecker}, used
   * for check its own rule: if the ship has collision with other ships
   * 
   * @param:theShip: the ship that is to be checked
   * @param: theBoard: the board that the ship may be located
   * @return: null if there is no error placing, otherwise returns the string that
   *          describes the error
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      if (theBoard.whatIsAtForSelf(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.\n";

      }
    }
    return null;
  }

}
