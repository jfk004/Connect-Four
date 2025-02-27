package ttt3player.mvc;

import c4.mvc.GridObserver;
import c4.mvc.ResultObserver;
import ttt3player.players.TTT3PlayerAbstractPlayer;

public class TTT3PlayerView implements GridObserver, ResultObserver{
	TTT3PlayerModel model;
	TTT3PlayerController controller;
	boolean gameOver;
	String gridOutput;
	
	public TTT3PlayerView(TTT3PlayerModel model, TTT3PlayerController con){
		this.model = model;
		this.controller = con;
		this.gameOver = false;
		this.gridOutput = "";
	}
	
	public void createView(){
		gridOutput = "- - - -\n" + "- - - -\n" + "- - - -\n" + "- - - -\n";
		System.out.println(gridOutput);
		
		model.registerObserver((GridObserver) this);
		model.registerObserver((ResultObserver) this);
	}
	
	public void playGame(){
		gameOver = false;
		while(!gameOver){
			char playerChar = model.getTurn();
			if(playerChar == 'X')
				System.out.println("Current turn: X");
			else if(playerChar == 'O')
				System.out.println("Current turn: O");
			else
				System.out.println("Current turn: +");
			
			TTT3PlayerAbstractPlayer player = controller.getPlayer(playerChar);
			int move = player.getMove();
			controller.placeToken(move);
		}
	}
	
	public void updateGrid(){
		char[][] grid = model.getGrid();
		String gridOutput = "";
		
		for(int row=0; row<4; row++){
			String text = "";
			for(int col=0; col<4; col++)
				text += grid[row][col] + " ";
			text += "\n";
			gridOutput += text;
		}
			
		System.out.println(gridOutput);
	}
	
	public void announceWinner(char winner){
		System.out.println("Player "+winner+" wins!");
	}
	
	public void announceDraw(){
		System.out.println("It's a draw!");
	}
	
	public void reportResult(int result){
		gameOver = true;
	}
}
