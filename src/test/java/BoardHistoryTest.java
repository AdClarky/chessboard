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
    void noLastPieceMoved(){
        Piece lastMoved = history.getLastPieceMoved();
        assertNull(lastMoved);
    }

    @Test
    void lastPieceMovedTest(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(1, 0);
        history.push(new Move(0, 0, board.getPiece(1, 0), null, board));
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void lastPieceMovedMultipleMoves(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(4, 6);
        history.push(new Move(4, 2, board.getPiece(4, 1), null, board));
        history.push(new Move(4, 5, board.getPiece(4, 6), board.getPiece(4, 2), board));
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void lastPieceMovedMultipleMovesThenUndoOnce(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(4, 6);
        history.push(new Move(4, 2, board.getPiece(4, 1), null, board));
        history.push(new Move(4, 5, board.getPiece(4, 6), board.getPiece(4, 2), board));
        history.undoMove();
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void lastPieceMovedMultipleMovesThenUndoMultipleTimes(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(4, 1);
        history.push(new Move(4, 2, board.getPiece(4, 1), null, board));
        history.push(new Move(4, 5, board.getPiece(4, 6), board.getPiece(4, 2), board));
        history.undoMultipleMoves(2);
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void lastPieceMovedMultipleMovesThenUndoThenRedo(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(4, 1);
        history.push(new Move(4, 2, board.getPiece(4, 1), null, board));
        history.push(new Move(4, 5, board.getPiece(4, 6), board.getPiece(4, 2), board));
        history.undoMultipleMoves(2);
        history.redoMove();
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void lastPieceMovedMultipleMovesThenUndoThenRedoAll(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        Piece piece = board.getPiece(4, 6);
        history.push(new Move(4, 2, board.getPiece(4, 1), null, board));
        history.push(new Move(4, 5, board.getPiece(4, 6), board.getPiece(4, 2), board));
        history.undoMultipleMoves(2);
        history.redoAllMoves();
        Piece lastMoved = history.getLastPieceMoved();
        assertEquals(piece, lastMoved);
    }

    @Test
    void basicMoveGetLastMovesSize() {
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()-> game.makeMove(4, 1, 4, 2));
        assertEquals(1, game.getLastMoveMade().size());
    }

    @Test
    void takingMoveLastMovesSize() {
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->{
            game.makeMove(4, 1, 4, 3);
            game.makeMove(3, 6, 3, 4);
            game.makeMove(4, 3, 3, 4);
        });
        assertEquals(1, game.getLastMoveMade().size());
    }


    @Test
    void takingMoveLastMovesWithUndoSize() {
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->{
            game.makeMove(4, 1, 4, 3);
            game.makeMove(3, 6, 3, 4);
            game.makeMove(4, 3, 3, 4);
        });
        game.undoMove();
        assertEquals(2, game.getLastMoveMade().size());
    }

    @Test
    void takingMoveLastMovesWithRedoSize() {
        ChessGame game = new ChessGame();
        assertDoesNotThrow(()->{
            game.makeMove(4, 1, 4, 3);
            game.makeMove(3, 6, 3, 4);
            game.makeMove(4, 3, 3, 4);
        });
        game.undoMove();
        game.redoMove();
        assertEquals(1, game.getLastMoveMade().size());
    }

    @Test
    void castlingMoveLastMovesSize() {
        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1"));
        assertDoesNotThrow(()-> game.makeMove(3, 0, 1, 0));
        assertEquals(2, game.getLastMoveMade().size());
    }

    @Test
    void canRedoWhenNoMoves(){
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenNoMovesUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()-> history.push(new Move(4, 1, board.getPiece(3, 1), null, board)));
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenMoveUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()-> history.push(new Move(4, 1, board.getPiece(3, 1), null, board)));
        history.undoMove();
        assertTrue(history.canRedoMove());
    }

    @Test
    void halfMovesAtStart(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertEquals(0, board.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterKnightMove(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        board.makeMove(1, 0, 0, 3);
        assertEquals(1, board.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterPawnMove(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        board.makeMove(0, 1, 0, 3);
        assertEquals(0, board.getNumHalfMoves());
    }

    @Test
    void halfMovesAfterKnightTakesKnight(){
        assertDoesNotThrow(()->{
            Chessboard board = new ChessboardBuilder().fromFen("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/RNBQKB1R w KQkq - 0 1");
            board.makeMove(1, 2, 2, 4);
            assertEquals(0, board.getNumHalfMoves());
        });
    }

    @Test
    void fullMovesAfterWhiteMove(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        board.makeMove(0, 1, 0, 3);
        assertEquals(1, board.getNumFullMoves());
    }

    @Test
    void fullMovesAfterBlackMove(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        board.makeMove(0, 6, 0, 5);
        assertEquals(2, board.getNumFullMoves());
    }

    @Test
    void fullMovesAfterInvalidMove(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        board.makeMove(3, 3, 3, 3);
        assertEquals(1, board.getNumFullMoves());
    }

    @Test
    void setFullMovesAfterMoveMade(){
        assertThrows(AccessedHistoryDuringGameException.class, ()->{
            Chessboard board = new ChessboardBuilder().defaultSetup();
            board.makeMove(0, 1, 0, 3);
            board.setNumFullMoves(2);
        });
    }

    @Test
    void setHalfMovesAfterMoveMade(){
        assertThrows(AccessedHistoryDuringGameException.class, ()->{
            Chessboard board = new ChessboardBuilder().defaultSetup();
            board.makeMove(0, 1, 0, 3);
            board.setNumHalfMoves(2);
        });
    }

    @Test
    void setFullMovesAfterMoveMadeAndUndone(){
        assertThrows(AccessedHistoryDuringGameException.class, ()->{
            Chessboard board = new ChessboardBuilder().defaultSetup();
            board.makeMove(0, 1, 0, 3);
            board.undoMove();
            board.setNumFullMoves(2);
        });
    }

    @Test
    void setHalfMovesAfterMoveMadeAndUndone(){
        assertThrows(AccessedHistoryDuringGameException.class, ()->{
            Chessboard board = new ChessboardBuilder().defaultSetup();
            board.makeMove(0, 1, 0, 3);
            board.undoMove();
            board.setNumHalfMoves(2);
        });
    }

    @Test
    void getLastMovesWhenNoMovesHaveBeenMade(){
        List<MoveValue> lastMoves = history.getLastMoves();
        assertTrue(lastMoves.isEmpty());
    }

    @Test
    void areHalfMovesCorrectWhenClearingRedoAfterPawnMoves(){
        ChessGame game = new ChessGame();
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
        ChessGame game = new ChessGame();
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
        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
        assertDoesNotThrow(()->{
           game.makeMove("e5");
           game.undoMove();
           game.makeMove("d5");
        });
        assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", game.getFenString());
    }

    @Test
    void doesTheGameGoBackToDefaultPosAfterClearing(){
        ChessGame game = new ChessGame();
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


    @Test
    void lastMoveCaptureTest(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2"));
        assertDoesNotThrow(()->game.makeMove("exd5"));
        assertTrue(game.wasMoveCapture());
    }

    @Test
    void isInCheckTest(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("4k3/8/8/8/8/8/8/3QK3 w - - 0 1"));
        assertFalse(game.isInCheck());
        assertDoesNotThrow(()->game.makeMove("Qe2+"));
        assertTrue(game.isInCheck());
    }
}