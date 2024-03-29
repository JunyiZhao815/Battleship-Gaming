package edu.duke.jz423.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public Ship<T> fireAt(Coordinate c);

  public T whatIsAtForEnemy(Coordinate where);

  public boolean isAllSunk();

  public Ship<T> getShip(Coordinate c);

  public void removeShip(Ship<T> s);


}
