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

    @Disabled
    void depth7(){
        ChessGame game = new ChessGame();
        int depth = 7;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(3_195_901_860L, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }



    @Test
    void depth1Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 1;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(48, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
    @Test
    void depth2Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 2;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(2039, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
    @Test
    void depth3Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 3;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(97_862, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Test
    void depth4Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 4;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(4_085_603, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Test
    void depth5Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 5;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(193_690_690, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }

    @Disabled
    void depth6Kiwipete(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 6;
        DepthTester tester = new DepthTester(game, depth);
        assertEquals(8_031_647_685L, assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }


    @Test
    void testWithMoves(){
        ChessGame game = assertDoesNotThrow(()->new ChessGame("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1"));
        int depth = 3;
        List<String> moves = List.of("a2", "a3", "d7", "d6");
        for (int i = 0; i < moves.size(); i+=2) {
            int finalI = i;
            assertDoesNotThrow(()->game.makeMove(Coordinate.fromString(moves.get(finalI)), Coordinate.fromString(moves.get(finalI + 1))));
        }
        DepthTester tester = new DepthTester(game, depth);
        System.out.println(assertDoesNotThrow(()->tester.testDepthCopying(game, depth)));
    }
}
