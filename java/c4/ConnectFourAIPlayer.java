package c4;

import java.util.ArrayList;
import java.util.Random;

import c4.mvc.ConnectFourModel;
import c4.players.ConnectFourPlayer;


public class ConnectFourAIPlayer extends ConnectFourPlayer {

    ConnectFourModel model;
    Random random;

    
    public ConnectFourAIPlayer(ConnectFourModel model) {
        this.model = model;
        this.random = new Random();
    }

    @Override
    public int getMove() {
        boolean[] moves = model.getValidMoves();
        int m = random.nextInt(7);
        while (!moves[m])
            m = random.nextInt(7);
        return m;
    }

    // Checks if the player has won or if the game has reached a draw
    public boolean terminalTest(int[][] board) {
        return this.model.checkForWinner() != -1 || this.model.checkForDraw();
    }

    public int[] actions(int [][] state){
		ArrayList<Integer> moves = new ArrayList<Integer>();
        
        int row = 0; // Only checks the columns 

        for(int col= 0; col<6; col++){
            if(state[col][row] == -1){
                moves.add(col);
            }
        }

        int[] results = new int[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            results[i] = moves.get(i);
        }
		
		return results;
	}


    public int[][] results(int[][] state, int action){

        int[][] newState = new int[state.length][state[0].length];
        //deep copy of the board
        for(int row = 0; row < state.length; row++){
            for(int col = 0; col < state[0].length; col++){
                newState[row][col] = state[row][col];
            }

        }

        // determine whose turn it is 
        int player = getTurn(state);


        // Find the lowest available row in the selected column
        for(int row = state.length - 1; row>= 0; row--){
            if(newState[row][action] == 0){    // empty spot found
                newState[row][action] = player;

                break;
            }
        }

        return newState;
     
    }

    protected int getTurn(int[][] state){
        int empties = 0;

        // count empty spaces on the board
        for(int row = 0; row < state.length; row++){
            for(int col = 0; col < state[0].length; col++){
                if(state[row][col] == 0){
                    empties++;
                }
            }
        }

        // if empty count is odd, player 1 moves else player 2 moves
        return(empties % 2 == 1) ? 1 : 2;
    }

}





