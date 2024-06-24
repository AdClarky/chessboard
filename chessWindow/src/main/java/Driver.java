import javax.swing.JFileChooser;
import java.io.File;
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
        BoardListener gameWindow = new GameWindow(board, Piece.BLANK_PIECE);
        board.addBoardListener(gameWindow);
        Autoplay autoplay = new Autoplay(board);
        JFileChooser chooser = new JFileChooser("games");
        chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
        if(file == null)
            return;
        autoplay.importGame(Path.of(file.getPath()));
        autoplay.play(5);
    }
}
