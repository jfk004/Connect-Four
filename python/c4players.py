import random


class ConnectFourPlayer:
    def get_move(self):
        # Must return a value between 0 and 6 (inclusive), where 0 is the left-most column and 6 is the right-most column.
        raise NotImplementedError('Must be implemented by subclass')

    def is_automated(self):
        # AI players should return True, human players should return False
        return True


class ConnectFourHumanPlayer(ConnectFourPlayer):
    def __init__(self, model):
        self.model = model

    def is_automated(self):
        return False

    def get_move(self):
        valid_input = False
        valid_columns = self.model.get_valid_moves()

        while not valid_input:
            try:
                column = int(input('Enter column (1-7): '))
                if column < 1 or column > 7:
                    raise ValueError()
                else:
                    valid_input = True

                if valid_columns[column-1]:
                    return column-1
                else:
                    print('That column is full. Pick again.')
                    valid_input = False
            except ValueError:
                print('Invalid input.')

        # Should not get here
        return -1


class ConnectFourRandomPlayer(ConnectFourPlayer):
    def __init__(self, model):
        self.model = model

    def get_move(self):
        moves = self.model.get_valid_moves()
        m = random.randrange(7)
        while not moves[m]:
            m = random.randrange(7)
        return m
