package c4.players;

import java.util.Random;

import c4.mvc.ConnectFourModelInterface;

public class ConnectFourRandomPlayer extends ConnectFourPlayer{
	ConnectFourModelInterface model;
	Random random;
	
	public ConnectFourRandomPlayer(ConnectFourModelInterface model){
		this.model = model;
		this.random = new Random();
	}
	
	@Override
	public int getMove() {
		boolean[] moves = model.getValidMoves();
		int m = random.nextInt(7);
		while(!moves[m])
			m = random.nextInt(7);
		return m;
	}
	
}
