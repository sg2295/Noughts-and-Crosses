# Noughts and Crosses
The solution is capable of calculating the state of a given noughts and crosses board. It can detect:
- The winner of the game or if it is a draw
- An ongoing game, i.e. there are still possible moves
- Detect if the given board configuration is invalid whether it is a draw, and recognise invalid board states)

## The Game
Noughts and Crosses (or Tic-tac-toe) is a two-player game played on a 3x3 board, where the players take turns in assigning noughts ('O') and crosses ('X'). The game ends when one of the players places three of their symbol ('X' or 'O') in a row horizontally, vertically or diagonally. The game is declared a draw if all slots of the board are populated, and neither player has won.

## Rules 📜
- Crosses always go first.
- Players must make a move during their turn in one of the empty slots.
- Game ends if a player gets three of their symbol in a row, or if there are no empty slots left. 

## Board Representation 
The board is represented as a String of nine characters, corresponding to the nine slots, starting from the top left and moving across each row. 'X' is used to represent Crosses, 'O' is used for Noughts, and '\_' to signify empty slots. The solution can handle _any_ permutation with repetition of the characters.
