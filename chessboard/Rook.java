package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Rook extends Piece{
    private boolean notMoved = true;

    public Rook(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int x = this.x+1; x < 8 && x >= 0; x++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1; x < 8 && x >= 0; x--){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y+1; y < 8 && y >= 0; y++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y-1; y < 8 && y >= 0; y--){
            if(cantMove(x, y, board, moves))
                break;
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    public boolean getNotMoved(){return this.notMoved;}

    @Override
    public void firstMove(){
        notMoved = false;
    }

    @Override
    public String toString() {return "ChessBoard.Rook, " + x + "," + y + ", " + direction + "; ";}

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_rook.png");
        else
            return ImageUtils.getStrechedImage("assets/white_rook.png");
    }
}
