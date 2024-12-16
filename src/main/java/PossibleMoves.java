import java.util.HashMap;
import java.util.Map;

public class PossibleMoves{
    private final Map<Coordinate, Bitboard> possibleMovesBoard = new HashMap<>(16);

    public PossibleMoves(){}

    public void addMoves(Coordinate position, Bitboard map){
        possibleMovesBoard.put(position, map);
    }

    public void removeMoves(Coordinate position, Bitboard moves){
        possibleMovesBoard.get(position).removeAll(moves);
    }

    public Bitboard getPossibleMove(Coordinate position){
        return possibleMovesBoard.get(position);
    }

    public boolean isPossible(Coordinate currentPos, Coordinate move){
        return possibleMovesBoard.get(currentPos).contains(move);
    }

    public boolean isEmpty(){
        for(Bitboard moves : possibleMovesBoard.values()){
            if(!moves.isEmpty())
                return false;
        }
        return true;
    }
}
