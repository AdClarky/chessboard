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
        Chessboard board = new ChessboardBuilder().defaultSetup();
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fenString);
    }

    @Test
    void randomGameTest(){
        Chessboard board = new ChessboardBuilder().FromFen("r2qkb1r/1bp2ppp/P4n2/4p1B1/3nP3/3P2P1/1P3PBP/RN1QK1NR b KQkq - 0 10");
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("r2qkb1r/1bp2ppp/P4n2/4p1B1/3nP3/3P2P1/1P3PBP/RN1QK1NR b KQkq - 0 10", fenString);
    }

    @Test
    void noCastlingTest(){
        Chessboard board = new ChessboardBuilder().FromFen("8/5Qpp/8/4p2k/4B3/2PP1PP1/7P/2K3NR b - - 0 25");
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("8/5Qpp/8/4p2k/4B3/2PP1PP1/7P/2K3NR b - - 0 25", fenString);
    }

    @Test
    void oneSideCanCastleTest(){
        Chessboard board = new ChessboardBuilder().FromFen("r2q1rk1/1bp2ppp/p1np1n2/1p2p3/4P3/PBNPP2P/1PPQ2P1/R3K1NR w KQ - 1 11");
        String fenString = new FenGenerator(board).getFenString();
        assertEquals("r2q1rk1/1bp2ppp/p1np1n2/1p2p3/4P3/PBNPP2P/1PPQ2P1/R3K1NR w KQ - 1 11", fenString);

    }
}
