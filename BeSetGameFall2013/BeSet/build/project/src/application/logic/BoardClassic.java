package application.logic;

import application.core.Dealer;
import application.core.Tile;

public class BoardClassic implements IBoard, IBoardClassic {
	
	private Tile[][] _board; //Contains the tiles of the board
	private int _sizeX, _sizeY; //The size of the board. SizeX x SizeY
	private Dealer _dealer; //The structure that deals out tiles and shuffles then
	private boolean _setExists; //Determines if a set exists
	private StringBuilder sb;
	
	public BoardClassic() {
		_sizeX = 4;
		_sizeY = 4;
		_dealer = new Dealer(false);
		_board = new Tile[_sizeX][_sizeY];
		sb = new StringBuilder();
		fillBoard();
	}
	public BoardClassic(int sizeX, int sizeY) {
		_sizeX = sizeX;
		_sizeY = sizeY;
		_dealer = new Dealer(false);
		_board = new Tile[_sizeX][_sizeY];
		sb = new StringBuilder();
		fillBoard();
	}
	public BoardClassic(int sizeX, int sizeY, boolean idiotsBoard) {
		_sizeX = sizeX;
		_sizeY = sizeY;
		if (idiotsBoard) {
			System.out.println("Loading idiot's board.");
		} else {
			System.out.println("Loading regular board.");
		}
		_dealer = new Dealer(idiotsBoard);
		_board = new Tile[_sizeX][_sizeY];
		sb = new StringBuilder();
		fillBoard();
	}
	public int getBoardSizeX() {
		return _sizeX;
	}
	public int getBoardSizeY() {
		return _sizeY;
	}
	public boolean setExists() {
		return _setExists;
	}
	public Tile getTileAt(int x, int y) {
		if (x < 0 || x >= _sizeX) {
			throw new IndexOutOfBoundsException("X index passed to getTileAt is out of bounds. Range = 0-" + _sizeX + ". Provided = " + x);
		}
		else if (y < 0 || y >= _sizeY){
			throw new IndexOutOfBoundsException("Y index passed to getTileAt is out of bounds. Range = 0-" + _sizeY + ". Provided = " + y);
		}
		else {
			return _board[x][y];
		}
	}
	public Tile getTileAt(String ID) {
		String[] coords = ID.split(":");
		return getTileAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
	}
	public Tile getTileAtID(String id) {
		for (int x = 0; x < _sizeX; ++x){
			for (int y = 0; y < _sizeY; ++y){
				if (_board[x][y].positionID == id){
					return _board[x][y];
				}
			}
			
		}
		return Tile.Undefined;
	}
	public boolean checkSet(int x1, int y1, int x2, int y2, int x3, int y3) throws InterruptedException {
		System.out.println("Checking tiles at {" + x1 + "," + y1 + "} , {" + x2 + "," + y2 + "} , {" + x3 + "," + y3 + "}");
		if (x1 < 0 || x1 >= _sizeX) {
			throw new IndexOutOfBoundsException("x1 index passed to checkSet is out of bounds. Range = 0-" + (_sizeX - 1) + ". Provided = " + x1);
		}
		if (x2 < 0 || x2 >= _sizeX) {
			throw new IndexOutOfBoundsException("x2 index passed to checkSet is out of bounds. Range = 0-" + (_sizeX - 1) + ". Provided = " + x2);
		}
		if (x3 < 0 || x3 >= _sizeX) {
			throw new IndexOutOfBoundsException("x3 index passed to checkSet is out of bounds. Range = 0-" + (_sizeX - 1) + ". Provided = " + x3);
		}
		if (y1 < 0 || y1 >= _sizeY) {
			throw new IndexOutOfBoundsException("y1 index passed to checkSet is out of bounds. Range = 0-" + (_sizeY - 1) + ". Provided = " + y1);
		}
		if (y2 < 0 || y2 >= _sizeY) {
			throw new IndexOutOfBoundsException("y2 index passed to checkSet is out of bounds. Range = 0-" + (_sizeY - 1) + ". Provided = " + y2);
		}
		if (y3 < 0 || y3 >= _sizeY) {
			throw new IndexOutOfBoundsException("y3 index passed to checkSet is out of bounds. Range = 0-" + (_sizeY - 1) + ". Provided = " + y3);
		}
		if (_board[x1][y1] == Tile.Undefined || _board[x2][y2] == Tile.Undefined || _board[x3][y3] == Tile.Undefined) {
			return false;
		}
		boolean isSet = Tile.checkSetDebugging(_board[x1][y1], _board[x2][y2], _board[x3][y3]);
		if (isSet) {
			if (!_dealer.isRigged()) {
				System.out.println("The tiles at {" + x1 + "," + y1 + "} , {" + x2 + "," + y2 + "} , {" + x3 + "," + y3 + "} are a match. Swapping and returning true.");
				//String oldTile1 = _board[x1][y1].toString();
				_dealer.discard(_board[x1][y1]);
				_board[x1][y1] = _dealer.deal();
				_board[x1][y1].setPosition(x1, y1);
				_board[x1][y1].setPositionID(setTilePositionID(x1, y1));
				//System.out.println("The tile at {" + x1 + "," + y1 + "} (" + oldTile1 + ") has been replaced by (" + _board[x1][y1].toString() + ").");
				//System.out.println("Tile ID at {" + x1 + "," + y1 + "} is now " + _board[x1][y1].positionID);
				//String oldTile2 = _board[x2][y2].toString();
				_dealer.discard(_board[x2][y2]);
				_board[x2][y2] = _dealer.deal();
				_board[x2][y2].setPosition(x2, y2);
				_board[x2][y2].setPositionID(setTilePositionID(x2, y2));
				//System.out.println("The tile at {" + x2 + "," + y2 + "} (" + oldTile2 + ") has been replaced by (" + _board[x2][y2].toString() + ").");
				//System.out.println("Tile ID at {" + x2 + "," + y2 + "} is now " + _board[x2][y2].positionID);
				//String oldTile3 = _board[x3][y3].toString();
				_dealer.discard(_board[x3][y3]);
				_board[x3][y3] = _dealer.deal();
				_board[x3][y3].setPosition(x3, y3);
				_board[x3][y3].setPositionID(setTilePositionID(x3, y3));
				//System.out.println("The tile at {" + x3 + "," + y3 + "} (" + oldTile3 + ") has been replaced by (" + _board[x3][y3].toString() + ").");
				//System.out.println("Tile ID at {" + x3 + "," + y3 + "} is now " + _board[x3][y3].positionID);
				checkSetExists(false);
				return true;
			} else {
				boolean areSets;
				
				do {
					for (int x = 0; x < 3; ++x) {
						for (int y = 0; y < 1; ++y) {
							//System.out.println("Placing tile at {" + x + ", " + y + "}");
							_dealer.discard(_board[x][y]);
							_board[x][y] = _dealer.deal();
							_board[x][y].setPosition(x, y);
							_board[x][y].setPositionID(setTilePositionID(x, y));
							//System.out.println("The tile at {" + x + ", " + y + "} has a position of {" + _board[x][y].getPosition()[0] + ", " + _board[x][y].getPosition()[1] + "} with an ID of " + _board[x][y].positionID + ".");
						}
					}
					areSets = Tile.checkSet(_board[0][0], _board[1][0], _board[2][0]);
				} while (!areSets);
				return true;
			}
		} else {
			System.out.println("The tiles at {" + x1 + "," + y1 + "} , {" + x2 + "," + y2 + "} , {" + x3 + "," + y3 + "} are not a match. Returning false.");
			return false;
		}
	}
	public boolean checkSet(Tile t1, Tile t2, Tile t3) throws InterruptedException {
		return checkSet(t1.X(), t1.Y(), t2.X(), t2.Y(), t3.X(), t3.Y());
	}
	private void fillBoard() {
		System.out.println("Filling the game board...");
		
		if (!_dealer.isRigged())
		{
			boolean areSets;
		
			do {
				_dealer.shuffle();
				for (int x = 0; x < _sizeX; ++x) {
					for (int y = 0; y < _sizeY; ++y) {
						//System.out.println("Placing tile at {" + x + ", " + y + "}");
						_board[x][y] = _dealer.deal();
						_board[x][y].setPosition(x, y);
						_board[x][y].setPositionID(setTilePositionID(x, y));
						//System.out.println("The tile at {" + x + ", " + y + "} has a position of {" + _board[x][y].getPosition()[0] + ", " + _board[x][y].getPosition()[1] + "} with an ID of " + _board[x][y].positionID + ".");
					}
				}
				areSets = checkSetExists(true);
			} while (!areSets);
		} else {
			boolean areSets;
			
			do {
				_dealer.shuffle();
				for (int x = 0; x < _sizeX; ++x) {
					for (int y = 0; y < _sizeY; ++y) {
						//System.out.println("Placing tile at {" + x + ", " + y + "}");
						_board[x][y] = _dealer.deal();
						_board[x][y].setPosition(x, y);
						_board[x][y].setPositionID(setTilePositionID(x, y));
						//System.out.println("The tile at {" + x + ", " + y + "} has a position of {" + _board[x][y].getPosition()[0] + ", " + _board[x][y].getPosition()[1] + "} with an ID of " + _board[x][y].positionID + ".");
					}
				}
				areSets = Tile.checkSet(_board[0][0], _board[1][0], _board[2][0]);
			} while (!areSets);
		}

		/*System.out.println("Printing board...");
		for (int y = 0; y < _sizeY; ++y) {
			for (int x = 0; x < _sizeX; ++x) {
				System.out.println("{" + x + ", " + y + "} --> " + _board[x][y].toString());
			}
		}*/
	}
	
