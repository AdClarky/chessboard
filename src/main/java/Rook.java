import java.util.ArrayList;
import java.util.Objects;

public class Rook extends Piece{
    private boolean moved = false;

    public Rook(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(ChessLogic board) {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateStraightMoves(moves, board);
        removeMovesInCheck(moves, board);
        return moves;
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
}
