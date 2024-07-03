import java.util.ArrayList;
import java.util.Objects;

public class Rook extends Piece{
    private boolean moved = false;

    public Rook(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateStraightMoves(moves);
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void setPos(int x, int y) {
        super.setPos(x, y);
        moved = true;
    }

    @Override
    public void firstMove(){
        moved = true;
    }

    @Override
    public boolean hadFirstMove(){return moved;}

    @Override
    public String toString() {
        return "rook";
    }

    @Override
    public char toCharacter() {
        return 'R';
    }

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
