package chessboard;

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
        assertEquals(20, assertDoesNotThrow(()->tester.testDepth(depth)));
    }
    @Test
    void depth2(){
        ChessGame game = new ChessGame();
        int depth = 2;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(400, assertDoesNotThrow(()->tester.testDepth(depth)));
    }
    @Test
    void depth3(){
        ChessGame game = new ChessGame();
        int depth = 3;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(8902, assertDoesNotThrow(()->tester.testDepth(depth)));
    }

    @Test
    void depth4(){
        ChessGame game = new ChessGame();
        int depth = 4;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(197_281, assertDoesNotThrow(()->tester.testDepth(depth)));
    }

    @Test
    void depth5(){
        ChessGame game = new ChessGame();
        int depth = 5;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(4_865_609, assertDoesNotThrow(()->tester.testDepth(depth)));
    }

    @Test
    void depth6(){
        ChessGame game = new ChessGame();
        int depth = 6;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(119_060_324, assertDoesNotThrow(()->tester.testDepth(depth)));
    }

    @Disabled
    void testWithMoves(){
        ChessGame game = new ChessGame();
        int depth = 2;
        List<String> moves = List.of("e2", "e4", "d7", "d5");
        for (int i = 0; i < moves.size(); i+=2) {
            int finalI = i;
            assertDoesNotThrow(()->game.makeMove(Coordinate.fromString(moves.get(finalI)), Coordinate.fromString(moves.get(finalI + 1))));
        }
        DepthTester tester = new DepthTester(game, depth);
        assertDoesNotThrow(()->tester.testDepth(depth));
    }
}
