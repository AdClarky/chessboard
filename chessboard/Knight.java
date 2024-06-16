package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Knight extends Piece{
    private static final int[] possibleX = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] possibleY = {-2, -1, 1, 2, -2, -1, 1, 2};

    public Knight(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();

        for(int i = 0; i<8; i++){
            cantMove(x+possibleX[i], y+possibleY[i], board, moves);
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public String toString() {
        return "ChessBoard.Knight, " + x + "," + y + ", " + direction + "; ";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_knight.png");
        else
            return ImageUtils.getStrechedImage("assets/white_knight.png");
    }
}
