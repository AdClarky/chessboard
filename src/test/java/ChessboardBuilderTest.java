import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ALL")
class ChessboardBuilderTest {

    @Test
    void defaultMiddleOfBoardBlank() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultAreWhitePiecesCorrect(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(PieceColour.WHITE, board.getPiece(x, 1).getColour());
            assertEquals(PieceColour.WHITE, board.getPiece(x, 0).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 0));
        assertFalse(board.getPiece(7, 0).hadFirstMove());
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
        assertFalse(board.getPiece(0, 0).hadFirstMove());
        assertInstanceOf(Knight.class, board.getPiece(6, 0));
        assertInstanceOf(Knight.class, board.getPiece(1, 0));
        assertInstanceOf(Bishop.class, board.getPiece(5, 0));
        assertInstanceOf(Bishop.class, board.getPiece(2, 0));
        assertInstanceOf(Queen.class, board.getPiece(4, 0));
        assertInstanceOf(King.class, board.getPiece(3, 0));
        assertFalse(board.getPiece(3, 0).hadFirstMove());
    }

    @Test
    void defaultAreBlackPiecesCorrect(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(PieceColour.BLACK, board.getPiece(x, 6).getColour());
            assertEquals(PieceColour.BLACK, board.getPiece(x, 7).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 7));
        assertFalse(board.getPiece(7, 7).hadFirstMove());
        assertInstanceOf(Rook.class, board.getPiece(0, 7));
        assertFalse(board.getPiece(7, 7).hadFirstMove());
        assertInstanceOf(Knight.class, board.getPiece(6, 7));
        assertInstanceOf(Knight.class, board.getPiece(1, 7));
        assertInstanceOf(Bishop.class, board.getPiece(5, 7));
        assertInstanceOf(Bishop.class, board.getPiece(2, 7));
        assertInstanceOf(Queen.class, board.getPiece(4, 7));
        assertInstanceOf(King.class, board.getPiece(3, 7));
        assertFalse(board.getPiece(3, 7).hadFirstMove());
    }

    @Test
    void fenStartingMiddleOfBoardBlank(){
        Chessboard board = new ChessboardBuilder().FromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultWithFenWhitePieces(){
        Chessboard board = new ChessboardBuilder().FromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(PieceColour.WHITE, board.getPiece(x, 1).getColour());
            assertEquals(PieceColour.WHITE, board.getPiece(x, 0).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 0));
        assertFalse(board.getPiece(7, 0).hadFirstMove());
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
        assertFalse(board.getPiece(0, 0).hadFirstMove());
        assertInstanceOf(Knight.class, board.getPiece(6, 0));
        assertInstanceOf(Knight.class, board.getPiece(1, 0));
        assertInstanceOf(Bishop.class, board.getPiece(5, 0));
        assertInstanceOf(Bishop.class, board.getPiece(2, 0));
        assertInstanceOf(Queen.class, board.getPiece(4, 0));
        assertInstanceOf(King.class, board.getPiece(3, 0));
        assertFalse(board.getPiece(3, 0).hadFirstMove());
    }

    @Test
    void defaultWithFenBlackPieces(){
        Chessboard board = new ChessboardBuilder().FromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(PieceColour.BLACK, board.getPiece(x, 6).getColour());
            assertEquals(PieceColour.BLACK, board.getPiece(x, 7).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 7));
        assertFalse(board.getPiece(7, 7).hadFirstMove());
        assertInstanceOf(Rook.class, board.getPiece(0, 7));
        assertFalse(board.getPiece(7, 7).hadFirstMove());
        assertInstanceOf(Knight.class, board.getPiece(6, 7));
        assertInstanceOf(Knight.class, board.getPiece(1, 7));
        assertInstanceOf(Bishop.class, board.getPiece(5, 7));
        assertInstanceOf(Bishop.class, board.getPiece(2, 7));
        assertInstanceOf(Queen.class, board.getPiece(4, 7));
        assertInstanceOf(King.class, board.getPiece(3, 7));
        assertFalse(board.getPiece(3, 7).hadFirstMove());
    }
}
