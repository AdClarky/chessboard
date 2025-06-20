package exception;

import common.Coordinate;

/**
 * Indicates a move is not a valid move given the current board state.
 */
public class InvalidMoveException extends Exception {
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
