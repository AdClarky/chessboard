import javax.swing.Icon;
import java.util.ArrayList;

public class Knight extends Piece{
    private static final int[] POSSIBLE_X = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] POSSIBLE_Y = {-2, -1, 1, 2, -2, -1, 1, 2};

    public Knight(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        for(int i = 0; i<8; i++){
            cantMove(x+ POSSIBLE_X[i], y+ POSSIBLE_Y[i], moves);
        }
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    @Override
    public String toString() {
        return "knight";
    }

    @Override
    public char toCharacter() {
        return 'N';
    }
}
