import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    private Piece currentPiece;

    public Square(Color color) {
        super();
        setBackground(color);
        if(currentPiece != null){
            setText(currentPiece.toString());
        }
    }
}
