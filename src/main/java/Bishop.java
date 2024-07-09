public class Bishop extends Piece{
    public Bishop(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateDiagonalMoves(board);
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    @Override
    public String toString() {
        return "bishop";
    }

    @Override
    public char toCharacter() {
        return 'B';
    }
}
