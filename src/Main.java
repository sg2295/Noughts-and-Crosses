import java.util.regex.*;

public class Main {

    private enum BoardState {
        NOUGHTS_WIN, CROSSES_WIN, DRAW,
        INVALID_BOARD, INVALID_STATE, INCOMPLETE
    }

    private static boolean isValidState(String board) {
        boolean isValid = false;
        // Has more noughts than crosses (#X > #O)
        // Noughts OR crosses are more than 3
        return isValid;
    }

    /**
     * Checks the state of the board.
     * Checks the length and contents of the board
     * @param board
     * @return
     */
    private static boolean isValidBoard(String board) {
        if (board.length() != 9) return false;  // Board is of right length
        return Pattern.matches("[XO_]+", board); // Use a regular expression to check that the board only contains noughts, crosses or empty spaces
    }

    private static BoardState getStateOfBoard(String board) {
        BoardState state = null;
        state = getBoardResult(board); // Get the result of the board
        return state;
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
            state = triadState(board.charAt(i), board.charAt(i + 1), board.charAt(i + 2));
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
            state = triadState(board.charAt(i), board.charAt(i + 3), board.charAt(i + 6));
            if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) { //
                return state;
            } else if (state == BoardState.INCOMPLETE) isIncomplete = true;
        }
        return isIncomplete ? BoardState.INCOMPLETE : BoardState.DRAW;
    }

    private static BoardState checkDiagonal(String board) {
        boolean isIncomplete = false; // Used to check if the board has open positions
        // Check first diagonal
        BoardState state = triadState(board.charAt(0), board.charAt(4), board.charAt(8));
        if (state != BoardState.INCOMPLETE && state != BoardState.DRAW) {
            return state;
        } else if (state == BoardState.INCOMPLETE) isIncomplete = true;
        // Check second diagonal
        state = triadState(board.charAt(2), board.charAt(4), board.charAt(6));
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
    private static BoardState triadState(char x, char y, char z) {
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
        // Diagonal
        String board1 = "OXOXO_XOX";
        printBoard(board1);
        System.out.println(getBoardResult(board1));
//        System.out.println(checkHorizontal(board1));
//        System.out.println(checkVertical(board1));
//        System.out.println(checkDiagonal(board1));
    }
}
