import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PawnTest {
    @Test
    void isNotPassantableAfterAnotherPawnMove() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 6), new Coordinate(3, 4)));
        assertEquals(new Coordinate(3, 4), chessGame.getEnPassantSquare());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 6), new Coordinate(3, 4)));
        assertEquals(new Coordinate(3, 4), chessGame.getEnPassantSquare());
        chessGame.undoMove();
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
    }

    @Test
    void isNotPassantableAfterAnotherPawnMoveThenRedoAfterUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 6), new Coordinate(3, 4)));
        assertEquals(new Coordinate(3, 4), chessGame.getEnPassantSquare());
        chessGame.undoMove();
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        chessGame.redoMove();
        assertEquals(new Coordinate(3, 4), chessGame.getEnPassantSquare());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMove() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(() -> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(() -> chessGame.makeMove(new Coordinate(6, 7), new Coordinate(5, 5)));
        assertNull(chessGame.getEnPassantSquare());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(6, 7), new Coordinate(5, 5)));
        assertNull(chessGame.getEnPassantSquare());
        chessGame.undoMove();
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
    }

    @Test
    void isNotPassantableAfterAnotherPieceMoveThenRedoAfterUndone() {
        ChessGame chessGame = new ChessGame();
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(3, 1), new Coordinate(3, 3)));
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        assertDoesNotThrow(()-> chessGame.makeMove(new Coordinate(6, 7), new Coordinate(5, 5)));
        assertNull(chessGame.getEnPassantSquare());
        chessGame.undoMove();
        assertEquals(new Coordinate(3, 3), chessGame.getEnPassantSquare());
        chessGame.redoMove();
        assertNull(chessGame.getEnPassantSquare());
    }

    @Test
    void pawnNotMoved(){
        Chessboard board = new Chessboard();
        Pawn pawn = new Pawn(1, 1, PieceColour.BLACK);
        Pawn pawn2 = new Pawn(1, 1, PieceColour.BLACK);
        assertEquals(pawn, pawn2);
    }
}
