import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class King extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_king.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_king.png");
    private boolean notMoved = true;

    public King(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(x, y, board, moves);
            }
        }
        if(notMoved){
            if(board.getPiece(x+3, y) instanceof Rook rook && rook.getNotMoved()){
                if(board.getPiece(x+1, y) == null && board.getPiece(x+2, y) == null)
                    moves.add(new Coordinate(x+2, y));
            }
            if(board.getPiece(x-4, y) instanceof Rook rook && rook.getNotMoved()){
                if(board.getPiece(x-1, y) == null && board.getPiece(x-2, y) == null && board.getPiece(x-3, y) == null)
                    moves.add(new Coordinate(x-2, y));
            }
        }
        checkMovesForCheck(board, moves);
        return moves;
    }

    @Override
    public void setX(int x) {
        notMoved = false;
        super.setX(x);
    }

    @Override
    public String toString() {
        return "King, " + x + "," + y + ", " + direction + "; ";
    }
}
