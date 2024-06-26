import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class KingTest {
    @Test
    void sameKing(){
        King king = new King(1, 1, Piece.WHITE_PIECE);
        King king2 = king;
        assertEquals(king, king2);
    }

    @Test
    void differentPiece(){
        King king = new King(1, 1, Piece.WHITE_PIECE);
        Pawn king2 = new Pawn(1, 1, Piece.WHITE_PIECE);
        assertNotEquals(king, king2);
    }

    @Test
    void kingHasntMoved(){
        King king = new King(1, 1, Piece.WHITE_PIECE);
        King king2 = new King(1, 1, Piece.WHITE_PIECE);
        assertEquals(king, king2);
    }

    @Test
    void oneKingMoved(){
        King king = new King(1, 1, Piece.WHITE_PIECE);
        king.firstMove();
        King king2 = new King(1, 1, Piece.WHITE_PIECE);
        assertNotEquals(king, king2);
    }

    @Test
    void bothKingMoved(){
        King king = new King(1, 1, Piece.WHITE_PIECE);
        king.firstMove();
        King king2 = new King(1, 1, Piece.WHITE_PIECE);
        king2.firstMove();
        assertEquals(king, king2);
    }
}
