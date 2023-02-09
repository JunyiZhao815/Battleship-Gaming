package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

public class TextPlayer {
  protected boolean isRobot;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  protected int moveTokens = 3;
  protected int sonarTokens = 3;
  public HashMap<Ship<Character>, Character> map_getCoordinate;
  final ArrayList<Ship<Character>> myShips_enemyView = new ArrayList<>();
  int lastFireWidth = 0;
  int lastFireHeight = 0;

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
    map_getCoordinate = new HashMap<>();
    this.isRobot = false;
  }

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory, boolean isRobot) {
    this(name, theBoard, inputSource, out, shipFactory);
    this.isRobot = isRobot;
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
    Placement p = null;
    while (true) {
      try {
        String s = this.inputReader.readLine();
        p = new Placement(s);
      } catch (IllegalArgumentException e) {
        this.out.println("Please enter a correct placement!");
        continue;
      }
      break;
    }
    return p;

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
    /**
     * try {
     * s = createFn.apply(p);
     * } catch (Exception e) {
     * out.println(e);
     * doOnePlacement(shipName, createFn);
     * return;
     * }
     */

    String error = theBoard.tryAddShip(s);
    if (error != null) {
      this.out.print(error);
      doOnePlacement(shipName, createFn);
    } else {
      map_getCoordinate.put(s, Character.toUpperCase(p.getOrientation()));
      myShips_enemyView.add(s);
      this.out.print(line + "Current ocean:\n" + this.view.displayMyOwnBoard() + line);
      return;
    }
  }

  /**
   * This function do placement phase for this player. i.e, place all the ship on
   * its own board
   */
  public void doPlacementPhase() throws IOException {
    if (!isRobot) {
      this.out.print("Current ocean:\n" + this.view.displayMyOwnBoard());
      String line = "--------------------------------------------------------------------------------\n";
      this.out.print(line +
          "Player " + this.name + ": you are going to place the following ships.\n" +
          "For each ship, type the coordinate of the upper left\n" +
          "side of the ship, followed by either H (for horizontal) or V (for\n" +
          "vertical), or U (up), R (right)), D (Down), L (Left).  For example M4H would place a ship horizontally starting\n"
          +
          "at M4 and going to the right.  You have\n\n" +
          "2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n" +
          "3 \"Battleships\" that are now shaped as shown below\n" +
          "       b      OR    b         bbb         b\n" +
          "      bbb           bb   OR    b     OR  bb\n" +
          "                    b                     b\n" +
          "\n" +
          "       Up          Right      Down      Left\n" +
          " 2 \"Carriers\" that are now shaped as shown below\n" +
          "\n" +
          "          c                       c             \n" +
          "          c           cccc        cc         ccc\n" +
          "          cc   OR    ccc      OR  cc   OR  cccc     \n" +
          "          cc                       c         \n" +
          "           c                       c\n" +
          "\n" +
          "         Up           Right     Down          Left\n" + line);
      for (String str : this.shipsToPlace) {
        this.doOnePlacement(str, shipCreationFns.get(str));
      }
    } else {
      this.RobotAddToPlacement();
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
   * This function allows the user to choose which tokens they want to take.
   * 
   * @return: it returns 0 if it directly goes to fire, else choose steps in the
   *          playOneTurn function
   */
  public boolean chooseSteps() {
    if (moveTokens == 0 && sonarTokens == 0) {
      return false;
    }
    String line = "--------------------------------------------------------------------------------";
    out.println(line);
    out.println("Possible actions for Player " + this.name + ":");
    out.println();
    out.println("F Fire at a square");
    if (moveTokens != 0) {
      out.println("M Move a ship to another square (" + this.moveTokens + " remaining)");
    }
    if (sonarTokens != 0) {
      out.println("S Sonar scan (" + this.sonarTokens + " remaining)");
    }
    out.println();
    out.println("Player " + this.name + ", what would you like to do?");
    out.println(line);
    return true;
  }

  /**
   * This function generate placement, and add ship on board
   * the board
   * 
   */
  public void RobotAddToPlacement() throws IOException {
    Placement p1 = new Placement("a0h");
    Placement p2 = new Placement("b0h");
    Placement p3 = new Placement("c0h");
    Placement p4 = new Placement("d0h");
    Placement p5 = new Placement("e0h");
    Placement p6 = new Placement("a5d");
    Placement p7 = new Placement("c5d");
    Placement p8 = new Placement("e5d");
    Placement p9 = new Placement("h3r");
    Placement p10 = new Placement("o3L");
    Ship<Character> ship1 = shipFactory.makeSubmarine(p1);
    Ship<Character> ship2 = shipFactory.makeSubmarine(p2);
    Ship<Character> ship3 = shipFactory.makeDestroyer(p3);
    Ship<Character> ship4 = shipFactory.makeDestroyer(p4);
    Ship<Character> ship5 = shipFactory.makeDestroyer(p5);
    Ship<Character> ship6 = shipFactory.makeBattleship(p6);
    Ship<Character> ship7 = shipFactory.makeBattleship(p7);
    Ship<Character> ship8 = shipFactory.makeBattleship(p8);
    Ship<Character> ship9 = shipFactory.makeCarrier(p9);
    Ship<Character> ship10 = shipFactory.makeCarrier(p10);
    theBoard.tryAddShip(ship1);
    theBoard.tryAddShip(ship2);
    theBoard.tryAddShip(ship3);
    theBoard.tryAddShip(ship4);
    theBoard.tryAddShip(ship5);
    theBoard.tryAddShip(ship6);
    theBoard.tryAddShip(ship7);
    theBoard.tryAddShip(ship8);
    theBoard.tryAddShip(ship9);
    theBoard.tryAddShip(ship10);

  }

  /**
   * This function is used when the player is robot, so it just fires at a random
   * place on its enemy's board.
   * It will tell the user if it hits a coordinate or not
   * 
   * @param: enemyBoard: its enemy's board
   * 
   */
  public void RobotFireCoordinate(Board<Character> enemyBoard) {
    String c = (char) (lastFireHeight + 65) + Integer.toString(lastFireWidth);
    Ship<Character> fireTheTarget = enemyBoard.fireAt(new Coordinate(c));
    if (fireTheTarget == null) {
      this.out.println("Player " + this.name + " missed");
    } else {
      this.out.print("Player " + this.name + " hits a " + fireTheTarget.getName() + " at " + c + " !\n");
    }
    if (lastFireWidth == theBoard.getWidth() - 1) {
      lastFireWidth = 0;
      lastFireHeight++;
      return;
    }
    lastFireWidth++;

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
    boolean noFire = false;
    if (!isRobot) {
      noFire = this.chooseSteps();
    }
    while (noFire) {
      String s = this.inputReader.readLine();
      if (s.equals("F")) {
        break;
      } else if (s.equals("M")) {
        if (moveTokens <= 0) {
          out.println("There is no more move action tokens, please choose again!");
          continue;
        }
        this.useMove(enemyBoard, enemyView, name);
        return;
      } else if (s.equals("S")) {
        if (sonarTokens <= 0) {
          out.println("There is no more sonar action tokens, please choose again!");
          continue;
        }
        this.useSonar(enemyBoard);
        return;
      } else {
        this.out
            .println("Please enter a correct option: F, M, S! If one of them has 0 tokens, please do not choose that");
      }
    }
    if (!isRobot) {
      this.out.println("--------------------------------------------------------------------------------");
      this.out.println("Where do you want to fire at? please enter a String e.g. 'A0', A is row, 0 is column");
      this.out.println("--------------------------------------------------------------------------------");
      this.out.print(this.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player " + name + "'s ocean"));
      this.out.println("Enter the fire location here: ");

      Coordinate c;
      while (true) {
        try {
          String str = this.inputReader.readLine();
          c = new Coordinate(str);
        } catch (IllegalArgumentException e) {
          this.out.println("Please enter a correct Coordinate!");

          continue;
        }
        break;
      }
      Ship<Character> fireTheTarget = enemyBoard.fireAt(c);
      if (fireTheTarget == null) {
        this.out.println("You missed");
      } else {
        this.out.print("You hit a " + fireTheTarget.getName() + " !\n");
      }
    } else {
      RobotFireCoordinate(enemyBoard);
    }
  }

  /**
   * This function perform the move action.
   * 
   * @param: enemyBoard: enemy's board
   * @param: enemyView:  enemy's textBoardView
   * @param: name:       enemy's name
   * 
   */
  public void useMove(Board<Character> enemyBoard, BoardTextView enemyView, String name) throws IOException {
    out.println(this.view.displayMyOwnBoard());
    String line = "--------------------------------------------------------------------------------\n";
    out.print(line);
    // first, ask the user to choose the correct coordinate
    out.println("Which ship are you going to remove? Please enter the coordinate of that ship!");
    Coordinate c = null;
    while (true) {
      try {
        String str = this.inputReader.readLine();
        c = new Coordinate(str);
        if (this.theBoard.whatIsAtForSelf(c) == null) {
          throw new IllegalArgumentException("There is no ship at the coordinate, please choose again");
        }
        break;
      } catch (IllegalArgumentException e) {
        out.println(e);
      }
    }
    // second, remove the old ship
    Ship<Character> ship = this.theBoard.getShip(c);
    // third, let user choose move ship to which valid place, here we get the new
    // ship
    Placement p = null;
    Ship<Character> s = null;
    while (true) {
      try {
        this.theBoard.removeShip(ship);
        p = readPlacement("Where do you want to move your " + ship.getName() + "?");
        s = shipCreationFns.get(ship.getName()).apply(p);
        String error = this.theBoard.tryAddShip(s);
        if (error != null) {
          throw new Exception(error);
        }
      } catch (Exception e) {
        this.out.println(
            e +
                "Incorrect coordinate or orientation, may be out of bounds or there is already a ship on this coordinate. Do you want reselect the coordinate, or select another action? Press 'y' if you want to reselect the placement, otherwise press 'n', you can select another action!");
        String choose = this.inputReader.readLine();
        if (choose.equals("y") || choose.equals("Y")) {
          this.theBoard.tryAddShip(ship);// we add back the ship if we do not want to remove ship
          continue;
        } else if (choose.equals("n") || choose.equals("N")) {
          this.theBoard.tryAddShip(ship);// we add back the ship if we do not want to remove ship
          this.playOneTurn(enemyBoard, enemyView, name);
          return;
        } else {
          this.theBoard.tryAddShip(ship);
          String record = null;
          while (true) {
            out.println("Please choose again between Y and N!");
            String ans = this.inputReader.readLine();
            if (ans.toUpperCase().equals("Y") || ans.toUpperCase().equals("N")) {
              record = ans.toUpperCase();
              break;
            }
          }
          if (record.equals("Y")) {
            continue;
          } else {
            this.playOneTurn(enemyBoard, enemyView, name);
            return;
          }
        }
      }
      break;
    }
    // this.theBoard.removeShip(ship);
    transferHit(ship, map_getCoordinate.get(ship), s, p.getOrientation());
    map_getCoordinate.remove(ship);
    map_getCoordinate.put(s, p.getOrientation());
    for (int i = 0; i < myShips_enemyView.size(); i++) {
      if (myShips_enemyView.get(i) == ship) {
        myShips_enemyView.get(i).makeNULL();
      }
    }
    myShips_enemyView.add(s);
    --this.moveTokens;
  }

  /**
   * This function is to transfer the hit coordinate from the old ship to a new
   * ship, with fixed relative coordinate.
   * 
   * @param: oldShip,            the old ship that needs to be transfered from
   * @param: newShip,            the new ship that needs to get transfered
   * @param: oldShipOrientation: The orientation of old ship
   */
  public void transferHit(Ship<Character> oldShip, Character oldShipOrientation, Ship<Character> newShip,
      Character newShipOrientation) {
    ArrayList<Coordinate> list_old = oldShip.getCoordinates_inorder(oldShipOrientation);
    ArrayList<Coordinate> list_new = newShip.getCoordinates_inorder(newShipOrientation);
    for (int i = 0; i < list_old.size(); i++) {
      if (oldShip.wasHitAt(list_old.get(i))) {
        newShip.recordHitAt(list_new.get(i));
      }
    }

  }

  /**
   * This function takes the enemyBoard, print what sonar does in the board, print
   * four lines
   * 
   * @param: enemy's board
   */
  public void useSonar(Board<Character> enemyBoard) throws IOException {
    this.out.println("Where do you want to use the sonar?");
    Coordinate c;
    // First, check if the user's input is valid, if not, enter again
    while (true) {
      try {
        String str = this.inputReader.readLine();
        c = new Coordinate(str);
      } catch (IllegalArgumentException e) {
        this.out.println("Please enter a correct Coordinate!");
        continue;
      }
      break;
    }
    // Second, store the result in the map. And we can initialize the map with four
    // elemens.
    HashMap<Character, Integer> map = new HashMap<>();
    // map.put('H', 0);
    // map.put('V', 0);
    map.put('s', 0);
    map.put('d', 0);
    map.put('b', 0);
    map.put('c', 0);
    for (int i = c.getRow() - 3; i <= c.getRow() + 3; i++) {
      int colStartIndex = Math.abs(i - c.getRow()) - 3;
      for (int j = c.getColumn() + colStartIndex; j <= c.getColumn() - colStartIndex; j++) {
        Coordinate c_around = new Coordinate(i, j);

        Character target = enemyBoard.whatIsAtForSelf(c_around);
        if (target != null) {
          map.put(target, map.getOrDefault(target, 0) + 1);
        }

      }
    }
    String squares = " squares";
    String square = " square";
    String aim1, aim2, aim3, aim4;
    if (map.get('s') != 1) {
      aim1 = squares;
    } else {
      aim1 = square;
    }
    if (map.get('d') != 1) {
      aim2 = squares;
    } else {
      aim2 = square;
    }
    if (map.get('b') != 1) {
      aim3 = squares;
    } else {
      aim3 = square;
    }
    if (map.get('c') != 1) {
      aim4 = squares;
    } else {
      aim4 = square;
    }
    this.out.println("--------------------------------------------------------------------------------");
    this.out.println("Submarines occupy " + map.get('s') + aim1);
    this.out.println("Destroyers occupy " + map.get('d') + aim2);
    this.out.println("Battleships occupy " + map.get('b') + aim3);
    this.out.println("Carriers occupy " + map.get('c') + aim4);
    this.out.println("--------------------------------------------------------------------------------");
    --this.sonarTokens;
  }

}
