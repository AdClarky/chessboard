public class Queen extends Piece{
    public Queen(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
        calculateStraightMoves(board);
        removeMovesInCheck(board);
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

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
