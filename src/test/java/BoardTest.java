import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void getPiece() {
        assertInstanceOf(Rook.class, board.getPiece(0, 0));
    }

    @Test
    void isSquareBlank() {
    }

    @Test
    void getCurrentTurn() {
    }

    @Test
    void isMoveSafe() {
    }

    @Test
    void isKingInCheck() {
    }

    @Test
    void moveWithValidation() {
    }

    @Test
    void testMoveWithValidation() {
    }

    @Test
    void undoMove() {
    }

    @Test
    void redoMove() {
    }

    @Test
    void redoAllMoves() {
    }

    @Test
    void isCheckmate() {
    }

    @Test
    void setSquare() {
    }

    @Test
    void getColourPieces() {
    }

    @Test
    void testGetColourPieces() {
    }

    @Test
    void getLastMoveMade() {
    }

    @Test
    void addBoardListener() {
    }
}