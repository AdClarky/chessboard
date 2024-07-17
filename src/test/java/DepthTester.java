import java.util.ArrayList;
import java.util.Collection;

public class DepthTester {
    private final ChessGame board;

    public DepthTester(ChessGame board) {
        this.board = board;
    }

    public int testDepth(int currentDepth) throws InvalidMoveException {
        int positions = 0;
        Collection<Piece> pieces = new ArrayList<>(board.getColourPieces(board.getCurrentTurn()));
        for(Piece piece : pieces){
            Collection<Coordinate> positionCoordinates = piece.getPossibleMoves();
            if(currentDepth == 1) {
                positions += positionCoordinates.size();
                continue;
            }
            for(Coordinate position : positionCoordinates){
                Coordinate originalPos = new Coordinate(piece.getX(), piece.getY());
                board.makeMove(piece.getX(), piece.getY(), position.x(), position.y());
                int currentPos = testDepth(currentDepth - 1);
                positions += currentPos;
                board.undoMove();
            }
        }
        return positions;
    }
    // new String("" + originalPos + position + ": " + currentPos)
}
