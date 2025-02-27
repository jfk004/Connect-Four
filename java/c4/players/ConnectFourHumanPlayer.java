package c4.players;

import java.util.Scanner;

import c4.mvc.ConnectFourModelInterface;

public class ConnectFourHumanPlayer extends ConnectFourPlayer{
	ConnectFourModelInterface model;
	Scanner inputScanner = new Scanner(System.in);
	
	public ConnectFourHumanPlayer(ConnectFourModelInterface model){
		this.model = model;
	}
	
	@Override
	public int getMove() {
		boolean validInput = false;
		boolean[] validColumns = model.getValidMoves();
		
		while(!validInput){
			System.out.print("Enter column (1-7): ");
			String result = inputScanner.nextLine();
			validInput = validateInput(result);
			
			if(validInput){
				int column = Integer.parseInt(result);
				if(validColumns[column-1]){
					return column-1;
				}
				else{
					System.out.println("That column is full. Pick again.");
					validInput = false;
				}
			}
			else{
				System.out.println("Invalid input.");
			}
		}
		
		//Should not get here
		return -1;
	}
	
	private boolean validateInput(String input){
		if(input.length() != 1)
			return false;
		
		char c = input.charAt(0);
		
		if(Character.isDigit(c)){
			int column = Integer.parseInt(input);
			return column >= 1 && column <= 7;
		}
		return false;
	}

	public boolean isAutomated(){
		return false;
	}
}
