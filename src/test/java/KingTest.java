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

    @Test
    void oneKingMoved(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK);
        king.firstMove();
        King king2 = new King(1, 1, PieceColour.BLACK);
        assertNotEquals(king, king2);
    }

    @Test
    void bothKingMoved(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK);
        king.firstMove();
        King king2 = new King(1, 1, PieceColour.BLACK);
        king2.firstMove();
        assertEquals(king, king2);
    }
}
