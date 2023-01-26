package edu.duke.jz423.battleship;

/**
   It is the class that check the bounds rules
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate c : set) {
      if (c.getRow() >= 0 && c.getRow() < theBoard.getHeight() && c.getColumn() >= 0
          && c.getColumn() < theBoard.getWidth()) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }// 把这个给implement了，然后再进入这个的test，把这个文件的test和PlacementRuleChecker给搞到80

}
