import c4view


class ConnectFourController:
    def __init__(self, model, p1, p2, verbose=False):
        self.players = [p1, p2]
        self.model = model
        self.model.initialize()
        self.model.register_result_observer(self)
        self.game_winner = None

        if verbose:
            self.view = c4view.ConnectFourConsoleView(model, self)
        else:
            self.view = c4view.ConnectFourSilentView(model, self)

    def start(self):
        self.view.create_view()
        self.view.play_game()
        return self.game_winner

    def place_token(self, column):
        row = self.model.set_grid_position(column, self.model.get_turn())
        self.model.next_player()
        if row == 0:
            self.view.disable_column(column)

    def reset(self):
        self.model.initialize()
        for col in range(7):
            self.view.enable_column(col)

    def quit(self):
        import sys
        sys.exit(0)

    def get_player(self, p):
        return self.players[p-1]

    def report_result(self, result):
        self.game_winner = result
        if result > 0:
            self.view.announce_winner(result)
        else:
            self.view.announce_draw()
        for col in range(7):
            self.view.disable_column(col)
