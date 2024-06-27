import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ChessboardBuilderTest {

    @Test
    void defaultMiddleOfBoardBlank() {
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultAreWhitePiecesCorrect(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 1).getDirection());
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 0).getDirection());
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
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 6).getDirection());
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 7).getDirection());
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
    void separatorTestOnFenString(){
        ChessboardBuilder builder = new ChessboardBuilder();
        ArrayList<String> sections =
                builder.separateIntoSections("8/8/6Q1/4p1k1/4B3/2PP1PP1/7P/2K3NR b - - 2 28", ' ');
        assertEquals(6, sections.size());
        assertEquals("8/8/6Q1/4p1k1/4B3/2PP1PP1/7P/2K3NR", sections.getFirst());
        assertEquals("b", sections.get(1));
        assertEquals("-", sections.get(2));
        assertEquals("-", sections.get(3));
        assertEquals("2", sections.get(4));
        assertEquals("28", sections.get(5));
    }

    @Test
    void separatorTestOnBoardLayoutString(){
        ChessboardBuilder builder = new ChessboardBuilder();
        ArrayList<String> sections =
                builder.separateIntoSections("8/8/6Q1/4p1k1/4B3/2PP1PP1/7P/2K3NR", '/');
        assertEquals(8, sections.size());
        assertEquals("8", sections.getFirst());
        assertEquals("8", sections.get(1));
        assertEquals("6Q1", sections.get(2));
        assertEquals("4p1k1", sections.get(3));
        assertEquals("4B3", sections.get(4));
        assertEquals("2PP1PP1", sections.get(5));
        assertEquals("7P", sections.get(6));
        assertEquals("2K3NR", sections.get(7));
    }

    @Test
    void fenStartingMiddleOfBoardBlank(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultWithFenWhitePieces(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 1).getDirection());
            assertEquals(Piece.WHITE_PIECE, board.getPiece(x, 0).getDirection());
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
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 6).getDirection());
            assertEquals(Piece.BLACK_PIECE, board.getPiece(x, 7).getDirection());
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
