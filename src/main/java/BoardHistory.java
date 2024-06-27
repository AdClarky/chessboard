import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

public class BoardHistory {
    private final ArrayDeque<Move> moves = new ArrayDeque<>(40);
    private final ArrayDeque<Move> redoMoves = new ArrayDeque<>(40);
    private Piece lastPieceMoved;

    public @Nullable Piece getLastPieceMoved(){
        return lastPieceMoved;
    }

    void push(Move move){
        moves.push(move);
        lastPieceMoved = move.getPiece();
    }

    private Move redoMove(){
        Move move = redoMoves.pop();
        move.makeMove();
        lastPieceMoved = move.getPiece();
        moves.push(move);
        return move;
    }

    private void redoMoves(){
        while(!redoMoves.isEmpty()){
            redoMove();
        }
    }

    private Move undoMove(){
        Move move = moves.pop();
        move.undo();
        lastPieceMoved = move.getPiece();
        redoMoves.push(move);
        return move;
    }

    private void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }
}
