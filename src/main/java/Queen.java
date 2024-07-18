/**
 * A Queen in chess which can move horizontally and diagonally.
 */
public class Queen extends Piece{
    /**
     * Creates a {@code Queen}
     * @param colour if it is black or white
     */
    public Queen(Coordinate position, PieceColour colour) {
        super(position, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
        calculateStraightMoves(board);
    }

    @Override
    public String toString() {
        return "queen";
    }

    @Override
    public char toCharacter() {
        return 'Q';
    }
}
