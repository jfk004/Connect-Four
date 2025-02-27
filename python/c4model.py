from c4exceptions import IllegalMoveError

PLAYER1 = 1
PLAYER2 = 2
EMPTY = -1

NUMROWS = 6
NUMCOLS = 7

class ConnectFourModel:
    def __init__(self):
        self.__grid = None
        self.__turn = -1
        self.__grid_observers = []
        self.__result_observers = []

    def initialize(self):
        self.__grid = []
        for i in range(NUMCOLS):
            column = []
            for j in range(NUMROWS):
                column.append(EMPTY)
            self.__grid.append(column)

        self.__turn = PLAYER1
        self.__notify_grid_observers()

    def set_grid_position(self, column, player):
        if column < 0 or column > NUMCOLS-1:
            raise IllegalMoveError(column, player)

        row = NUMROWS - 1
        while self.__grid[column][row] != EMPTY:
            row -= 1

        if row < 0:
            raise IllegalMoveError(column, player)

        self.__grid[column][row] = player
        self.__notify_grid_observers()

        result = self.check_for_winner()
        if result > 0:
            self.__notify_result_observers(result)
        elif self.check_for_draw():
            self.__notify_result_observers(0)

        return row

    def __notify_grid_observers(self):
        for o in self.__grid_observers:
            o.update_grid()

    def __notify_result_observers(self, result):
        for o in self.__result_observers:
            o.report_result(result)

    def check_for_winner(self):
        win = self.__check_horizontal_win()
        if win < 0:
            win = self.__check_vertical_win()
        if win < 0:
            win = self.__check_neg_diagonal_win()
        if win < 0:
            win = self.__check_pos_diagonal_win()

        return win

    def __check_horizontal_win(self):
        win = False
        for row in range(NUMROWS):
            for col in range(4):
                if self.__grid[col][row] != EMPTY:
                    win = (self.__grid[col][row] == self.__grid[col + 1][row]) and (
                        self.__grid[col][row] == self.__grid[col + 2][row]) and (
                              self.__grid[col][row] == self.__grid[col + 3][row])
                if win:
                    return self.__grid[col][row]
        return -1

    def __check_vertical_win(self):
        win = False
        for col in range(NUMCOLS):
            for row in range(3):
                if self.__grid[col][row] != EMPTY:
                    win = (self.__grid[col][row] == self.__grid[col][row + 1]) and (
                        self.__grid[col][row] == self.__grid[col][row + 2]) and (
                              self.__grid[col][row] == self.__grid[col][row + 3])
                if win:
                    return self.__grid[col][row]
        return -1

    def __check_neg_diagonal_win(self):
        win = False
        for col in range(4):
            for row in range(3):
                if self.__grid[col][row] != EMPTY:
                    win = (self.__grid[col][row] == self.__grid[col + 1][row + 1]) and (
                        self.__grid[col][row] == self.__grid[col + 2][row + 2]) and (
                              self.__grid[col][row] == self.__grid[col + 3][row + 3])
                if win:
                    return self.__grid[col][row]
        return -1

    def __check_pos_diagonal_win(self):
        win = False
        for col in range(3, 7):
            for row in range(3):
                if self.__grid[col][row] != EMPTY:
                    win = (self.__grid[col][row] == self.__grid[col - 1][row + 1]) and (
                        self.__grid[col][row] == self.__grid[col - 2][row + 2]) and (
                              self.__grid[col][row] == self.__grid[col - 3][row + 3])
                if win:
                    return self.__grid[col][row]
        return -1

    def check_for_draw(self):
        for i in range(NUMCOLS):
            for j in range(NUMROWS):
                if self.__grid[i][j] == EMPTY:
                    return False
        return True

    def next_player(self):
        if self.__turn == PLAYER1:
            self.__turn = PLAYER2
        else:
            self.__turn = PLAYER1

    def get_turn(self):
        return self.__turn

    def get_grid(self):
        return self.__grid

    def get_valid_moves(self):
        return [(self.__grid[x][0] == EMPTY) for x in range(NUMCOLS)]

    def register_grid_observer(self, o):
        self.__grid_observers.append(o)

    def register_result_observer(self, o):
        self.__result_observers.append(o)

    def remove_grid_observer(self, o):
        try:
            self.__grid_observers.remove(o)
        except ValueError:
            pass

    def remove_result_observer(self, o):
        try:
            self.__result_observers.remove(o)
        except ValueError:
            pass
