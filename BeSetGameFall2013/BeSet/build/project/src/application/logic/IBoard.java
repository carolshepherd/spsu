package application.logic;

import application.core.Tile;

public interface IBoard {
	//Get the Tile at [x,y]
	Tile getTileAt(int x, int y);
	Tile getTileAt(String ID);
	//Checks if a set exists
	boolean setExists();
	//Get board size
	int getBoardSizeX();
	int getBoardSizeY();
}
