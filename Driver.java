import chessboard.Board;
import chessboard.BoardListener;

import java.nio.file.Path;

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
        BoardListener gameWindow = new GameWindow(board);
        board.addBoardListener(gameWindow);
        Autoplay autoplay = new Autoplay(board);
        autoplay.importGame(Path.of("games/junk437_vs_AdClarky_2024.06.18.pgn"));
        autoplay.play(1);
    }
}
