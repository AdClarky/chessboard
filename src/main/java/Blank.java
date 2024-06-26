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

    // TODO: test
    @Override
    public List<Coordinate> getPossibleMoves(Board board) {
        return new ArrayList<>(0);
    }

    // TODO: test
    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove() {return false;}

    // TODO: test
    @Override
    public String toString() {
        return "Blank";
    }
}