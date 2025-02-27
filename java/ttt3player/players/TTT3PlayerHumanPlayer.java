package ttt3player.players;

import java.util.Scanner;

import ttt3player.mvc.TTT3PlayerModel;

public class TTT3PlayerHumanPlayer extends TTT3PlayerAbstractPlayer{
	TTT3PlayerModel model;
	char symbol;
	Scanner inputScanner = new Scanner(System.in);
	
	public TTT3PlayerHumanPlayer(TTT3PlayerModel model, char symbol){
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
			System.out.print("Enter move (1-16): ");
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
		try{
			int result = Integer.parseInt(input);
			return result >= 1 && result <= 16;
		}
		catch(NumberFormatException nfe){
			return false;
		}
	}


}
