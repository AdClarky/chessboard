import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class King extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_king.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_king.png");

    public King(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                if(canMove(x, y, board)) {
                    moves.add(new Coordinate(x, y));
                }
            }
        }
        return moves;
    }

    private boolean canMove(int x, int y, Board board) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        return board.getPiece(x, y) == null || board.getPiece(x, y).getDirection() != direction;
    }

    @Override
    public String toString() {
        return "King";
    }
}
