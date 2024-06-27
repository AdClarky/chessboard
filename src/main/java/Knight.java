import javax.swing.Icon;
import java.nio.file.Path;
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
    public Knight(int x, int y, int direction, Chessboard board) {
        super(x, y, getIcon(direction), direction, 'N', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        for(int i = 0; i<8; i++){
            cantMove(x+ POSSIBLE_X[i], y+ POSSIBLE_Y[i], board, moves);
        }
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    private static Icon getIcon(int colour){
        if(colour == BLACK_PIECE)
            return ImageUtils.getStretchedImage(Knight.class.getResource("/black_knight.png"));
        else
            return ImageUtils.getStretchedImage(Knight.class.getResource("/white_knight.png"));
    }
}
