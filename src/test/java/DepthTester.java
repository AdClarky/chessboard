import java.util.ArrayList;
import java.util.Collection;

public class DepthTester {
    private final ChessGame game;

    public DepthTester(ChessGame game) {
        this.game = game;
    }

    public int testDepth(int currentDepth) throws InvalidMoveException {
        int positions = 0;
        Collection<Coordinate> pieces = game.getAllColourPieces(game.getTurn());
        for(Coordinate piece : pieces){
            Collection<Coordinate> positionCoordinates = game.getPossibleMoves(piece);
            if(currentDepth == 1) {
                positions += positionCoordinates.size();
                continue;
            }
            for(Coordinate newMove : positionCoordinates){
                game.makeMove(piece, newMove);
                int currentPos = testDepth(currentDepth - 1);
                positions += currentPos;
                game.undoMove();
            }
        }
        return positions;
    }
    // new String("" + originalPos + position + ": " + currentPos)
}
