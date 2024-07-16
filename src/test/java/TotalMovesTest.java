import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

class TotalMovesTest {
    @Test
    void depth1(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertDoesNotThrow(()->tester.testDepth(1));
        assertEquals(20, tester.getPositions());
    }

    @Test
    void depth2(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertDoesNotThrow(()->tester.testDepth(2));
        assertEquals(400, tester.getPositions());
    }

    @Test
    void depth3(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertDoesNotThrow(()->tester.testDepth(3));
        assertEquals(34, tester.getCaptures());
        assertEquals(12, tester.getChecks());
        assertEquals(8902, tester.getPositions());
    }

    @Test
    void depth4(){
        ChessGame board = new ChessGame();
        DepthTester tester = new DepthTester(board);
        assertDoesNotThrow(()->tester.testDepth(4));
        assertEquals(197281902, tester.getPositions());
    }
}
