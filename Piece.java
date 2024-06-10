import javax.swing.Icon;
import java.util.ArrayList;

public abstract class Piece {
    protected Icon icon;
    protected int x;
    protected int y;

    public Piece(int x, int y, Icon icon) {
        this.x = x;
        this.y = y;
        this.icon = icon;
    }

    public abstract ArrayList<int[]> possibleMoves(Board board);

    @Override
    public abstract String toString();

    public Icon getIcon() {return icon;}
}
