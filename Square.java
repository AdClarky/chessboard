import ChessBoard.Piece;

import javax.swing.JButton;
import java.awt.Color;

public class Square extends JButton {
    public static Color SELECTED = new Color(245, 246, 130);
    public static Color POSSIBLE_MOVE = new Color(200, 50, 50);
    private final Color colour;
    private Piece currentPiece;
    private final int boardX;
    private final int boardY;

    public Square(Piece currentPiece, Color color, int boardX, int boardY) {
        super();
        this.boardX = boardX;
        this.boardY = boardY;
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

    public int getBoardX() {return boardX;}
    public int getBoardY() {return boardY;}

    public void selected(){
        setBackground(SELECTED);
    }

    public void unhighlight(){
        setBackground(colour);
    }

    public void showPossibleMove(){
        setBackground(POSSIBLE_MOVE);
    }
}
