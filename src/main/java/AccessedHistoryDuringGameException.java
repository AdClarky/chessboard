public class AccessedHistoryDuringGameException extends Exception {
    public AccessedHistoryDuringGameException() {
        super("Cannot set history elements during a game.");
    }
}
