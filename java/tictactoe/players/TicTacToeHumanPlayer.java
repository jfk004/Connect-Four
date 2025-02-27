package tictactoe.players;

import java.util.Scanner;

import tictactoe.mvc.TicTacToeModel;

public class TicTacToeHumanPlayer extends TicTacToePlayer{
	TicTacToeModel model;
	char symbol;
	Scanner inputScanner = new Scanner(System.in);
	
	public TicTacToeHumanPlayer(TicTacToeModel model, char symbol){
		this.model = model;
		this.symbol = symbol;
	}
	
	public boolean isAutomated(){
		return false;
	}
	
	public int getMove(){
		boolean validInput = false;
		int[] validMoves = model.getValidMoves();
		
		while(!validInput){
			System.out.print("Enter move (1-9): ");
			String result = inputScanner.nextLine();
			validInput = validateInput(result);
			
			if(validInput){
				int move = Integer.parseInt(result);
				boolean found = false;
				for(int i:validMoves){
					if(i == move)
						found = true;
				}
				if(found)
					return move;
				else{
					System.out.println("That spot is full. Pick again.");
					validInput = false;
				}
			}
			else{
				System.out.println("Invalid input.");
			}
		}
		
		return -1; // should not get here
	}

	private boolean validateInput(String input){
		if(input.length() != 1)
			return false;
		
		char c = input.charAt(0);
		
		if(Character.isDigit(c)){
			int column = Integer.parseInt(input);
			return column >= 1 && column <= 9;
		}
		return false;
	}


}
