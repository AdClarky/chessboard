import java.util.ArrayList;
import java.util.Collection;

public class DepthTester {
    private final ChessGame board;
    private int captures = 0;
    private int checks = 0;

    public DepthTester(ChessGame board) {
        this.board = board;
    }

    public int testDepth(int currentDepth) throws InvalidMoveException {
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
                if(board.wasMoveCapture())
                    captures++;
                if(board.isInCheck())
                    checks++;
                int currentPos = testDepth(currentDepth - 1);
                positions += currentPos;
                board.undoMove();
            }
        }
        return positions;
    }

    public int getCaptures() {
        return captures;
    }

    public int getChecks() {
        return checks;
    }
    // System.out.println("" + originalPos + position + ": " + currentPos);
}
