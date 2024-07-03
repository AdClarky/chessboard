public class InvalidMoveException extends Exception {
    public InvalidMoveException(int oldX, int oldY, int newX, int newY) {
        super("Cannot move from " + new Coordinate(oldX, oldY) + " to " + new Coordinate(newX, newY));
    }
}
