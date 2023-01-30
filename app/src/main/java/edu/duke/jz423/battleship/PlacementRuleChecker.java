package edu.duke.jz423.battleship;

/**
 * This class is the rule checker that checks many ruls, and it is the parent
 * class of many rule checker classes.
 */
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  // more stuff
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * This function is used recursion to check if the ship follows all of the rules
   * 
   * @param theShip:  it is the ship that may follow or against the rule on the
   *                  board
   * @param theBoard: it is the board
   * @return what it returns is true or false that the ship follows the placement
   *         rule
   */
  public String  checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    String error = checkMyRule(theShip, theBoard);
    // if we fail our own rule: stop the placement is not legal
    if (error != null) {
      return error;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }

}
