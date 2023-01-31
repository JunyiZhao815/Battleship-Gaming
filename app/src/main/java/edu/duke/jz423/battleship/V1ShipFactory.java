package edu.duke.jz423.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

  /**
     This method create a four kinds of ships based on user input
     @return: a Rectangle ship, which belongs to one of the four kinds. 
   */
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    if (where.getOrientation() != 'V' && where.getOrientation() != 'v' && where.getOrientation() != 'h' && where.getOrientation() != 'H' ) {
      throw new IllegalArgumentException("Please enter the correct orientation, your orientation for now is: "
          + where.getOrientation() + ", and it should be a valid direction");
    }
    if(where.getOrientation() == 'h' || where.getOrientation() == 'H'){
      int temp;
      temp = w;
      w = h;
      h = temp;
    }
    //}
    RectangleShip<Character>ship = new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
    return ship;
  }

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

}
