/**
 * A Queen in chess which can move horizontally and diagonally.
 */
public class Queen extends Piece{
    /**
     * Creates a {@code Queen}
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public Queen(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
        calculateStraightMoves(board);
    }

    @Override
    void firstMove() {}

    @Override
    void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    @Override
    public String toString() {
        return "queen";
    }

    @Override
    public char toCharacter() {
        return 'Q';
    }
}
