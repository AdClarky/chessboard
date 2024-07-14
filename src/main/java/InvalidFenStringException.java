/**
 * Thrown to indicate a given FEN String is invalid.
 */
public class InvalidFenStringException extends Exception {
    /**
     * Constructs an {@code InvalidFenStringException} with
     * the default message.
     */
    public InvalidFenStringException() {
        super("Fen string input is not valid");
    }
}
