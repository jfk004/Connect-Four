package c4.mvc;

import java.util.Scanner;

import c4.players.ConnectFourPlayer;

public class ConnectFourSilentView implements ConnectFourViewInterface, ResultObserver{

	ConnectFourModelInterface model;
	ConnectFourController controller;
	boolean gameOver;
	boolean[] validColumns;
	Scanner inputScanner;
	
	public ConnectFourSilentView(ConnectFourModelInterface model, ConnectFourController con){
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
		model.registerObserver((ResultObserver) this);
	}
	
	public void playGame(){
		this.gameOver = false;
		while(!gameOver){
			int playerNum = model.getTurn();
			ConnectFourPlayer player = controller.getPlayer(playerNum);
			
			int move = player.getMove();
			controller.placeToken(move);
		}
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
		//System.out.println("Player "+winner+" wins!");
	}

	@Override
	public void announceDraw() {
		//System.out.println("It's a draw!");
	}
	
	public void reportResult(int result){
		gameOver = true;
	}

}
