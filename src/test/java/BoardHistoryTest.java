import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertDoesNotThrow(()->{
            game.makeMove(4, 1, 4, 2);
        });
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
        ChessGame game = new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1");
        assertDoesNotThrow(()->{
            game.makeMove(3, 0, 1, 0);
        });
        assertEquals(2, game.getLastMoveMade().size());
    }

    @Test
    void canRedoWhenNoMoves(){
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenNoMovesUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()->{
            history.push(new Move(4, 1, board.getPiece(3, 1), null, board));
        });
        assertFalse(history.canRedoMove());
    }

    @Test
    void canRedoWhenMoveUndone(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertDoesNotThrow(()->{
            history.push(new Move(4, 1, board.getPiece(3, 1), null, board));
        });
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
        Chessboard board = new ChessboardBuilder().fromFen("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/RNBQKB1R w KQkq - 0 1");
        board.makeMove(1, 2, 2, 4);
        assertEquals(0, board.getNumHalfMoves());
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
}