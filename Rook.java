import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Rook extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_rook.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_rook.png");

    public Rook(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> possibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        return moves;
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
