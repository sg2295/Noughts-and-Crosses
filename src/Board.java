import java.util.HashMap;
import java.util.regex.Pattern;

public class Board {
    public enum State {
        NOUGHTS_WIN, CROSSES_WIN, DRAW,
        INVALID_BOARD, // Board representation is not valid (Length is not 9, does not consist of 'X','O' and '_')
        INVALID_STATE, // The given board does not contain a valid combination of noughts and crosses. (Board is not allowed).
        INCOMPLETE_BOARD  // No winner and the board has empty spaces. Game must end in either a win or draw state.
    }

    /**
     * 2-d integer representation of the board.
     * Representation (key):
     * 1 --> 'X'
     * 0 --> 'O'
     * -1 --> '_'
     */
    private int[][] board;

    /**
     * Holds the state of the board.
     */
    private State boardState;

    /**
     * Holds the NOUGHTS_WIN, CROSSES_WIN and INCOMPLETE_BOARD state occurrences. Used to decide the final state of the board.
     */
    private HashMap<State, Integer> map;

    /**
     * Initializes all variables
     * @param board
     */
    public Board (String board) {
        this.boardState = State.DRAW;
        this.map = new HashMap<>(); this.populateMap();
        this.board = new int[3][3]; this.buildBoard(board);
    }

    /**
     * Accessor (getter) method for the state of the board.
     * @return The current state of the board.
     */
    public State getBoardState() {
        return this.boardState;
    }

    /**
     * Updates the state of the board based on the contents of the hash map.
     */
    private void updateState() {
        if (this.boardState == State.INVALID_BOARD || this.boardState == State.INVALID_STATE) return;
        int noughts = this.map.get(State.NOUGHTS_WIN), crosses = this.map.get(State.CROSSES_WIN),
                incomplete = this.map.get(State.INCOMPLETE_BOARD);
        if (noughts == 1 && crosses == 0) { // Noughts 1, Crosses 0
            this.boardState = State.NOUGHTS_WIN;
        } else if (noughts == 0 && crosses == 1) { // Crosses 1, Noughts 0
            this.boardState = State.CROSSES_WIN;
        } else if ((noughts > 0 && crosses > 0) || (noughts > 1 || crosses > 1)) { // Impossible board
            this.boardState = State.INVALID_STATE;
        } else if (incomplete > 0) { // Draw, but the board still has possible moves, so incomplete
            this.boardState = State.INCOMPLETE_BOARD;
        } else {
            this.boardState = State.DRAW;
        }
    }

    /**
     * Populates the hashmap with the initial values.
     */
    private void populateMap() {
        this.map.put(State.CROSSES_WIN, 0);
        this.map.put(State.NOUGHTS_WIN, 0);
        this.map.put(State.DRAW, 0);
        this.map.put(State.INCOMPLETE_BOARD, 0);
    }

    /**
     * Creates a 2-d integer representation of the board (3x3). Has error checking for invalid board configurations.
     * Representation:
     * 1 --> 'X'
     * 0 --> 'O'
     * -1 --> '_'
     * @param board The String representation of the board.
     */
    public void buildBoard(String board){
        if (!isValidBoard(board)) this.boardState = State.INVALID_BOARD; // Check that the board is valid (length = 9 and contents = XO_)
        int crossCount = 0, noughtCount = 0; // Holds the count of noughts and crosses
        char c; // Used to temporarily hold the current character value of the board (String)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                c = board.charAt(row * 3 + col);
                if (c == 'X') {
                    this.board[row][col] = 1; // X's are represented by 1's
                    crossCount++;
                } else if (c == 'O') {
                    this.board[row][col] = 0; // O's are represented by 0's
                    noughtCount++;
                } else {
                    this.board[row][col] = -1; // _'s are represented by -1's
                }
            }
        }
        // At any given time: #X = #O or #X = #O + 1. Otherwise, the board is in an invalid state.
        if (crossCount != noughtCount && crossCount != noughtCount + 1) this.boardState = State.INVALID_STATE;
    }

    /**
     * Evaluates the rows, columns and diagonals of the board and updates board's final state.
     */
    public void evaluateBoard() {
        checkHorizontal(); // Check the horizontal combinations (rows)
        checkVertical(); // Check the vertical combinations (columns)
        checkDiagonal(); // Check the diagonals
        updateState(); // Update the final board state using the contents of the hashmap
    }

    /**
     * Checks the rows of the board and updates the hashmap accordingly.
     */
    private void checkHorizontal() {
        State tempState;
        for (int i = 0; i < 3; i++) {
            tempState = checkState(board[i][0], board[i][1], board[i][2]);
            this.map.put(tempState, map.get(tempState) + 1);
        }
    }

    /**
     * Checks the columns of the board and updates the hashmap accordingly.
     */
    private void checkVertical() {
        State tempState;
        for (int i = 0; i < 3; i++) {
            tempState = checkState(board[0][i], board[1][i], board[2][i]);
            this.map.put(tempState, map.get(tempState) + 1);
        }
    }

    /**
     * Checks the diagonals of the board and updates the hashmap accordingly.
     */
    private void checkDiagonal() {
        State tempState = checkState(board[0][0], board[1][1], board[2][2]);
        this.map.put(tempState, map.get(tempState) + 1);
        tempState = checkState(board[0][2], board[1][1], board[2][0]);
        this.map.put(tempState, map.get(tempState) + 1);
    }

    /**
     * Checks the length and contents of the board. Returns a boolean value.
     * Length must be equal to 9. Board can only contain noughts ('O'), crosses ('X') and empty slots ('_').
     * @param board The String representation of the board.
     * @return true if the board is valid, false otherwise.
     */
    private static boolean isValidBoard(String board) {
        if (board.length() != 9) return false;  // Board length must be 9 (3x3 board)
        return Pattern.matches("[XO_]+", board); // Use a REGEX to check that the board only contains X, O and _
    }

    /**
     * Checks if the state of the given triad (three indices). Evaluates if a player won, if it is a draw, or if the set
     * is incomplete. Returns a BoardState corresponding to each outcome.
     * @param x First index
     * @param y Second index
     * @param z Third index
     * @return CROSSES_WIN if the characters are crosses, NOUGHTS_WIN if the characters are noughts, DRAW
     * if the set is complete with no winner, and INCOMPLETE if there are available moves
     */
    private State checkState(int x, int y, int z) {
        if (x == y && y == z && x != -1) {  // All crosses
            return x == 1 ? State.CROSSES_WIN : State.NOUGHTS_WIN;
        } else {
            return (x == -1 || y == -1 || z == -1) ? State.INCOMPLETE_BOARD : State.DRAW;
        }
    }

    /**
     * Prints the given board for debugging purposes. Assumes the board meets the required criteria.
     */
    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
