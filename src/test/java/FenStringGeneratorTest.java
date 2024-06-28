import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class FenStringGeneratorTest {
    @Test
    void blankBoardTest(){
        Chessboard board = new Chessboard();
        assertThrows(NoSuchElementException.class, ()-> new FenGenerator(board).getFenString());
    }

    @Test
    void defaultStartingTest(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fenString);
    }

    @Test
    void randomGameTest(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("r2qkb1r/1bp2ppp/P4n2/4p1B1/3nP3/3P2P1/1P3PBP/RN1QK1NR b KQkq - 0 10");
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("r2qkb1r/1bp2ppp/P4n2/4p1B1/3nP3/3P2P1/1P3PBP/RN1QK1NR b KQkq - 0 10", fenString);
    }
}
