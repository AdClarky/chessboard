import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Pawn extends Piece{
    public static Icon black = new ImageIcon("assets/black_pawn.png");
    public static Icon white = new ImageIcon("assets/white_pawn.png");

    public Pawn(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
