import java.util.ArrayList;
import java.util.Objects;

/**
 * Chess Rook piece
 */
public class Rook extends Piece{
    private boolean moved = false;

    /**
     * Initialises Rook piece
     * @param x starting x
     * @param y starting y
     * @param colour black or white
     */
    public Rook(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, 'R', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateStraightMoves(board, moves);
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void firstMove(){
        moved = true;
    }

    @Override
    public boolean hadFirstMove(){return moved;}

    @Override
    public void undoMoveCondition(){moved = false;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Rook rook = (Rook) obj;
        return moved == rook.moved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moved);
    }
}
