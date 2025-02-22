package chessboard;

import common.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class PossibleMoves{
    private final Map<Coordinate, Bitboard> possibleMovesBoard;

    public PossibleMoves(){
        possibleMovesBoard = new HashMap<>(16);
    }

    private PossibleMoves(Map<Coordinate, Bitboard> possibleMovesBoard){
        this.possibleMovesBoard = possibleMovesBoard;
    }

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
        Bitboard board = possibleMovesBoard.get(currentPos);
        if(board == null)
            return false;
        return possibleMovesBoard.get(currentPos).contains(move);
    }

    public boolean isEmpty(){
        for(Bitboard moves : possibleMovesBoard.values()){
            if(!moves.isEmpty())
                return false;
        }
        return true;
    }

    PossibleMoves copy() {
        return new PossibleMoves(new HashMap<>(possibleMovesBoard));
    }
}
