package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
//import java.security.cert.X509CRL;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    this.shipsToPlace = new ArrayList<String>();
    setupShipCreationMap();
    setupShipCreationList();
  }

  /**
   * It is a function that reads Placement from the input String on system.
   * 
   * @param prompt: Words that asks user to input string.
   * @return return a Placement
   */
  public Placement readPlacement(String prompt) throws IOException {
    if(prompt == null){
      throw new IllegalArgumentException("You are sending a null in the readPlacement process in TextPlayer");
    }
    this.out.println(prompt);
    String s = this.inputReader.readLine();
    return new Placement(s);

  }

  /**
   * It is a function that place one ship to a board based on system in, and it
   * will print out
   * the board
   */
  // doOnePlacement("Submarine", (p)->shipFactory.makeSubmarine(p));
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    theBoard.tryAddShip(s);
    out.print(view.displayMyOwnBoard());
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
    for (String str : this.shipsToPlace) {
      this.doOnePlacement(str, shipCreationFns.get(str));
    }
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));

  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }

}
