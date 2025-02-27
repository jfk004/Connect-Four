package c4.mvc;

public interface ConnectFourModelInterface {
	final int PLAYER1 = 1;
	final int PLAYER2 = 2;
	final int EMPTY = -1;
	
	void initialize();
	int setGridPosition(int column, int player);
	void nextPlayer();
	
	int getTurn();
	boolean[] getValidMoves();
	int[][] getGrid();
	void registerObserver(GridObserver o);
	void registerObserver(ResultObserver o);
	void removeObserver(GridObserver o);
	void removeObserver(ResultObserver o);
}
