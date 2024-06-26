import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BoardTest {
    Board board = new Board();

    @AfterEach
    void setUp() {
        board = new Board();
    }

    @Test
    void isMiddleOfBoardBlank() {
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void areWhitePiecesSetupCorrectly(){
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 1).getDirection());
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 0).getDirection());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 0));
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
        assertInstanceOf(Knight.class, board.getPiece(6, 0));
        assertInstanceOf(Knight.class, board.getPiece(1, 0));
        assertInstanceOf(Bishop.class, board.getPiece(5, 0));
        assertInstanceOf(Bishop.class, board.getPiece(2, 0));
        assertInstanceOf(Queen.class, board.getPiece(4, 0));
        assertInstanceOf(King.class, board.getPiece(3, 0));
    }

    @Test
    void areBlackPiecesSetupCorrectly(){
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 6).getDirection());
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 7).getDirection());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 7));
        assertInstanceOf(Rook.class, board.getPiece(0, 7));
        assertInstanceOf(Knight.class, board.getPiece(6, 7));
        assertInstanceOf(Knight.class, board.getPiece(1, 7));
        assertInstanceOf(Bishop.class, board.getPiece(5, 7));
        assertInstanceOf(Bishop.class, board.getPiece(2, 7));
        assertInstanceOf(Queen.class, board.getPiece(4, 7));
        assertInstanceOf(King.class, board.getPiece(3, 7));
    }

    @Test
    void doesCustomPositionWork(){
        ArrayList<Piece> whitePieces = new ArrayList<>(3);
        ArrayList<Piece> blackPieces = new ArrayList<>(3);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new King(0, 1, Piece.WHITE_PIECE));
        whitePieces.add(new King(0, 2, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 3, Piece.BLACK_PIECE));
        blackPieces.add(new King(0, 4, Piece.BLACK_PIECE));
        blackPieces.add(new King(0, 5, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
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
    void isWhiteTheStartingPlayer() {
        assertEquals(Piece.WHITE_PIECE, board.getCurrentTurn());
    }

    @Test
    void doesCurrentMoveSwitchCorrectly(){
        board.moveWithValidation(0, 1, 0, 2);
        assertEquals(Piece.BLACK_PIECE, board.getCurrentTurn());
    }

    @Test
    void isInvalidMoveSafe() {
        new Move(2, 0, board.getPiece(4, 7), board);
        assertTrue(board.isMoveUnsafe(7, 2, board.getPiece(7, 1)));
    }

    @Test
    void queenNextToKingInCheck(){
        new Move(2, 0, board.getPiece(4, 7), board);
        assertTrue(board.isKingInCheck(Piece.WHITE_PIECE));
    }

    @Test
    void hashcodeTestOnBasicPawnMoveWithUndo(){
        int state = board.boardState();
        board.moveWithValidation(4, 1, 4, 3);
        assertNotEquals(state, board.boardState());
        board.undoMove();
        assertEquals(state, board.boardState());
    }

    @Disabled
    void moveWithValidation() {
    }

    @Disabled
    void testMoveWithValidation() {
    }

    @Disabled
    void undoMove() {
    }

    @Disabled
    void redoMove() {
    }

    @Disabled
    void redoAllMoves() {
    }

    @Disabled
    void isCheckmate() {
    }

    @Disabled
    void setSquare() {
    }

    @Disabled
    void getColourPieces() {
    }

    @Disabled
    void testGetColourPieces() {
    }

    @Disabled
    void getLastMoveMade() {
    }

    @Disabled
    void addBoardListener() {
    }
}