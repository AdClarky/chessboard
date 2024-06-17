package chessboard;

import chessboard.assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Chess Rook piece
 */
public class Rook extends Piece{
    private boolean notMoved = false;

    /**
     * Initialises Rook piece
     * @param x starting x
     * @param y starting y
     * @param direction black or white
     */
    public Rook(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateStraightMoves(board, moves);
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove(){
        notMoved = false;
    }

    @Override
    public boolean hadFirstMove(){return notMoved;}

    @Override
    public String toString() {return "ChessBoard.Rook, " + x + "," + y + ", " + direction + "; ";}

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStretchedImage("chessboard/assets/black_rook.png");
        else
            return ImageUtils.getStretchedImage("chessboard/assets/white_rook.png");
    }
}
