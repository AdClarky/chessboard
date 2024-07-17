import java.util.Collection;

public class PossibleMoves {
    private Bitboard whiteBoard = new Bitboard();
    private Bitboard blackBoard = new Bitboard();

    public PossibleMoves(){

    }

    public void clearBoard(PieceColour colour){
        getPossibleMovesBoard(colour).clear();
    }

    public void updatePossibleMoves(PieceColour colour, Collection<Coordinate> moves){
        getPossibleMovesBoard(colour).addAll(moves);
    }

    public void removePossible(PieceColour colour, Coordinate move){
        getPossibleMovesBoard(colour).remove(move);
    }

    private Bitboard getPossibleMovesBoard(PieceColour colour){
        if(colour == PieceColour.BLACK){
            return blackBoard;
        }
        return whiteBoard;
    }

    public boolean isPossible(PieceColour colour, Coordinate move){
        return getPossibleMovesBoard(colour).contains(move);
    }

    public boolean isCheckmate(PieceColour colour){
        return getPossibleMovesBoard(colour).isEmpty();
    }
}
