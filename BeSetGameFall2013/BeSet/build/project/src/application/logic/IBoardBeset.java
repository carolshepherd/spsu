package application.logic;

import application.core.Tile;

public interface IBoardBeset {
	//Swap two tiles at [x1,y1] and [x2,y2]
	boolean swapTiles(int x1, int y1, int x2, int y2) throws InterruptedException;
	boolean swapTiles(Tile t1, Tile t2) throws InterruptedException;
	//Tell Bastet to claim a set on the board
	void claimSet();
	//Break the set that contains the Tile at [x,y]
	void breakSetAt(int x, int y);
}
