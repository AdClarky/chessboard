package chessboard;

import common.MoveValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class BoardHistory {
    private final ArrayDeque<Move> moves;
    private final ArrayDeque<Move> redoMoves;
    private Deque<Move> lastMove = moves;
    private int numHalfMoves = 0;
    private int numFullMoves = 1;

    public BoardHistory() {
        moves = new ArrayDeque<>(40);
        redoMoves = new ArrayDeque<>(40);
    }

    public BoardHistory(int numHalfMoves, int numFullMoves) {
        this();
        this.numHalfMoves = numHalfMoves;
        this.numFullMoves = numFullMoves;
    }

    private BoardHistory(ArrayDeque<Move> moves, ArrayDeque<Move> redoMoves, Deque<Move> lastMove, int numHalfMoves, int numFullMoves) {
        this.moves = moves;
        this.redoMoves = redoMoves;
        this.lastMove = lastMove;
        this.numHalfMoves = numHalfMoves;
        this.numFullMoves = numFullMoves;
    }

    public @NotNull List<MoveValue> getLastMoves(){
        if(lastMove.isEmpty())
            return new ArrayList<>(0);
        return lastMove.getFirst().getMovesToUndo();
    }

    public void push(Move move){
        moves.push(move);
        countMoves(move);
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
        countMoves(move);
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
        uncountMoves(move);
        return move;
    }

    public void undoMultipleMoves(int numOfMoves){
        for(int i = 0; i < numOfMoves; i++){
            undoMove();
        }
    }

    public boolean canRedoMove() {
        return !redoMoves.isEmpty();
    }

    public boolean canUndoMove() {
        return !moves.isEmpty();
    }

    public int getNumHalfMoves(){
        return numHalfMoves;
    }

    public int getNumFullMoves(){
        return numFullMoves;
    }

    public void clearRedoMoves() {
        countHalfMoves();
        lastMove = moves;
        redoMoves.clear();
    }

    private void countHalfMoves(){
        Deque<Move> temp = new ArrayDeque<>(5);
        numHalfMoves = 0;
        while(!moves.isEmpty()){
            Move move = moves.pop();
            temp.push(move);
            if(move.hasTaken() || move.isPieceAPawn())
                break;
            numHalfMoves++;
        }
        while(!temp.isEmpty()){
            moves.push(temp.pop());
        }
    }

    private void countMoves(Move move){
        if(move.isPieceColourBlack())
            numFullMoves++;
        if(move.isPieceAPawn() || move.hasTaken())
            numHalfMoves = 0;
        else
            numHalfMoves++;
    }

    private void uncountMoves(Move move) {
        if(move.isPieceColourBlack())
            numFullMoves--;
        if(move.isPieceAPawn() || move.hasTaken())
            countHalfMoves();
        else
            numHalfMoves--;
    }

    BoardHistory copy() {
        ArrayDeque<Move> movesCopy = new ArrayDeque<>(moves);
        ArrayDeque<Move> redoMovesCopy = new ArrayDeque<>(redoMoves);
        ArrayDeque<Move> lastMoveCopy = new ArrayDeque<>(lastMove);
        return new BoardHistory(movesCopy, redoMovesCopy, lastMoveCopy, numHalfMoves, numFullMoves);
    }
}
