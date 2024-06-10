import assets.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Knight extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_knight.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_knight.png");

    public Knight(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
