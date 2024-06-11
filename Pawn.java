import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Pawn extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_pawn.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_pawn.png");

    public Pawn(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        if(y != 7 && y != 0) // if not at the end of a row
            moves.add(new Coordinate(x, y+direction));
        if((y == 6 && direction == UP) || (y == 1 && direction == DOWN))
            moves.add(new Coordinate(x, y+(direction<<1)));
        if(x != 0 && board.getPiece(x-1,y+direction) != null && board.getPiece(x-1,y+direction).getDirection() != direction)
            moves.add(new Coordinate(x-1,y+direction));
        if(x != 7 && board.getPiece(x+1,y+direction) != null && board.getPiece(x+1,y+direction).getDirection() != direction)
            moves.add(new Coordinate(x+1,y+direction));

        return moves;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
