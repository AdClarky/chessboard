package window;

import chessboard.ChessGame;
import chessboard.ChessInterface;
import common.BoardListener;
import common.PieceColour;
import exception.InvalidMoveException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Used to run the program. Creates a window in a new thread.
 */
public final class Driver {
    private Driver(){}

    /**
     * Runs the program opening a game window.
     * @param args none taken.
     */
    public static void main(String[] args) throws IOException, InterruptedException, InvalidMoveException {
        ChessInterface chessGame = new ChessInterface();
        BoardListener gameWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(gameWindow);
    }
}
