import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BoardHistory {
    private final ArrayDeque<Move> moves = new ArrayDeque<>(40);
    private final ArrayDeque<Move> redoMoves = new ArrayDeque<>(40);
    private Deque<Move> lastMove = moves;

    public @Nullable Piece getLastPieceMoved(){
        return lastMove.isEmpty() ? null : lastMove.getFirst().getPiece();
    }

    public @NotNull List<MoveValue> getLastMoves(){
        if(lastMove.isEmpty())
            return new ArrayList<>(0);
        return lastMove.getFirst().getMovesToUndo();
    }

    public int getNumberOfMoves(){
        return moves.size() + redoMoves.size();
    }

    public void push(Move move){
        moves.push(move);
        lastMove = moves;
    }

    public @Nullable Move redoMove(){
        if(redoMoves.isEmpty())
            return null;
        Move move = redoMoves.pop();
        move.makeMove();
        lastMove = moves;
        moves.push(move);
        return move;
    }

    public void redoAllMoves(){
        while(!redoMoves.isEmpty()){
            redoMove();
        }
    }

    public @Nullable Move undoMove(){
        if(moves.isEmpty())
            return null;
        Move move = moves.pop();
        move.undo();
        lastMove = redoMoves;
        redoMoves.push(move);
        return move;
    }

    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

    public boolean canRedoMove() {return !redoMoves.isEmpty();}
}
