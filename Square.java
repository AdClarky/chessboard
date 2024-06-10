import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    private Piece currentPiece;
    private final int xPos;
    private final int yPos;

    public Square(Piece currentPiece, Color color, int xPos, int yPos) {
        super();
        this.xPos = xPos;
        this.yPos = yPos;
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

    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
}
