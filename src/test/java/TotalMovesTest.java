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
    void depth2a3() {
        ChessGame board = assertDoesNotThrow(() -> new ChessGame("rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR b KQkq - 0 1"));
        DepthTester tester = new DepthTester(board);
        assertEquals(380, assertDoesNotThrow(()->tester.testDepth(2)));
    }

    @Test
    void depth2a3a6() {
        ChessGame board = assertDoesNotThrow(() -> new ChessGame("rnbqkbnr/1ppppppp/p7/8/8/P7/1PPPPPPP/RNBQKBNR w KQkq - 0 2"));
        DepthTester tester = new DepthTester(board);
        assertEquals(19, assertDoesNotThrow(()->tester.testDepth(1)));
    }

    @Test
    void depth4(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(197_281, assertDoesNotThrow(()->tester.testDepth(4)));
    }

    @Disabled // does not work yet off by 14
    void depth5(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(4_865_609, assertDoesNotThrow(()->tester.testDepth(5)));
    }
}
