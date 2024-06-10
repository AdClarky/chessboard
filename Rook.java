import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Rook extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_rook.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_rook.png");

    public Rook(int x, int y, Icon icon) {
        super(x, y, icon);
    }


    @Override
    public String toString() {
        return "Rook";
    }
}
