package chessboard;

import assets.ImageUtils;

import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Bishop for Chess. Only moves diagonally.
 */
public class Bishop extends Piece{
    public Bishop(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int x = this.x+1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x++, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x--, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x+1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x++, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x--, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public String toString() {
        return "ChessBoard.Bishop, " + x + "," + y + ", " + direction + "; ";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_bishop.png");
        else
            return ImageUtils.getStrechedImage("assets/white_bishop.png");
    }
}
