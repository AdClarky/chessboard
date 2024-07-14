import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class TotalMovesTest {
    @Test
    void testTotalMovesDepth1(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 1));
        assertEquals(20, positions);
    }

    @Test
    void testTotalMovesDepth2(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 2));
        assertEquals(400, positions);
    }







    int testDepth(ChessGame board, int currentDepth) throws InvalidMoveException {
        if (currentDepth == 0)
            return 0;

        int positions = 0;
        Collection<Piece> pieces = board.getColourPieces(board.getCurrentTurn());
        for(Piece piece : pieces){
            Collection<Coordinate> positionCoordinates = new ArrayList<>(piece.getPossibleMoves());
            positions += positionCoordinates.size();
            for(Coordinate position : positionCoordinates){
                board.makeMove(piece.getX(), piece.getY(), position.x(), position.y());
                positions += testDepth(board, currentDepth - 1);
                board.undoMove();
            }
        }

        return positions;
    }
}
