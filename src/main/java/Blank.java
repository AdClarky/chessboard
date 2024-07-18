/**
 * Represents a blank square in the board. Mostly can be treated as a piece in generic functions, methods will just
 * do nothing or return empty lists/false.
 */
public class Blank extends Piece {
    /**
     * Creates a {@code Blank}
     */
    public Blank(Coordinate position) {
        super(position, PieceColour.BLANK);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board){}

    @Override
    public String toString() {
        return "Blank";
    }

    @Override
    public char toCharacter() {
        return 'Z';
    }
}