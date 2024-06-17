package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Rook extends Piece{
    private boolean notMoved = false;

    public Rook(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        calculateStraightMoves(board, moves);
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove(){
        notMoved = false;
    }

    @Override
    public boolean hadFirstMove(){return notMoved;}

    @Override
    public String toString() {return "ChessBoard.Rook, " + x + "," + y + ", " + direction + "; ";}

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_rook.png");
        else
            return ImageUtils.getStrechedImage("assets/white_rook.png");
    }
}
