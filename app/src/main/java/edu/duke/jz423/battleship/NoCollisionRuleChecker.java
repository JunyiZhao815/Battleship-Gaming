package edu.duke.jz423.battleship;
/**
   This class should check the rule that theShip does not collide with anything else
on theBoard (that all the squares it needs are empty).

 */
public class NoCollisionRuleChecker  <T> extends PlacementRuleChecker<T> {
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> set = theShip.getCoordinates();
    for(Coordinate c: set){
      if(theBoard.whatIsAt(c) != null){
        return false;
      }
    }
    return true;
  }
  
}
