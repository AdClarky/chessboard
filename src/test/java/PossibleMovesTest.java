import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class PossibleMovesTest {
    @Test
    void doesBoardClear() {
        PossibleMoves possibleMoves = new PossibleMoves();
        Collection<Coordinate> moves = new ArrayList<>(64);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                moves.add(new Coordinate(x, y));
            }
        }
        possibleMoves.updatePossibleMoves(PieceColour.WHITE, moves);
        possibleMoves.clearBoard(PieceColour.WHITE);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                assertFalse(possibleMoves.isPossible(PieceColour.WHITE, new Coordinate(x, y)));
            }
        }
    }

    @Test
    void areAllSquaresCovered() {
        PossibleMoves possibleMoves = new PossibleMoves();
        Collection<Coordinate> moves = new ArrayList<>(64);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                Coordinate coord = new Coordinate(x, y);
                moves.add(new Coordinate(x, y));
                possibleMoves.updatePossibleMoves(PieceColour.WHITE, moves);
                assertTrue(possibleMoves.isPossible(PieceColour.WHITE, coord));
                moves.clear();
                possibleMoves.clearBoard(PieceColour.WHITE);
            }
        }
    }
}