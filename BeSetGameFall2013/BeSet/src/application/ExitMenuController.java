package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ExitMenuController implements Initializable, ControlledScreen{
	ScreensController myController;

	@FXML	BorderPane	exitMenu;
	@FXML	TextField	userName;
	@FXML	Label		userFeedback;
	@FXML   TextField   scoreBox;
	@FXML	Button		submitBtn;
	@FXML 	Button	  	quitBtn;
	@FXML	Button		playAgainBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("\n\nINIT EXIT MENU\n\n" + this.toString());
		scoreBox.setText("" + Main.SCORE);
		Main.EMC = this;
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		System.out.println("setScreenParent exit menu");
		myController = screenPage;
		setScore();
	}
	
	@FXML
	private void playAgain(){
		System.out.println("go to start menu");
		myController.setScreen(Main.START_MENU);
		Main.SCORE = 0;
		Main.GBC.count = 0;
		Main.GBC.scoreBox.setText("0");
		Main.GBC.aiScoreBox.setText("0");
		Main.newGame = false;
		Main.GBC.progress = 1.0;
		Main.PAUSE = true;
		Main.GBC.startBtn.setVisible(true);
	}
	
	@FXML
	public void setScore(){
		scoreBox.setText("" + Main.SCORE);
	}
	
	// exitMenu submit button sends the user entered name to this method;
	@FXML
	private void submitName(ActionEvent event) throws Exception{
		boolean accepted = false;
		
		String name = userName.getText();
		System.out.println("\n\n\n Name " + name);
		String score = "" + Main.SCORE;
		System.out.println("Score " + score);
		accepted = checkProfanity(name);
		if(accepted && Main.DEMO == true){
			System.out.println("Not demo send score");
			sendScore(name, score);
			playAgain();
		} else if (Main.DEMO == true){
			//send user error message
			System.out.println("Not demo bad name");
			userFeedback.setText("Your name was not accepted please enter new name");
			
		} else {
			System.out.println("demo don't send score");
			userFeedback.setText("Scores are not\naccepted in\nDemo mode");
			playAgain();
		}
	}
	
	// sends user entered name to profanity checker
	// returns true if the name is accepted
	// returns false if the name is profane
	private boolean checkProfanity(String userName) throws Exception{
		return addScore.checkName(userName);
	}
	
	// sends score to website returns true if the score is a high score
	// false if the score is not in the top ten.
	private boolean sendScore(String userName, String score) throws Exception{
		addScore.addScoreAndName(userName, score);
		return false;
	}

}
