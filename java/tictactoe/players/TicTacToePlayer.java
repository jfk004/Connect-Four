package tictactoe.players;

public abstract class TicTacToePlayer {
	public abstract int getMove();
	
	public boolean isAutomated(){
		return true;
	}
}
