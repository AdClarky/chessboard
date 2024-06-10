import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class Piece {
    public static int WHITE = 0;
    public static int BLACK = 1;

    protected Icon icon;
    protected int x;
    protected int y;

    public  Piece(int x, int y, Icon icon) {
        this.x = x;
        this.y = y;
        this.icon = icon;
    }

    @Override
    public abstract String toString();

}
