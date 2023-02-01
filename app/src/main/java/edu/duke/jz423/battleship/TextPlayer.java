package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
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
    if (prompt == null) {
      throw new IllegalArgumentException("You are sending a null in the readPlacement process in TextPlayer");
    }
    this.out.println("--------------------------------------------------------------------------------");
    this.out.println(prompt);
    this.out.println("--------------------------------------------------------------------------------");
    String s = this.inputReader.readLine();
    if (s == null) {
      throw new EOFException("Cannot read your placement string, which shows null");
    }
    return new Placement(s);

  }

  /**
   * It is a function that place one ship to a board based on system in, and it
   * will print out
   * the board
   */
  // doOnePlacement("Submarine", (p)->shipFactory.makeSubmarine(p));
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    String line = "--------------------------------------------------------------------------------\n";

    Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    Ship<Character> s = createFn.apply(p);
    String error = theBoard.tryAddShip(s);
    if (error != null) {
      this.out.print(error);
      doOnePlacement(shipName, createFn);
    } else {
      this.out.print(line + "Current ocean:\n" + this.view.displayMyOwnBoard() + line);
      return;
    }
  }

  public void doPlacementPhase() throws IOException {

    this.out.print("Current ocean:\n" + this.view.displayMyOwnBoard());
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

  /**
   * This function is to create a map, mapping what kind of ship that is created
   * to a function that make that ship
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));

  }

  /**
   * This function is to create all of the ships
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }

  /**
   * This function display this player's perspective of two boards, one is his own
   * board, another one is enemy's board that the player has hit
   * 
   * @param: enemyView:   enemy's boardTextView,
   * @param: myHeader:    my header,
   * @param: enemyHeader: enemy's header
   * @return: it returns a String that display two boards on the same level.
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {

    String myOwnBoard = this.view.displayMyOwnBoard();
    String enemysBoard = enemyView.displayEnemyBoard();
    int w = this.theBoard.getWidth();
    String header = "     " + myHeader;

    int len = header.length();
    for (int i = 0; i < 2 * w + 22 - len; i++) {
      header += " ";
    }

    header += enemyHeader + "\n";
    String bodyBlank = "";
    for (int i = 0; i < 16; i++) {
      bodyBlank += " ";
    }

    String body = "";
    String[] myBoardArray = myOwnBoard.split("\n");
    String[] enemyBoardArray = enemysBoard.split("\n");
    int myLen = myBoardArray.length;
    for (int i = 0; i < myLen; i++) {
      if (i == 0 || i == myLen - 1) {
        String currRow = myBoardArray[i] + bodyBlank + "  " + enemyBoardArray[i] + "\n";
        body += currRow;
        continue;
      }
      String currRow = myBoardArray[i] + bodyBlank + enemyBoardArray[i] + "\n";
      body += currRow;
    }
    String line = "--------------------------------------------------------------------------------\n";
    return line + "Player " + this.name + "'s turn:\n" + header + body + line;
  }

  /**
   * This function is to check if all the ships are sunk
   * 
   * @return: if all ships are sunk: return true; else return false;
   */
  public boolean isLose() {
    return this.theBoard.isAllSunk();
  }

  /**
   * It is a function that reads coordinate from the input String on system.
   * 
   * @param prompt: Words that asks user to input string.
   * @return return a coordinate
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    if (prompt == null) {
      throw new IllegalArgumentException("You are sending a null in the readCoordinate process in TextPlayer");
    }
    this.out.println("--------------------------------------------------------------------------------");
    this.out.println(prompt);
    this.out.println("--------------------------------------------------------------------------------");
    String s = this.inputReader.readLine();
    if (s == null) {
      throw new EOFException("Cannot read your placement string, which shows null");
    }
    return new Coordinate(s);

  }

  /**
   * This function taks enemyBoard, enemyView, and the name of enemy, do a turn
   * for self.
   * 
   * @param: enemyBoard: enemy's board
   * @param: enemyView:  enemy's view
   * @param: enemy's     name
   */

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String name) throws IOException {
    this.out.print(this.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player " + name + "'s ocean"));
    Coordinate c = readCoordinate(
        "Where do you want to fire at? please enter a String e.g. 'A0', A is row, 0 is column\n");
    Ship<Character> fireTheTarget = enemyBoard.fireAt(c);
    if (fireTheTarget == null) {
      this.out.println("You missed");
    } else {
      this.out.print("You hit a " + fireTheTarget.getName() + " !\n");
    }

  }

}
