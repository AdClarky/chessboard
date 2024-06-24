import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {

    private Board board = new Board();

    @Test
    public void getPiece() {
        assertTrue(board.getPiece(0,0) instanceof Rook);
    }

    @Test
    public void isSquareBlank() {
    }

    @Test
    public void getCurrentTurn() {
    }

    @Test
    public void isMoveSafe() {
    }

    @Test
    public void isKingInCheck() {
    }

    @Test
    public void moveWithValidation() {
    }

    @Test
    public void testMoveWithValidation() {
    }

    @Test
    public void undoMove() {
    }

    @Test
    public void redoMove() {
    }

    @Test
    public void redoAllMoves() {
    }

    @Test
    public void isCheckmate() {
    }

    @Test
    public void setSquare() {
    }

    @Test
    public void getColourPieces() {
    }

    @Test
    public void testGetColourPieces() {
    }

    @Test
    public void getLastMoveMade() {
    }

    @Test
    public void addBoardListener() {
    }
}