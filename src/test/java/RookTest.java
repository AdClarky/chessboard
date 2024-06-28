import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RookTest {
    @Test
    void rookHasntMoved(){
        Chessboard board = new Chessboard();
        Rook rook = new Rook(1, 1, PieceColour.WHITE, board);
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE, board);
        assertEquals(rook, rook2);
    }

    @Test
    void oneRookMoved(){
        Chessboard board = new Chessboard();
        Rook rook = new Rook(1, 1, PieceColour.WHITE, board);
        rook.firstMove();
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE, board);
        assertNotEquals(rook, rook2);
    }

    @Test
    void bothRookMoved(){
        Chessboard board = new Chessboard();
        Rook rook = new Rook(1, 1, PieceColour.WHITE, board);
        rook.firstMove();
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE, board);
        rook2.firstMove();
        assertEquals(rook, rook2);
    }
}
