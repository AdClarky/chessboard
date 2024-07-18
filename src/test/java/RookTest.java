import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RookTest {
    @Test
    void rookNotMoved(){
        Rook rook = new Rook(1, 1, PieceColour.WHITE);
        Rook rook2 = new Rook(1, 1, PieceColour.WHITE);
        assertEquals(rook, rook2);
    }
}
