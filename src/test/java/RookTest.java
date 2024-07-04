import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RookTest {
    @Test
    void rookNotMoved(){
        Rook rook = new Rook(1, 1, PieceColour.WHITE);
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE);
        assertEquals(rook, rook2);
    }

    @Test
    void oneRookMoved(){
        Rook rook = new Rook(1, 1, PieceColour.WHITE);
        rook.firstMove();
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE);
        assertNotEquals(rook, rook2);
    }

    @Test
    void bothRookMoved(){
        Rook rook = new Rook(1, 1, PieceColour.WHITE);
        rook.firstMove();
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE);
        rook2.firstMove();
        assertEquals(rook, rook2);
    }
}
