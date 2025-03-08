package c4;
import c4.mvc.ConnectFourController;
import c4.mvc.ConnectFourModel;
import c4.players.*;

public class ConnectFour {

	public static void main(String[] args) {
		// Choose one
		playSingleGame();
		//playBatchGames(500);
				
	}
	
	public static void playSingleGame(){
		ConnectFourModel m = new ConnectFourModel();
		
		// Change the constructor calls to change the players used
		ConnectFourPlayer player1 = new ConnectFourRandomPlayer(m);
		ConnectFourPlayer player2 = new ConnectFourAIPlayer(m, 12);
		
		// Choose 1 of the Controller/View set-ups below.
		
		// Sets up "silent" view -- No output at all
		//ConnectFourController c = new ConnectFourController(m, player1, player2, false);

		// Sets up console view -- All output to console
		ConnectFourController c = new ConnectFourController(m, player1, player2, true);
		
		// Sets up GUI view -- All output thru Swing GUI
		//ConnectFourController c = new ConnectFourController(m, player1, player2);
		
		// Start the game
		c.start();
	}
	
	public static void playBatchGames(int plays){
		int[] results = {0,0,0}; // draws, P1 wins, P2 wins
		int[] forfeits = {0,0,0}; // unused, P1 forfeits, P2 forfeits
		
		for(int i=0; i<plays; i++){
			try{
				ConnectFourModel m = new ConnectFourModel();
			
				// Change the constructor calls to change the players used. Do not use HumanPlayer with this set-up.
				ConnectFourPlayer player1 = new ConnectFourRandomPlayer(m);
				ConnectFourPlayer player2 = new ConnectFourRandomPlayer(m);
				
				ConnectFourController c = new ConnectFourController(m, player1, player2, false);
				//System.out.println("Starting game "+i); //Useful for debugging.
				int winner = c.start();
				results[winner] += 1;
			}
			catch(IllegalMoveException e){
				int player = e.getPlayer();
				forfeits[player] += 1;
				results[3-player] += 1; // Award win to other player
			}
		}
		System.out.print("Player 1 record (W-L-D): "+results[1]+"-"+results[2]+"-"+results[0]);
		System.out.println(" (including "+forfeits[1]+" forfeits)");
		
		System.out.print("Player 2 record (W-L-D): "+results[2]+"-"+results[1]+"-"+results[0]);
		System.out.println(" (including "+forfeits[2]+" forfeits)");
	}

}
