package application.logic;

import application.core.Tile;

public interface IBoardClassic {
	//Check Three Tiles
	boolean checkSet(int x1, int y1, int x2, int y2, int x3, int y3) throws InterruptedException;
	boolean checkSet(Tile t1, Tile t2, Tile t3) throws InterruptedException;
}
