from ttt3player_mvc import *
from ttt3player_players import *


def main():
    play_single_game()


def play_single_game():
    model = TTT3PlayerModel()

    # Human is X, AI is O, Human is +
    # Feel free to rotate which type of player is which
    player1 = TTT3PlayerHumanPlayer(model, 'X')
    player2 = TTT3PlayerAIPlayer(model, 'O')
    player3 = TTT3PlayerHumanPlayer(model, '+')

    # Player 1 must be X player, Player 2 must be O player
    # and Player 3 must be + player
    controller = TTT3PlayerController(model, player1, player2, player3)

    winner = controller.start()


if __name__ == '__main__':
    main()
