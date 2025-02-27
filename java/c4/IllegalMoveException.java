package c4;

public class IllegalMoveException extends IllegalArgumentException {
	private int player;
	
	public IllegalMoveException(int move, int player){
		super("Illegal move "+move+" by player "+player);
		this.player = player;
	}
	
	public int getPlayer(){
		return player;
	}
}
