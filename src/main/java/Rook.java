import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Chess Rook piece
 */
public class Rook extends Piece{
    private boolean moved = true;

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
        moved = true;
    }

    @Override
    public boolean hadFirstMove(){return moved;}

    @Override
    public String toString() {return "R";}

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStretchedImage("chessboard/src/main/resources/black_rook.png");
        else
            return ImageUtils.getStretchedImage("chessboard/src/main/resources/white_rook.png");
    }
}
