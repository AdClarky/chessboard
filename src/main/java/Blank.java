import java.util.List;

/**
 * Represents a blank square in the board. Mostly can be treated as a piece in generic functions, methods will just
 * do nothing or return empty lists/false.
 */
public class Blank extends Piece {
    public Blank(int x, int y) {
        super(x, y, PieceColour.BLANK);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board){}

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove() {return false;}

    @Override
    public String toString() {
        return "Blank";
    }

    @Override
    public char toCharacter() {
        return 'Z';
    }
}