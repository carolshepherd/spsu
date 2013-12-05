package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.core.Tile;
import application.logic.BoardClassic;
import application.logic.SelectionHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoardController implements Initializable, ControlledScreen {

		ScreensController 			myController;
		// Timer
		Timer 						timer		 				= new java.util.Timer();
		int 						boardSize					= 4,
									realTime					= 0,
									tSeconds 					= 0,
									tMilliSeconds 				= 0,
									timeDelay				 	= 5,
									x 							= -1,
									y 							= 0,
									count 						= 0,
									select						= 0,
									place 						= 0,
									difficulty					= 0,
									onePosX						= 0,
									twoPosX						= 0,
									threePosX					= 0,
									onePosY						= 0,
									twoPosY						= 0,
									threePosY					= 0,
									moves						= 0,
									aiMoves						= 0;	

		public double 				progress 					= 1.0,
									progInterval				= 0,
									glowIntensity 				= 0.8;
		boolean						initGame					= false,
									resetBar					= false,
									loadScreen					= false,
									match						= false;
		Random 						r;
		
		//images
		Image						backGnd;
								
		String 						first 						= null, 
									second						= null, 
									third 						= null;					
		ArrayList<Tile> 			selections;
		
		//BoardClassic 				board 						= new BoardClassic(boardSize, boardSize);
		SelectionHelper             helper                      = new SelectionHelper();
		
		@FXML		Button			menuBtn;
		@FXML		Button			quitBtn;
		@FXML		Button			counterBtn;
		@FXML		Button			pauseBtn;
		@FXML		Button			startBtn;
		@FXML		Button			demoBtn;
		
		@FXML		ProgressBar		progressBar;
		
		@FXML		TextField		timerTb;
		@FXML		TextField	 	testBox;
		@FXML		TextField	 	scoreBox;
		
		@FXML		TextArea	 	aiScoreBox;
		@FXML		TextArea	 	hammerNumber;
		
		
		@FXML		ImageView		basetImage;
		@FXML		ImageView		one				= null;
		@FXML		ImageView		two				= null;
		@FXML		ImageView		three			= null;
		@FXML		ImageView		zeroZero;
		@FXML		ImageView		zeroOne;
		@FXML		ImageView		zeroTwo;
		@FXML		ImageView		zeroThree;
		@FXML		ImageView		oneZero;
		@FXML		ImageView		oneOne;
		@FXML		ImageView		oneTwo;
		@FXML		ImageView		oneThree;
		@FXML		ImageView		twoZero;
		@FXML		ImageView		twoOne;
		@FXML		ImageView		twoTwo;
		@FXML		ImageView		twoThree;
		@FXML		ImageView		threeZero;
		@FXML		ImageView		threeOne;
		@FXML		ImageView		threeTwo;
		@FXML		ImageView		threeThree;
		@FXML		ImageView		hammer;		
		@FXML		Rectangle		testRect;
		@FXML		AnchorPane		gameBoard;
		@FXML		BorderPane		gameBoardBorderPane;
		@FXML		GridPane		gameGrid;
		
		
		
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		System.out.println("setScreenParent gameBoard");
		myController = screenPage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Main.GBC = this;
	}

	@FXML
	private void goToStartMenu(ActionEvent event) {
		System.out.println("go to start menu");
		myController.setScreen(Main.START_MENU);
		Main.PAUSE = true;
	}

	@FXML
	private void pauseGame(ActionEvent event) {
		if (Main.PAUSE == false){
			Main.PAUSE = true;
			gameGrid.setVisible(false);
		} else {
			Main.PAUSE = false;
			gameGrid.setVisible(true);
			
		}
	}

	@FXML
	private void exitGame(ActionEvent event) {
		Main.PAUSE = true;
		myController.setScreen(Main.EXIT_MENU);
	}
	
	@FXML
	private void gameOver() {
		Main.PAUSE = true;
		myController.setScreen(Main.EXIT_MENU);
	}

	@FXML
	private void advCounter(ActionEvent event){
		timerTb.setText("" + tMilliSeconds);
	}
	
	@FXML
	public void STARTGAME(ActionEvent event){
		startBtn.setVisible(false);
		Main.SCORE = 0;
		scoreBox.setText("0");
		aiScoreBox.setText("0");
		progress 		= 1.0;
		progressBar.setProgress(progress);
		realTime		= 0;
		tSeconds 		= 0;
		tMilliSeconds 	= 0;
		Main.PAUSE = false;
		if (Main.DEMO){
			System.out.println("NORMAL GAME");
			init(event);
		} else {
			System.out.println("DEMO GAME");
			initDemo(event);
		}
	}
	
	@FXML
	public void initDemo(ActionEvent event){
		if (initGame == false){
			System.out.println("difficulty = " + difficulty);
			if (difficulty == 0){
				progInterval = (10.0 / 100000);
				System.out.println("dif = 0 progInterval = " + progInterval);
			} else if (difficulty == 1){
				progInterval = (10.0 / 8000);
				System.out.println("dif = 1 progInterval = " + progInterval);
			} else {
				progInterval = (10.0 / 6000);
				System.out.println("dif = 2 progInterval = " + progInterval);
			}
			System.out.println("progInterval = " + progInterval);
			Main.BOARDCLASSIC = new BoardClassic(boardSize, boardSize, true);
			loadBoard();
			if (Main.newGame == true){
				timer.schedule(new TimerTask() {
					public void run() {
						Platform.runLater(new Runnable() {
							public void run() {
								++realTime;
								if (realTime % 1000 == 0){
									//System.out.println(realTime);
								}
								if (realTime > 4){
									if (Main.PAUSE != true || startBtn.isVisible() == false){
										++tMilliSeconds;
										if(tMilliSeconds % 1000 == 0){
											++tSeconds;
											update();
										}
										if (difficulty == 0){
											if (tMilliSeconds % 10 == 0){
												updateProgressBar();
											}
										} else if (difficulty == 1){
											if (tMilliSeconds % 8 == 0){
												updateProgressBar();
											}
										}else if (difficulty == 2){
											if (tMilliSeconds % 6 == 0){
												updateProgressBar();
											}
										} else if (difficulty == 3){
											if (tMilliSeconds % 3 == 0){
												updateProgressBar();
											}
										}
									}
								}
							}
						});
					}
				}, 0, 1);
			}
		}
	}
		
	@FXML
	public void init(ActionEvent event){
		if (initGame == false){
			System.out.println("difficulty = " + difficulty);
			if (difficulty == 0){
				progInterval = (10.0 / 100000);
				System.out.println("dif = 0 progInterval = " + progInterval);
			} else if (difficulty == 1){
				progInterval = (10.0 / 8000);
				System.out.println("dif = 1 progInterval = " + progInterval);
			} else {
				progInterval = (10.0 / 6000);
				System.out.println("dif = 2 progInterval = " + progInterval);
			}
			System.out.println("progInterval = " + progInterval);
			scoreBox.setText("0");
			Main.BOARDCLASSIC = new BoardClassic(boardSize, boardSize, false);
			loadBoard();
			if (Main.newGame == true){
				timer.schedule(new TimerTask() {
					public void run() {
						Platform.runLater(new Runnable() {
							public void run() {
								++realTime;
								if (realTime % 1000 == 0){
									//System.out.println(realTime);
								}
								if (realTime > 4){
									if (Main.PAUSE != true){
										++tMilliSeconds;
										if(tMilliSeconds % 1000 == 0){
											++tSeconds;
											update();
										}
										if (difficulty == 0){
											if (tMilliSeconds % 10 == 0){
												updateProgressBar();
											}
										} else if (difficulty == 1){
											if (tMilliSeconds % 8 == 0){
												updateProgressBar();
											}
										}else if (difficulty == 2){
											if (tMilliSeconds % 6 == 0){
												updateProgressBar();
											}
										} else if (difficulty == 3){
											if (tMilliSeconds % 3 == 0){
												updateProgressBar();
											}
										}
									}
								}
							}
						});
					}
				}, 0, 1);
			}
		}
	}
	
	@FXML
	private void updateProgressBar(){
		progressBar.setProgress(progress);
		if (Main.PAUSE == true){
			//System.out.println("game paused update PGBar");
		} else {
			if ((progress -= (progInterval)) <= 0.0) {
				//System.out.println("resetBar : timeDelay : tSeconds " + resetBar + " : " + timeDelay + " : " + tSeconds); 
				if (!resetBar){
					resetBar = true;
					++aiMoves;
					if (aiMoves > 4){
						//end game
						Main.PAUSE = true;
						gameOver();
					}
					timeDelay = tSeconds + 5;
					progress = -1;
				}
				if ((timeDelay - tSeconds) <= 0) {
					progress = 1.0;
					resetBar = false;
				} 
			} else {
				progressBar.setProgress(progress -= progInterval);
			}
			
			if (progress < .5){
	
			}
		}
	}
	
	private void loadBoard(){
		for (int x = 0; x < (boardSize); ++x){
			for (int y = 0; y < boardSize; ++y){
				Tile holder = Main.BOARDCLASSIC.getTileAt(x,y);
				System.out.println(" load tile images : tile location : id before set tile  " + x + "  : " + y + "  : " +
						holder.X() + "  :  " + holder.Y() + "  : " + holder.positionID);
				setBoardTiles(holder, false, false, 0.0);
				System.out.println(" load tile images : tile location : id after set tile " + x + "  : " + y + "  : " + 
						holder.X() + "  :  " + holder.Y() + "  : " + holder.positionID);
			}
		}
		
		helper.SetMatch(Main.BOARDCLASSIC.findMatch());
	}
	
	@FXML
	public void update() {
		//System.out.println("Game linker update time : " + tMilliSeconds);
		
		helper.Update(tMilliSeconds);
		
		if(helper.ShouldGlow())
		{
		    setBoardTiles(helper.Glow(), false, false, 0.5);   
		}
	}
	
	// First tile selected glows
	// records user selected tiles
	// when three are selected send to game logic to determine a match
	private void selectTile(ImageView iV, int x, int y) {
		if (Main.PAUSE == true){
			//System.out.println("select tile game paused");
		} else {
		String id;
		boolean found = false;//, glow = false;
		InnerShadow inShad = new InnerShadow();
		inShad.setRadius(62);
		inShad.setWidth(125);
		inShad.setColor(Color.web("0x999300"));
		System.out.println("first :: second :: third are they null?" + (first==null) + " :: " + (second==null) + " :: " +
				(third==null));
		System.out.println("one :: two :: three are they null?" + (one==null) + " :: " + (two==null) + " :: " +
				(three==null));

		id = Main.BOARDCLASSIC.getTileAt(x, y).positionID;
		
		System.out.println("name of tile " + Main.BOARDCLASSIC.getTileAt(x, y).getPositionID());

		if (first != null) {
			System.out.println("first != null\n one :: id" + one.getId() + " :: " + id);
			if (first.equalsIgnoreCase(id)) {
				System.out.println("first unglow");
				one.setEffect(new Glow(0.0));;
				//setBoardTiles(Main.BOARDCLASSIC.getTileAt(x, y), false, false, 0.0);
				first = null;
				found = true;
				iV = null;
				onePosX = -1;
				onePosY = -1;
			}
		}
		
		if (second != null) {
			System.out.println("second != null\n two :: id" + two.getId() + " :: " + id);
			if (second.equalsIgnoreCase(id)) {
				System.out.println("second unglow");
				two.setEffect(new Glow(0.0));
				//setBoardTiles(Main.BOARDCLASSIC.getTileAt(x, y), false, false, 0.0);
				second = null;
				found = true;
				iV = null;
				twoPosX = -1;
				twoPosY = -1;
			}
		}

		if (third != null) {
			System.out.println("three != null\n three :: id" + three.getId() + " :: " + id);
			if (third.equalsIgnoreCase(id)) {
				System.out.println("third unglow");
				three.setEffect(new Glow(0.0));
				//setBoardTiles(Main.BOARDCLASSIC.getTileAt(x, y), false, false, 0.0);
				third = null;
				found = true;
				iV = null;
				threePosX = -1;
				threePosY = -1;
			}
		}

		if (!found) {
			System.out.println("!found");
			if (first == null) {
				System.out.println("first = null");
				first = id;
				one = iV;
				iV = null;
				onePosX = x;
				onePosY = y;
			}else if (second == null) {
				System.out.println("second = null");
				second = id;
				two = iV;
				iV = null;
				twoPosX = x;
				twoPosY = y;
			}else if (third == null) {
				System.out.println("third = null");
				third = id;
				three = iV;
				iV = null;
				threePosX = x;
				threePosY = y;
			}
		}

		if (one != null) {
			System.out.println("fisrt " + one.getId());
		}
		if (two != null) {
			System.out.println("second " + two.getId());
		}
		if (three != null) {
			System.out.println("third " + three.getId());
		}

		
		if (first != null) {
			//one.setEffect(new Glow(glowIntensity));
			System.out.println("inner shadow first");
			//setBoardTiles(Main.BOARDCLASSIC.getTileAt(x, y), true, false, 0.0);
			one.setEffect(inShad);
			
		}
		if (second != null) {
			//two.setEffect(new Glow(glowIntensity));
			System.out.println("inner shadow second");
			//setBoardTiles(Main.BOARDCLASSIC.getTileAt(x, y), false, true, 0.0);
			two.setEffect(inShad);
		}
		if (third != null) {
			//three.setEffect(new Glow(glowIntensity));
			
		}
		
		System.out.println("BEFORE MATCH\nfirst :: second :: third are they null?" + (first==null) + " :: " + (second==null) + " :: " +
				(third==null));
		System.out.println("one :: two :: three are they null?" + (one==null) + " :: " + (two==null) + " :: " +
				(three==null));
		if (first != null && second != null && third != null){
			//check match
			System.out.println(" before match = " + match);
			System.out.println("onePos x : y " + onePosX + "  :  " + onePosY);
			System.out.println("twoPos x : y " + twoPosX + "  :  " + twoPosY);
			System.out.println("threePos x : y " + threePosX + "  :  " + threePosY);
			try {
				match = Main.BOARDCLASSIC.checkSet(
						Main.BOARDCLASSIC.getTileAt(onePosX, onePosY), 
						Main.BOARDCLASSIC.getTileAt(twoPosX, twoPosY), 
						Main.BOARDCLASSIC.getTileAt(threePosX, threePosY));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("after match = " + match);
			if (match){
				System.out.println("match is true");
				loadBoard();
				glowIntensity = 0.0;
				first = null;
				one.setEffect(new Glow(glowIntensity));
				one = null;
				onePosX = -1;
				onePosY = -1;
				second = null;
				two.setEffect(new Glow(glowIntensity));
				two = null;
				twoPosX = -1;
				twoPosY = -1;
				third = null;
				three.setEffect(new Glow(glowIntensity));
				three = null;
				threePosX = -1;
				threePosY = -1;
				match = false;
				glowIntensity = 0.8;
				System.out.println("Score " + Main.SCORE + " ");
				double moveScore = 100 + ( 1000 * (progress));
				Main.SCORE += 100 + ( 1000 * (progress));
				System.out.print( moveScore  + "  score after " + Main.SCORE);
				scoreBox.setText("" + Main.SCORE);
				progress = 1.0;
				if (Main.SCORE > 75000){
					
					difficulty = 3;
					System.out.println("difficulty 1 set " + difficulty);
				} else if (Main.SCORE > 50000){
					
					difficulty = 2;
					System.out.println("difficulty 2 set " + difficulty);
				} else if (Main.SCORE > 25000){
					difficulty = 1;
					System.out.println("difficulty 2 set " + difficulty);
				}
				++moves;
			} else {
				System.out.println("match is not true");
				loadBoard();
				glowIntensity = 0.0;
				first = null;
				one.setEffect(new Glow(glowIntensity));
				one = null;
				onePosX = -1;
				onePosY = -1;
				second = null;
				two.setEffect(new Glow(glowIntensity));
				two = null;
				twoPosX = -1;
				twoPosY = -1;
				third = null;
				three.setEffect(new Glow(glowIntensity));
				three = null;
				threePosX = -1;
				threePosY = -1;
				match = false;
				glowIntensity = 0.8;
				System.out.println("Score " + Main.SCORE + " ");
				double moveScore = 100 + ( 1000 * (progress));
				Main.SCORE -= 100 + ( 1000 * (1 - progress));
				if (Main.SCORE < 0){
					Main.SCORE = 0;
				}
				System.out.print( moveScore  + "  score after " + Main.SCORE);
				scoreBox.setText("" + Main.SCORE);
			}
		}
		System.out.println("AFTER MATCH RESET\nfirst :: second :: third are they null?" + (first==null) + " :: " + (second==null) + " :: " +
				(third==null));
		}
	}

	// if glow is true then the selected tile is glowed unless it has already been glowed by user.
	public void setBoardTiles(Tile tile, Boolean firstChoice, Boolean secondChoice, double intensity) {
		int pos = (tile.X() * 10) + tile.Y();
		int y = tile.Y();
		int x = tile.X();
		System.out.println("set board tiles xy : x : y " + pos + " : " + x + " : " + y);
		//double intensity = 0.5;
	
		InnerShadow firstTile = new InnerShadow();
		firstTile.setRadius(62);
		firstTile.setWidth(125);
		firstTile.setColor(Color.web("0x999300"));

		InnerShadow secondTile = new InnerShadow();
		secondTile.setRadius(62);
		secondTile.setWidth(125);
		secondTile.setColor(Color.web("0x999300"));
	
		switch (pos) {
		case 00:
			zeroZero.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("zeroZero");
			//System.out.println("00 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				zeroZero.setEffect(firstTile);
			} else if (secondChoice == true){
				zeroZero.setEffect(secondTile);
			} else {
				zeroZero.setEffect(new Glow(intensity));
			}
			break;
		case 01:
			zeroOne.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("zeroOne");
			//System.out.println("01 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				zeroOne.setEffect(firstTile);
			} else if (secondChoice == true){
				zeroOne.setEffect(secondTile);
			} else {
				zeroOne.setEffect(new Glow(intensity));
			}
			break;
		case 02:
			zeroTwo.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("zeroTwo");
			//System.out.println("02 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				zeroTwo.setEffect(firstTile);
			} else if (secondChoice == true){
				zeroTwo.setEffect(secondTile);
			} else {
				zeroTwo.setEffect(new Glow(intensity));
			}
			break;
		case 03:
			zeroThree.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("zeroThree");
			//System.out.println("03 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				zeroThree.setEffect(firstTile);
			} else if (secondChoice == true){
				zeroThree.setEffect(secondTile);
			} else {
				zeroThree.setEffect(new Glow(intensity));
			}
			break;
		case 10:
			oneZero.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("oneZero");
			//System.out.println("10 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				oneZero.setEffect(firstTile);
			} else if (secondChoice == true){
				oneZero.setEffect(secondTile);
			} else {
				oneZero.setEffect(new Glow(intensity));
			}
			break;
		case 11:
			oneOne.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("oneOne");
			//System.out.println("11 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				oneOne.setEffect(firstTile);
			} else if (secondChoice == true){
				oneOne.setEffect(secondTile);
			} else {
				oneOne.setEffect(new Glow(intensity));
			}
			break;
		case 12:
			oneTwo.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("oneTwo");
			//System.out.println("12 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				oneTwo.setEffect(firstTile);
			} else if (secondChoice == true){
				oneTwo.setEffect(secondTile);
			} else {
				oneTwo.setEffect(new Glow(intensity));
			}
			break;
		case 13:
			oneThree.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("oneThree");
			//System.out.println("13 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				oneThree.setEffect(firstTile);
			} else if (secondChoice == true){
				oneThree.setEffect(secondTile);
			} else {
				oneThree.setEffect(new Glow(intensity));
			}
			break;
		case 20:
			twoZero.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("twoZero");
			//System.out.println("20 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				twoZero.setEffect(firstTile);
			} else if (secondChoice == true){
				twoZero.setEffect(secondTile);
			} else {
				twoZero.setEffect(new Glow(intensity));
			}
			break;
		case 21:
			twoOne.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("twoOne");
			//System.out.println("21 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				twoOne.setEffect(firstTile);
			} else if (secondChoice == true){
				twoOne.setEffect(secondTile);
			} else {
				twoOne.setEffect(new Glow(intensity));
			}
			break;
		case 22:
			twoTwo.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("twoTwo");
			//System.out.println("22 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				twoTwo.setEffect(firstTile);
			} else if (secondChoice == true){
				twoTwo.setEffect(secondTile);
			} else {
				twoTwo.setEffect(new Glow(intensity));
			}
			break;
		case 23:
			twoThree.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("twoThree");
			//System.out.println("23 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				twoThree.setEffect(firstTile);
			} else if (secondChoice == true){
				twoThree.setEffect(secondTile);
			} else {
				twoThree.setEffect(new Glow(intensity));
			}
			break;
		case 30:
			threeZero.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("threeZero");
			//System.out.println("30 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				threeZero.setEffect(firstTile);
			} else if (secondChoice == true){
				threeZero.setEffect(secondTile);
			} else {
				threeZero.setEffect(new Glow(intensity));
			}
			break;
		case 31:
			threeOne.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("threeOne");
			//System.out.println("31 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				threeOne.setEffect(firstTile);
			} else if (secondChoice == true){
				threeOne.setEffect(secondTile);
			} else {
				threeOne.setEffect(new Glow(intensity));
			}
			break;
		case 32:
			threeTwo.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("threeTwo");
			//System.out.println("32 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				threeTwo.setEffect(firstTile);
			} else if (secondChoice == true){
				threeTwo.setEffect(secondTile);
			} else {
				threeTwo.setEffect(new Glow(intensity));
			}
			break;
		case 33:
			threeThree.setImage(tile.getTexture());
			Main.BOARDCLASSIC.getTileAt(x, y).setPositionID("threeThree");
			//System.out.println("33 board id = " + tile.X() + tile.Y());
			if (firstChoice == true){
				threeThree.setEffect(firstTile);
			} else if (secondChoice == true){
				threeThree.setEffect(secondTile);
			} else {
				threeThree.setEffect(new Glow(intensity));
			}
			break;
		}
	}
	
	

	@FXML
	private void zeroZero(MouseEvent event) {
		selectTile(zeroZero, 0, 0);
		event.consume();
	}

	@FXML
	private void zeroOne(MouseEvent event) {
		selectTile(zeroOne, 0, 1);
		event.consume();
	}

	@FXML
	private void zeroTwo(MouseEvent event) {
		selectTile(zeroTwo, 0, 2);
		event.consume();
	}

	@FXML
	private void zeroThree(MouseEvent event) {
		selectTile(zeroThree, 0, 3);
		event.consume();
	}

	@FXML
	private void oneZero(MouseEvent event) {
		selectTile(oneZero, 1, 0);
		event.consume();
	}

	@FXML
	private void oneOne(MouseEvent event) {
		selectTile(oneOne, 1, 1);
	}

	@FXML
	private void oneTwo(MouseEvent event) {
		selectTile(oneTwo, 1, 2);
		event.consume();
	}

	@FXML
	private void oneThree(MouseEvent event) {
		selectTile(oneThree, 1, 3);
		event.consume();
	}

	@FXML
	private void twoZero(MouseEvent event) {
		selectTile(twoZero, 2, 0);
		event.consume();
	}

	@FXML
	private void twoOne(MouseEvent event) {
		selectTile(twoOne, 2, 1);
		event.consume();
	}

	@FXML
	private void twoTwo(MouseEvent event) {
		selectTile(twoTwo, 2, 2);
		event.consume();
	}

	@FXML
	private void twoThree(MouseEvent event) {
		selectTile(twoThree, 2, 3);
		event.consume();
	}

	@FXML
	private void threeZero(MouseEvent event) {
		selectTile(threeZero, 3, 0);
		event.consume();
	}

	@FXML
	private void threeOne(MouseEvent event) {
		selectTile(threeOne, 3, 1);
		event.consume();
	}

	@FXML
	private void threeTwo(MouseEvent event) {
		selectTile(threeTwo, 3, 2);
		event.consume();
	}

	@FXML
	private void threeThree(MouseEvent event) {
		selectTile(threeThree, 3, 3);
		event.consume();
	}
	
}
