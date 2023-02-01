package edu.duke.jz423.battleship;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * x
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * A protected function that display the board for both self and enemy
   * 
   * @param: a function
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {

    String header = makeHeader();
    StringBuilder ans = new StringBuilder(header);
    int width = toDisplay.getWidth();
    int height = toDisplay.getHeight();
    for (int i = 0; i < height; i++) { // create many rows, or heights
      StringBuilder sb = new StringBuilder(String.valueOf((char) (i + 65)) + " ");
      String sep = "";
      for (int j = 0; j < width; j++) { // create one row
        sb.append(sep);
        Coordinate coordinate = new Coordinate(i, j);
        Character hasShip = getSquareFn.apply(coordinate);
        if (hasShip != null) {
          sb.append(hasShip);
        } else {
          sb.append(" ");
        }
        sep = "|";
      }
      sb.append(" " + String.valueOf((char) (i + 65) + "\n"));
      ans.append(sb);
    }
    ans.append(header);
    return ans.toString(); // this is a placeholder for the moment
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This returns a string that displays the blank broad
   * 
   * @return a string that displays my own broad, with head on the top and
   *         bottom
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * This returns a string that displays the blank broad
   * 
   * @return a string that displays enemy's broad, with head on the top and
   *         bottom
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

}
