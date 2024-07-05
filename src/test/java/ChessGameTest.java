import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ChessGameTest {
    private static class Listener implements BoardListener {
        int movesMade = 0;
        int checkmate = 0;
        int draw = 0;
        int boardChange = 0;

        @Override
        public void moveMade(int oldX, int oldY, int newX, int newY) {
            movesMade++;
        }

        @Override
        public void checkmate(int kingX, int kingY) {
            checkmate++;
        }

        @Override
        public void draw(int whiteX, int whiteY, int blackX, int blackY) {
            draw++;
        }

        @Override
        public void boardChanged(int oldX, int oldY, int newX, int newY) {
            boardChange++;
        }
    }
    private Listener listener;
    private ChessGame chessGame;

    @BeforeEach
    void setUp() {
        listener = new Listener();
        chessGame = new ChessGame();
        chessGame.addBoardListener(listener);
    }

    @Test
    void basicMove(){
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void checkmateGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/checkmateGame.pgn")).play());
        assertEquals(46, listener.movesMade);
        assertEquals(1, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void repetitionGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/repetitionGame.pgn")).play());
        assertEquals(8, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void stalemateGame() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/stalemateGame.pgn")).play());
        assertEquals(105, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void oneMoveOneUndo(){
        assertDoesNotThrow( ()->chessGame.makeMove(3, 1, 3, 3));
        chessGame.undoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(1, listener.boardChange);
    }

    @Test
    void oneMoveUndoAndRedo(){
        assertDoesNotThrow( ()->chessGame.makeMove(3, 1, 3, 3));
        chessGame.undoMove();
        chessGame.redoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(2, listener.boardChange);
    }

    @Test
    void fullGameUndoAllThenRedo() {
        assertDoesNotThrow( ()->new Autoplay(chessGame, Path.of("src/test/resources/checkmateGame.pgn")).play());
        chessGame.undoMultipleMoves(46);
        chessGame.redoAllMoves();
        assertEquals(46, listener.movesMade);
        assertEquals(2, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(92, listener.boardChange);
    }

    @Test
    void basicMoveThenRedoManyTimes(){
        assertDoesNotThrow( ()->chessGame.makeMove(3, 1, 3, 3));
        chessGame.undoMove();
        chessGame.redoMove();
        chessGame.redoMove();
        chessGame.redoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(2, listener.boardChange);
    }

    @Test
    void basicMoveThenUndoManyTimes(){
        assertDoesNotThrow( ()->chessGame.makeMove(3, 1, 3, 3));
        chessGame.undoMove();
        chessGame.undoMove();
        chessGame.undoMove();
        chessGame.undoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(1, listener.boardChange);
    }


    @Test
    void invalidMove() {
        assertThrows(InvalidMoveException.class, () -> chessGame.makeMove(0, 1, 0, 4));
    }

    @Test
    void noPieceMove(){
        assertThrows(InvalidMoveException.class, () -> chessGame.makeMove(4, 4, 4, 3));
    }
}
