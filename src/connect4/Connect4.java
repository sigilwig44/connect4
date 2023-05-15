package connect4;

import java.util.ArrayList;

public class Connect4 {

	/** Two dimensional board */
	private int[][] board;
	
	/** Empty spot */
	public static int EMPTY = 0;

	/** Number of columns */
	public static int COLS = 7;
	
	/** Number of rows */
	public static int ROWS = 6;
	
	/** Number of spaces remaining */
	private int numberSpacesRemaining;
	
	/** Current player */
	private int player;
	
	/** Last updated row */
	private int lastRow;
	
	/** Last updated col */
	private int lastCol;

	/**
	 * Default constructor
	 */
	public Connect4()
	{
		board = new int[ROWS][COLS];
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				board[row][col] = EMPTY;
			}
		}
		numberSpacesRemaining = ROWS * COLS;
		player = 1;
	}
	
	/**
	 * Copy constructor
	 * @param original game to be copied
	 */
	public Connect4(Connect4 original) {
		board = new int[ROWS][COLS];
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				board[row][col] = original.getBoard()[row][col];
			}
		}
		numberSpacesRemaining = original.getSpacesRemaining();
		this.player = original.getPlayer();
		lastRow = original.getLastRow();
		lastCol = original.getLastCol();
	}

	/**
	 * Function to place chip in a particular spot by specifying row and column
	 * @param row The row number of the board
	 * @param col The column number of the board
	 * @param the player whose turn it currently is
	 */
	public void addChip(int col)
	{
		int row = 0;
		for(; row < ROWS; row++) {
			if(board[row][col] == EMPTY) break;
		}
		// Add item to board
		board[row][col] = player;
		numberSpacesRemaining--;
		newTurn();
		lastRow = row;
		lastCol = col;
	}

	/**
	 * Getter for the board
	 * @return The current board
	 */
	public int[][] getBoard()
	{
		return board;
	}
	
	/**
	 * Tests whether a chip can be placed in a certain space
	 * @param row of the space to be tested
	 * @param col of the space to be tested
	 * @param player who is placing the chip
	 * @return true if the move is valid, false otherwise
	 */
	public boolean isValidMove(int col) {
		for(int row = 0; row < ROWS; row++) {
			if(board[row][col] == EMPTY) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the current player
	 * @return 1 for player 1, 2 for player 2
	 */
	public int getPlayer() {
		return player;
	}
	
	/**
	 * Getter for numberSpacesRemaining
	 * @return number of spaces remaining
	 */
	public int getSpacesRemaining() {
		return numberSpacesRemaining;
	}
	
	/**
	 * Getter for last updated row
	 * @return lastRow
	 */
	public int getLastRow() {
		return lastRow;
	}
	
	/**
	 * Getter for last updated column
	 * @return lastCol
	 */
	public int getLastCol() {
		return lastCol;
	}
	
	/**
	 * Switches the current player to be the other player
	 */
	private void newTurn() {
		player = player == 1 ? 2 : 1;
	}
	
	public ArrayList<Integer> getPossibleMoves() {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		for(int col = 0; col < COLS; col++) {
			for(int row = 0; row < ROWS; row++) {
				if(board[row][col] == EMPTY) {
					possibleMoves.add(col);
					break;
				}
			}
		}
		return possibleMoves;
	}
	
	/**
	 * Method that checks if a player has won
	 * @return 1 if player 1 won, 2 if player 2 won, 0 if there is no winner, and -1 if there is a tie
	 */
	public int checkForWin()
	{
		if((board[lastRow][lastCol] == 1 || board[lastRow][lastCol] == 2)) {
			if(checkVerticalWins() || checkHorizonalWins() || checkDiagonalWins()) {
				return board[lastRow][lastCol];
			}
		}
		
		if(numberSpacesRemaining == 0) return -1;
		return 0;
	}
	
	/**
	 * Check for wins in the vertical direction
	 * @return true if there is a win
	 */
	public boolean checkVerticalWins() {
		int consecutive = 1;
		int player = board[lastRow][lastCol];
		for(int i = 1; i < 4; i++) {
			if((lastRow + i) < ROWS) {
				if(board[lastRow+i][lastCol] == player) {
					consecutive++;
				} else {
					break;
				}
			}
		}
		for(int i = 1; i < 4; i++) {
			if((lastRow - i) >= 0) {
				if(board[lastRow-i][lastCol] == player) {
					consecutive++;
				}
				else {
					break;
				}
			}
		}
		if(consecutive >= 4) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check for wins in the horizontal direction
	 * @return true if there is a win
	 */
	public boolean checkHorizonalWins() {
		int consecutive = 1;
		int player = board[lastRow][lastCol];
		for(int i = 1; i < 4; i++) {
			if((lastCol + i) < COLS) {
				if(board[lastRow][lastCol+i] == player) {
					consecutive++;
				} else {
					break;
				}
			}
		}
		for(int i = 1; i < 4; i++) {
			if((lastCol - i) >= 0) {
				if(board[lastRow][lastCol-i] == player) {
					consecutive++;
				}
				else {
					break;
				}
			}
		}
		if(consecutive >= 4) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check for wins in the diagonal direction
	 * @return true if there is a win
	 */
	public boolean checkDiagonalWins() {
		int consecutive = 1;
		int player = board[lastRow][lastCol];
		for(int i = 1; i < 4; i++) {
			if((lastRow + i) < ROWS && (lastCol + i) < COLS) {
				if(board[lastRow+i][lastCol+i] == player) {
					consecutive++;
				} else {
					break;
				}
			}
		}
		for(int i = 1; i < 4; i++) {
			if((lastRow - i) >= 0 && (lastCol - i) >= 0) {
				if(board[lastRow-i][lastCol-i] == player) {
					consecutive++;
				}
				else {
					break;
				}
			}
		}
		if(consecutive >= 4) {
			return true;
		}
		consecutive = 1;
		for(int i = 1; i < 4; i++) {
			if((lastRow - i) >= 0 && (lastCol + i) < COLS) {
				if(board[lastRow-i][lastCol+i] == player) {
					consecutive++;
				} else {
					break;
				}
			}
		}
		for(int i = 1; i < 4; i++) {
			if((lastRow + i) < ROWS && (lastCol - i) >= 0) {
				if(board[lastRow+i][lastCol-i] == player) {
					consecutive++;
				}
				else {
					break;
				}
			}
		}
		if(consecutive >= 4) {
			return true;
		}
		return false;
	}
	
	/**
	 * Scores a specific Connect4 Game
	 * @return score for the game
	 */
	public int getScore() {
		int winner = checkForWin();
		if(winner == 0) {
			int totalScore = 0;
			for(int col = 0; col < COLS; col++) {
				for(int row = 0; row < ROWS; row++) {
					if(board[row][col] == EMPTY) {
						// Add weight to the max player's scores to weight the AI to be defensive
						totalScore += getVerticalScore(row, col, Connect4Player.MAXPLAYER)*1.2;
						totalScore += getHorizontalScore(row, col, Connect4Player.MAXPLAYER)*1.2;
						totalScore += getDiagonalScore(row, col, Connect4Player.MAXPLAYER)*1.2;
						totalScore -= getVerticalScore(row, col, Connect4Player.MINPLAYER);
						totalScore -= getHorizontalScore(row, col, Connect4Player.MINPLAYER);
						totalScore -= getDiagonalScore(row, col, Connect4Player.MINPLAYER);
						break;
					}
				}
			}
			return totalScore;
		} else if(winner == Connect4Player.MAXPLAYER){
			return 1000;
		} else if(winner == Connect4Player.MINPLAYER) {
			return -1000;
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the score on the vertical
	 * @param row of the space to calculate score for
	 * @param col of the space to calculate score for
	 * @param player to calculate score for
	 * @return vertical score
	 */
	public int getVerticalScore(int row, int col, int player) {
		int verticalScore = 0;
		for(int currentRow = row - 1; currentRow >= 0; currentRow--) {
			if(board[currentRow][col] == player) {
				verticalScore++;
			}
			else {
				break;
			}
		}
		
		if(verticalScore == 2) verticalScore = 4;
		else if(verticalScore == 3) verticalScore = 500;
		
		return verticalScore;
	}
	
	/**
	 * Gets the score on the horizontal
	 * @param row of the space to calculate score for
	 * @param col of the space to calculate score for
	 * @param player to calculate score for
	 * @return vertical score
	 */
	public int getHorizontalScore(int row, int col, int player) {
		int leftScore = 0;
		int rightScore = 0;
		int multiplier = 1;
		//Check to the left first
		for(int currentCol = col - 1; currentCol >= 0; currentCol--) {
			if(board[row][currentCol] == player) {
				leftScore++;
			}
			else if(board[row][currentCol] == EMPTY) {
				multiplier = 100;
			}
			else {
				break;
			}
		}
		
		
		//Check to the right next
		for(int currentCol = col + 1; currentCol < COLS; currentCol++) {
			if(board[row][currentCol] == player) {
				rightScore++;
			}
			else if(board[row][currentCol] == EMPTY) {
				multiplier = 100;
			}
			else {
				break;
			}
		}
		
		if(rightScore == 1) rightScore = 9;
		else if(rightScore == 2) rightScore = 400;
		else if(rightScore == 3) rightScore = 500 * multiplier;
		
		if(leftScore == 1) leftScore = 9;
		else if(leftScore == 2) leftScore = 400;
		else if(leftScore == 3) leftScore = 500 * multiplier;
		return leftScore * rightScore;
	}
	
	/**
	 * Gets the score on the diagonals
	 * @param row of the space to calculate score for
	 * @param col of the space to calculate score for
	 * @param player to calculate score for
	 * @return vertical score
	 */
	public int getDiagonalScore(int row, int col, int player) {
		int topLeftScore = 0;
		int topRightScore = 0;
		int bottomLeftScore = 0;
		int bottomRightScore = 0;
		
		boolean breakOut = false;
		for(int currentCol = col - 1; currentCol >= 0 && !breakOut; currentCol--) {
			for(int currentRow = row + 1; currentRow < ROWS && !breakOut; currentRow++) {
				if(board[currentRow][currentCol] == player) {
					topLeftScore++;
				}
				else {
					breakOut = true;
				}
			}
		}
		
		breakOut = false;
		for(int currentCol = col + 1; currentCol < COLS && !breakOut; currentCol++) {
			for(int currentRow = row + 1; currentRow < ROWS && !breakOut; currentRow++) {
				if(board[currentRow][currentCol] == player) {
					topRightScore++;
				}
				else {
					breakOut = true;
				}
			}
		}
		
		breakOut = false;
		for(int currentCol = col - 1; currentCol >= 0 && !breakOut; currentCol--) {
			for(int currentRow = row - 1; currentRow >= 0 && !breakOut; currentRow--) {
				if(board[currentRow][currentCol] == player) {
					bottomLeftScore++;
				}
				else {
					breakOut = true;
				}
			}
		}
		
		breakOut = false;
		for(int currentCol = col + 1; currentCol < COLS && !breakOut; currentCol++) {
			for(int currentRow = row - 1; currentRow >= 0 && !breakOut; currentRow--) {
				if(board[currentRow][currentCol] == player) {
					bottomRightScore++;
				}
				else {
					breakOut = true;
				}
			}
		}
		
		if(topLeftScore == 1) topLeftScore = 9;
		else if(topLeftScore == 2) topLeftScore = 200;
		else if (topLeftScore == 3) topLeftScore = 500;
		
		if(topRightScore == 1) topRightScore = 9;
		else if(topRightScore == 2) topRightScore = 200;
		else if (topRightScore == 3) topRightScore = 500;
		
		if(bottomLeftScore == 1) bottomLeftScore = 9;
		else if(bottomLeftScore == 2) bottomLeftScore = 200;
		else if (bottomLeftScore == 3) bottomLeftScore = 500;
		
		if(bottomRightScore == 1) bottomRightScore = 9;
		else if(bottomRightScore == 2) bottomRightScore = 200;
		else if (bottomRightScore == 3) bottomRightScore = 500;
		
		return topLeftScore * topRightScore * bottomLeftScore * bottomRightScore;
	}


}