	public Tile[] findMatch() {
		Tile[] temp = new Tile[3];
		for (int x1 = 0; x1 < _sizeX; ++x1) {
			for (int x2 = 0; x2 < _sizeX; ++x2) {
				for (int x3 = 0; x3 < _sizeX; ++x3) {
					for (int y1 = 0; y1 < _sizeY; ++y1) {
						for (int y2 = 0; y2 < _sizeY; ++y2) {
							for (int y3 = 0; y3 < _sizeY; ++y3) {
								if ((x1 == x2 && y1 == y2) || (x1 == x3 && y1 == y3) || (x2 == x3 && y2 == y3)) {
									continue;
								}
								if (Tile.checkSet(_board[x1][y1], _board[x2][y2], _board[x3][y3])) {
									temp[0] = _board[x1][y1];
									temp[1] = _board[x2][y2];
									temp[2] = _board[x3][y3];
									return temp;
								}
							}
						}
					}
				}
			}
		}
		temp[0] = Tile.Undefined;
		temp[1] = Tile.Undefined;
		temp[2] = Tile.Undefined;
		return temp;
	}
	private boolean checkSetExists(boolean initial) {
		byte numMatches = 0;
		System.out.println("Checking whether a set exists...");
		for (int x1 = 0; x1 < _sizeX; ++x1) {
			for (int x2 = 0; x2 < _sizeX; ++x2) {
				for (int x3 = 0; x3 < _sizeX; ++x3) {
					for (int y1 = 0; y1 < _sizeY; ++y1) {
						for (int y2 = 0; y2 < _sizeY; ++y2) {
							for (int y3 = 0; y3 < _sizeY; ++y3) {
								if ((x1 == x2 && y1 == y2) || (x1 == x3 && y1 == y3) || (x2 == x3 && y2 == y3)) {
									continue;
								}
								if (Tile.checkSet(_board[x1][y1], _board[x2][y2], _board[x3][y3])) {
									_setExists = true;
									++numMatches;
								}
							}
						}
					}
				}
			}
		}
		if (numMatches > 0) {
			System.out.println("There are " + (numMatches / 3) + " matches on this board.");
			return true;
		}
		if (initial) {
			_setExists = false;
			System.out.println("A set does not exist on this board. Redealing initial board...");
			return false;
		} else {
			/*
			 * Send a message to Clint that no matches exist and that I am refilling the board.
			 * fillBoard();
			 */
			System.out.println("A set does not exist on this board. Redealing board...");
			return false;
		}
	}

	private String setTilePositionID(int x, int y) {
		sb.setLength(0);
		switch (x) {
			case 0: sb.append("zero"); break;
			case 1: sb.append("one"); break;
			case 2: sb.append("two"); break;
			case 3: sb.append("three"); break;
			case 4: sb.append("four"); break;
			case 5: sb.append("five"); break;
			default: sb.append("negativeOne"); break;
		}
		switch (y) {
			case 0: sb.append("Zero"); break;
			case 1: sb.append("One"); break;
			case 2: sb.append("Two"); break;
			case 3: sb.append("Three"); break;
			case 4: sb.append("Four"); break;
			case 5: sb.append("Five"); break;
			default: sb.append("NegativeOne"); break;
		}
		return sb.toString();
	}

}
