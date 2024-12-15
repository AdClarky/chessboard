import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
class BoardHistoryDisabled {
    private BoardHistory history;

    @BeforeEach
    void setUp() {
        history = new BoardHistory();
    }

    @Disabled
    void noLastPieceMoved(){
        Piece lastMoved = history.getLastPieceMoved();
        assertNull(lastMoved);
    }

    @Disabled
    void lastPieceMovedDisabled(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(new Coordinate(1, 0));
//        history.push(new Move(board.getPiece(new Coordinate(1, 0)), new Coordinate(0, 0), board));
        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
    }

    @Disabled
    void lastPieceMovedMultipleMoves(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(4, 6);
//        history.push(new Move(board.getPiece(new Coordinate(4, 1)), new Coordinate(4, 2), board));
//        history.push(new Move(board.getPiece(4, 6), new Coordinate(4, 5), board));
        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
    }

    @Disabled
    void lastPieceMovedMultipleMovesThenUndoOnce(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(4, 6);
//        history.push(new Move(board.getPiece(new Coordinate(4, 1)), new Coordinate(4, 2), board));
//        history.push(new Move(board.getPiece(4, 6), new Coordinate(4, 5), board));
        history.undoMove();
        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
    }

    @Disabled
    void lastPieceMovedMultipleMovesThenUndoMultipleTimes(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(new Coordinate(4, 1));
//        history.push(new Move(board.getPiece(new Coordinate(4, 1)), new Coordinate(4, 2), board));
//        history.push(new Move(board.getPiece(4, 6), new Coordinate(4, 5), board));
//        history.undoMultipleMoves(2);
//        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
    }
//
//    @Disabled
//    void lastPieceMovedMultipleMovesThenUndoThenRedo(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(new Coordinate(4, 1));
//        history.push(new Move(board.getPiece(new Coordinate(4, 1)), new Coordinate(4, 2), board));
//        history.push(new Move(board.getPiece(4, 6), new Coordinate(4, 5), board));
//        history.undoMultipleMoves(2);
//        history.redoMove();
//        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
//    }
//
//    @Disabled
//    void lastPieceMovedMultipleMovesThenUndoThenRedoAll(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Piece piece = board.getPiece(4, 6);
//        history.push(new Move(board.getPiece(new Coordinate(4, 1)), new Coordinate(4, 2), board));
//        history.push(new Move(board.getPiece(4, 6), new Coordinate(4, 5), board));
//        history.undoMultipleMoves(2);
//        history.redoAllMoves();
//        Piece lastMoved = history.getLastPieceMoved();
//        assertEquals(piece, lastMoved);
//    }
//
//    @Disabled
//    void basicMoveGetLastMovesSize() {
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()-> game.makeMove(new Coordinate(4, 1), new Coordinate(4, 2)));
//        assertEquals(1, game.getLastMoveMade().size());
//    }
//
//    @Disabled
//    void takingMoveLastMovesSize() {
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()->{
//            game.makeMove(new Coordinate(4, 1), new Coordinate(4, 3));
//            game.makeMove(new Coordinate(3, 6), new Coordinate(3, 4));
//            game.makeMove(new Coordinate(4, 3), new Coordinate(3, 4));
//        });
//        assertEquals(1, game.getLastMoveMade().size());
//    }
//
//
//    @Disabled
//    void takingMoveLastMovesWithUndoSize() {
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()->{
//            game.makeMove(new Coordinate(4, 1), new Coordinate(4, 3));
//            game.makeMove(new Coordinate(3, 6), new Coordinate(3, 4));
//            game.makeMove(new Coordinate(4, 3), new Coordinate(3, 4));
//        });
//        game.undoMove();
//        assertEquals(2, game.getLastMoveMade().size());
//    }
//
//    @Disabled
//    void takingMoveLastMovesWithRedoSize() {
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()->{
//            game.makeMove(new Coordinate(4, 1), new Coordinate(4, 3));
//            game.makeMove(new Coordinate(3, 6), new Coordinate(3, 4));
//            game.makeMove(new Coordinate(4, 3), new Coordinate(3, 4));
//        });
//        game.undoMove();
//        game.redoMove();
//        assertEquals(1, game.getLastMoveMade().size());
//    }
//
//    @Disabled
//    void castlingMoveLastMovesSize() {
//        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1"));
//        assertDoesNotThrow(()-> game.makeMove(new Coordinate(3, 0), new Coordinate(1, 0)));
//        assertEquals(2, game.getLastMoveMade().size());
//    }
//
//    @Disabled
//    void canRedoWhenNoMoves(){
//        assertFalse(history.canRedoMove());
//    }
//
//    @Disabled
//    void canRedoWhenNoMovesUndone(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()-> history.push(new Move(board.getPiece(3, 1), new Coordinate(4, 1), board)));
//        assertFalse(history.canRedoMove());
//    }
//
//    @Disabled
//    void canRedoWhenMoveUndone(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()-> history.push(new Move(board.getPiece(3, 1), new Coordinate(4, 1), board)));
//        history.undoMove();
//        assertTrue(history.canRedoMove());
//    }
//
//    @Disabled
//    void halfMovesAtStart(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertEquals(0, board.getNumHalfMoves());
//    }
//
//    @Disabled
//    void halfMovesAfterKnightMove(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        board.makeMove(new Coordinate(1, 0), new Coordinate(0, 3));
//        assertEquals(1, board.getNumHalfMoves());
//    }
//
//    @Disabled
//    void halfMovesAfterPawnMove(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        board.makeMove(new Coordinate(0, 1), new Coordinate(0, 3));
//        assertEquals(0, board.getNumHalfMoves());
//    }
//
//    @Disabled
//    void halfMovesAfterKnightTakesKnight(){
//        assertDoesNotThrow(()->{
//            Chessboard board = new ChessboardBuilder().fromFen("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/RNBQKB1R w KQkq - 0 1");
//            board.makeMove(1, 2, 2, 4);
//            assertEquals(0, board.getNumHalfMoves());
//        });
//    }
//
//    @Disabled
//    void fullMovesAfterWhiteMove(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        board.makeMove(new Coordinate(0, 1), new Coordinate(0, 3));
//        assertEquals(1, board.getNumFullMoves());
//    }
//
//    @Disabled
//    void fullMovesAfterBlackMove(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        board.makeMove(0, 6, 0, 5);
//        assertEquals(2, board.getNumFullMoves());
//    }
//
//    @Disabled
//    void fullMovesAfterInvalidMove(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        board.makeMove(3, 3, 3, 3);
//        assertEquals(1, board.getNumFullMoves());
//    }
//
//    @Disabled
//    void setFullMovesAfterMoveMade(){
//        assertThrows(AccessedHistoryDuringGameException.class, ()->{
//            Chessboard board = new ChessboardBuilder().defaultSetup();
//            board.makeMove(new Coordinate(1, 0), new Coordinate(0, 3));
//            board.setNumFullMoves(2);
//        });
//    }
//
//    @Disabled
//    void setHalfMovesAfterMoveMade(){
//        assertThrows(AccessedHistoryDuringGameException.class, ()->{
//            Chessboard board = new ChessboardBuilder().defaultSetup();
//            board.makeMove(new Coordinate(1, 0), new Coordinate(0, 3));
//            board.setNumHalfMoves(2);
//        });
//    }
//
//    @Disabled
//    void setFullMovesAfterMoveMadeAndUndone(){
//        assertThrows(AccessedHistoryDuringGameException.class, ()->{
//            Chessboard board = new ChessboardBuilder().defaultSetup();
//            board.makeMove(new Coordinate(1, 0), new Coordinate(0, 3));
//            board.undoMove();
//            board.setNumFullMoves(2);
//        });
//    }
//
//    @Disabled
//    void setHalfMovesAfterMoveMadeAndUndone(){
//        assertThrows(AccessedHistoryDuringGameException.class, ()->{
//            Chessboard board = new ChessboardBuilder().defaultSetup();
//            board.makeMove(new Coordinate(1, 0), new Coordinate(0, 3));
//            board.undoMove();
//            board.setNumHalfMoves(2);
//        });
//    }
//
//    @Disabled
//    void getLastMovesWhenNoMovesHaveBeenMade(){
//        List<MoveValue> lastMoves = history.getLastMoves();
//        assertTrue(lastMoves.isEmpty());
//    }
//
//    @Disabled
//    void areHalfMovesCorrectWhenClearingRedoAfterPawnMoves(){
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()->game.makeMove("a4"));
//        assertDoesNotThrow(()->game.makeMove("a5"));
//        assertDoesNotThrow(()->game.makeMove("b4"));
//        assertDoesNotThrow(()->game.makeMove("b5"));
//        assertDoesNotThrow(()->game.makeMove("c4"));
//        assertDoesNotThrow(()->game.makeMove("c5"));
//        game.undoMove();
//        assertDoesNotThrow(()->game.makeMove("d5"));
//        assertEquals("rnbqkbnr/2p1pppp/8/pp1p4/PPP5/8/3PPPPP/RNBQKBNR w KQkq d6 0 4", game.getFenString());
//    }
//
//    @Disabled
//    void areFullMovesCorrectWhenClearingRedoBlackSecond(){
//        ChessGame game = new ChessGame();
//        assertDoesNotThrow(()->{
//            game.makeMove("e4");
//            game.makeMove("e5");
//            game.undoMove();
//            game.makeMove("d5");
//        });
//        assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", game.getFenString());
//    }
//
//    @Disabled
//    void areFullMovesCorrectWhenClearingRedoBlackStarts(){
//        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
//        assertDoesNotThrow(()->{
//           game.makeMove("e5");
//           game.undoMove();
//           game.makeMove("d5");
//        });
//        assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", game.getFenString());
//    }
//
//    @Disabled
//    void doesTheGameGoBackToDefaultPosAfterClearing(){
//        ChessGame game = new ChessGame();
//        Collection<String> moves = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bc4", "Bc5");
//        assertDoesNotThrow(()->new Autoplay(game, moves).play());
//        game.undoMove();
//        assertDoesNotThrow(()->game.makeMove("Nf6"));
//        assertEquals("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4", game.getFenString());
//        game.undoMultipleMoves(6);
//        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", game.getFenString());
//        game.redoAllMoves();
//        assertEquals("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4", game.getFenString());
//    }
//
//
//    @Disabled
//    void lastMoveCaptureDisabled(){
//        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2"));
//        assertDoesNotThrow(()->game.makeMove("exd5"));
//        assertTrue(game.wasMoveCapture());
//    }
//
//    @Disabled
//    void isInCheckDisabled(){
//        ChessGame game = assertDoesNotThrow(()->new ChessGame("4k3/8/8/8/8/8/8/3QK3 w - - 0 1"));
//        assertFalse(game.isInCheck());
//        assertDoesNotThrow(()->game.makeMove("Qe2+"));
//        assertTrue(game.isInCheck());
//    }
}