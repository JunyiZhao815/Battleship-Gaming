
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.jz423.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
  TextPlayer player1;
  TextPlayer player2;
  
  public App(TextPlayer p1, TextPlayer p2) {
    this.player1 = p1;
    this.player2 = p2;
  }



  public void doPlacementPhase() throws IOException {
    player1.doPlacementPhase();
    player2.doPlacementPhase();
  }

  public static void main(String[] args) throws IOException {
    // Board<Character> b = new BattleShipBoard<>(10, 20);
    // App app = new App(b, new InputStreamReader(System.in), System.out);
    // app.doOnePlacement();

    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();

    TextPlayer p1 = new TextPlayer("AAA", b1, input, System.out, factory);
    TextPlayer p2 = new TextPlayer("BBB", b2, input, System.out, factory);
    App app = new App(p1, p2);
    // app.player1.doOnePlacement();
    app.doPlacementPhase();
  }

}
