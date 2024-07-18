/**
 * A Knight in chess which can move forward two then left or right.
 */
public class Knight extends Piece{
    private static final int[] POSSIBLE_X = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] POSSIBLE_Y = {-2, -1, 1, 2, -2, -1, 1, 2};

    /**
     * Creates a {@code Knight}
     * @param colour if it is black or white
     */
    public Knight(Coordinate position, PieceColour colour) {
        super(position, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        for(int i = 0; i<8; i++){
            Coordinate move = new Coordinate(getX()+ POSSIBLE_X[i], getY()+ POSSIBLE_Y[i]);
            if(move.isInRange())
                possibleMoves.add(move);
        }
    }

    @Override
    public String toString() {
        return "knight";
    }

    @Override
    public char toCharacter() {
        return 'N';
    }
}
