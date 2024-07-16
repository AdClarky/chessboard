import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class TotalMovesTest {
    @Test
    void depth1(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 1));
        assertEquals(20, positions);
    }

    @Test
    void depth2(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 2));
        assertEquals(400, positions);
    }

    @Test
    void depth3(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 3));
        assertEquals(8902, positions);
    }

    @Test
    void depth4(){
        ChessGame board = new ChessGame();
        int positions = assertDoesNotThrow(()->testDepth(board, 4));
        assertEquals(197281902, positions);
    }

    int testDepth(ChessGame board, int currentDepth) throws InvalidMoveException {
        if (currentDepth == 0)
            return 0;

        int positions = 0;
        Collection<Piece> pieces = board.getColourPieces(board.getCurrentTurn());
        for(Piece piece : pieces){
            Collection<Coordinate> positionCoordinates = new ArrayList<>(piece.getPossibleMoves());
            if(currentDepth == 1) {
                positions += positionCoordinates.size();
                continue;
            }
            for(Coordinate position : positionCoordinates){
                Coordinate originalPos = new Coordinate(piece.getX(), piece.getY());
                board.makeMove(piece.getX(), piece.getY(), position.x(), position.y());
                int positionCurrent = testDepth(board, currentDepth - 1);
                positions += positionCurrent;
                board.undoMove();
            }
        }
        return positions;
    }
}
