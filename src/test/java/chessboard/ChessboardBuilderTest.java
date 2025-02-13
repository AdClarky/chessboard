package chessboard;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SpellCheckingInspection")
class ChessboardBuilderTest {

    @Test
    void defaultMiddleOfBoardBlank() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            for (int y = 2; y < 6; y++) {
                assertEquals(Pieces.BLANK, board.getPiece(new Coordinate(x, y)));
            }
        }
    }

    @Test
    void defaultAreWhitePiecesCorrect() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            assertEquals(Pieces.PAWN, board.getPiece(new Coordinate(x, 1)));
            assertEquals(PieceColour.WHITE, board.getColour(new Coordinate(x, 1)));
            assertEquals(PieceColour.WHITE, board.getColour(new Coordinate(x, 0)));
        }
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 0)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 0)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(6, 0)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(1, 0)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(5, 0)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(2, 0)));
        assertEquals(Pieces.QUEEN, board.getPiece(new Coordinate(3, 0)));
        assertEquals(Pieces.KING, board.getPiece(new Coordinate(4, 0)));
    }

    @Test
    void defaultAreBlackPiecesCorrect() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            assertEquals(Pieces.PAWN, board.getPiece(new Coordinate(x, 6)));
            assertEquals(PieceColour.BLACK, board.getColour(new Coordinate(x, 6)));
            assertEquals(PieceColour.BLACK, board.getColour(new Coordinate(x, 7)));
        }
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 7)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 7)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(6, 7)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(1, 7)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(5, 7)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(2, 7)));
        assertEquals(Pieces.QUEEN, board.getPiece(new Coordinate(3, 7)));
        assertEquals(Pieces.KING, board.getPiece(new Coordinate(4, 7)));
    }

    @Test
    void fenStartingMiddleOfBoardBlank() {
        Chessboard board = assertDoesNotThrow(() -> new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            for (int y = 2; y < 6; y++) {
                assertEquals(Pieces.BLANK, board.getPiece(new Coordinate(x, y)));
            }
        }
    }

    @Test
    void defaultWithFenWhitePieces() {
        Chessboard board = assertDoesNotThrow(() -> new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            assertEquals(Pieces.PAWN, board.getPiece(new Coordinate(x, 1)));
            assertEquals(PieceColour.WHITE, board.getColour(new Coordinate(x, 1)));
            assertEquals(PieceColour.WHITE, board.getColour(new Coordinate(x, 0)));
        }
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 0)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 0)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(6, 0)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(1, 0)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(5, 0)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(2, 0)));
        assertEquals(Pieces.QUEEN, board.getPiece(new Coordinate(3, 0)));
        assertEquals(Pieces.KING, board.getPiece(new Coordinate(4, 0)));
    }

    @Test
    void defaultWithFenBlackPieces() {
        Chessboard board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            assertEquals(Pieces.PAWN, board.getPiece(new Coordinate(x, 6)));
            assertEquals(PieceColour.BLACK, board.getColour(new Coordinate(x, 6)));
            assertEquals(PieceColour.BLACK, board.getColour(new Coordinate(x, 7)));
        }
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 7)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 7)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(6, 7)));
        assertEquals(Pieces.KNIGHT, board.getPiece(new Coordinate(1, 7)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(5, 7)));
        assertEquals(Pieces.BISHOP, board.getPiece(new Coordinate(2, 7)));
        assertEquals(Pieces.QUEEN, board.getPiece(new Coordinate(3, 7)));
        assertEquals(Pieces.KING, board.getPiece(new Coordinate(4, 7)));
    }

    @Test
    void editBoardDuringGame(){
        ChessboardBuilder builder = new ChessboardBuilder();
        Chessboard board = builder.defaultSetup();
        board.movePiece(new Coordinate(0, 1), new Coordinate(0, 2));
        assertThrows(RuntimeException.class, builder::defaultSetup);
    }

    @Test
    void fenStringWithRandomCharacterInBoard(){
        assertThrows(InvalidFenStringException.class, ()->new ChessboardBuilder().fromFen("ZZZZnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/RNBQKB1R w KQkq - 0 1"));
    }

    @Test
    void whiteCanOnlyCastleQueenSide(){
        String fenString = "rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(4, 0), new Coordinate(2, 0)));
    }

    @Test
    void blackCanOnlyCastleQueenSide(){
        String fenString = "r3kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQq - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(4, 7), new Coordinate(2, 7)));
    }

    @Test
    void whiteCanOnlyCastleKingSide(){
        String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w Kkq - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(4, 0), new Coordinate(6, 0)));
    }

    @Test
    void blackCanOnlyCastleKingSide(){
        String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQk - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(4, 7), new Coordinate(6, 7)));
    }

    @Test
    void correctFirstSectionOnly(){
        String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        assertThrows(InvalidFenStringException.class, ()-> new ChessboardBuilder().fromFen(fenString));
    }

    @Test
    void randomStringWith6Sections(){
        String fenString = "ajskd ajksd asljkd ajks asjdk alsjkd";
        assertThrows(InvalidFenStringException.class, ()-> new ChessboardBuilder().fromFen(fenString));
    }
}
