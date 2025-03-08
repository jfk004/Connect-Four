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
        // Check for terminal
        if (this.model.checkForDraw()) {
            return 0; // draw
        } else if (this.model.checkForWinner() == player) {
            return 2000; // win
        } else if (this.model.checkForWinner() != -1) {
            return -2000; // loss
        }
       
        // utility return if not terminal
        int score = 0;
   
        // Test all directions            
        // horizontal
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                //track spot types
                int play = 0;
                int opp = 0;
                int empty = 0;
               
                //check 4 positions of row
                for (int traverse = 0; traverse < 4; traverse++) {
                    int travRow = row;
                    int travCol = col + traverse;
                    int position = state[travCol][travRow];
           
                    if (position == player) {
                        play++;
                    } else if (position == -1) {
                        empty++;
                    } else {
                        opp++;
                    }
                }

                //adds to score if 2 or 3 in a row
                score += measureScore(play,opp,empty);
            }
        }
   
        // vertical
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= 2; row++) {
                //track spot types
                int play = 0;
                int opp = 0;
                int empty = 0;
               
                //check 4 positions of column
                for (int traverse = 0; traverse < 4; traverse++) {
                    int travRow = row + traverse;
                    int travCol = col;
                    int position = state[travCol][travRow];
           
                    if (position == player) {
                        play++;
                    } else if (position == -1) {
                        empty++;
                    } else {
                        opp++;
                    }
                }

                //adds to score if 2 or 3 in a row
                score += measureScore(play,opp,empty);
            }
        }
   
        // diagonal (left to right)
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 3; col++) {
                //track spot types
                int play = 0;
                int opp = 0;
                int empty = 0;
               
                //check 4 positions of column
                for (int traverse = 0; traverse < 4; traverse++) {
                    int travRow = row + traverse;
                    int travCol = col + traverse;
                    int position = state[travCol][travRow];
           
                    if (position == player) {
                        play++;
                    } else if (position == -1) {
                        empty++;
                    } else {
                        opp++;
                    }
                }

                //adds to score if 2 or 3 in a row
                score += measureScore(play,opp,empty);
            }
        }
   
        // diagonal (right to left))
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                //track spot types
                int play = 0;
                int opp = 0;
                int empty = 0;
               
                //check 4 positions of column
                for (int traverse = 0; traverse < 4; traverse++) {
                    int travRow = row - traverse;
                    int travCol = col + traverse;
                    int position = state[travCol][travRow];
           
                    if (position == player) {
                        play++;
                    } else if (position == -1) {
                        empty++;
                    } else {
                        opp++;
                    }
                }

                //adds to score if 2 or 3 in a row
                score += measureScore(play,opp,empty);
            }
        }
   
        //inverts if opposite player
        score = (player == this.model.getTurn()) ? score : -score;

        return score;
    }


    //for each 4 spots checked, is there an important value that should be taken into account (2 or 3 or 4 in a row)
    private int measureScore(int player, int opponent, int empty){
        //heuristic utility value
        //if four in a row (just in case during iteration)
        if(player == 4){
            return 1000; 
        } else if (opponent ==4){
            return -1000;
        }
        //xxx- or xx-x or x-xx or -xxx
        else if (player == 3 && empty == 1) {
            return 100; 
        } else if (opponent == 3 && empty == 1) {
            return -100; 
        //xx-- or x-x- or x--x or -x-x or --xx or -xx-
        } else if (player == 2 && empty == 2) {
            return 50; 
        } else if (opponent == 2 && empty == 2) {
            return -50; 
        }
   
        return 0; //no wins, triples or doubles
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
        if ((terminalTest(state) || depth == cutoff)) {
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

        //check available moves method to see if move possible
        boolean validMoves[] = this.model.getValidMoves();

        //add column to moves if it has at least one empty spot at top
        for (int col = 0; col < 7; col++) {
            if (validMoves[col])
                moves.add(col);
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
        int[][] newState = new int[7][6];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                newState[col][row] = state[col][row];
            }
        }

        // determine whose turn it is
        int player = getTurn(state);

        // Find the lowest available row in the selected column
        for (int row = 5; row >= 0; row--) {
            if (newState[action][row] == ConnectFourModel.EMPTY) { // empty spot found
                newState[action][row] = player;

                break;
            }
        }

        return newState;

    }

    //gets current turn based on number of empty spaces
    protected int getTurn(int[][] state) {
        int empties = 0;

        // count empty spaces on the board
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (state[col][row] == ConnectFourModel.EMPTY) {
                    empties++;
                }
            }
        }

        // if empty count is odd, player 1 moves else player 2 moves
        return (empties % 2 == 1) ? 2 : 1;
    }

}
