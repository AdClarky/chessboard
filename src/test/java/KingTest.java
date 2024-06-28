import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class KingTest {
    @Test
    void sameKing(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK, board);
        King king2 = king;
        assertEquals(king, king2);
    }

    @Test
    void differentPiece(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK, board);
        Pawn king2 = new Pawn(1, 1, PieceColour.BLACK, board);
        assertNotEquals(king, king2);
    }

    @Test
    void kingHasntMoved(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK, board);
        King king2 = new King(1, 1, PieceColour.BLACK, board);
        assertEquals(king, king2);
    }

    @Test
    void oneKingMoved(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK, board);
        king.firstMove();
        King king2 = new King(1, 1, PieceColour.BLACK, board);
        assertNotEquals(king, king2);
    }

    @Test
    void bothKingMoved(){
        Chessboard board = new Chessboard();
        King king = new King(1, 1, PieceColour.BLACK, board);
        king.firstMove();
        King king2 = new King(1, 1, PieceColour.BLACK, board);
        king2.firstMove();
        assertEquals(king, king2);
    }
}
