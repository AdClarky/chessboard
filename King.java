import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class King extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_king.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_king.png");

    public King(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public ArrayList<Integer> possibleMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        return moves;
    }

    @Override
    public String toString() {
        return "King";
    }
}
