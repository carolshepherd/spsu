package application.logic;

import java.util.ArrayList;
import java.util.Random;

//import org.junit.Test;




import application.core.*;

public class BoardBeset implements IBoard, IBoardBeset {
	
	private Tile[][] _board; //Contains the tiles of the board
	private ArrayList<Tile[]> _claimedTiles; //The sets of sets claimed by Bastet
	private Tile[] _matchedTiles; //The list of 3 tiles successfully grabbed by the last swapTiles call
	private int _sizeX, _sizeY; //The size of the board. SizeX x SizeY
	private Dealer _dealer; //The structure that deals out tiles and shuffles them
	private Random _r; //RNG for which tiles Bastet claims
	private int _chainSize; //How many chains the player gets
	private boolean _setExists; //Determines if a set exists
	
	public BoardBeset() {
		_sizeX = 4;
		_sizeY = 3;
		_dealer = new Dealer(false);
		_board = new Tile[_sizeX][_sizeY];
		_claimedTiles = new ArrayList<Tile[]>();
		_matchedTiles = new Tile[3];
		_r = new Random();
		_chainSize = 0;
		
		fillBoard(); //Fill the board with tiles.
	}
	public BoardBeset(int sizeX, int sizeY) {
		_sizeX = sizeX;
		_sizeY = sizeY;
		_dealer = new Dealer(false);
		_board = new Tile[sizeX][sizeY];
		_claimedTiles = new ArrayList<Tile[]>();
		_matchedTiles = new Tile[3];
		_r = new Random();
		_chainSize = 0;
		
		fillBoard(); //Fill the board with tiles.
	}
	public int getBoardSizeX() {
		return _sizeX;
	}
	public int getBoardSizeY() {
		return _sizeY;
	}
	public void claimSet() { //COMPLETE
		findSetToClaim(_r.nextInt(_sizeX), _r.nextBoolean());
	}
	//Returns whether or not a set can be made.
	public boolean setExists() { //COMPLETE
		return _setExists;
	}
	public Tile getTileAt(int x, int y) { //COMPLETE
		if (x < 0 || x >= _sizeX) {
			throw new IndexOutOfBoundsException("x index passed to getTileAt is out of bounds. Range = 0-" + _sizeX + ". Provided = " + x);
		}
		if (y < 0 || y >= _sizeY){
			throw new IndexOutOfBoundsException("y index passed to getTileAt is out of bounds. Range = 0-" + _sizeY + ". Provided = " + y);
		}
		return _board[x][y];
	}
	public Tile getTileAt(String ID) {
		String[] coords = ID.split(":");
		return getTileAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
	}
	public Tile[] getMatchedTiles() {
		return _matchedTiles;
	}
	public void breakSetAt(int x, int y) { //COMPLETE
		if (x < 0 || x >= _sizeX) {
			throw new IndexOutOfBoundsException("x index passed to breakSetAt is out of bounds. Range = 0-" + _sizeX + ". Provided = " + x);
		}
		if (y < 0 || y >= _sizeY){
			throw new IndexOutOfBoundsException("y index passed to breakSetAt is out of bounds. Range = 0-" + _sizeY + ". Provided = " + y);
		}
		Tile tileAt = _board[x][y];
		
		for (int i = 0; i < _claimedTiles.size(); ++i) {
			for (int j = 0; j < _claimedTiles.get(i).length; ++j) {
				if (_claimedTiles.get(i)[j] == tileAt) {
					for (Tile t : _claimedTiles.get(i)) {
						_dealer.discard(_board[t.X()][t.Y()]);
						_board[t.X()][t.Y()] = _dealer.deal();
					}
					return;
				}
			}
		}
	}
	public boolean swapTiles(Tile t1, Tile t2) throws InterruptedException {
		return swapTiles(t1.X(), t1.Y(), t2.X(), t2.Y());
	}
	public boolean swapTiles(int x1, int y1, int x2, int y2) throws InterruptedException { //COMPLETE
		if (x1 < 0 || x1 >= _sizeX) {
			throw new IndexOutOfBoundsException("x1 index passed to swapTiles is out of bounds. Range = 0-" + _sizeX + ". Provided = " + x1);
		}
		if (x2 < 0 || x2 >= _sizeX) {
			throw new IndexOutOfBoundsException("x2 index passed to swapTiles is out of bounds. Range = 0-" + _sizeX + ". Provided = " + x2);
		}
		if (y1 < 0 || y1 >= _sizeY) {
			throw new IndexOutOfBoundsException("y1 index passed to swapTiles is out of bounds. Range = 0-" + _sizeY + ". Provided = " + y1);
		}
		if (y2 < 0 || y2 >= _sizeY) {
			throw new IndexOutOfBoundsException("y2 index passed to swapTiles is out of bounds. Range = 0-" + _sizeY + ". Provided = " + y2);
		}
		//Check for illegal actions
		if (Math.abs(x1 - x2) > 1) { 
			_matchedTiles[0] = Tile.Undefined;
			_matchedTiles[1] = Tile.Undefined;
			_matchedTiles[2] = Tile.Undefined;
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false; }
		if (Math.abs(y1 - y2) > 1) { 
			_matchedTiles[0] = Tile.Undefined;
			_matchedTiles[1] = Tile.Undefined;
			_matchedTiles[2] = Tile.Undefined;
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false; }
		if (Math.abs(x1 - x2) - Math.abs(y1 - y2) != 1) { 
			_matchedTiles[0] = Tile.Undefined;
			_matchedTiles[1] = Tile.Undefined;
			_matchedTiles[2] = Tile.Undefined;
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false; }
		if (x1 == x2 && y1 == y2) { 
			_matchedTiles[0] = Tile.Undefined;
			_matchedTiles[1] = Tile.Undefined;
			_matchedTiles[2] = Tile.Undefined;
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false; }
		if (_board[x1][y1].isClaimed() || _board[x2][y2].isClaimed()) { 
			_matchedTiles[0] = Tile.Undefined;
			_matchedTiles[1] = Tile.Undefined;
			_matchedTiles[2] = Tile.Undefined;
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false; }
		
		//Swap the tiles
		swap(x1, y1, x2, y2);
		
		//If a set doesn't exist, switch them back
		if (!calculateSet(x1, y1, ActionToTake.STANDARD_CHECK) && !calculateSet(x2, y2, ActionToTake.STANDARD_CHECK)) {
			swap(x1, y1, x2, y2);
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			return false;
		} else {
			//Else increase the score by the chain size
			//_player.increaseScore(_chainSize);
			//Reset the chain size to 0
			_chainSize = 0;
			//See if any more matches can be found
			setExistsWithSwapping();
///////////////////////////
Thread.sleep(5000);			
///////////////////////////
			//Return that we found a match
			return true;
		}
	}
	
