import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Square which displays a chess piece or blank.
 */
public class Square extends JButton {
    private static final Color SELECTED = new Color(245, 246, 130);
    private static final Color POSSIBLE_MOVE = new Color(200, 50, 50);
    private final Color colour;
    private Piece currentPiece;
    private final int boardX;
    private final int boardY;

    /**
     * Initialises the square either with a piece or with no piece (blank).
     * @param currentPiece the piece which is currently on the square
     * @param colour background colour of the square
     * @param boardX the x which the square represents on the board
     * @param boardY the y which the square represents on the board
     */
    public Square(Piece currentPiece, Color colour, int boardX, int boardY) {
        super();
        this.boardX = boardX;
        this.boardY = boardY;
        this.currentPiece = currentPiece;
        this.colour = colour;
        setBackground(colour);
        setCurrentPiece(currentPiece);
    }

    @NotNull
    public Piece getCurrentPiece() {return currentPiece;}

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
        setIcon(currentPiece.getPieceIcon());
    }

    public boolean isBlank(){return currentPiece instanceof Blank;}

    public int getBoardX() {return boardX;}
    public int getBoardY() {return boardY;}

    /**
     * If the square has been selected
     */
    public void selected(){
        setBackground(SELECTED);
    }

    /**
     * Sets the background to the default colour
     */
    public void unhighlight(){
        setBackground(colour);
    }

    /**
     * Sets the background to red.
     */
    public void showPossibleMove(){
        setBackground(POSSIBLE_MOVE);
    }
}
