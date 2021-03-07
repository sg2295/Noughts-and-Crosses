/**
 * Driver class used to show code functionality.
 */
public class Main {

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

    /**
     * Calculates and returns the state of the given board.
     * @param board String representation of the board.
     * @return The state of the board.
     */
    private static Board.State getStateOfBoard(String board) {
        Board b = new Board(board);
        return b.getBoardState();
    }

    public static void main(String[] args) {
        // Main (driver) function, for illustrating code functionality
        String[] boards = {"XXXOO____", "XX_OOOX__", "X_OOO_XXX", "XXXOXOXOO", "XOOOXXXXO", "_________", "_X_XX_X__",
        "XXXXOXOOO", "XXXXOOXOO"};
        for(String board: boards) {
//            printBoard(board);
            System.out.println(getStateOfBoard(board));

        }
    }
}
