package ttt3player;

import ttt3player.mvc.*;
import ttt3player.players.*;

public class TTT3Player {
	public static void main(String[] args) {
		playSingleGame();		
	}
	
	public static void playSingleGame(){
		TTT3PlayerModel m = new TTT3PlayerModel();
		
		// Human is X, AI is O, Human is +
		// You can rotate these any way you want
		TTT3PlayerAbstractPlayer player1 = new TTT3PlayerHumanPlayer(m, 'X');
		TTT3PlayerAbstractPlayer player2 = new TTT3PlayerAIPlayer(m, 'O');
		TTT3PlayerAbstractPlayer player3 = new TTT3PlayerHumanPlayer(m, '+');
				
		// X player must be player 1, O must be player 2, + must be player 3
		TTT3PlayerController c = new TTT3PlayerController(m, player1, player2, player3);
		
		// Start the game
		c.start();
	}
}
