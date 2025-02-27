package tictactoe.mvc;

import c4.mvc.ResultObserver;
import tictactoe.players.TicTacToePlayer;

public class TicTacToeController implements ResultObserver{
	TicTacToePlayer[] players;
	TicTacToeModel model;
	char gameWinner;
	TicTacToeView view;
	
	public TicTacToeController(TicTacToeModel model, TicTacToePlayer p1, TicTacToePlayer p2){
		players = new TicTacToePlayer[2];
		players[0] = p1;
		players[1] = p2;
		
		this.model = model;
		this.model.initialize();
		this.model.registerObserver((ResultObserver) this);
		
		gameWinner = '-';
		this.view = new TicTacToeView(model, this);
	}
	
	public char start(){
		view.createView();
		view.playGame();
		return gameWinner;
	}
	
	public void placeToken(int move){
		model.setPosition(move, model.getTurn());
		model.nextPlayer();
	}
	
	public void reset(){
		model.initialize();
	}
	
	public void quit(){
		System.exit(0);
	}
	
	public TicTacToePlayer getPlayer(char p){
		if(p == 'X')
			return players[0];
		return players[1];
	}
	
	public void reportResult(int result){
		if(result == 1)
			gameWinner = 'X';
		else if(result == 2)
			gameWinner = 'O';
		
		if(result == 0)
			view.announceDraw();
		else
			view.announceWinner(gameWinner);
	}
}
