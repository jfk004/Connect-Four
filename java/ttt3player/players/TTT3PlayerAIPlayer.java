package ttt3player.players;

// Jessica Theodore CSC350 Question 8

import java.util.ArrayList;

import ttt3player.mvc.TTT3PlayerModel;

public class TTT3PlayerAIPlayer extends TTT3PlayerAbstractPlayer {
	TTT3PlayerModel model;
	char symbol;
	int playerNumber;
	
	public TTT3PlayerAIPlayer(TTT3PlayerModel model, char symbol){
		this.model = model;
		this.symbol = symbol;
		switch(symbol){
			case 'X':
				this.playerNumber = 0;
				break;
			case 'O':
				this.playerNumber = 1;
				break;
			case '+':
				this.playerNumber = 2;
				break;
			default:
				throw new IllegalArgumentException("Invalid player symbol");
		}
	}
	
	// Assume actions are numbered 1-16
	public char[][] result(char[][] state, int action){
		// Deep copy the state
		char[][] newstate = new char[4][4];
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				newstate[row][col] = state[row][col];
		
		char turn = this.getTurn(state);
		
		action -= 1;
		int col = action % 4;
		int row = action / 4;
		newstate[row][col] = turn;
		
		return newstate;
	}
	
	public int[] actions(char[][] state){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				if(state[row][col] == '-')
					moves.add(row*4+col+1);
		
		int[] results = new int[moves.size()];
		for(int i=0; i<results.length; i++)
			results[i] = moves.get(i);
		
		return results;
	}
	
	public boolean terminalTest(char[][] state){
		// Check for horizontal win
		for(int row=0; row<4; row++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[row][startcol] != '-' && state[row][startcol] == state[row][startcol+1] && state[row][startcol] == state[row][startcol+2])
					return true;
			}
		}
		// Check for vertical win
		for(int col=0; col<4; col++){
			for(int startrow=0; startrow<2; startrow++){
				if(state[startrow][col] != '-' && state[startrow][col] == state[startrow+1][col] && state[startrow][col] == state[startrow+2][col])
					return true;
			}
		}
		// Check for diagonal \ win
		for(int startrow=0; startrow<2; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[startrow][startcol] != '-' && state[startrow][startcol] == state[startrow+1][startcol+1] && state[startrow][startcol] == state[startrow+2][startcol+2])
					return true;
			}
		}
		for(int startrow=2; startrow<4; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[startrow][startcol] != '-' && state[startrow][startcol] == state[startrow-1][startcol+1] && state[startrow][startcol] == state[startrow-2][startcol+2])
					return true;
			}
		}
		
		return isDraw(state);
	}
	
	public int[] utility(char[][] state){
		char winningSymbol = getWinner(state);
		if(winningSymbol == symbol){
			int[] utilResult = {-1000,-1000,-1000};
			utilResult[playerNumber] = 1000;
			return utilResult;
		}
		else if(winningSymbol != '-'){
			if(winningSymbol == 'X')
				return new int[] {1000, -1000, -1000};
			if(winningSymbol == 'O')
				return new int[] {-1000, 1000, -1000};
			if(winningSymbol == '+')
				return new int[] {-1000, -1000, 1000};
		}	
		else if(isDraw(state))
			return new int[] {0,0,0};
		
		return new int[] {-1,-1,-1}; //should not happen
	}
	
	protected boolean isDraw(char[][] state){
		boolean allFilled = true;
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				if(state[row][col] == '-')
					allFilled = false;
		return allFilled;
	}
	
	protected char getWinner(char[][] state){
		// Check for horizontal win
		for(int row=0; row<4; row++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[row][startcol] != '-' && state[row][startcol] == state[row][startcol+1] && state[row][startcol] == state[row][startcol+2])
					return state[row][startcol];
			}
		}
		// Check for vertical win
		for(int col=0; col<4; col++){
			for(int startrow=0; startrow<2; startrow++){
				if(state[startrow][col] != '-' && state[startrow][col] == state[startrow+1][col] && state[startrow][col] == state[startrow+2][col])
					return state[startrow][col];
			}
		}
		// Check for diagonal \ win
		for(int startrow=0; startrow<2; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[startrow][startcol] != '-' && state[startrow][startcol] == state[startrow+1][startcol+1] && state[startrow][startcol] == state[startrow+2][startcol+2])
					return state[startrow][startcol];
			}
		}
		for(int startrow=2; startrow<4; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(state[startrow][startcol] != '-' && state[startrow][startcol] == state[startrow-1][startcol+1] && state[startrow][startcol] == state[startrow-2][startcol+2])
					return state[startrow][startcol];
			}
		}
		
		return '-'; // Should not happen
	}
	
	protected char getTurn(char[][] state){
		int empties = 0;
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				if(state[row][col] == '-')
					empties++;
		
		if(empties%3 == 1)
			return 'X';
		else if(empties%3 == 0)
			return 'O';
		else
			return '+';
	}
	
	public int getMove() {
		// Start the minimax search with depth 0
		return minimax(model.getGrid(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
	}
	
	private MinimaxResult minimax(char[][] state, int depth, int alpha, int beta) {
		// If the game is over or we've reached the maximum depth, return the utility of the state
		if (terminalTest(state) || depth == 5) {
			int[] util = utility(state);
			return new MinimaxResult(util[playerNumber], -1);  // Return -1 for terminal states
		}
	
		// Get all possible moves
		int[] validMoves = actions(state);
		if (validMoves.length == 0) {
			return new MinimaxResult(0, -1);  // No valid moves left, return -1
		}
	
		// The best result we're looking for, initialized to the worst case for this player
		int bestValue = (playerNumber == 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int bestMove = -1;
	
		// Explore all possible moves
		for (int move : validMoves) {
			// Simulate the move
			char[][] newState = result(state, move);
	
			// Recursively call minimax on the new state
			MinimaxResult result = minimax(newState, depth + 1, alpha, beta);
	
			// Update best move for current player
			if (playerNumber == 0) {  // Maximize for 'X'
				if (result.utility > bestValue) {
					bestValue = result.utility;
					bestMove = move;
				}
				alpha = Math.max(alpha, bestValue);
			} else if (playerNumber == 1) {  // Maximize for 'O'
				if (result.utility > bestValue) {
					bestValue = result.utility;
					bestMove = move;
				}
				alpha = Math.max(alpha, bestValue);
			} else {  // Maximize for '+'
				if (result.utility > bestValue) {
					bestValue = result.utility;
					bestMove = move;
				}
				alpha = Math.max(alpha, bestValue);
			}
	
			// If the best move has been found, no need to explore further
			if (beta <= alpha) {
				break;
			}
		}
	
		// If no valid move has been found, return a default (fallback) valid move
		if (bestMove == -1) {
			bestMove = validMoves[0];  // Default to the first valid move if no move found
		}
	
		return new MinimaxResult(bestValue, bestMove);
	}
	
	// Helper class to store the result of the minimax algorithm
	private class MinimaxResult {
		int utility;
		int move;
	
		MinimaxResult(int utility, int move) {
			this.utility = utility;
			this.move = move;
		}
	}
	
	
}
