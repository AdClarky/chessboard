import assets.ImageUtils;
import javax.swing.Icon;

public class Pawn extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_pawn.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_pawn.png");

    public Pawn(int x, int y, Icon icon) {
        super(x, y, icon);
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
