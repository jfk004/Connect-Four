from tictactoe_mvc import *
from tictactoe_players import *


def main():
    play_single_game()


def play_single_game():
    model = TicTacToeModel()

    # Human is X, AI is O
    player1 = TicTacToeHumanPlayer(model, 'X')
    player2 = TicTacToeAIPlayer(model, 'O')

    # AI is X, Human is O
    # player1 = TicTacToeAIPlayer(model, 'X')
    # player2 = TicTacToeHumanPlayer(model, 'O')

    # Player 1 must be X player, Player 2 must be O player
    controller = TicTacToeController(model, player1, player2)

    winner = controller.start()


if __name__ == '__main__':
    main()
