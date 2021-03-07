import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void checkInvalidBoards() {
        String board1 = ""; // Empty board
        Board b1 = new Board(board1);
        assertEquals(Board.State.INVALID_BOARD, b1.getBoardState());

        String board2 = "XXO_O"; // Wrong size (less)
        Board b2 = new Board(board2);
        assertEquals(Board.State.INVALID_BOARD, b2.getBoardState());

        String board3 = "XXO_OX_XOO"; // Wrong size (greater)
        Board b3 = new Board(board3);
        assertEquals(Board.State.INVALID_BOARD, b3.getBoardState());

        String board4 = "XO_ABC_OX"; // Wrong values (not XO_)
        Board b4 = new Board(board4);
        assertEquals(Board.State.INVALID_BOARD, b4.getBoardState());

        String board5 = null; // Null value
        Board b5 = new Board(board5);
        assertEquals(Board.State.INVALID_BOARD, b5.getBoardState());

        String board6 = " ";
        Board b6 = new Board(board6);
        assertEquals(Board.State.INVALID_BOARD, b6.getBoardState());
    }

    @Test
    void checkInvalidStates() {
        String board1 = "XXXOOOXXX";
        Board b1 = new Board(board1);
        assertEquals(Board.State.INVALID_STATE, b1.getBoardState());

        String board2 = "XXXOOO___";
        Board b2 = new Board(board2);
        assertEquals(Board.State.INVALID_STATE, b2.getBoardState());

        String board3 = "XXXOXO___";
        Board b3 = new Board(board3);
        assertEquals(Board.State.INVALID_STATE, b3.getBoardState());

        String board4 = "XX_X__OOX";
        Board b4 = new Board(board4);
        assertEquals(Board.State.INVALID_STATE, b4.getBoardState());

        String board5 = "OXXOOXOXO";
        Board b5 = new Board(board5);
        assertEquals(Board.State.INVALID_STATE, b5.getBoardState());

        String board6 = "OXOOXX_O_";
        Board b6 = new Board(board6);
        assertEquals(Board.State.INVALID_STATE, b6.getBoardState());

        String board7 = "______O__";
        Board b7 = new Board(board7);
        assertEquals(Board.State.INVALID_STATE, b7.getBoardState());

        String board8 = "___X__X__";
        Board b8 = new Board(board8);
        assertEquals(Board.State.INVALID_STATE, b8.getBoardState());
    }

    @Test
    void checkDraws() {
        String board1 = "OXXXOOOXX";
        Board b1 = new Board(board1);
        assertEquals(Board.State.DRAW, b1.getBoardState());

        String board2 = "OXOXXOXOX";
        Board b2 = new Board(board2);
        assertEquals(Board.State.DRAW, b2.getBoardState());

        String board3 = "OXXXOOOXX";
        Board b3 = new Board(board3);
        assertEquals(Board.State.DRAW, b3.getBoardState());

        String board4 = "OXXXOOXOX";
        Board b4 = new Board(board4);
        assertEquals(Board.State.DRAW, b4.getBoardState());
    }

    @Test
    void checkIncompleteBoards() {
        String board1 = "_________";
        Board b1 = new Board(board1);
        assertEquals(Board.State.INCOMPLETE_BOARD, b1.getBoardState());

        String board2 = "OXOX_OXOX";
        Board b2 = new Board(board2);
        assertEquals(Board.State.INCOMPLETE_BOARD, b2.getBoardState());

        String board3 = "O_XXOOOXX";
        Board b3 = new Board(board3);
        assertEquals(Board.State.INCOMPLETE_BOARD, b3.getBoardState());

        String board4 = "OXXXOOXO_";
        Board b4 = new Board(board4);
        assertEquals(Board.State.INCOMPLETE_BOARD, b4.getBoardState());

        String board5 = "_XXXOOXOO";
        Board b5 = new Board(board5);
        assertEquals(Board.State.INCOMPLETE_BOARD, b5.getBoardState());
    }

    @Test
    void checkCrossesWin() {
        String board1 = "XXXOO____";
        Board b1 = new Board(board1);
        assertEquals(Board.State.CROSSES_WIN, b1.getBoardState());

        String board2 = "X_OOO_XXX";
        Board b2 = new Board(board2);
        assertEquals(Board.State.CROSSES_WIN, b2.getBoardState());

        String board3 = "XXXOXOXOO";
        Board b3 = new Board(board3);
        assertEquals(Board.State.CROSSES_WIN, b3.getBoardState());

        String board4 = "XXXOOXOOX";
        Board b4 = new Board(board4);
        assertEquals(Board.State.CROSSES_WIN, b4.getBoardState());

        String board5 = "XXXOXOXOO";
        Board b5 = new Board(board5);
        assertEquals(Board.State.CROSSES_WIN, b5.getBoardState());

        String board6 = "XOOXXOXOX";
        Board b6 = new Board(board6);
        assertEquals(Board.State.CROSSES_WIN, b6.getBoardState());

        String board7 = "OXOXXXOXO";
        Board b7 = new Board(board7);
        assertEquals(Board.State.CROSSES_WIN, b7.getBoardState());

        String board8 = "XXXOXOOOX";
        Board b8 = new Board(board8);
        assertEquals(Board.State.CROSSES_WIN, b8.getBoardState());
    }

    @Test
    void checkNoughtsWins() {
        String board1 = "XX_OOOX__";
        Board b1 = new Board(board1);
        assertEquals(Board.State.NOUGHTS_WIN, b1.getBoardState());

        String board2 = "OOO_X__XX";
        Board b2 = new Board(board2);
        assertEquals(Board.State.NOUGHTS_WIN, b2.getBoardState());

        String board3 = "O__OX_OXX";
        Board b3 = new Board(board3);
        assertEquals(Board.State.NOUGHTS_WIN, b3.getBoardState());

        String board4 = "O__XO_XXO";
        Board b4 = new Board(board4);
        assertEquals(Board.State.NOUGHTS_WIN, b4.getBoardState());

        String board5 = "X_XOOO__X";
        Board b5 = new Board(board5);
        assertEquals(Board.State.NOUGHTS_WIN, b5.getBoardState());
    }
}