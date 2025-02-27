package ttt3player.mvc;

import c4.mvc.ResultObserver;
import ttt3player.players.TTT3PlayerAbstractPlayer;

public class TTT3PlayerController implements ResultObserver{
	TTT3PlayerAbstractPlayer[] players;
	TTT3PlayerModel model;
	char gameWinner;
	TTT3PlayerView view;
	
	public TTT3PlayerController(TTT3PlayerModel model, TTT3PlayerAbstractPlayer p1, TTT3PlayerAbstractPlayer p2, TTT3PlayerAbstractPlayer p3){
		players = new TTT3PlayerAbstractPlayer[3];
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;
		
		this.model = model;
		this.model.initialize();
		this.model.registerObserver((ResultObserver) this);
		
		gameWinner = '-';
		this.view = new TTT3PlayerView(model, this);
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
	
	public TTT3PlayerAbstractPlayer getPlayer(char p){
		if(p == 'X')
			return players[0];
		else if(p == 'O')
			return players[1];
		else
			return players[2];
	}
	
	public void reportResult(int result){
		if(result == 1)
			gameWinner = 'X';
		else if(result == 2)
			gameWinner = 'O';
		else if(result == 3)
			gameWinner = '+';
		
		if(result == 0)
			view.announceDraw();
		else
			view.announceWinner(gameWinner);
	}
}
