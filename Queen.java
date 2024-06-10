import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Queen extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_queen.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_queen.png");

    public Queen(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public String toString() {
        return "Queen";
    }
}
