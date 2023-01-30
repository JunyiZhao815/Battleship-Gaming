package edu.duke.jz423.battleship;

//import java.security.cert.X509CRL;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;

  public TextPlayer(Board<Character> theBoard, Reader inputSource, PrintStream out, String name) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
    this.shipFactory = new V1ShipFactory();
    this.name = name;
  }

  /**
   * It is a function that reads Placement from the input String on system.
   * 
   * @param prompt: Words that asks user to input string.
   * @return return a Placement
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }
  /**
   * It is a function that place one ship to a board based on system in, and it
   * will print out
   * the board
   */
  public void doOnePlacement() throws IOException {
    Placement place = readPlacement("Where would you like to put your ship?");
    Ship<Character> ship = shipFactory.makeDestroyer(place);

    // BasicShip ship = new BasicShip(place.getWhere());
    theBoard.tryAddShip(ship);
    out.println(view.displayMyOwnBoard());
  }
}
