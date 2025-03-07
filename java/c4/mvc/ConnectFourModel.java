package c4.mvc;
import java.util.ArrayList;

import c4.IllegalMoveException;

public class ConnectFourModel implements ConnectFourModelInterface{

	int[][] grid;
	int turn;
	
	ArrayList<GridObserver> observers = new ArrayList<GridObserver>();
	ArrayList<ResultObserver> resultObs = new ArrayList<ResultObserver>();
	
	@Override
	public void initialize() {
		grid = new int[7][6];
		for(int i = 0; i < 7; i++){
			for (int j=0; j<6; j++){
				grid[i][j] = EMPTY;
			}
		}
		
		turn = PLAYER1;
		notifyGridObservers();
	}
	
	@Override
	public int setGridPosition(int column, int player) {
		if(column < 0 || column > 6){
			throw new IllegalMoveException(column, player);
		}
		
		int row = 5;
		while(grid[column][row] != EMPTY){
			row--;
		}
		
		if(row < 0){
			throw new IllegalMoveException(column, player);
		}
		
		grid[column][row] = player;
		notifyGridObservers();
		
		int result = checkForWinner();
		boolean draw = false;
		if(result > 0){
			notifyResultObservers(result);
		}
		else{
			draw = checkForDraw();
			if(draw)
				notifyResultObservers(0); // draw
		}
		return row;
	}
	
	private void notifyGridObservers(){
		for(GridObserver o:observers){
			o.updateGrid();
		}
	}
	
	private void notifyResultObservers(int result){
		for(ResultObserver o:resultObs){
			o.reportResult(result);
		}
	}
	
	public int checkForWinner(){
		int winResult = checkHorizontalWin();
		if (winResult < 0)
			winResult = checkVerticalWin();
		if (winResult < 0)
			winResult = checkNegDiagonalWin();
		if (winResult < 0)
			winResult = checkPosDiagonalWin();
		
		// Must not have one
		return winResult;
	}

	private int checkPosDiagonalWin() {
		boolean win = false;
		for(int col=3; col<7; col++){
			for(int row=0; row<=2; row++){
				if(grid[col][row] != EMPTY){
					win = (grid[col][row] == grid[col-1][row+1]) && (grid[col][row] == grid[col-2][row+2]) && (grid[col][row] == grid[col-3][row+3]);
				}
				if(win) return grid[col][row];
			}
		}
		return -1;
	}

	private int checkNegDiagonalWin() {
		boolean win = false;
		for(int col=0; col<=3; col++){
			for(int row=0; row<=2; row++){
				if(grid[col][row] != EMPTY){
					win = (grid[col][row] == grid[col+1][row+1]) && (grid[col][row] == grid[col+2][row+2]) && (grid[col][row] == grid[col+3][row+3]);
				}
				if(win) return grid[col][row];
			}
		}
		return -1;
	}

	private int checkVerticalWin() {
		boolean win = false;
		for(int col=0; col<7; col++){
			for(int row=0; row<=2; row++){
				if(grid[col][row] != EMPTY){
					win = (grid[col][row] == grid[col][row+1]) && (grid[col][row] == grid[col][row+2]) && (grid[col][row] == grid[col][row+3]);
				}
				if(win) return grid[col][row];
			}
		}
		return -1;
	}

	private int checkHorizontalWin() {
		boolean win = false;
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
				if(grid[col][row] != EMPTY){
					win = (grid[col][row] == grid[col+1][row]) && (grid[col][row] == grid[col+2][row]) && (grid[col][row] == grid[col+3][row]);
				}
				if(win) return grid[col][row];
			}
		}
		return -1;
	}
	
	public boolean checkForDraw(){
		for(int i=0; i<7; i++){
			for(int j=0; j<6; j++){
				if(grid[i][j] == EMPTY)
					return false;
			}
		}
		return true;
	}
	
	public void nextPlayer(){
		if(turn == PLAYER1)
			turn = PLAYER2;
		else
			turn = PLAYER1;
	}

	@Override
	public int getTurn() {
		return turn;
	}

	@Override
	public int[][] getGrid() {
		return grid;
	}

	@Override
	public void registerObserver(GridObserver o) {
		observers.add(o);
	}

	
	public void registerObserver(ResultObserver o){
		resultObs.add(o);
	}

	@Override
	public void removeObserver(GridObserver o) {
		int i = observers.indexOf(o);
		if(i >= 0)
			observers.remove(i);
	}
	
	public void removeObserver(ResultObserver o){
		int i = resultObs.indexOf(o);
		if(i >= 0)
			resultObs.remove(i);
	}

	@Override
	public boolean[] getValidMoves() {
		boolean[] avail = new boolean[7];
		for(int i=0; i<7; i++){
			avail[i] = (grid[i][0] == EMPTY);
		}
		
		return avail;
	}



}
