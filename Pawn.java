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
    public ArrayList<int[]> possibleMoves(Board board) {
        ArrayList<int[]> moves = new ArrayList<>();

        return moves;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
