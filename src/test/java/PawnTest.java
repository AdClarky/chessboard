import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PawnTest {
    @Test
    void isNotPassantableAfterAnotherPawnMove() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(3, 6, 3, 4));
        assertTrue(board.getPiece(3,4).hadFirstMove());
        assertFalse(board.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenUndone() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(3, 6, 3, 4));
        assertTrue(board.getPiece(3,4).hadFirstMove());
        assertFalse(board.getPiece(3,3).hadFirstMove());
        board.undoMove();
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertFalse(board.getPiece(3,6).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenRedoAfterUndone() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(3, 6, 3, 4));
        assertTrue(board.getPiece(3,4).hadFirstMove());
        assertFalse(board.getPiece(3,3).hadFirstMove());
        board.undoMove();
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertFalse(board.getPiece(3,6).hadFirstMove());
        board.redoMove();
        assertTrue(board.getPiece(3,4).hadFirstMove());
        assertFalse(board.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMove() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(6, 7, 5, 5));
        assertFalse(board.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenUndone() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(6, 7, 5, 5));
        assertFalse(board.getPiece(3,3).hadFirstMove());
        board.undoMove();
        assertTrue(board.getPiece(3,3).hadFirstMove());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenRedoAfterUndone() {
        Board board = new Board();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertTrue(board.getPiece(3,3).hadFirstMove());
        assertDoesNotThrow(()->board.makeMove(6, 7, 5, 5));
        assertFalse(board.getPiece(3,3).hadFirstMove());
        board.undoMove();
        assertTrue(board.getPiece(3,3).hadFirstMove());
        board.redoMove();
        assertFalse(board.getPiece(3,3).hadFirstMove());
    }

    @Test
    void pawnHasntMoved(){
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE);
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE);
        assertEquals(pawn, pawn2);
    }

    @Test
    void onePawnPassantable(){
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE);
        pawn.firstMove();
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE);
        assertNotEquals(pawn, pawn2);
    }

    @Test
    void bothPawnsPassantable(){
        Pawn pawn = new Pawn(1, 1, Piece.WHITE_PIECE);
        pawn.firstMove();
        Pawn pawn2 = new Pawn(1, 1, Piece.WHITE_PIECE);
        pawn2.firstMove();
        assertEquals(pawn, pawn2);
    }
}