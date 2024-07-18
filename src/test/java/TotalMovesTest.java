import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TotalMovesTest {
    @Test
    void depth1(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(20, assertDoesNotThrow(()->tester.testDepth(1)));
    }
    @Test
    void depth2(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(400, assertDoesNotThrow(()->tester.testDepth(2)));
    }
    @Test
    void depth3(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(8902, assertDoesNotThrow(()->tester.testDepth(3)));
    }

    @Test
    void depth4(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(197_281, assertDoesNotThrow(()->tester.testDepth(4)));
    }

    @Test
    void depth5(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(4_865_609, assertDoesNotThrow(()->tester.testDepth(5)));
    }

    @Disabled
    void depth6(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(119_060_324, assertDoesNotThrow(()->tester.testDepth(5)));
    }
}
