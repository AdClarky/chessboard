import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Bishop extends Piece{
    public static Icon black = new ImageIcon("assets/black_bishop.png");
    public static Icon white = new ImageIcon("assets/white_bishop.png");

    public Bishop(int x, int y, Icon icon) {
        super(x, y, icon);
    }


    @Override
    public String toString() {
        return "Bishop";
    }
}