	//Returns whether or not a swap occurred
	
	//Swap the tiles in [x1,y1] and [x2,y2]
	private boolean swap(int x1, int y1, int x2, int y2) { //COMPLETE
		if (x1 < 0 || x1 >= _sizeX || x2 < 0 || x2 >= _sizeX || y1 < 0 || y1 >= _sizeY || y2 < 0 || y2 >= _sizeY) {
			return false;
		}
		Tile temp = _board[x1][y1];
		_board[x1][y1] = _board[x2][y2];
		_board[x2][y2] = temp;
		return true;
	}
	//Calculates whether [x,y] is part of a set
	//Calculate if a set exists
	private boolean calculateSet(int x2, int y2, ActionToTake action) { //COMPLETE FOR NOW
		
		int tile1X = x2;
		int tile1Y = y2;
		int tile2X, tile2Y, tile3X, tile3Y;
		
		//check left/swappedTile/right
		tile2X = tile1X - 1;
		tile2Y = tile1Y;
		tile3X = tile1X + 1;
		tile3Y = tile1Y;
		//Border Check
		if (!(tile2X < 0) && !(tile3X >= _sizeX))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}
		
		//check up/swappedTile/down
		tile2X = tile1X;
		tile2Y = tile1Y - 1;
		tile3X = tile1X;
		tile3Y = tile1Y + 1;
		//Border Check
		if (!(tile2Y < 0) && !(tile3Y >= _sizeY))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}
		
		//check up/swappedTile/right
		tile2X = tile1X;
		tile2Y = tile1Y - 1;
		tile3X = tile1X + 1;
		tile3Y = tile1Y;
		//Border Check
		if (!(tile2Y < 0) && !(tile3X >= _sizeX))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}		
		
		//check up/swappedTile/left
		tile2X = tile1X;
		tile2Y = tile1Y - 1;
		tile3X = tile1X - 1;
		tile3Y = tile1Y;
		
		//Border Check
		if (!(tile2Y < 0) && !(tile3X < 0))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}
		
		//check down/swappedTile/right
		tile2X = tile1X;
		tile2Y = tile1Y + 1;
		tile3X = tile1X + 1;
		tile3Y = tile1Y;
		//Border Check
		if (!(tile2Y >= _sizeY) && !(tile3X >= _sizeX))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}		
		
		//check down/swappedTile/left
		tile2X = tile1X;
		tile2Y = tile1Y + 1;
		tile3X = tile1X - 1;
		tile3Y = tile1Y;
		
		//Border Check
		if (!(tile2Y >= _sizeY) && !(tile3X < 0))
		{
			if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
				return true;
			}
		}
		
		//check up
		tile2X = tile1X;
		tile2Y = tile1Y - 1;
		for (int x = -1; x <= 1; ++x) {
			tile3X = tile1X + x;
			if (x != 0) {
				tile3Y = tile1Y - 1;
			} else {
				tile3Y = tile1Y - 2;
			}
			
			//Border Check
			if (tile3X < 0 || tile2Y < 0 || tile3Y < 0 || tile3X >= _sizeX) {
				continue;
			} else {
				if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
					return true;
				}
			}
		}
		
		//check down
		tile2X = tile1X;
		tile2Y = tile1Y + 1;
		for (int x = -1; x <= 1; ++x) {
			tile3X = tile1X + x;
			if (x != 0) {
				tile3Y = tile1Y + 1;
			} else {
				tile3Y = tile1Y + 2;
			}
			
			//Border Check
			if (tile3X < 0 || tile2Y >= _sizeY || tile3Y >= _sizeY || tile3X >= _sizeX) {
				continue;
			} else {
				if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
					return true;
				}
			}
		}		
		
		//check left
		tile2X = tile1X - 1;
		tile2Y = tile1Y;
		for (int y = -1; y <= 1; ++y) {
			tile3Y = tile1Y + y;
			if (y != 0) {
				tile3X = tile1X - 1;
			} else {
				tile3X = tile1X - 2;
			}
			
			//Border Check
			if (tile2X < 0 || tile3X < 0 || tile3Y >= _sizeY || tile3Y < 0) {
				continue;
			} else {
				if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
					return true;
				}
			}
		}		
		
		//check right
		tile2X = tile1X + 1;
		tile2Y = tile1Y;
		for (int y = -1; y <= 1; ++y) {
			tile3Y = tile1Y + y;
			if (y != 0) {
				tile3X = tile1X + 1;
			} else {
				tile3X = tile1X + 2;
			}
			
			//Border Check
			if (tile2X >= _sizeX || tile3X >= _sizeX || tile3Y >= _sizeY || tile3Y < 0) {
				continue;
			} else {
				if (calcSetHelper(tile1X, tile2X, tile3X, tile1Y, tile2Y, tile3Y, action)) {
					return true;
				}
			}
		}		
		
		return false;
		
	}
	
	private boolean calcSetHelper(int tile1X, int tile2X, int tile3X, int tile1Y, int tile2Y, int tile3Y, ActionToTake action) { //COMPLETE...PROBABLY
		//Check if either tile is claimed
		if (!_board[tile2X][tile2Y].isClaimed() && !_board[tile3X][tile3Y].isClaimed()) {
			//Check if the three make a set
			if (Tile.checkSet(_board[tile1X][tile1Y], _board[tile2X][tile2Y], _board[tile3X][tile3Y])) {
				//Disregarding animation at this point. We increase the chain size and replace the set tiles with new tiles.
				if (action == ActionToTake.STANDARD_CHECK) {
					++_chainSize;
					//These are the three tiles that were a match
					_matchedTiles[0] = _board[tile1X][tile1Y];
					_matchedTiles[1] = _board[tile2X][tile2Y];
					_matchedTiles[2] = _board[tile3X][tile3Y];
					//Discard these cards (add them to the other deck)
					_dealer.discard(_board[tile1X][tile1Y]);
					_dealer.discard(_board[tile2X][tile2Y]);
					_dealer.discard(_board[tile3X][tile3Y]);
					//Replace the discarded cards and set their position to their position in the array
					_board[tile1X][tile1Y] = _dealer.deal();
					_board[tile1X][tile1Y].setPosition(tile1X, tile1Y);
					_board[tile2X][tile2Y] = _dealer.deal();
					_board[tile2X][tile2Y].setPosition(tile2X, tile2Y);
					_board[tile3X][tile3Y] = _dealer.deal();
					_board[tile3X][tile3Y].setPosition(tile3X, tile3Y);
					//Check for chains
					calculateSet(tile1X, tile1Y, ActionToTake.STANDARD_CHECK);
					calculateSet(tile2X, tile2Y, ActionToTake.STANDARD_CHECK);
					calculateSet(tile3X, tile3Y, ActionToTake.STANDARD_CHECK);
				} else if (action == ActionToTake.BASTET_CHECK) {
					_claimedTiles.add(new Tile[] { _board[tile1X][tile1Y], _board[tile2X][tile2Y], _board[tile3X][tile3Y] });
					for(Tile t : _claimedTiles.get(_claimedTiles.size() - 1)) {
						t.claim();
					}
				} else {
					
				}
				return true;
			}
		}
		return false;
	}
	//Fill the board with tiles.
	//@Test(timeout=75)
	private void fillBoard() { //COMPLETE
		boolean areSets;
		
		//Shuffle the deck, reset the pointer, and deal the tiles on the board
		do {
			_dealer.shuffle();
			for (int x = 0; x < _sizeX; ++x) {
				for (int y = 0; y < _sizeY; ++y) {
					_board[x][y] = _dealer.deal();
					_board[x][y].setPosition(x, y);
				}
			}
			//do this until no sets are possible - it is also an issue if there are no swappable sets
			areSets = setExistsWithoutSwapping();
		} while (areSets || !setExistsWithSwapping());
	}
	
	//Determines whether or not a set exists without swapping any tiles
	private boolean setExistsWithoutSwapping() { //COMPLETE
		
		for (int x = 0; x < _sizeX; ++x) {
			for (int y = 0; y < _sizeY; ++y) {
				if (calculateSet(x, y, ActionToTake.NO_CHECK)) {
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}
	//Determines if there is at least one set that can be made by swapping tiles
	private boolean setExistsWithSwapping() { //COMPLETE
		//We only need to swap the cards right and down (or left and up) since checking left is the same as checking right on the previous iteration
		for (int x = 0; x < _sizeX; ++x) {
			for (int y = 0; y < _sizeY; ++y) {
				//check right swap
				if (swap(x, y, x+1, y)) {
					if (calculateSet(x, y, ActionToTake.NO_CHECK) || calculateSet(x+1, y, ActionToTake.NO_CHECK)) {
						swap(x, y, x+1, y);
						_setExists = true;
						return true;
					} else {
						swap(x, y, x+1, y);
					}
				}
				//check down swap
				if (swap(x, y, x, y+1)) {
					if (calculateSet(x, y, ActionToTake.NO_CHECK) || calculateSet(x, y+1, ActionToTake.NO_CHECK)) {
						swap(x, y, x, y+1);
						_setExists = true;
						return true;
					} else {
						swap(x, y, x+1, y+1);
					}
				}
			}
		}
		_setExists = false;
		return false;
	}
	//Finds a set for Bastet to claim
	private void findSetToClaim(int x1, boolean startFromTop) { //COMPLETE
		if (startFromTop) {
			for (int x = x1; x < _sizeX; ++x) {
				for (int y = 0; y < _sizeY; ++y) {
					//check right swap
					if (swap(x, y, x+1, y)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x+1, y, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x+1, y);
							}
						}
					}
					//check down swap
					if (swap(x, y, x, y + 1)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x, y + 1, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x, y + 1);
							}
						}
					}
				}
			}
			for (int x = 0; x < x1; ++x) {
				for (int y = 0; y < _sizeY; ++y) {
					//check right swap
					if (swap(x, y, x+1, y)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x+1, y, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x+1, y);
							}
						}
					}
					//check down swap
					if (swap(x, y, x, y + 1)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x, y + 1, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x, y + 1);
							}
						}
					}
				}
			}
		} else {
			for (int x = x1; x < _sizeX; ++x) {
				for (int y = _sizeY - 1; y >= 0; ++y) {
					//check right swap
					if (swap(x, y, x+1, y)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x+1, y, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x+1, y);
							}
						}
					}
					//check down swap
					if (swap(x, y, x, y + 1)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x, y + 1, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x, y + 1);
							}
						}
					}
				}
			}
			for (int x = 0; x < x1; ++x) {
				for (int y = _sizeY - 1; y >= 0; ++y) {
					//check right swap
					if (swap(x, y, x+1, y)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x+1, y, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x+1, y);
							}
						}
					}
					//check down swap
					if (swap(x, y, x, y + 1)) {
						if (!calculateSet(x, y, ActionToTake.BASTET_CHECK)) {
							if (!calculateSet(x, y + 1, ActionToTake.BASTET_CHECK)) {
								swap(x, y, x, y + 1);
							}
						}
					}
				}
			}
		}
		setExistsWithoutSwapping();
	}

}
