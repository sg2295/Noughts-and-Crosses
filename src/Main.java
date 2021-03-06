import java.util.regex.*;

public class Main {

    private enum BoardState {
        NOUGHTS_WIN, CROSSES_WIN, DRAW,
        INVALID_BOARD, // Board representation is not valid (Length is not 9, does not consist of 'X','O' and '_')
        INVALID_STATE, // The given board does not contain a valid combination of noughts and crosses. (Board is not allowed).
        INCOMPLETE_BOARD  // No winner and the board has empty spaces. Game must end in either a win or draw state.
    }

    /**
     * Checks the length and contents of the board. Returns a boolean value.
     * Length must be equal to 9. Board can only contain noughts ('O'), crosses ('X') and empty slots ('_').
     * @param board The String representation of the board.
     * @return true if the board is valid, false otherwise.
     */
    private static boolean isValidBoard(String board) {
        if (board.length() != 9) return false;  // Board length must be 9 (3x3 board)
        return Pattern.matches("[XO_]+", board); // Use a regular expression to check that the board only contains X, O and _
    }

    /**
     * Checks that the given board contains a valid combination of noughts and crosses.
     * Assumption: Crosses always goes first.
     * @param board The String representation of the board.
     * @return true if the board's state is valid, false otherwise.
     */
    private static boolean isValidBoardState(String board) {
        int noughts = 0, crosses = 0; // Hold the number of noughts and crosses (respectively)
        for (char c: board.toCharArray()) { // Calculate the number of crosses and noughts
            if (c == 'X') crosses++;
            if (c == 'O') noughts++;
        }
        return !(crosses != noughts && crosses != noughts + 1); // #Crosses = #Noughts or #Noughts + 1
    }

    /**
     * Calculates and returns the state of the board.
     * @param board The String representation of the board.
     * @return the resulting BoardState (e.g. CROSSES_WIN, INVALID_BOARD, INVALID_STATE, DRAW)
     */
    private static BoardState getStateOfBoard(String board) {
        if (!isValidBoard(board)) return BoardState.INVALID_BOARD; // Check if the board is valid (length and contents)
        if (!isValidBoardState(board)) return BoardState.INVALID_STATE; // Check if the board's state is acceptable (Possible combination of X, O, _)
        return getBoardResult(board); // The resulting state of the board
    }

    /**
     *
     * @param board
     * @return
     */
    private static BoardState getBoardResult(String board) {
        BoardState horizontalRes = checkHorizontal(board);
        if (isWinState(horizontalRes)) return horizontalRes;
        BoardState verticalRes = checkVertical(board);
        if (isWinState(verticalRes)) return verticalRes;
        BoardState diagonalRes = checkDiagonal(board);
        if (isWinState(diagonalRes)) return diagonalRes;
        return (horizontalRes == verticalRes && verticalRes == diagonalRes && horizontalRes == BoardState.DRAW) ? BoardState.DRAW : BoardState.INCOMPLETE_BOARD;
    }

    /**
     * Checks if the given state is a winning state for either player.
     * @param state The BoardState to be checked.
     * @return true if either player won, false otherwise.
     */
    private static boolean isWinState(BoardState state) {
        return state == BoardState.CROSSES_WIN || state == BoardState.NOUGHTS_WIN;
    }

    /**
     *
     * @param board
     * @return
     */
    private static BoardState checkHorizontal(String board) {
        BoardState state;
        boolean isIncomplete = false; // Used to check if the board has open positions
        for (int i = 0; i < 9; i += 3) {
            state = checkState(board.charAt(i), board.charAt(i + 1), board.charAt(i + 2));
            if (isWinState(state)) {
                return state;
            } else if (state == BoardState.INCOMPLETE_BOARD) isIncomplete = true;
        }
        return isIncomplete ? BoardState.INCOMPLETE_BOARD : BoardState.DRAW;
    }

    /**
     *
     * @param board
     * @return
     */
    private static BoardState checkVertical(String board) {
        BoardState state;
        boolean isIncomplete = false; // Used to check if the board has open positions
        for (int i = 0; i < 3; i++) {
            state = checkState(board.charAt(i), board.charAt(i + 3), board.charAt(i + 6));
            if (isWinState(state)) {
                return state;
            } else if (state == BoardState.INCOMPLETE_BOARD) isIncomplete = true;
        }
        return isIncomplete ? BoardState.INCOMPLETE_BOARD : BoardState.DRAW;
    }

    /**
     *
     * @param board
     * @return
     */
    private static BoardState checkDiagonal(String board) {
        boolean isIncomplete = false; // Used to check if the board has open positions
        // Check first diagonal
        BoardState state = checkState(board.charAt(0), board.charAt(4), board.charAt(8));
        if (isWinState(state)) {
            return state;
        } else if (state == BoardState.INCOMPLETE_BOARD) isIncomplete = true;
        // Check second diagonal
        state = checkState(board.charAt(2), board.charAt(4), board.charAt(6));
        if (isWinState(state)) {
            return state;
        } else if (state == BoardState.INCOMPLETE_BOARD) isIncomplete = true;

        return isIncomplete ? BoardState.INCOMPLETE_BOARD : BoardState.DRAW;
    }

    /**
     * Checks if the state of the given character triad (three characters). Evaluates if a player won,
     * if it is a draw, or if the set is incomplete. Returns a BoardState corresponding to each outcome.
     * @param x First character
     * @param y Second character
     * @param z Third character
     * @return CROSSES_WIN if the characters are crosses, NOUGHTS_WIN if the characters are noughts, DRAW
     * if the set is complete with no winner, and INCOMPLETE if there are available moves
     */
    private static BoardState checkState(char x, char y, char z) {
        if (x == y && y == z && x != '_') {
            return x == 'X' ? BoardState.CROSSES_WIN  : BoardState.NOUGHTS_WIN;
        } else {
            return (x == '_' || y == '_' || z == '_') ? BoardState.INCOMPLETE_BOARD : BoardState.DRAW;
        }
    }

    /**
     * Prints the given board for debugging purposes. Assumes the board meets the required criteria.
     * @param board The String representation of the board
     */
    private static void printBoard(String board) {
        System.out.println("----\nBoard: ");
        for (int i = 0; i < 9; i+=3) {
            System.out.println(board.charAt(i) + " " + board.charAt(i + 1) + " " + board.charAt(i + 2));
        }
        System.out.println("----");
    }

    private static Board.State driver(String board) {
        Board b = new Board(board);
        return b.getBoardState();
    }

    public static void main(String[] args) {
        // Main (driver) function, for illustrating code functionality
        String[] boards = {"XXXOO____", "XX_OOOX__", "X_OOO_XXX", "XXXOXOXOO", "XOOOXXXXO", "_________", "_X_XX_X__",
        "XXXXOXOOO", "XXXXOOXOO"};
        for(String board: boards) {
            printBoard(board);
            System.out.println(getStateOfBoard(board));
            System.out.println(driver(board) + "\n");

        }
    }
}
