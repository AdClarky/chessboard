package exception;

import common.Coordinate;

/**
 * Indicates a move is not a valid move given the current board state.
 */
public class InvalidMoveException extends Exception {
    /**
     * Creates an {@code InvalidMoveException} giving the message in
     * algebraic form.
     * @param oldX old x of piece
     * @param oldY old y of piece
     * @param newX new x of piece
     * @param newY new y of piece
     */
    public InvalidMoveException(int oldX, int oldY, int newX, int newY) {
        super("Cannot move from " + new Coordinate(oldX, oldY) + " to " + new Coordinate(newX, newY));
    }

    public InvalidMoveException(Coordinate oldPos, Coordinate newPos){
        super("Cannot move from " + oldPos + " to " + newPos);
    }

    /**
     * Creates an {@code InvalidMoveException} giving the move given.
     * @param move the move which was invalid.
     */
    public InvalidMoveException(String move) {
        super("Cannot make move: " + move);
    }
}
