import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    public static Color selected = new Color(245, 246, 130);
    private Color colour;
    private Piece currentPiece;
    private final Coordinate coordinate;

    public Square(Piece currentPiece, Color color, Coordinate coordinate) {
        super();
        this.coordinate = coordinate;
        this.currentPiece = currentPiece;
        this.colour = color;
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

    public Coordinate getCoords() {return coordinate;}

    public void clicked(){
        if(colour.equals(getBackground()))
            setBackground(selected);
        else
            setBackground(colour);
    }
}
