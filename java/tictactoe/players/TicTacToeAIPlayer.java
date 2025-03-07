package tictactoe.players;

import java.util.ArrayList;

import tictactoe.mvc.TicTacToeModel;

public class TicTacToeAIPlayer extends TicTacToePlayer {
	TicTacToeModel model;
	char symbol;
	private int cutoff;
	
	public TicTacToeAIPlayer(TicTacToeModel model, char symbol){
		this.model = model;
		this.symbol = symbol;
		this.cutoff = 9;
	}

	public TicTacToeAIPlayer(TicTacToeModel model, char symbol, int cutoff){
		this.model = model;
		this.symbol = symbol;
		this.cutoff = cutoff;
	}
	
	// Assume actions are numbered 1-9
	public char[][] result(char[][] state, int action){
		// Deep copy the state
		char[][] newstate = new char[3][3];
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				newstate[row][col] = state[row][col];
		
		char turn = this.getTurn(state);
		
		action -= 1;
		int col = action % 3;
		int row = action / 3;
		newstate[row][col] = turn;
		
		return newstate;
	}
	
	public int[] actions(char[][] state){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				if(state[row][col] == '-')
					moves.add(row*3+col+1);
		
		int[] results = new int[moves.size()];
		for(int i=0; i<results.length; i++)
			results[i] = moves.get(i);
		
		return results;
	}
	
	public boolean terminalTest(char[][] state){
		for(int row=0; row<3; row++){
			if(state[row][0] != '-' && state[row][0] == state[row][1] && state[row][0] == state[row][2])
				return true;
		}
		for(int col=0; col<3; col++){
			if(state[0][col] != '-' && state[0][col] == state[1][col] && state[0][col] == state[2][col])
				return true;
		}
		if(state[0][0] != '-' && state[0][0] == state[1][1] && state[0][0] == state[2][2])
				return true;
		if(state[2][0] != '-' && state[2][0] == state[1][1] && state[2][0] == state[0][2])
				return true;
		
		return isDraw(state);
	}
	
	public int utility(char[][] state){
		if(getWinner(state) == symbol)
			return 1000;
		else if(getWinner(state) != '-')
			return -1000;
		else if(isDraw(state))
			return 0;

		//checks if values are next to eachother for columns and rows
		int score = 0;
		for(int rowcol = 1; rowcol < 3; rowcol ++){
			//checks vertical
			if(state[rowcol][0] == state[rowcol][1] && state[rowcol][2] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
			if(state[rowcol][1] == state[rowcol][2] && state[rowcol][0] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
			if(state[rowcol][0] == state[rowcol][2] && state[rowcol][1] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
			//checks horizontal
			if(state[0][rowcol] == state[1][rowcol] && state[rowcol][2] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
			if(state[1][rowcol] == state[2][rowcol] && state[rowcol][0] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
			if(state[0][rowcol] == state[2][rowcol] && state[rowcol][1] == '-'){
				//ensures only x or o 
				if(state[rowcol][1] == 'X'){
					score += 100;
				} else if (state[rowcol][1] == 'O'){
					score -= 100;
				}
			} 
		}

		//checks if the diagonals are unblocked
		if(state[0][0] == state[2][2] && state[1][1] == '-'){
			//ensures only x or o 
			if(state[0][0] == 'X'){
				score += 100;
			} else if (state[0][0] == 'O'){
				score -= 100;
			}
		}
		if(state[0][2] == state[2][0] && state[1][1] == '-'){
			//ensures only x or o 
			if(state[0][2] == 'X'){
				score += 100;
			} else if (state[0][2] == 'O'){
				score -= 100;
			}
		}
		if(state[0][0] == state[1][1] && state[2][2] == '-'){
			//ensures only x or o 
			if(state[1][1] == 'X'){
				score += 100;
			} else if (state[1][1] == 'O'){
				score -= 100;
			}
		}
		if(state[1][1] == state[2][2] && state[0][0] == '-'){
			//ensures only x or o 
			if(state[1][1] == 'X'){
				score += 100;
			} else if (state[1][1] == 'O'){
				score -= 100;
			}
		}
		if(state[0][2] == state[1][1] && state[2][0] == '-'){
			//ensures only x or o 
			if(state[1][1] == 'X'){
				score += 100;
			} else if (state[1][1] == 'O'){
				score -= 100;
			}
		}
		if(state[1][1] == state[2][0] && state[0][2] == '-'){
			//ensures only x or o 
			if(state[1][1] == 'X'){
				score += 100;
			} else if (state[1][1] == 'O'){
				score -= 100;
			}
		}

 
		//inverts score depending on who player is
		score = this.getTurn(state) == 'X' ? score : -score;
		
		return score; 
	}
	
	protected boolean isDraw(char[][] state){
		boolean allFilled = true;
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				if(state[row][col] == '-')
					allFilled = false;
		return allFilled;
	}
	
	protected char getWinner(char[][] state){
		for(int row=0; row<3; row++){
			if(state[row][0] != '-' && state[row][0] == state[row][1] && state[row][0] == state[row][2])
				return state[row][0];
		}
		for(int col=0; col<3; col++){
			if(state[0][col] != '-' && state[0][col] == state[1][col] && state[0][col] == state[2][col])
				return state[0][col];
		}
		if(state[0][0] != '-' && state[0][0] == state[1][1] && state[0][0] == state[2][2])
				return state[0][0];
		if(state[2][0] != '-' && state[2][0] == state[1][1] && state[2][0] == state[0][2])
				return state[2][0];
		
		return '-'; // Should not happen
	}
	
	protected char getTurn(char[][] state){
		int empties = 0;
		for(int row=0; row<3; row++)
			for(int col=0; col<3; col++)
				if(state[row][col] == '-')
					empties++;
		
		if(empties%2 == 1)
			return 'X';
		else
			return 'O';
	}
	
	public int getMove(){
		//run and return alpha beta search
        return alphaBetaSearch(this.model.getGrid());
	}
	
	public int alphaBetaSearch(char[][] state){
		int depth = 0;
		//begin recursive search with the max value function, alpha and beta each set to negative and positive infinity
        int utilityAction[] = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

        //return action after recursion ends
        return utilityAction[1];
	}
	
	public int[] maxValue(char[][] state, int alpha, int beta, int depth){
		//create utility-action pair for storing results
        int utilityAction[] = new int [2];

        //check if game is over and if cutoff
        if ( terminalTest(state) || depth == cutoff) {
            //return utility of game state, null action
            utilityAction[0] = utility(state);

            return utilityAction;
        }

        //initialize best utility to -inf (worst possible for max)
        utilityAction[0] = Integer.MIN_VALUE;

        //loop through all possible actions from current state
        for (int action : actions(state)) {
            //simulate the action to determine the resulting state recursively using min value function
            int minimumUtilityAction[] = minValue(result(state, action), alpha, beta, depth + 1);

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
	
	public int[] minValue(char[][] state, int alpha, int beta, int depth){
		//create utility-move pair for storing results
        int utilityAction[] = new int [2];

        //check if game is over
        if (depth == cutoff || terminalTest(state)) {
            //return utility of game state, null action
            utilityAction[0] = utility(state);

            return utilityAction;
        } 

        //initialize best utility to +inf (worst possible for min)
        utilityAction[0] = Integer.MAX_VALUE;

        //loop through all possible actions from current state
        for (int action : actions(state)) {
            //simulate the action to determine the resulting state recursively using max value function
            int maximumUtilityAction[] = maxValue(result(state, action), alpha, beta, depth + 1);

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
}
