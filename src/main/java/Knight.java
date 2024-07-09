/**
 * A Knight in chess which can move forward two then left or right.
 */
public class Knight extends Piece{
    private static final int[] POSSIBLE_X = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] POSSIBLE_Y = {-2, -1, 1, 2, -2, -1, 1, 2};

    /**
     * Creates a {@code Knight}
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public Knight(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        for(int i = 0; i<8; i++){
            cantMove(board, x+ POSSIBLE_X[i], y+ POSSIBLE_Y[i]);
        }
    }

    @Override
    void firstMove() {}

    @Override
    void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){
        return false;
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
