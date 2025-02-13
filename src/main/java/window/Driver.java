import chessboard.ChessGame;
import common.BoardListener;
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
        ChessGame chessGame = new ChessGame();
        BoardListener gameWindow = new GameWindow(chessGame, PieceColour.WHITE);
        chessGame.addBoardListener(gameWindow);
        int positions = testDepth(chessGame, 2);
        System.out.println(positions);
    }






    static int testDepth(ChessGame board, int currentDepth) throws InterruptedException {
        if (currentDepth == 0)
            return 0;

        int positions = 0;
        Collection<Piece> pieces = board.getColourPieces(board.getCurrentTurn());
        for(Piece piece : pieces){
            Collection<Coordinate> positionCoordinates = new ArrayList<>(piece.getPossibleMoves());
            positions += positionCoordinates.size();
            for(Coordinate position : positionCoordinates){
                try {
                    board.makeMove(piece.getX(), piece.getY(), position.x(), position.y());
                } catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                }
                positions += testDepth(board, currentDepth - 1);
                board.undoMove();
            }
        }

        return positions;
    }
}
