/**
 * A Bishop in chess which can only move diagonally.
 */
public class Bishop extends Piece{
    /**
     * Creates a {@code Bishop}
     * @param colour if it is black or white
     */
    public Bishop(Coordinate position, PieceColour colour) {
        super(position, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
    }

    @Override
    public String toString() {
        return "bishop";
    }

    @Override
    public char toCharacter() {
        return 'B';
    }
}
