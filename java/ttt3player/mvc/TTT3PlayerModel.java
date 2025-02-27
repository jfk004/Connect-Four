package ttt3player.mvc;

import java.util.ArrayList;

import c4.mvc.GridObserver;
import c4.mvc.ResultObserver;
import c4.IllegalMoveException;

public class TTT3PlayerModel {
	char[][] grid;
	char turn;
	ArrayList<GridObserver> gridObservers;
	ArrayList<ResultObserver> resultObservers;
	
	public TTT3PlayerModel(){
		grid = new char[4][4];
		
		turn = '-';
		gridObservers = new ArrayList<GridObserver>();
		resultObservers = new ArrayList<ResultObserver>();	
	}
	
	public void initialize(){
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				grid[i][j] = '-';
		
		turn = 'X';
		this.notifyGridObservers();
	}
	
	public void setPosition(int pos, char player){
		if(pos < 1 || pos > 16)
			throw new IllegalMoveException(pos, 0);
		
		pos -= 1;
		int row = pos / 4;
		int col = pos % 4;
		grid[row][col] = player;
		notifyGridObservers();
		
		char result = checkForWinner();
		if(result == 'X'){
			notifyResultObservers(1);
		}
		else if(result == 'O'){
			notifyResultObservers(2);
		}
		else if(result == '+'){
			notifyResultObservers(3);
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
		// Check for horizontal win
		for(int row=0; row<4; row++){
			for(int startcol=0; startcol<2; startcol++){
				if(grid[row][startcol] != '-' && grid[row][startcol] == grid[row][startcol+1] && grid[row][startcol] == grid[row][startcol+2])
					return grid[row][startcol];
			}
		}
		// Check for vertical win
		for(int col=0; col<4; col++){
			for(int startrow=0; startrow<2; startrow++){
				if(grid[startrow][col] != '-' && grid[startrow][col] == grid[startrow+1][col] && grid[startrow][col] == grid[startrow+2][col])
					return grid[startrow][col];
			}
		}
		// Check for diagonal \ win
		for(int startrow=0; startrow<2; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(grid[startrow][startcol] != '-' && grid[startrow][startcol] == grid[startrow+1][startcol+1] && grid[startrow][startcol] == grid[startrow+2][startcol+2])
					return grid[startrow][startcol];
			}
		}
		for(int startrow=2; startrow<4; startrow++){
			for(int startcol=0; startcol<2; startcol++){
				if(grid[startrow][startcol] != '-' && grid[startrow][startcol] == grid[startrow-1][startcol+1] && grid[startrow][startcol] == grid[startrow-2][startcol+2])
					return grid[startrow][startcol];
			}
		}
		
		return '-';
	}
	
	public boolean checkForDraw(){
		boolean allFilled = true;
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				if(grid[row][col] == '-')
					allFilled = false;
		return allFilled;
	}
	
	public void nextPlayer(){
		if(turn == 'X')
			turn = 'O';
		else if(turn == 'O')
			turn = '+';
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
		for(int row=0; row<4; row++)
			for(int col=0; col<4; col++)
				if(grid[row][col] == '-')
					moves.add(row*4+col+1);
		
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
