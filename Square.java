import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    private Piece currentPiece;

    public Square(Piece currentPiece, Color color) {
        super();
        setBackground(color);
        setCurrentPiece(currentPiece);
    }

    public Piece getCurrentPiece() {return currentPiece;}

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
        if(currentPiece == null){
            setIcon(null);
        }else{
            setIcon(currentPiece.getIcon());
        }
    }
}
