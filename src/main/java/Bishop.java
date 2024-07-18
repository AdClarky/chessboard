/**
 * A Bishop in chess which can only move diagonally.
 */
public class Bishop extends Piece{
    /**
     * Creates a {@code Bishop}
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public Bishop(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
    }

    @Override
    void firstMove() {}

    @Override
    public String toString() {
        return "bishop";
    }

    @Override
    public char toCharacter() {
        return 'B';
    }
}
