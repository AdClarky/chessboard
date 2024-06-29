import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, 'B', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        calculateDiagonalMoves(moves);
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}
}
