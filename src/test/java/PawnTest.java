import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PawnTest {
    @Test
    void isNotPassantableAfterAnotherPawnMove() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(3, 6, 3, 4));
        assertTrue(chessGame.getPiece(3,4).hadFirstMove());
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(3, 6, 3, 4));
        assertTrue(chessGame.getPiece(3,4).hadFirstMove());
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
        chessGame.undoMove();
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertFalse(chessGame.getPiece(3,6).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenRedoAfterUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(3, 6, 3, 4));
        assertTrue(chessGame.getPiece(3,4).hadFirstMove());
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
        chessGame.undoMove();
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertFalse(chessGame.getPiece(3,6).hadFirstMove());
        chessGame.redoMove();
        assertTrue(chessGame.getPiece(3,4).hadFirstMove());
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMove() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(6, 7, 5, 5));
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(6, 7, 5, 5));
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
        chessGame.undoMove();
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenRedoAfterUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(3, 1, 3, 3));
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()-> chessGame.makeMove(6, 7, 5, 5));
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
        chessGame.undoMove();
        assertTrue(chessGame.getPiece(3,3).hadFirstMove());
        chessGame.redoMove();
        assertFalse(chessGame.getPiece(3,3).hadFirstMove());
    }

    @Test
    void pawnHasntMoved(){
        Chessboard board = new Chessboard();
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        assertEquals(pawn, pawn2);
    }

    @Test
    void onePawnPassantable(){
        Chessboard board = new Chessboard();
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        pawn.firstMove();
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        assertNotEquals(pawn, pawn2);
    }

    @Test
    void bothPawnsPassantable(){
        Chessboard board = new Chessboard();
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        pawn.firstMove();
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE, board);
        pawn2.firstMove();
        assertEquals(pawn, pawn2);
    }
}
