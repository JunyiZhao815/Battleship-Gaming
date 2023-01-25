package edu.duke.jz423.battleship;

public interface ShipDisplayInfo <T>{
  public T getInfo(Coordinate where, boolean hit);

}
