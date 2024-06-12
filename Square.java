import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    public static Color SELECTED = new Color(245, 246, 130);
    public static Color POSSIBLE_MOVE = new Color(200, 50, 50);
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

    public void selected(){
        setBackground(SELECTED);
    }

    public void unselected(){
        setBackground(colour);
    }

    public void setPossibleMove(boolean possible){
        if(possible){
            setBackground(POSSIBLE_MOVE);
        }else{
            setBackground(colour);
        }
    }
}
