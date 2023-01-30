package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

//import java.security.cert.X509CRL;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;
  }

  /**
   * It is a function that reads Placement from the input String on system.
   * 
   * @param prompt: Words that asks user to input string.
   * @return return a Placement
   */
  public Placement readPlacement(String prompt) throws IOException {
    this.out.println(prompt);
    String s = this.inputReader.readLine();
      return new Placement(s);
    
  }

  /**
   * It is a function that place one ship to a board based on system in, and it
   * will print out
   * the board
   */
  public void doOnePlacement() throws IOException {
    Placement place = readPlacement("Player " + this.name + " where do you want to place a Destroyer?");
    Ship<Character> ship = shipFactory.makeDestroyer(place);

    // BasicShip ship = new BasicShip(place.getWhere());
    theBoard.tryAddShip(ship);
    out.println(view.displayMyOwnBoard());
  }

  public void doPlacementPhase() throws IOException {
    this.out.print(this.view.displayMyOwnBoard());
    String line = "--------------------------------------------------------------------------------\n";
    this.out.print(line +
        "Player " + this.name + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" +
        "2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n" + line);
    this.doOnePlacement();
  }
}
