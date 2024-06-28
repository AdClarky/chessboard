import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ChessboardTest {
    Chessboard board = new ChessboardBuilder().defaultSetup();

    @AfterEach
    void setUp() {
        board = new ChessboardBuilder().defaultSetup();
    }


    @Test
    void doesCustomPositionWork(){
        board = new Chessboard();
        ArrayList<Piece> whitePieces = new ArrayList<>(3);
        ArrayList<Piece> blackPieces = new ArrayList<>(3);
        whitePieces.add(new King(0, 0, PieceColour.WHITE, board));
        whitePieces.add(new King(0, 1, PieceColour.WHITE, board));
        whitePieces.add(new King(0, 2, PieceColour.WHITE, board));
        blackPieces.add(new King(0, 3, PieceColour.BLACK, board));
        blackPieces.add(new King(0, 4, PieceColour.BLACK, board));
        blackPieces.add(new King(0, 5, PieceColour.BLACK, board));
        board.populateBoard(whitePieces, blackPieces);
        for(int x = 1; x < 8; x++){
            for(int y = 0; y < 8; y++){
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
        for(int y = 0; y < 6; y++){
            assertInstanceOf(King.class, board.getPiece(0, y));
        }
    }

    @Test
    void doesBlankCheckerWork() {
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertTrue(board.isSquareBlank(x,y));
            }
        }
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 2; y++){
                assertFalse(board.isSquareBlank(x,y));
                assertFalse(board.isSquareBlank(x,y+6));
            }
        }
    }

    @Test
    void isInvalidMoveSafe() {
        new Move(2, 0, board.getPiece(4, 7), null, board);
        assertTrue(board.isMoveUnsafe(7, 2, board.getPiece(7, 1)));
    }

    @Test
    void queenNextToKingInCheck(){
        new Move(2, 0, board.getPiece(4, 7), null, board);
        assertTrue(board.isKingInCheck(PieceColour.WHITE));
    }

    @Test
    void hashcodeOnSameState(){
        int state = board.getState();
        assertEquals(state, board.getState());
    }

    @Test
    void hashcodeTestOnBasicPawnMoveWithUndo() {
        int state = board.getState();
        assertDoesNotThrow(()->board.makeMove(4, 1, 4, 3));
        assertNotEquals(state, board.getState());
        board.undoMove();
        assertEquals(state, board.getState());
    }

    @Test
    void hashcodeTestOnMoveWherePreviousWasPassantable() {
        assertDoesNotThrow(()->board.makeMove(4, 1, 4, 3));
        int state = board.getState();
        int whitePawnHash = board.getPiece(4,3).hashCode();
        int blackPawnHash = board.getPiece(4,6).hashCode();
        int blankSquareHash = board.getPiece(4, 4).hashCode();
        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
        assertNotEquals(state, board.getState());
        board.undoMove();
        assertEquals(whitePawnHash, board.getPiece(4,3).hashCode());
        assertEquals(blackPawnHash, board.getPiece(4,6).hashCode());
        assertEquals(blankSquareHash, board.getPiece(4, 4).hashCode());
        assertEquals(state, board.getState());
    }

    @Test
    void invalidMove() {
        assertThrows(InvalidMoveException.class, () -> board.makeMove(0, 1, 0, 4));
    }

    @Test
    void noPieceMove(){
        assertThrows(InvalidMoveException.class, () -> board.makeMove(4, 4, 4, 3));
    }

    @Test
    void undoMoreThanNecessary(){
        int state = board.getState();
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
        assertDoesNotThrow(()->board.makeMove(3, 3, 4, 4));
        board.undoMove();
        board.undoMove();
        board.undoMove();
        board.undoMove();
        board.undoMove();
        assertEquals(state, board.getState());
    }


    @Test
    void redoMoreThanNecessary(){
        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
        assertDoesNotThrow(()->board.makeMove(3, 3, 4, 4));
        int state = board.getState();
        board.undoMove();
        board.undoMove();
        board.undoMove();
        board.redoMove();
        board.redoMove();
        board.redoMove();
        board.redoMove();
        board.redoMove();
        board.redoMove();
        assertEquals(state, board.getState());
    }
}