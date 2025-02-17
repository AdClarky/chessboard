package app;

import chessboard.ChessInterface;
import common.BoardListener;
import common.PieceColour;
import exception.InvalidMoveException;
import window.GameWindow;

import java.io.IOException;

/**
 * Used to run the program. Creates a window in a new thread.
 */
public class Main {
    /**
     * Runs the program opening a game window.
     * @param args none taken.
     */
    public static void main(String[] args) throws IOException, InterruptedException, InvalidMoveException {
        ChessInterface chessGame = new ChessInterface();
        BoardListener whiteWindow = new GameWindow(chessGame, PieceColour.WHITE);
        BoardListener blackWindow = new GameWindow(chessGame, PieceColour.BLACK);
        chessGame.addBoardListener(whiteWindow);
        chessGame.addBoardListener(blackWindow);
    }
}
