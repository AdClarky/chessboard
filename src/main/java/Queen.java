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
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateDiagonalMoves(board, moves);
        calculateStraightMoves(board, moves);
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public boolean hadFirstMove(){return false;}

    @Override
    public String toString() {
        return "Q";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStretchedImage("chessboard/src/main/resources/black_queen.png");
        else
            return ImageUtils.getStretchedImage("chessboard/src/main/resources/white_queen.png");
    }
}
