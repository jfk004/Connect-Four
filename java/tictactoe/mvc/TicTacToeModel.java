package tictactoe.mvc;

import java.util.ArrayList;

import c4.mvc.GridObserver;
import c4.mvc.ResultObserver;
import c4.IllegalMoveException;

public class TicTacToeModel {
	char[][] grid;
	char turn;
	ArrayList<GridObserver> gridObservers;
	ArrayList<ResultObserver> resultObservers;
	
	public TicTacToeModel(){
		grid = new char[3][3];
		
		turn = '-';
		gridObservers = new ArrayList<GridObserver>();
		resultObservers = new ArrayList<ResultObserver>();	
	}
	
	public void initialize(){
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				grid[i][j] = '-';
		
		turn = 'X';
		this.notifyGridObservers();
	}
	
	public void setPosition(int pos, char player){
		if(pos < 1 || pos > 9)
			throw new IllegalMoveException(pos, 0);
		
		pos -= 1;
		int row = pos / 3;
		int col = pos % 3;
		grid[row][col] = player;
		notifyGridObservers();
		
		char result = checkForWinner();
		if(result == 'X'){
			notifyResultObservers(1);
		}
		else if(result == 'O'){
			notifyResultObservers(2);
		}
		else if(checkForDraw())
			notifyResultObservers(0);
	}
		
	private void notifyGridObservers(){
		for(GridObserver o:gridObservers){
			o.updateGrid();
		}
	}
	
	private void notifyResultObservers(int result){
		for(ResultObserver o:resultObservers){
			o.reportResult(result);
		}
	}
	
	public char checkForWinner(){
		for(int row=0; row<3; row++){
			if(grid[row][0] != '-' && grid[row][0] == grid[row][1] && grid[row][0] == grid[row][2])
				return grid[row][0];
		}
		for(int col=0; col<3; col++){
			if(grid[0][col] != '-' && grid[0][col] == grid[1][col] && grid[0][col] == grid[2][col])
				return grid[0][col];
		}
		if(grid[0][0] != '-' && grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2])
				return grid[0][0];
		if(grid[2][0] != '-' && grid[2][0] == grid[1][1] && grid[2][0] == grid[0][2])
				return grid[2][0];
		
		return '-';
	}
	
	public boolean checkForDraw(){
		boolean allFilled = true;
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				if(grid[row][col] == '-')
					allFilled = false;
		return allFilled;
	}
	
	public void nextPlayer(){
		if(turn == 'X')
			turn = 'O';
		else
			turn = 'X';
	}
	
	public char getTurn(){
		return turn;
	}
	
	public char[][] getGrid(){
		return grid;
	}
	
	public int[] getValidMoves(){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				if(grid[row][col] == '-')
					moves.add(row*3+col+1);
		
		int[] results = new int[moves.size()];
		for(int i=0; i<results.length; i++)
			results[i] = moves.get(i);
		
		return results;
	}
	
	public void registerObserver(GridObserver o) {
		gridObservers.add(o);
	}

	
	public void registerObserver(ResultObserver o){
		resultObservers.add(o);
	}

	public void removeObserver(GridObserver o) {
		int i = gridObservers.indexOf(o);
		if(i >= 0)
			gridObservers.remove(i);
	}
	
	public void removeObserver(ResultObserver o){
		int i = resultObservers.indexOf(o);
		if(i >= 0)
			resultObservers.remove(i);
	}
}
