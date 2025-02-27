package c4.mvc;

import c4.players.ConnectFourPlayer;

public class ConnectFourController implements ResultObserver{
	ConnectFourModelInterface model;
	ConnectFourViewInterface view;
	ConnectFourPlayer[] players = new ConnectFourPlayer[2];
	int gameWinner;
	
	public ConnectFourController(ConnectFourModelInterface model, ConnectFourPlayer p1, ConnectFourPlayer p2){
		players[0] = p1;
		players[1] = p2;
		
		this.model = model;
		model.initialize();
		model.registerObserver(this);
		view = new ConnectFourGUIView(model, this);
	}
	
	public ConnectFourController(ConnectFourModelInterface model, ConnectFourPlayer p1, ConnectFourPlayer p2, boolean showOutput){
		players[0] = p1;
		players[1] = p2;
		
		this.model = model;
		model.initialize();
		model.registerObserver(this);
		if(showOutput)
			view = new ConnectFourConsoleView(model, this);
		else{
			view = new ConnectFourSilentView(model, this);
		}
		
	}
	
	public int start(){
		view.createView();
		view.playGame();
		return this.gameWinner;
	}
	
	public void placeToken(int column){
		int row = model.setGridPosition(column, model.getTurn());
		model.nextPlayer();
		if(row == 0)
			view.disableColumn(column);
	}
	
	public void reset(){
		model.initialize();
		for(int col=0; col<7; col++)
			view.enableColumn(col);
		view.playGame();
	}
	
	public void quit(){
		System.exit(0);
	}

	@Override
	public void reportResult(int result) {
		gameWinner = result;
		if(result > 0)
			view.announceWinner(result);
		else
			view.announceDraw();
		for(int col = 0; col<7; col++)
			view.disableColumn(col);
	}
	
	public ConnectFourPlayer getPlayer(int p){
		return players[p-1];
	}
}
