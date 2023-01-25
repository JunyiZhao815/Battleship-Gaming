/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.InputStreamReader;
public class App {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;

  public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
  }
  /**
     It is a function that reads Placement from the input String on system.
     @param prompt: Words that asks user to input string.
     @return return a Placement
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }
  /**
     It is a function that place one ship to a board based on system in, and it will print out
     the board
   */
  public void doOnePlacement() throws IOException {
    Placement place = readPlacement("Where would you like to put your ship?");
    RectangleShip<Character> ship = new RectangleShip<Character>(place.getWhere(), 's', '*');
    //BasicShip ship = new BasicShip(place.getWhere());
    theBoard.tryAddShip(ship);
    out.println(view.displayMyOwnBoard());
  }

  public static void main(String[] args) throws IOException {
   Board<Character> b = new BattleShipBoard<>(10, 20);
   App app = new App(b, new InputStreamReader(System.in), System.out);
   app.doOnePlacement();
    
  }

}
