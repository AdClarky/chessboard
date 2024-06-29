import java.util.ArrayList;

/**
 * Queen chess piece
 */
public class Queen extends Piece{
    /**
     * Initialises the Queen.
     * @param x starting x position
     * @param y starting y position
     * @param colour black or white
     */
    public Queen(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, 'Q', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateDiagonalMoves(moves);
        calculateStraightMoves(moves);
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
