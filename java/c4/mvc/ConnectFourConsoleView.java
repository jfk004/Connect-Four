package c4.mvc;
import java.util.Scanner;

import c4.players.ConnectFourPlayer;

public class ConnectFourConsoleView implements ConnectFourViewInterface, GridObserver, ResultObserver {

	ConnectFourModelInterface model;
	ConnectFourController controller;
	String gridOutput;
	boolean[] validColumns;
	Scanner inputScanner;
	boolean gameOver;
	
	public ConnectFourConsoleView(ConnectFourModelInterface model, ConnectFourController con){
		this.model = model;
		this.controller = con;
		this.gameOver = false;
		
		validColumns = new boolean[7];
		for(int i=0; i<validColumns.length; i++) 
			validColumns[i] = true;
		inputScanner = new Scanner(System.in);
	}
	
	@Override
	public void createView() {
		gridOutput = "- - - - - - -\n"+"- - - - - - -\n"+"- - - - - - -\n"+
				"- - - - - - -\n"+"- - - - - - -\n"+"- - - - - - -\n";
		
		System.out.println(gridOutput);
		
		model.registerObserver((GridObserver) this);
		model.registerObserver((ResultObserver) this);
	}
	
	public void playGame(){
		this.gameOver = false;
		while(!gameOver){
			int playerNum = model.getTurn();
			if(playerNum == ConnectFourModelInterface.PLAYER1){
				System.out.println("Current Turn: PLAYER 1");
			}
			else{
				System.out.println("Current Turn: PLAYER 2");
			}
			ConnectFourPlayer player = controller.getPlayer(playerNum);
			
			int move = player.getMove();
			controller.placeToken(move);
		}
	}

	@Override
	public void updateGrid() {
		int[][] grid = model.getGrid();
		gridOutput = "";
		
		for(int j=0; j<6; j++){
			String row = "";
			for(int i=0; i<7; i++){
				if(grid[i][j] == ConnectFourModelInterface.PLAYER1){
					row += "X ";
				}
				else if(grid[i][j] == ConnectFourModelInterface.PLAYER2){
					row += "O ";
				}
				else{
					row += "- ";
				}
			}
			row += "\n";
			gridOutput += row;
		}
		
		System.out.println(gridOutput);
	}

		@Override
	public void enableColumn(int col) {
		validColumns[col] = true;

	}

	@Override
	public void disableColumn(int col) {
		validColumns[col] = false;

	}

	@Override
	public void announceWinner(int winner) {
		System.out.println("Player "+winner+" wins!");
	}

	@Override
	public void announceDraw() {
		System.out.println("It's a draw!");
	}
	
	@Override
	public void reportResult(int result) {
		gameOver = true;
	}

}
