package chessboard;

import common.Coordinate;
import exception.InvalidMoveException;

import java.util.Collection;

public class DepthTester {
    private final ChessGame game;
    private final int topDepth;

    public DepthTester(ChessGame game, int topDepth) {
        this.game = game;
        this.topDepth = topDepth;
        System.out.println(new String("Testing depth " + topDepth));
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
//                if(currentDepth == topDepth) System.out.println(new String("" + piece + newMove + ": " + currentPos));
                positions += currentPos;
                game.undoMove();
            }
        }
        return positions;
    }
    // new String("" + originalPos + position + ": " + currentPos)
}
