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
    private int numHalfMoves = 0;
    private int numFullMoves = 1;

    public @Nullable Piece getLastPieceMoved(){
        return lastMove.isEmpty() ? null : lastMove.getFirst().getPiece();
    }

    public @NotNull List<MoveValue> getLastMoves(){
        if(lastMove.isEmpty())
            return new ArrayList<>(0);
        return lastMove.getFirst().getMovesToUndo();
    }

    public void push(Move move){
        moves.push(move);
        if(move.isPieceColourBlack())
            numFullMoves++;
        if(move.isPieceAPawn() || move.hasTaken())
            numHalfMoves = 0;
        else
            numHalfMoves++;
        lastMove = moves;
    }

    /** Returns null if there are no moves to redo. */
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

    /** Returns null if there are no moves to undo. */
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

    public int getNumHalfMoves(){
        return numHalfMoves;
    }
    public int getNumFullMoves(){return numFullMoves;}

    public void setNumFullMoves(int numFullMoves) throws AccessedHistoryDuringGameException {
        if(!redoMoves.isEmpty() || !moves.isEmpty())
            throw new AccessedHistoryDuringGameException();
        this.numFullMoves = numFullMoves;
    }

    public void setNumHalfMoves(int numHalfMoves) throws AccessedHistoryDuringGameException {
        if(!redoMoves.isEmpty() || !moves.isEmpty())
            throw new AccessedHistoryDuringGameException();
        this.numHalfMoves = numHalfMoves;
    }
}
