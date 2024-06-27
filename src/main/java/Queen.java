import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Queen chess piece
 */
public class Queen extends Piece{
    /**
     * Initialises the Queen.
     * @param x starting x position
     * @param y starting y position
     * @param direction black or white
     */
    public Queen(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction, 'Q');
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateDiagonalMoves(board, moves);
        calculateStraightMoves(board, moves);
        moves.removeIf(move -> board.isMoveUnsafe(move.x(), move.y(), this));
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
            return ImageUtils.getStretchedImage(Queen.class.getResource("/black_queen.png"));
        else
            return ImageUtils.getStretchedImage(Queen.class.getResource("/white_queen.png"));
    }
}
