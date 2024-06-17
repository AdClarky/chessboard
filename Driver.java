import chessboard.Board;
import chessboard.BoardListener;

/**
 * Used to run the program. Creates a window in a new thread.
 */
public final class Driver {
    private Driver(){}

    /**
     * Runs the program opening a game window.
     * @param args none taken.
     */
    public static void main(String[] args) {
        Board board = new Board();
        new Thread(() -> {
                BoardListener gameWindow = new GameWindow(board);
                board.addBoardListener(gameWindow);
        }).start();
    }
}
