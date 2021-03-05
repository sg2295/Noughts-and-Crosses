import java.util.regex.*;

public class Main {

    private enum BoardState {
        NOUGHTS_WIN, CROSSES_WIN, DRAW,
        INVALID_BOARD, INVALID_STATE, INCOMPLETE
    }

    /**
     * Checks the length and contents of the board. Returns a boolean value.
     * Length must be equal to 9. Board can only contain noughts ('O'), crosses ('X') and empty slots ('_').
     * @param board The String representation of the board.
     * @return true if the board is valid, false otherwise.
     */
    private static boolean isValidBoard(String board) {
        if (board.length() != 9) return false;  // Board is of right length
        return Pattern.matches("[XO_]+", board); // Use a regular expression to check that the board only contains noughts, crosses or empty spaces
    }

    private static boolean isValidBoardState(String board) {
        boolean isValid = true;
        int noughts = 0, crosses = 0;
        for (char c: board.toCharArray()) {
            if (c == 'X') crosses++;
            if (c == 'O') noughts++;
        }
        if (crosses != noughts && crosses != noughts + 1) isValid = false;
        // Noughts OR crosses are more than 3
        return isValid;
    }

    private static BoardState getStateOfBoard(String board) {
        // Todo: could merge the error checking
        if (!isValidBoard(board)) return BoardState.INVALID_BOARD;
        if (!isValidBoardState(board)) return BoardState.INVALID_STATE;
        return getBoardResult(board); // Get the result of the board
    }

    private static BoardState getBoardResult(String board) {
        BoardState horizontalRes = checkHorizontal(board);
        if (isWinState(horizontalRes)) return horizontalRes;
        BoardState verticalRes = checkVertical(board);
        if (isWinState(verticalRes)) return verticalRes;
        BoardState diagonalRes = checkDiagonal(board);
        if (isWinState(diagonalRes)) return diagonalRes;
        return (horizontalRes == verticalRes && verticalRes == diagonalRes && horizontalRes == BoardState.DRAW) ? BoardState.DRAW : BoardState.INCOMPLETE;
    }

    private static boolean isWinState(BoardState state) {
        return state == BoardState.CROSSES_WIN || state == BoardState.NOUGHTS_WIN;
    }

    private static BoardState checkHorizontal(String board) {
        BoardState state;
        boolean isIncomplete = false; // Used to check if the board has open positions
        for (int i = 0; i < 9; i += 3) {
            state = checkState(board.charAt(i), board.charAt(i + 1), board.charAt(i + 2));
            if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) {
                return state;
            } else if (state == BoardState.INCOMPLETE) isIncomplete = true;
        }
        return isIncomplete ? BoardState.INCOMPLETE : BoardState.DRAW;
    }

    private static BoardState checkVertical(String board) {
        BoardState state;
        boolean isIncomplete = false; // Used to check if the board has open positions
        for (int i = 0; i < 3; i++) {
            state = checkState(board.charAt(i), board.charAt(i + 3), board.charAt(i + 6));
            if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) {
                return state;
            } else if (state == BoardState.INCOMPLETE) isIncomplete = true;
        }
        return isIncomplete ? BoardState.INCOMPLETE : BoardState.DRAW;
    }

    private static BoardState checkDiagonal(String board) {
        boolean isIncomplete = false; // Used to check if the board has open positions
        // Check first diagonal
        BoardState state = checkState(board.charAt(0), board.charAt(4), board.charAt(8));
        if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) {
            return state;
        } else if (state == BoardState.INCOMPLETE) isIncomplete = true;
        // Check second diagonal
        state = checkState(board.charAt(2), board.charAt(4), board.charAt(6));
        if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) {
            return state;
        } else if (state == BoardState.INCOMPLETE) isIncomplete = true;

        return isIncomplete ? BoardState.INCOMPLETE : BoardState.DRAW;
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
            return (x == '_' || y == '_' || z == '_') ? BoardState.INCOMPLETE : BoardState.DRAW;
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

    public static void main(String[] args) {
        // Main (driver) function, for illustrating code functionality
        String[] boards = {"XXXOO____", "XX_OOOX__", "X_OOO_XXX", "XXXOXOXOO", "XOOOXXXXO"};
        for(String board: boards) {
            System.out.println(getStateOfBoard(board));
        }
    }
}
