package tictactoe;

import tictactoe.mvc.TicTacToeController;
import tictactoe.mvc.TicTacToeModel;
import tictactoe.players.TicTacToeAIPlayer;
import tictactoe.players.TicTacToeHumanPlayer;
import tictactoe.players.TicTacToePlayer;

public class TicTacToe {
	public static void main(String[] args) {
		playSingleGame();		
	}
	
	public static void playSingleGame(){
		TicTacToeModel m = new TicTacToeModel();
		
		// Human is X, AI is O
		TicTacToePlayer player1 = new TicTacToeAIPlayer(m, 'X');
		TicTacToePlayer player2 = new TicTacToeHumanPlayer(m, 'O');
		
		// AI is X, Human is O
		// TicTacToePlayer player1 = new TicTacToeAIPlayer(m, 'X');
		// TicTacToePlayer player2 = new TicTacToeHumanPlayer(m, 'O');
		
		// X player must be player 1
		TicTacToeController c = new TicTacToeController(m, player1, player2);
		
		// Start the game
		c.start();
	}
}
