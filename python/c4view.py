import c4model
import time


class ConnectFourViewBase:
    def create_view(self):
        raise NotImplementedError('Must be implemented by subclass')

    def play_game(self):
        raise NotImplementedError('Must be implemented by subclass')

    def enable_column(self, column):
        raise NotImplementedError('Must be implemented by subclass')

    def disable_column(self, column):
        raise NotImplementedError('Must be implemented by subclass')

    def announce_winner(self, winner):
        raise NotImplementedError('Must be implemented by subclass')

    def announce_draw(self):
        raise NotImplementedError('Must be implemented by subclass')


class ConnectFourConsoleView(ConnectFourViewBase):
    def __init__(self, model, con):
        self.model = model
        self.controller = con
        self.game_over = False
        self.valid_columns = [True]*7
        self.grid_output = ''

    def create_view(self):
        self.grid_output = '- - - - - - -\n'*6
        print(self.grid_output)

        self.model.register_grid_observer(self)
        self.model.register_result_observer(self)

    def play_game(self):
        self.game_over = False
        while not self.game_over:
            player_num = self.model.get_turn()
            if player_num == c4model.PLAYER1:
                print('Current turn: PLAYER 1')
            else:
                print('Current turn: PLAYER 2')

            player = self.controller.get_player(player_num)

            move = player.get_move()
            self.controller.place_token(move)

    def update_grid(self):
        grid = self.model.get_grid()
        grid_output = ''

        for j in range(6):
            row = ''
            for i in range(7):
                if grid[i][j] == c4model.PLAYER1:
                    row += 'X '
                elif grid[i][j] == c4model.PLAYER2:
                    row += 'O '
                else:
                    row += '- '
            row += '\n'
            grid_output += row

        print(grid_output)

    def enable_column(self, column):
        self.valid_columns[column] = True

    def disable_column(self, column):
        self.valid_columns[column] = False

    def announce_winner(self, winner):
        print('Player '+str(winner)+' wins!')

    def announce_draw(self):
        print("It's a draw!")

    def report_result(self, result):
        self.game_over = True


class ConnectFourSilentView(ConnectFourViewBase):
    def __init__(self, model, con):
        self.model = model
        self.controller = con
        self.game_over = False
        self.valid_columns = [True]*7

    def create_view(self):
        self.model.register_result_observer(self)

    def play_game(self):
        self.game_over = False
        while not self.game_over:
            player = self.controller.get_player(self.model.get_turn())
            self.controller.place_token(player.get_move())

    def enable_column(self, column):
        self.valid_columns[column] = True

    def disable_column(self, column):
        self.valid_columns[column] = False

    def announce_winner(self, winner):
        pass

    def announce_draw(self):
        pass

    def report_result(self, result):
        self.game_over = True


