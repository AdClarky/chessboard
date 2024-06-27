import javax.swing.Icon;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Bishop for Chess. Only moves diagonally.
 */
public class Bishop extends Piece{
    /**
     * Constructor for a bishop.
     * @param x starting x position
     * @param y starting y position
     * @param direction flags are {@link Piece#BLACK_PIECE} and {@link Piece#WHITE_PIECE}
     */
    public Bishop(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction, 'B');
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        calculateDiagonalMoves(board, moves);
        moves.removeIf(move -> board.isMoveUnsafe(move.x(), move.y(), this));
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    /**
     * Returns an icon based on the piece colour.
     * @param colour the colour of the piece
     * @return an icon which is white or black.
     */
    private static Icon getIcon(int colour){
        if(colour == BLACK_PIECE)
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/black_bishop.png"));
        else
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/white_bishop.png"));
    }
}
