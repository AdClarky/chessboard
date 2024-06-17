package chessboard;

import chessboard.assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Knight in chess
 */
public class Knight extends Piece{
    private static final int[] POSSIBLE_X = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] POSSIBLE_Y = {-2, -1, 1, 2, -2, -1, 1, 2};

    /**
     * Initialises the knight piece.
     * @param x starting x value
     * @param y starting y value
     * @param direction black or white
     */
    public Knight(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(8);

        for(int i = 0; i<8; i++){
            cantMove(x+ POSSIBLE_X[i], y+ POSSIBLE_Y[i], board, moves);
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public boolean hadFirstMove(){return false;}

    @Override
    public String toString() {
        return "ChessBoard.Knight, " + x + "," + y + ", " + direction + "; ";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("chessboard/assets/black_knight.png");
        else
            return ImageUtils.getStrechedImage("chessboard/assets/white_knight.png");
    }
}
