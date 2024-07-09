import java.util.Objects;

public class Rook extends Piece{
    private boolean moved = false;

    public Rook(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateStraightMoves(board);
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
