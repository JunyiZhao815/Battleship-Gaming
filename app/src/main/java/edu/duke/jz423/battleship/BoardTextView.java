package edu.duke.jz423.battleship;

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
  private final Board toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  /**
     This returns a string that displays the blank broad
     @return a string that displays the blank broad, with head on the top and bottom
   */
  public String displayMyOwnBoard() {
    String header = makeHeader();
    StringBuilder ans = new StringBuilder(header);
    int width = toDisplay.getWidth();
    int height = toDisplay.getHeight();
    for (int i = 0; i < height; i++) { // create many rows, or heights
      StringBuilder sb = new StringBuilder(String.valueOf((char) (i + 65)) + " ");
      String sep = "";
      for (int j = 0; j < width; j++) { // create one row
        sb.append(sep);
        sb.append(" ");
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

}
