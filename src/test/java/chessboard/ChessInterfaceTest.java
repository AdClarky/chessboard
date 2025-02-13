package chessboard;

import common.BoardListener;
import common.Coordinate;
import exception.InvalidMoveException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class ChessInterfaceTest {
    private static class Listener implements BoardListener {
        int movesMade = 0;
        int checkmate = 0;
        int draw = 0;
        int boardChange = 0;

        @Override
        public void moveMade(Coordinate oldPos, Coordinate newPos) {
            movesMade++;
        }

        @Override
        public void checkmate(Coordinate kingPos) {
            checkmate++;
        }

        @Override
        public void draw(Coordinate whitePos, Coordinate blackPos) {
            draw++;
        }

        @Override
        public void boardChanged(Coordinate oldPos, Coordinate newPos) {
            boardChange++;
        }
    }
    private Listener listener;
    private ChessInterface game;

    @BeforeEach
    void setUp() {
        listener = new Listener();
        game = new ChessInterface();
        game.addBoardListener(listener);
    }

    @Test
    void basicMove(){
        assertDoesNotThrow(()-> game.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void checkmateGame() {
        assertDoesNotThrow( ()->new Autoplay(game, Path.of("src/test/resources/checkmateGame.pgn")).play());
        assertEquals(46, listener.movesMade);
        assertEquals(1, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void repetitionGame() {
        assertDoesNotThrow( ()->new Autoplay(game, Path.of("src/test/resources/repetitionGame.pgn")).play());
        assertEquals(8, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void stalemateGame() {
        assertDoesNotThrow( ()->new Autoplay(game, Path.of("src/test/resources/stalemateGame.pgn")).play());
        assertEquals(105, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(1, listener.draw);
        assertEquals(0, listener.boardChange);
    }

    @Test
    void oneMoveOneUndo(){
        assertDoesNotThrow( ()-> game.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        game.undoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(1, listener.boardChange);
    }

    @Test
    void oneMoveUndoAndRedo(){
        assertDoesNotThrow( ()-> game.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        game.undoMove();
        game.redoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(2, listener.boardChange);
    }

    @Test
    void fullGameUndoAllThenRedo() {
        assertDoesNotThrow( ()->new Autoplay(game, Path.of("src/test/resources/checkmateGame.pgn")).play());
        game.undoMultipleMoves(46);
        game.redoAllMoves();
        assertEquals(46, listener.movesMade);
        assertEquals(2, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(92, listener.boardChange);
    }

    @Test
    void basicMoveThenRedoManyTimes(){
        assertDoesNotThrow( ()-> game.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        game.undoMove();
        game.redoMove();
        game.redoMove();
        game.redoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(2, listener.boardChange);
    }

    @Test
    void basicMoveThenUndoManyTimes(){
        assertDoesNotThrow( ()-> game.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        game.undoMove();
        game.undoMove();
        game.undoMove();
        game.undoMove();
        assertEquals(1, listener.movesMade);
        assertEquals(0, listener.checkmate);
        assertEquals(0, listener.draw);
        assertEquals(1, listener.boardChange);
    }


    @Test
    void invalidMove() {
        assertThrows(InvalidMoveException.class, () -> game.makeMove(new Coordinate(0, 1), new Coordinate(0, 4)));
    }

    @Test
    void noPieceMove(){
        assertThrows(InvalidMoveException.class, () -> game.makeMove(new Coordinate(4, 4), new Coordinate(4, 3)));
    }

    @Test
    void enPassantTest(){
        assertDoesNotThrow(()->game.makeMove("h4"));
        assertDoesNotThrow(()->game.makeMove("a6"));
        assertDoesNotThrow(()->game.makeMove("h5"));
        assertDoesNotThrow(()->game.makeMove("g5"));
        assertDoesNotThrow(()->game.makeMove("hxg6"));
    }
}
