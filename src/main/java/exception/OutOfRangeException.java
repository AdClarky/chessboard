package exception;

public class OutOfRangeException extends IllegalArgumentException {
    public OutOfRangeException(Coordinate position) {
        super("Coordinate " + position.toString() + " is out of range");
    }
}
