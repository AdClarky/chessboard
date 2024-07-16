import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(34, tester.getCaptures());
        assertEquals(12, tester.getChecks());
    }
    @Test
    void depth4(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertEquals(197281902, assertDoesNotThrow(()->tester.testDepth(4)));
    }
}
