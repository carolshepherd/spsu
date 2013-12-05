package application;

//https://forums.oracle.com/message/10089405
import java.io.IOException;
import java.util.Stack;

import application.logic.BoardClassic;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	final 	static String 					GAMEBOARD 			= "gameBoard";
	final 	static String 					GAMEBOARD_FXML 		= "gameBoard2.fxml";
	final 	static String 					START_MENU 			= "startMenu";
	final 	static String 					START_MENU_FXML		= "startMenu.fxml";
	final 	static String 					EXIT_MENU 			= "exit";
	final 	static String 					EXIT_MENU_FXML 		= "exitMenu.fxml";
			static Boolean					PAUSE				= false;
			static Boolean					newGame				= true;
			static BoardClassic				BOARDCLASSIC;
			static int						SCORE				= 0;
			static Boolean					DEMO				= false;
			static Group					root;
			static ExitMenuController		EMC;
			public static GameBoardController		GBC;
			static StartMenuController		SMC;
			
			

	@Override
	public void start(Stage primaryStage) {
		boolean gameLoad, startLoad, exitLoad;
		ScreensController mainContainer = new ScreensController();
		System.out.println("toStringcall::: " + mainContainer.toString());
		
		gameLoad 	= mainContainer.loadScreen(Main.GAMEBOARD, Main.GAMEBOARD_FXML);
		startLoad	= mainContainer.loadScreen(Main.START_MENU, Main.START_MENU_FXML);
		exitLoad 	= mainContainer.loadScreen(Main.EXIT_MENU, Main.EXIT_MENU_FXML);
		
		System.out.println("toStringcall::: " + mainContainer.toString());
		System.out.println("game loaded? " + gameLoad + " startLoaded? " + startLoad + " exitLoaded? " + exitLoad);
		
		mainContainer.setScreen(Main.START_MENU);

		root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
		System.out.println("start init");
	}

}
