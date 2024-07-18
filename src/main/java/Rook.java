/**
 * A Rook in chess which can only move horizontally.
 */
public class Rook extends Piece{
    private boolean moved = false;

    /**
     * Creates a {@code Rook}
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public Rook(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        calculateStraightMoves(board);
    }

    @Override
    public String toString() {
        return "rook";
    }

    @Override
    public char toCharacter() {
        return 'R';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Rook rook = (Rook) obj;
        return moved == rook.moved;
    }
}
