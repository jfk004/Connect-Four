import copy


class TTT3PlayerHumanPlayer:
    def __init__(self, model, symbol):
        self.model = model
        self.symbol = symbol

    def is_automated(self):
        return False

    def get_move(self):
        valid_input = False
        valid_moves = self.model.get_valid_moves()

        while not valid_input:
            try:
                move = int(input('Enter move (1-16): '))
                if move < 1 or move > 16:
                    raise ValueError()
                else:
                    valid_input = True

                if move in valid_moves:
                    return move
                else:
                    print('That spot is full. Pick again.')
                    valid_input = False
            except ValueError:
                print('Invalid input.')

        # Should not get here
        return -1


class TTT3PlayerAIPlayer:
    def __init__(self, model, symbol):
        self.model = model
        self.symbol = symbol
        self.symbol_player = {'X':0, 'O':1, '+':2}
        self.player_number = self.symbol_player[self.symbol]

    def is_automated(self):
        return True

    # Assume actions are numbered 1-16
    def result(self, state, action):
        newstate = copy.deepcopy(state)
        turn = self._get_turn(state)

        action -= 1 # Adjustment of 1
        col = action % 4
        row = action // 4
        newstate[row][col] = turn

        return newstate

    def actions(self, state):
        moves = []
        for row in range(4):
            for col in range(4):
                if state[row][col] is None:
                    moves.append(row*4 + col + 1)
        return moves

    def terminal_test(self, state):
        # Check for horizontal win
        for row in range(4):
            for startcol in range(2):
                if state[row][startcol] is not None and state[row][startcol] == state[row][startcol+1] and state[row][startcol] == state[row][startcol+2]:
                    return True
        # Check for vertical win
        for col in range(4):
            for startrow in range(2):
                if state[startrow][col] is not None and state[startrow][col] == state[startrow+1][col] and state[startrow][col] == state[startrow+2][col]:
                    return True
        # Check for diagonal \ win
        for startrow in range(2):
            for startcol in range(2):
                if state[startrow][startcol] is not None and state[startrow][startcol] == state[startrow+1][startcol+1] and state[startrow][startcol] == state[startrow+2][startcol+2]:
                    return True
        # Check for diagonal / win
        for startrow in range(2,4):
            for startcol in range(2):
                if state[startrow][startcol] is not None and state[startrow][startcol] == state[startrow-1][startcol+1] and state[startrow][startcol] == state[startrow-2][startcol+2]:
                    return True
        
        return self._is_draw(state)

    def utility(self, state):
        winning_symbol = self._get_winner(state)
        if winning_symbol == self.symbol:
            vector = [-1000,-1000,-1000]
            vector[self.player_number] = 1000
            return vector
        elif winning_symbol is not None:
            vector = [-1000, -1000, -1000]
            index = self.symbol_player[winning_symbol]
            vector[index] = 1000
            return vector
        if self._is_draw(state):
            return [0,0,0]

        return [-1,-1,-1] # Should not happen

    def _is_draw(self, state):
        all_filled = True
        for row in range(4):
            for col in range(4):
                if state[row][col] is None:
                    all_filled = False
        return all_filled

    def _get_winner(self, state):
        # Check for horizontal win
        for row in range(4):
            for startcol in range(2):
                if state[row][startcol] is not None and state[row][startcol] == state[row][startcol+1] and state[row][startcol] == state[row][startcol+2]:
                    return state[row][startcol]
        # Check for vertical win
        for col in range(4):
            for startrow in range(2):
                if state[startrow][col] is not None and state[startrow][col] == state[startrow+1][col] and state[startrow][col] == state[startrow+2][col]:
                    return state[0][col]
        # Check for diagonal \ win
        for startrow in range(2):
            for startcol in range(2):
                if state[startrow][startcol] is not None and state[startrow][startcol] == state[startrow+1][startcol+1] and state[startrow][startcol] == state[startrow+2][startcol+2]:
                    return state[startrow][startcol]
        # Check for diagonal / win
        for startrow in range(2,4):
            for startcol in range(2):
                if state[startrow][startcol] is not None and state[startrow][startcol] == state[startrow-1][startcol+1] and state[startrow][startcol] == state[startrow-2][startcol+2]:
                    return state[startrow][startcol]

        return None # Uh-oh

    def _get_turn(self, state):
        empties = 0
        for row in range(4):
            for col in range(4):
                if state[row][col] is None:
                    empties += 1

        if empties % 3 == 1:
            return 'X'
        elif empties % 3 == 0:
            return 'O'
        else:
            return '+'

    def get_move(self):
        """INSERT YOUR CODE HERE -- Should return an integer between 1-16"""
        pass


