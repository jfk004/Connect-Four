import c4model
import c4players
import c4controller
from c4exceptions import IllegalMoveError


def main():
    # Choose one of the following
    # play_single_game()
    play_batch_games(500)


def play_single_game():
    model = c4model.ConnectFourModel()

    # Change the constructor calls to change the players used
    player1 = c4players.ConnectFourHumanPlayer(model)
    player2 = c4players.ConnectFourRandomPlayer(model)

    # Choose 1 of the Controller/View set-ups below

    # Sets up 'silent' view -- No output
    # controller = c4controller.ConnectFourController(model, player1, player2)

    # Sets up console view -- All output to console
    controller = c4controller.ConnectFourController(model, player1, player2, verbose=True)

    # Start game
    winner = controller.start()


def play_batch_games(games):
    results = [0, 0, 0]  # Draws, P1 wins, P2 wins
    forfeits = {1: 0, 2: 0}  # Each player starts with 0 forfeits
    for i in range(games):
        try:
            model = c4model.ConnectFourModel()

            # Change the constructor calls to change the players used. Don't use HumanPlayer here.
            player1 = c4players.ConnectFourRandomPlayer(model)
            player2 = c4players.ConnectFourRandomPlayer(model)

            controller = c4controller.ConnectFourController(model, player1, player2)

            # print('Starting game', i) # Useful for debugging

            winner = controller.start()
            results[winner] += 1
        except IllegalMoveError as e:
            player = e.get_player()
            forfeits[player] += 1
            results[3 - player] += 1  # Award a win to the other player

    print('Player 1 record (W-L-D): ' + str(results[1]) + '-' + str(results[2]) + '-' + str(results[0]) + ' (including '+str(forfeits[1])+' forfeits)')
    print('Player 2 record (W-L-D): ' + str(results[2]) + '-' + str(results[1]) + '-' + str(results[0]) + ' (including '+str(forfeits[2])+' forfeits)')


if __name__ == '__main__':
    main()
