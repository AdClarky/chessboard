import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class KingTest {
    @Test
    void kingNotMoved(){
        King king = new King(1, 1, PieceColour.BLACK);
        King king2 = new King(1, 1, PieceColour.BLACK);
        assertEquals(king, king2);
    }
}
