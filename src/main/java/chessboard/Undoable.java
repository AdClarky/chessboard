package chessboard;

import common.MoveValue;

public interface Undoable {
    /**
     * Moves forward one move. Does nothing if there are no more moves to be made.
     */
    void redoMove();

    /**
     * Moves backwards one move. Does nothing if there are no more moves to be made.
     */
    void undoMove();

    /**
     * Undoes the given number of moves.
     * If greater than the number of moves made, it will remain in the starting position.
     *
     * @param numOfMoves the number of moves to undo.
     * @see Undoable#undoMove()
     */
    void undoMultipleMoves(int numOfMoves);

    /**
     * Sets the board back to the most recent position. Does nothing if there are no moves to redo.
     *
     * @see Undoable#redoMove()
     */
    void redoAllMoves();
}
