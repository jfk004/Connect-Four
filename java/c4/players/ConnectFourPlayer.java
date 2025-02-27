package c4.players;

public abstract class ConnectFourPlayer {
	// This function must return a value between 0 and 6 (inclusive), where 0 is the left-most column and 6 is the right-most.
	public abstract int getMove();
	
	// AI players should return true, human players should retun false.
	public boolean isAutomated(){
		return true;
	}
}
