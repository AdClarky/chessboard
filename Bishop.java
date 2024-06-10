import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Bishop extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_bishop.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_bishop.png");

    public Bishop(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public ArrayList<Integer> possibleMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        return moves;
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
