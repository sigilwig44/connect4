package connect4;

public class Connect4Player {
	
	private final int MAXPLAYER = 2;
	
	private final int MINPLAYER = 1;
	
	public Connect4 makeMove(Connect4 gameState, int depth)
	{
		// If the game is over or we have reached maximum depth, return the current state
	    if (gameState.checkForWin() != 0 || depth == 0) {
	        return gameState;
	    }
	    
	    // Initialize the best score to the opposite of the current player's score
	    int bestScore = gameState.getPlayer() == MAXPLAYER ? Integer.MIN_VALUE : Integer.MAX_VALUE;
	    
	    // Initialize the best move to an invalid move
	    Integer bestMove = null;
	    
	    // Loop over all possible moves
	    for (int move : gameState.getPossibleMoves()) {
	    	// Create a new copy of the game state with the move applied
	        Connect4 newState = new Connect4(gameState);
	        newState.addChip(move);
	    	
	        // Recursively evaluate the new state with the opposite player
	        Connect4 resultState = makeMove(newState, depth - 1);
	        
	        // Update the best score and best move based on the current player's turn
	        int score = resultState.getScore();
	        if (gameState.getPlayer() == MAXPLAYER && score > bestScore) {
	            bestScore = score;
	            bestMove = move;
	        } else if (gameState.getPlayer() == MINPLAYER && score < bestScore) {
	            bestScore = score;
	            bestMove = move;
	        }
	    }
	    
	    // If no valid move was found, return the current state
	    if (bestMove == null) {
	        return gameState;
	    }
	    
	    // Return the new state with the best move applied
	    Connect4 newState = new Connect4(gameState);
	    newState.addChip(bestMove);
	    return newState;
	}
}