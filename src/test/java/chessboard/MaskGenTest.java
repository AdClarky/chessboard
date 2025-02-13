package chessboard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

class MaskGenTest {
    @Test
    void blankTest(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        MaskGenerator generator = new MaskGenerator(board);
        assertEquals(0, generator.getMaskForPiece(new Coordinate(5,5 )));
    }

    @Test
    void testAllStartingPieces(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        MaskGenerator generator = new MaskGenerator(board);
        for(int x = 0; x < 8; x++){
            Collection<Coordinate> whiteMask = new Bitboard(generator.getMaskForPiece(new Coordinate(x,1)));
            assertTrue(whiteMask.contains(new Coordinate(x, 2)));
            assertTrue(whiteMask.contains(new Coordinate(x, 3)));
            assertEquals(2, whiteMask.size());
            Collection<Coordinate> blackMask = new Bitboard(generator.getMaskForPiece(new Coordinate(x,6)));
            assertTrue(blackMask.contains(new Coordinate(x, 5)));
            assertTrue(blackMask.contains(new Coordinate(x, 4)));
            assertEquals(2, whiteMask.size());
            if(x != 1 && x != 6){
                assertEquals(0, generator.getMaskForPiece(new Coordinate(x,0)));
                assertEquals(0, generator.getMaskForPiece(new Coordinate(x,7)));
                continue;
            }
            Collection<Coordinate> whiteKnight = new Bitboard(generator.getMaskForPiece(new Coordinate(x,0)));
            assertTrue(whiteKnight.contains(new Coordinate(x-1, 2)));
            assertTrue(whiteKnight.contains(new Coordinate(x+1, 2)));
            assertEquals(2, whiteKnight.size());
            Collection<Coordinate> blackKnight = new Bitboard(generator.getMaskForPiece(new Coordinate(x,7)));
            assertTrue(blackKnight.contains(new Coordinate(x-1, 5)));
            assertTrue(blackKnight.contains(new Coordinate(x+1, 5)));
            assertEquals(2, blackKnight.size());
        }
    }
}
