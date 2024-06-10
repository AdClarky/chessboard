import javax.swing.Icon;
import javax.swing.ImageIcon;

public class King extends Piece{
    public static Icon black = new ImageIcon("assets/black_king.png");
    public static Icon white = new ImageIcon("assets/white_king.png");

    public King(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public String toString() {
        return "King";
    }
}
