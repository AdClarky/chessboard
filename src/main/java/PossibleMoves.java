import java.util.Collection;

public class PossibleMoves {
    private PossibleMovesBoard whiteBoard = new PossibleMovesBoard();
    private PossibleMovesBoard blackBoard = new PossibleMovesBoard();

    public PossibleMoves(){

    }

    public void clearBoard(PieceColour colour){
        getPossibleMovesBoard(colour).clear();
    }

    public void updatePossibleMoves(PieceColour colour, Collection<Coordinate> moves){
        PossibleMovesBoard board = getPossibleMovesBoard(colour);
        for(Coordinate coord : moves){
            board.possibleMove(coord);
        }
    }

    private PossibleMovesBoard getPossibleMovesBoard(PieceColour colour){
        if(colour == PieceColour.BLACK){
            return blackBoard;
        }
        return whiteBoard;
    }

    public boolean isPossible(PieceColour colour, Coordinate move){
        return getPossibleMovesBoard(colour).isPossible(move);
    }
}
