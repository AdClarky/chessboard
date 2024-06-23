import chessboard.Board;
import chessboard.BoardListener;
import chessboard.Piece;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
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
        BoardListener whitePieces = new GameWindow(board, Piece.WHITE_PIECE);
        board.addBoardListener(whitePieces);
        BoardListener blackPieces = new GameWindow(board, Piece.BLACK_PIECE);
        board.addBoardListener(blackPieces);
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
