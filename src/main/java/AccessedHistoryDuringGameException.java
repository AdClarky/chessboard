/**
 * Thrown to indicate a game's history has been accessed after a game has started
 */
public class AccessedHistoryDuringGameException extends Exception {
    /**
     * Constructs an {@code AccessedHistoryDuringGameException} with
     * the default message.
     */
    public AccessedHistoryDuringGameException() {
        super("Cannot set history elements during a game.");
    }
}
