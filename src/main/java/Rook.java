/**
 * A Rook in chess which can only move horizontally.
 */
public class Rook extends Piece{
    /**
     * Creates a {@code Rook}
     * @param colour if it is black or white
     */
    public Rook(Coordinate position, PieceColour colour) {
        super(position, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateStraightMoves(board);
    }

    @Override
    public String toString() {
        return "rook";
    }

    @Override
    public char toCharacter() {
        return 'R';
    }
}
