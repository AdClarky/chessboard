package chessboard;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

class HashHistory {
    private final Deque<HistoryEntry> moves;
    private final Deque<HistoryEntry> redoMoves;
    private int numFullMoves = 1;
    private int numHalfMoves = 0;

    public HashHistory(long initialHash) {
        moves = new ArrayDeque<>(40);
        redoMoves = new ArrayDeque<>(40);
        moves.push(new HistoryEntry(initialHash, 0));
    }

    public HashHistory(long intialHash, int numFullMoves, int numHalfMoves) {
        this(intialHash);
        this.numFullMoves = numFullMoves;
        this.numHalfMoves = numHalfMoves;
    }

    public void push(long hash, boolean isHalfMove) {
        int nextHalfMove = isHalfMove ? 0 : getNumHalfMoves() + 1;
        moves.push(new HistoryEntry(hash, nextHalfMove));
        redoMoves.clear();
    }

    public @Nullable Long undo() {
        if (moves.size() <= 1)
            return null;
        HistoryEntry entry = moves.pop();
        redoMoves.push(entry);
        return entry.hash();
    }

    public @Nullable Long redo() {
        if (redoMoves.isEmpty())
            return null;
        HistoryEntry entry = redoMoves.pop();
        moves.push(entry);
        return entry.hash();
    }

    public void redoAllMoves() {
        while (!redoMoves.isEmpty()) {
            redo();
        }
    }

    public void undoMultipleMoves(int numOfMoves) {
        for (int i = 0; i < numOfMoves; i++) {
            undo();
        }
    }

    public boolean canUndoMove() {
        return moves.size() > 1;
    }

    public boolean canRedoMove() {
        return !redoMoves.isEmpty();
    }

    public int getNumHalfMoves() {
        if(numHalfMoves != 0){
            int temp = numHalfMoves;
            numHalfMoves = 0;
            return temp;
        }
        if (moves.isEmpty()) {
            return 0;
        }
        return moves.peek().halfMoves();
    }

    public int getNumFullMoves() {
        int singleMove = moves.size() - 1;
        if (singleMove < 0)
            singleMove = 0;

        return 0 + (singleMove / 2) + numFullMoves;
    }

    public long getCurrentHash(){
        if (moves.isEmpty())
            throw new IllegalStateException("History is empty");
        return moves.peek().hash();
    }

    public boolean isRepetition() {
        if (moves.isEmpty()) {
            return false;
        }

        long currentHash = getCurrentHash();
        int occurences = 0;

        for (HistoryEntry entry : moves) {
            if (entry.hash() == currentHash)
                occurences++;
        }

        return occurences >= 3;
    }

    private record HistoryEntry(long hash, int halfMoves) {
    }
}
