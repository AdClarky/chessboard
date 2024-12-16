import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
class BoardHistoryTest {
    private BoardHistory history;

    @BeforeEach
    void setUp() {
        history = new BoardHistory();
    }

    @Test
    void canRedoWhenNoMoves(){
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenNoMovesUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()-> history.push(new Move(board, new Coordinate(3, 1), new Coordinate(4, 1))));
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenMoveUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()-> history.push(new Move(board, new Coordinate(3, 1), new Coordinate(3, 2))));
        history.undoMove();
        assertTrue(history.canRedoMove());
    }

    @Test
    void halfMovesAtStart(){
        ChessGame game = new ChessGame();
        assertEquals(0, game.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterKnightMove(){
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->game.makeMove(new Coordinate(1, 0), new Coordinate(0, 3)));
        assertEquals(1, game.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterPawnMove(){
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->game.makeMove(new Coordinate(0, 1), new Coordinate(0, 3)));
        assertEquals(0, game.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterKnightTakesKnight(){
        assertDoesNotThrow(()->{
            ChessGame game = new ChessGame("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/RNBQKB1R w KQkq - 0 1");
            game.makeMove(new Coordinate(1, 2), new Coordinate(2, 4));
            assertEquals(0, game.getNumHalfMoves());
        });
    }

    @Test
    void fullMovesAfterWhiteMove(){
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->game.makeMove(new Coordinate(0, 1), new Coordinate(0, 3)));
        assertEquals(1, game.getNumFullMoves());
    }

    @Test
    void fullMovesAfterBlackMove(){
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->game.makeMove(new Coordinate(0, 6), new Coordinate(0, 5)));
        assertEquals(2, game.getNumFullMoves());
    }

    @Test
    void getLastMovesWhenNoMovesHaveBeenMade(){
        List<MoveValue> lastMoves = history.getLastMoves();
        assertTrue(lastMoves.isEmpty());
    }

    @Test
    void areHalfMovesCorrectWhenClearingRedoAfterPawnMoves(){
        ChessInterface game = new ChessInterface();
        assertDoesNotThrow(()->game.makeMove("a4"));
        assertDoesNotThrow(()->game.makeMove("a5"));
        assertDoesNotThrow(()->game.makeMove("b4"));
        assertDoesNotThrow(()->game.makeMove("b5"));
        assertDoesNotThrow(()->game.makeMove("c4"));
        assertDoesNotThrow(()->game.makeMove("c5"));
        game.undoMove();
        assertDoesNotThrow(()->game.makeMove("d5"));
        assertEquals("rnbqkbnr/2p1pppp/8/pp1p4/PPP5/8/3PPPPP/RNBQKBNR w KQkq d6 0 4", game.getFenString());
    }

    @Test
    void areFullMovesCorrectWhenClearingRedoBlackSecond(){
        ChessInterface game = new ChessInterface();
        assertDoesNotThrow(()->{
            game.makeMove("e4");
            game.makeMove("e5");
            game.undoMove();
            game.makeMove("d5");
        });
        assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", game.getFenString());
    }

    @Test
    void areFullMovesCorrectWhenClearingRedoBlackStarts(){
        ChessInterface game = assertDoesNotThrow(()->new ChessInterface("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
        assertDoesNotThrow(()->{
           game.makeMove("e5");
           game.undoMove();
           game.makeMove("d5");
        });
        assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", game.getFenString());
    }

    @Test
    void doesTheGameGoBackToDefaultPosAfterClearing(){
        ChessInterface game = new ChessInterface();
        Collection<String> moves = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bc4", "Bc5");
        assertDoesNotThrow(()->new Autoplay(game, moves).play());
        game.undoMove();
        assertDoesNotThrow(()->game.makeMove("Nf6"));
        assertEquals("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4", game.getFenString());
        game.undoMultipleMoves(6);
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", game.getFenString());
        game.redoAllMoves();
        assertEquals("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4", game.getFenString());
    }
}