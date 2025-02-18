package chessboard;

import common.Coordinate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

class TotalMovesTest {
    @Test
    void depth1(){
        ChessGame game = new ChessGame();
        int depth = 1;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(20, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
    @Test
    void depth2(){
        ChessGame game = new ChessGame();
        int depth = 2;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(400, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
    @Test
    void depth3(){
        ChessGame game = new ChessGame();
        int depth = 3;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(8902, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Test
    void depth4(){
        ChessGame game = new ChessGame();
        int depth = 4;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(197_281, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Test
    void depth5(){
        ChessGame game = new ChessGame();
        int depth = 5;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(4_865_609, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Test
    void depth6(){
        ChessGame game = new ChessGame();
        int depth = 6;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(119_060_324, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
}
