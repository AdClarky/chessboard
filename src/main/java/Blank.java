import java.util.ArrayList;
import java.util.List;

/**
 * Represents a blank square in the board
 */
public class Blank extends Piece {
    /**
     * Initialises a blank piece with a null icon.
     * @param x x position
     * @param y y position
     */
    public Blank(int x, int y) {
        super(x, y, null, EMPTY_PIECE, 'Z');
    }

    @Override
    public List<Coordinate> getPossibleMoves(Board board) {
        return new ArrayList<>(0);
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove() {return false;}

    @Override
    public String toString() {
        return "Blank " + x + "," + y + "; ";
    }
}