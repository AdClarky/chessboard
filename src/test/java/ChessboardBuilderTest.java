import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SpellCheckingInspection")
class ChessboardBuilderTest {

    @Test
    void defaultMiddleOfBoardBlank() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            for (int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultAreWhitePiecesCorrect() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(PieceColour.WHITE, board.getPiece(x, 1).getColour());
            assertEquals(PieceColour.WHITE, board.getPiece(x, 0).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 0));
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
        assertInstanceOf(Knight.class, board.getPiece(6, 0));
        assertInstanceOf(Knight.class, board.getPiece(1, 0));
        assertInstanceOf(Bishop.class, board.getPiece(5, 0));
        assertInstanceOf(Bishop.class, board.getPiece(2, 0));
        assertInstanceOf(Queen.class, board.getPiece(4, 0));
        assertInstanceOf(King.class, board.getPiece(new Coordinate(3, 0)));
    }

    @Test
    void defaultAreBlackPiecesCorrect() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for (int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(PieceColour.BLACK, board.getPiece(x, 6).getColour());
            assertEquals(PieceColour.BLACK, board.getPiece(x, 7).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 7));
        assertInstanceOf(Rook.class, board.getPiece(0, 7));
        assertInstanceOf(Knight.class, board.getPiece(6, 7));
        assertInstanceOf(Knight.class, board.getPiece(1, 7));
        assertInstanceOf(Bishop.class, board.getPiece(new Coordinate(5, 7)));
        assertInstanceOf(Bishop.class, board.getPiece(2, 7));
        assertInstanceOf(Queen.class, board.getPiece(4, 7));
        assertInstanceOf(King.class, board.getPiece(3, 7));
    }

    @Test
    void fenStartingMiddleOfBoardBlank() {
        Chessboard board = assertDoesNotThrow(() -> new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            for (int y = 2; y < 6; y++) {
                assertInstanceOf(Blank.class, board.getPiece(x, y));
            }
        }
    }

    @Test
    void defaultWithFenWhitePieces() {
        Chessboard board = assertDoesNotThrow(() -> new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 1));
            assertEquals(PieceColour.WHITE, board.getPiece(x, 1).getColour());
            assertEquals(PieceColour.WHITE, board.getPiece(x, 0).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 0));
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
        assertInstanceOf(Knight.class, board.getPiece(6, 0));
        assertInstanceOf(Knight.class, board.getPiece(1, 0));
        assertInstanceOf(Bishop.class, board.getPiece(5, 0));
        assertInstanceOf(Bishop.class, board.getPiece(2, 0));
        assertInstanceOf(Queen.class, board.getPiece(4, 0));
        assertInstanceOf(King.class, board.getPiece(new Coordinate(3, 0)));
    }

    @Test
    void defaultWithFenBlackPieces() {
        Chessboard board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        for (int x = 0; x < 8; x++) {
            assertInstanceOf(Pawn.class, board.getPiece(x, 6));
            assertEquals(PieceColour.BLACK, board.getPiece(x, 6).getColour());
            assertEquals(PieceColour.BLACK, board.getPiece(x, 7).getColour());
        }
        assertInstanceOf(Rook.class, board.getPiece(7, 7));
        assertInstanceOf(Rook.class, board.getPiece(0, 7));
        assertInstanceOf(Knight.class, board.getPiece(6, 7));
        assertInstanceOf(Knight.class, board.getPiece(1, 7));
        assertInstanceOf(Bishop.class, board.getPiece(new Coordinate(5, 7)));
        assertInstanceOf(Bishop.class, board.getPiece(2, 7));
        assertInstanceOf(Queen.class, board.getPiece(4, 7));
        assertInstanceOf(King.class, board.getPiece(3, 7));
    }

    @Test
    void editBoardDuringGame(){
        ChessboardBuilder builder = new ChessboardBuilder();
        Chessboard board = builder.defaultSetup();
        board.makeMove(0, 1, 0, 2);
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
        assertDoesNotThrow(()->game.makeMove(new Coordinate(3, 0), new Coordinate(5, 0)));
    }

    @Test
    void blackCanOnlyCastleQueenSide(){
        String fenString = "r3kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQq - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(3, 7), new Coordinate(5, 7)));
    }

    @Test
    void whiteCanOnlyCastleKingSide(){
        String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w Kkq - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(3, 0), new Coordinate(1, 0)));
    }

    @Test
    void blackCanOnlyCastleKingSide(){
        String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQk - 0 1";
        ChessGame game = assertDoesNotThrow(()->new ChessGame(fenString));
        assertDoesNotThrow(()->game.makeMove(new Coordinate(3, 7), new Coordinate(1, 7)));
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
