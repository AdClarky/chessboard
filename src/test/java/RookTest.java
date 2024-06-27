//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//
//public class RookTest {
//    @Test
//    void rookHasntMoved(){
//        Rook rook = new Rook(1, 1, Piece.WHITE_PIECE);
//        Rook rook2 = new Rook(1, 1, Piece.WHITE_PIECE);
//        assertEquals(rook, rook2);
//    }
//
//    @Test
//    void oneRookMoved(){
//        Rook rook = new Rook(1, 1, Piece.WHITE_PIECE);
//        rook.firstMove();
//        Rook rook2 = new Rook(1, 1, Piece.WHITE_PIECE);
//        assertNotEquals(rook, rook2);
//    }
//
//    @Test
//    void bothRookMoved(){
//        Rook rook = new Rook(1, 1, Piece.WHITE_PIECE);
//        rook.firstMove();
//        Rook rook2 = new Rook(1, 1, Piece.WHITE_PIECE);
//        rook2.firstMove();
//        assertEquals(rook, rook2);
//    }
//}
