package c4;

import java.util.*;

import c4.mvc.ConnectFourModel;
import c4.players.ConnectFourPlayer;

public class ConnectFourAIPlayer extends ConnectFourPlayer {

    ConnectFourModel model;
    Random random;
    private int cutoff;

    //AI player constructor
    public ConnectFourAIPlayer(ConnectFourModel model) {
        this.model = model;
        this.random = new Random();
        this.cutoff = 42;
    }

    //AI player constructor with cutoff
    public ConnectFourAIPlayer(ConnectFourModel model, int cutoff) {
        this.model = model;
        this.random = new Random();
        this.cutoff = cutoff;
    }

    //determine move, current lifted from random player
    @Override
    public int getMove() {
        //run and return alpha beta search
        return alphaBetaSearch(this.model.getGrid());
    }

    public int utilityFunction(int[][] state, int player) {
        if (this.model.checkForDraw()) //if draw
            return 0;
        else if (this.model.checkForWinner() == player) //if win
            return 2000;
        else if(this.model.checkForWinner() != -1)
            return -2000;
        
        
		
        //checks if values are next to eachother for columns and rows
		int score = 0;

		//if horizontal
		for(int row=0; row<6; row++){
			for(int col=0; col<=3; col++){
                //3 in a row xxx-
				if((state[col][row] == state[col+1][row]) && (state[col][row] == state[col+2][row]) && ('-' == state[col+3][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row xx-x
				if((state[col][row] == state[col+1][row]) && ('-' == state[col+2][row]) && (state[col][row] == state[col+3][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row x-xx
				if((state[col+1][row] == '-') && (state[col][row] == state[col+2][row]) && (state[col][row] == state[col+3][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row -xxx
				if((state[col][row] == '-') && (state[col+1][row] == state[col+2][row]) && (state[col+1][row] == state[col+3][row])){
					//ensures only x or o 
                    if(state[col+1][row] == 'X'){
                        score += 200;
                    } else if (state[col+1][row] == 'O'){
                        score -= 200;
                    }
				} 

                //2 in a row xx-
                if((state[col][row] == state[col+1][row]) && ('-' == state[col+2][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                if((state[col+1][row] == state[col+2][row]) && ('-' == state[col+3][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                //2 in a row x-x
                if((state[col][row] == state[col+2][row]) && ('-' == state[col+1][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                if((state[col+1][row] == state[col+3][row]) && ('-' == state[col+2][row])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                //2 in a row -xx
                if((state[col+2][row] == state[col+1][row]) && ('-' == state[col][row])){
					//ensures only x or o 
                    if(state[col+1][row] == 'X'){
                        score += 100;
                    } else if (state[col+1][row] == 'O'){
                        score -= 100;
                    }
				}
                if((state[col+3][row] == state[col+2][row]) && ('-' == state[col+1][row])){
					//ensures only x or o 
                    if(state[col+3][row] == 'X'){
                        score += 100;
                    } else if (state[col+3][row] == 'O'){
                        score -= 100;
                    }
				}
			}
		}

        //vertical pattern
        for(int col=0; col<7; col++){
			for(int row=0; row<=2; row++){
                //3 in a row 
				//3 in a row xxx-
				if((state[col][row] == state[col][row+1]) && (state[col][row] == state[col][row+2]) && ('-' == state[col][row+3])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row xx-x
				if((state[col][row] == state[col][row+1]) && ('-' == state[col][row+2]) && (state[col][row] == state[col][row+3])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row x-xx
				if((state[col][row+1] == '-') && (state[col][row] == state[col][row+2]) && (state[col][row] == state[col][row+3])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                //3 in a row -xxx
				if((state[col][row] == '-') && (state[col][row+1] == state[col][row+2]) && (state[col][row+1] == state[col][row+3])){
					//ensures only x or o 
                    if(state[col][row+1] == 'X'){
                        score += 200;
                    } else if (state[col][row+1] == 'O'){
                        score -= 200;
                    }
				} 

                //2 in a row xx-
                if((state[col][row] == state[col][row+1]) && ('-' == state[col][row+2])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                if((state[col][row+1] == state[col][row+2]) && ('-' == state[col][row+3])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                //2 in a row x-x
                if((state[col][row] == state[col][row+2]) && ('-' == state[col][row+1])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				} 
                if((state[col][row+1] == state[col][row+3]) && ('-' == state[col][row+2])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				}
                //2 in a row -xx
                if((state[col][row+2] == state[col][row+1]) && ('-' == state[col][row])){
					//ensures only x or o 
                    if(state[col][row+1] == 'X'){
                        score += 100;
                    } else if (state[col][row+1] == 'O'){
                        score -= 100;
                    }
				}
                if((state[col][row+3] == state[col][row+2]) && ('-' == state[col][row+1])){
					//ensures only x or o 
                    if(state[col][row+3] == 'X'){
                        score += 100;
                    } else if (state[col][row+3] == 'O'){
                        score -= 100;
                    }
				}
			}
            
		}

        //checks diagonal
        //pos diagonal
        for(int col=3; col<7; col++){
			for(int row=0; row<=2; row++){
                //3 in a row
				if((state[col][row] == state[col-1][row+1]) && (state[col][row] == state[col-2][row+2]) && ('-' == state[col-3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                if((state[col][row] == state[col-1][row+1]) && ('-' == state[col-2][row+2]) && (state[col][row] == state[col-3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                if((state[col][row] == '-') && (state[col-1][row+1] == state[col-2][row+2]) && (state[col-1][row+1] == state[col-3][row+3])){
                    if(state[col-1][row+1] == 'X'){
                        score += 200;
                    } else if (state[col-1][row+1] == 'O'){
                        score -= 200;
                    }
				}
                if(('-' == state[col-1][row+1]) && (state[col][row] == state[col-2][row+2]) && (state[col][row] == state[col-3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}

                //2 in a row
                //2 in a row xx-
                if((state[col][row] == state[col-1][row+1]) && ('-' == state[col-2][row+2])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				} 
                if((state[col-1][row+1] == state[col-2][row+2]) && ('-' == state[col-3][row+3])){
					//ensures only x or o 
                    if(state[col-1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col-1][row+1] == 'O'){
                        score -= 100;
                    }
				}
                //2 with a gap x-x
                if((state[col][row] == state[col-2][row+2]) && ('-' == state[col-1][row+1])){
					//ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
				} 
                if((state[col-1][row+1] == state[col-3][row+3]) && ('-' == state[col-2][row+2])){
					//ensures only x or o 
                    if(state[col-1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col-1][row+1] == 'O'){
                        score -= 100;
                    }
				}
                //2 in a row -xx
                if((state[col][row] == '-') && ( state[col-1][row+1]== state[col-2][row+2])){
					//ensures only x or o 
                    if(state[col-1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col-1][row+1] == 'O'){
                        score -= 100;
                    }
				}
                if(('-' == state[col-2][row+2]) && (state[col-1][row+1] == state[col-3][row+3])){
					//ensures only x or o 
                    if(state[col-2][row+2] == 'X'){
                        score += 100;
                    } else if (state[col-2][row+2] == 'O'){
                        score -= 100;
                    }
				}
			}
		}

        //negative diagonal
        for(int col=0; col<=3; col++){
			for(int row=0; row<=2; row++){
                //3 in a row
				if((state[col][row] == state[col+1][row+1]) && (state[col][row] == state[col+2][row+2]) && ('-' == state[col+3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                if((state[col][row] == state[col+1][row+1]) && ('-' == state[col+2][row+2]) && (state[col][row] == state[col+3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                if(('-' == state[col+1][row+1]) && (state[col][row] == state[col+2][row+2]) && (state[col][row] == state[col+3][row+3])){
                    if(state[col][row] == 'X'){
                        score += 200;
                    } else if (state[col][row] == 'O'){
                        score -= 200;
                    }
				}
                if((state[col][row] == '-') && (state[col+1][row+1] == state[col+2][row+2]) && (state[col+1][row+1] == state[col+3][row+3])){
                    if(state[col+1][row+1] == 'X'){
                        score += 200;
                    } else if (state[col+1][row+1] == 'O'){
                        score -= 200;
                    }
				}

                //2 in a row
                //xx-
                if(((state[col][row] == state[col+1][row+1]) && ('-' == state[col+2][row+2]))){
                    //ensures only x or o 
                    if(state[col+1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col+1][row+1] == 'O'){
                        score -= 100;
                    }
                }
                if((state[col+1][row+1] == state[col+2][row+2]) && ('-' == state[col+3][row+3])){
                    //ensures only x or o 
                    if(state[col+1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col+1][row+1] == 'O'){
                        score -= 100;
                    }
                }
                //x-x
                if(((state[col][row] == state[col+2][row+2]) && ('-' == state[col+1][row+1]))){
                    //ensures only x or o 
                    if(state[col][row] == 'X'){
                        score += 100;
                    } else if (state[col][row] == 'O'){
                        score -= 100;
                    }
                }
                if((state[col+1][row+1] == state[col+3][row+3]) && ('-' == state[col+2][row+2])){
                    //ensures only x or o 
                    if(state[col+1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col+1][row+1] == 'O'){
                        score -= 100;
                    }
                }
                //-xx
                if((state[col+1][row+1] == state[col+2][row+2]) && ('-' == state[col][row])){
                    //ensures only x or o 
                    if(state[col+1][row+1] == 'X'){
                        score += 100;
                    } else if (state[col+1][row+1] == 'O'){
                        score -= 100;
                    }
                }
                if((state[col+3][row+3] == state[col+2][row+2]) && ('-' == state[col+1][row+1])){
                    //ensures only x or o 
                    if(state[col+2][row+2] == 'X'){
                        score += 100;
                    } else if (state[col+2][row+2] == 'O'){
                        score -= 100;
                    }
                }
                
			}
		}

 
		//inverts score depending on who player is
		score = this.getTurn(state) == 'X' ? score : -score;
		
        return score;
    }

    //search method to start alpha beta search recursion
    public int alphaBetaSearch(int[][] state) {
        //begin recursive search with the max value function, alpha and beta each set to negative and positive infinity
        int utilityAction[] = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE,0);

        //return action after recursion ends
        return utilityAction[1];
    }

    //recursive method to return a maximum utility move pairing
    public int[] maxValue(int[][] state, int alpha, int beta, int depth) {
        //create utility-action pair for storing results
        int utilityAction[] = new int [2];

        //check if game is over
        if (terminalTest(state) || depth == cutoff) {
            //return utility of game state, null action
            utilityAction[0] = utilityFunction(state, getTurn(state));

            return utilityAction;
        }

        //initialize best utility to -inf (worst possible for max)
        utilityAction[0] = Integer.MIN_VALUE;

        //loop through all possible actions from current state
        for (int action : actions(state)) {
            //simulate the action to determine the resulting state recursively using min value function
            int minimumUtilityAction[] = minValue(results(state, action), alpha, beta, depth + 1);

            //check if best utility is less than minimum utility value
            if (minimumUtilityAction[0] > utilityAction[0]) {
                //update best value
                utilityAction[0] = minimumUtilityAction[0];
                //store move
                utilityAction[1] = action;
            }

            //update the alpha value if best utility is better than alpha
            alpha = Math.max(alpha, utilityAction[0]);

            //prune the search if the current best utility is worse than beta
            if (utilityAction[0] >= beta) {
                return utilityAction;
            }
        }
        
        //return the best utility and action found
        return utilityAction;
    }

    //recursive method to return a minimum utility move pairing
    public int[] minValue(int [][] state, int alpha, int beta, int depth) {
        //create utility-move pair for storing results
        int utilityAction[] = new int [2];

        //check if game is over
        if (terminalTest(state)) {
            //return utility of game state, null action
            utilityAction[0] = utilityFunction(state, getTurn(state));

            return utilityAction;
        }

        //initialize best utility to +inf (worst possible for min)
        utilityAction[0] = Integer.MAX_VALUE;

        //loop through all possible actions from current state
        for (int action : actions(state)) {
            //simulate the action to determine the resulting state recursively using max value function
            int maximumUtilityAction[] = maxValue(results(state, action), alpha, beta, depth + 1);

            //check if best utility is greater than maximum utility value
            if (maximumUtilityAction[0] < utilityAction[0]) {
                //update best value
                utilityAction[0] = maximumUtilityAction[0];
                //store move
                utilityAction[1] = action;
            }

            //update the beta value if best utility is better than beta
            beta = Math.min(beta, utilityAction[0]);

            //prune the search if the current best utility is worse than alpha
            if (utilityAction[0] <= alpha) {
                return utilityAction;
            }
        }

        //return best utility and action found
        return utilityAction;
    }

    // Checks if the player has won or if the game has reached a draw
    public boolean terminalTest(int[][] board) {
        return this.model.checkForWinner() != ConnectFourModel.EMPTY || this.model.checkForDraw();
    }

    //returns available actions based on current state
    public int[] actions(int[][] state) {
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int row = 0; // Only checks the columns

        //add column to moves if it has at least one empty spot at top
        for (int col = 0; col < state[0].length; col++) {
            if (state[row][col] == ConnectFourModel.EMPTY) {
                moves.add(col);
            }
        }

        //convert arraylist to integer array
        int[] results = new int[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            results[i] = moves.get(i);
        }

        return results;
    }

    //returns resulting state from current state and action
    public int[][] results(int[][] state, int action) {

        // deep copy of the board
        int[][] newState = new int[state.length][state[0].length];
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state[0].length; col++) {
                newState[row][col] = state[row][col];
            }
        }

        // determine whose turn it is
        int player = getTurn(state);

        // Find the lowest available row in the selected column
        for (int row = state.length - 1; row >= 0; row--) {
            if (newState[row][action] == ConnectFourModel.EMPTY) { // empty spot found
                newState[row][action] = player;

                break;
            }
        }

        return newState;

    }

    //gets current turn based on number of empty spaces
    protected int getTurn(int[][] state) {
        int empties = 0;

        // count empty spaces on the board
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state[0].length; col++) {
                if (state[row][col] == ConnectFourModel.EMPTY) {
                    empties++;
                }
            }
        }

        // if empty count is odd, player 1 moves else player 2 moves
        return (empties % 2 == 1) ? 2 : 1;
    }

}
