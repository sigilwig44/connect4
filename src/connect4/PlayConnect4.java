package connect4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlayConnect4 extends Application implements EventHandler<ActionEvent> {
	
	/** Connect4 game object which keeps track of game state */
	private Connect4 game;
	
	private Connect4Player aiAgent = new Connect4Player();
	
	/** Array of buttons displayed in the Connect 4 GUI*/
	private Button[][] space = new Button[6][7];
	
	/** Reset button */
	private Button reset = new Button("Reset");
	
	/** Message TextArea which displays instructions to the user */
	private static TextArea message = new TextArea();
	
	/** Keeps track of whether the game is in progress and the winner */
	private static int gameOver = 0;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		game = new Connect4();
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #1531ff");
		Scene scene = new Scene(root, 500, 470);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Connect 4");
				
		message.setMinSize(425, 28);
		message.setMaxSize(425, 28);
		message.setEditable(false);
		message.setWrapText(true);
		message.setText("Player " + game.getPlayer() + "'s turn!");
		reset.setMinSize(75, 28);
		reset.setMaxSize(75, 28);
		reset.setOnAction(this);
		
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		bottom.getChildren().add(message);
		bottom.getChildren().add(reset);
		root.setBottom(bottom);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
		
		for(int row = 0; row < Connect4.ROWS; row++) {
			for(int col = 0; col < Connect4.COLS; col++) {
				space[row][col] = new Button();
				space[row][col].setMinSize(100, 100);
				space[row][col].setOnAction(this);
				space[row][col].setStyle(
						"-fx-background-color: #e4e4e4; " +
						"-fx-background-radius: 5em; " +
		                "-fx-min-width: 60px; " +
		                "-fx-min-height: 60px; " +
		                "-fx-max-width: 60px; " +
		                "-fx-max-height: 60px;"
				);
				GridPane.setColumnIndex(space[row][col], col);
				GridPane.setRowIndex(space[row][col], row);
				grid.add(space[row][col], col, Connect4.ROWS - row);
			}
		}
		
		root.setCenter(grid);
        primaryStage.show();
	}
	
	@Override
	public void handle(ActionEvent ev) {
		if(ev.getSource() == reset) {
			reset();
			return;
		}
		else if(gameOver == 0) {
			for(int row = 0; row < Connect4.ROWS; row++) {
				for(int col = 0; col < Connect4.COLS; col++) {
					if(ev.getSource() == space[row][col]) {
						if(game.isValidMove(col)) {
							game.addChip(col);
							updateDisplay(game.getLastRow(), game.getLastCol());
						}
						else {
							message.setText("Invalid move!");
						}
					}
				}
			}
		}
		gameOver = game.checkForWin();
		if(gameOver == 0) {
			message.setText("AI Player's Turn!");
			game = aiAgent.makeMove(game, 7);
			updateDisplay(game.getLastRow(), game.getLastCol());
			gameOver = game.checkForWin();
		}
		if (gameOver == 0) {
			message.setText("Player " + game.getPlayer() + "'s turn!");
		}
		else if (gameOver == -1) {
			message.setText("Game over! It's a tie!");
		}
		else {
			message.setText("Game over! Player " + gameOver + " wins!");
		}
	}
	
	
	/**
	 * Reset the game and start over
	 */
	public void reset() {
		game = new Connect4();
		gameOver = 0;
		message.setText("Player " + game.getPlayer() + "'s turn!");
		for(int row = 0; row < Connect4.ROWS; row++) {
			for(int col = 0; col < Connect4.COLS; col++) {
				updateDisplay(row, col);
			}
		}
	}
	
	/**
	 * Update all the buttons to display the correct color
	 * based on who occupies the space
	 */
	public void updateDisplay(int row, int col) {
		for(row = 0; row < Connect4.ROWS; row++) {
			for(col = 0; col < Connect4.COLS; col++) {
				if(game.getBoard()[row][col] == 1) {
					space[row][col].setStyle(
							"-fx-background-color: #e63326; " +
							"-fx-background-radius: 5em; " +
			                "-fx-min-width: 60px; " +
			                "-fx-min-height: 60px; " +
			                "-fx-max-width: 60px; " +
			                "-fx-max-height: 60px;"
					);
				}
				else if(game.getBoard()[row][col] == 2) {
					space[row][col].setStyle(
							"-fx-background-color: #f5d80c; " +
							"-fx-background-radius: 5em; " +
			                "-fx-min-width: 60px; " +
			                "-fx-min-height: 60px; " +
			                "-fx-max-width: 60px; " +
			                "-fx-max-height: 60px;"
					);
				}
				else {
					space[row][col].setStyle(
							"-fx-background-color: #e4e4e4; " +
							"-fx-background-radius: 5em; " +
			                "-fx-min-width: 60px; " +
			                "-fx-min-height: 60px; " +
			                "-fx-max-width: 60px; " +
			                "-fx-max-height: 60px;"
					);
				}
			}
		}
	}
}
