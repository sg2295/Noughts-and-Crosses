/**
 * Driver class used to show code functionality.
 */
public class Main {

    /**
     * Prints the given board in a readable way (in a 3x3 configuration).
     * Used for debugging purposes. Assumes the board meets the required criteria.
     * Example print result:
     *      X X O
     *      O X _
     *      O _ _
     * @param board The String representation of the board.
     */
    private static void printBoard(String board) {
        System.out.println("----\nBoard: ");
        for (int i = 0; i < 9; i+=3) {
            System.out.println(board.charAt(i) + " " + board.charAt(i + 1) + " " + board.charAt(i + 2));
        }
        System.out.println("----");
    }

    /**
     * Calculates and returns the state of the given board.
     * @param board String representation of the board.
     * @return The state of the board.
     */
    private static Board.State getStateOfBoard(String board) {
        Board b = new Board(board); // Create the board and analyze its state
        return b.getBoardState(); // Return the state of the board
    }

    /**
     * Main (driver) function used to illustrate code's functionality.
     * Calls the appropriate functions to calculate each board's state.
     * @param args Noughts and Crosses boards separated by spaces (" ").
     */
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(getStateOfBoard(args[i]));
        }
    }
}
