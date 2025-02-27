class IllegalMoveError(ValueError):
    def __init__(self, column, player):
        super().__init__("Illegal move "+str(column)+" by player "+str(player))
        self.__player = player

    def get_player(self):
        return self.__player
