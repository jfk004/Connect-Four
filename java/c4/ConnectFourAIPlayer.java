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
        // Check for terminal states (win, lose, or draw)
        if (this.model.checkForDraw()) {
            return 0; // Draw
        } else if (this.model.checkForWinner() == player) {
            return 2000; // Current player wins
        } else if (this.model.checkForWinner() != -1) {
            return -2000; // Opponent wins
        }
        
        // Evaluate the board state for non-terminal positions
        int score = 0;
    
        // Evaluate horizontal, vertical, and diagonal patterns
        score += evaluatePatterns(state, player);
    
        // Invert score if the current player is 'O' (minimizing player)
        if (player == 'O') {
            score = -score;
        }
    
        return score;
    }
    
    // Helper method to evaluate patterns (horizontal, vertical, diagonal)
    private int evaluatePatterns(int[][] state, int player) {
        int score = 0;
    
        // Evaluate horizontal patterns
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                score += evaluateLine(state, row, col, 0, 1, player); // Horizontal
            }
        }
    
        // Evaluate vertical patterns
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= 2; row++) {
                score += evaluateLine(state, row, col, 1, 0, player); // Vertical
            }
        }
    
        // Evaluate positive diagonal patterns (top-left to bottom-right)
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 3; col++) {
                score += evaluateLine(state, row, col, 1, 1, player); // Positive diagonal
            }
        }
    
        // Evaluate negative diagonal patterns (bottom-left to top-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                score += evaluateLine(state, row, col, -1, 1, player); // Negative diagonal
            }
        }
    
        return score;
    }
    
    // Helper method to evaluate a line of 4 cells
    private int evaluateLine(int[][] state, int startRow, int startCol, int rowDelta, int colDelta, int player) {
        int playerCount = 0; // Count of current player's tokens
        int opponentCount = 0; // Count of opponent's tokens
        int emptyCount = 0; // Count of empty cells
    
        // Evaluate the 4 cells in the line
        for (int i = 0; i < 4; i++) {
            int row = startRow + i * rowDelta;
            int col = startCol + i * colDelta;
            int cell = state[col][row];
    
            if (cell == player) {
                playerCount++;
            } else if (cell == -1) {
                emptyCount++;
            } else {
                opponentCount++;
            }
        }
    
        // Assign scores based on the pattern
        if (playerCount == 4) {
            return 1000; // Current player has 4 in a row (win)
        } else if (opponentCount == 4) {
            return -1000; // Opponent has 4 in a row (loss)
        } else if (playerCount == 3 && emptyCount == 1) {
            return 100; // Current player has 3 in a row with an empty spot
        } else if (opponentCount == 3 && emptyCount == 1) {
            return -100; // Opponent has 3 in a row with an empty spot
        } else if (playerCount == 2 && emptyCount == 2) {
            return 50; // Current player has 2 in a row with two empty spots
        } else if (opponentCount == 2 && emptyCount == 2) {
            return -50; // Opponent has 2 in a row with two empty spots
        }
    
        return 0; // No significant pattern
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
