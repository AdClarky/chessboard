package window;

import common.Coordinate;
import common.PieceColour;
import common.PieceValue;
import common.Pieces;
import org.jetbrains.annotations.Nullable;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Square which displays a chess piece or blank.
 */
public class Square extends JButton {
    private static final Color SELECTED = new Color(245, 246, 130);
    private static final Color POSSIBLE_MOVE = new Color(200, 50, 50);
    private final Color bgColour;
    private Pieces piece;
    private Coordinate position;
    private PieceColour colour;

    /**
     * Initialises the square either with a piece or with no piece (blank).
     * @param piece the piece which is currently on the square
     * @param bgColour background colour of the square
     */
    public Square(PieceValue piece, Color bgColour) {
        super();
        position = piece.position();
        this.piece = piece.pieceType();
        colour = piece.colour();
        this.bgColour = bgColour;
        setBackground(bgColour);
        setCurrentPiece(piece);
    }

    @Nullable
    public Pieces getPiece() {
        return piece;
    }

    @Nullable
    public PieceColour getColour() {
        return colour;
    }


    public void setCurrentPiece(PieceValue currentPiece) {
        piece = currentPiece.pieceType();
        colour = currentPiece.colour();
        setIcon(ImageUtils.getPieceImage(currentPiece));
    }

    public boolean isBlank(){
        return colour == null;
    }

    public Coordinate getPosition() {
        return position;
    }

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
        setBackground(bgColour);
    }

    /**
     * Sets the background to red.
     */
    public void showPossibleMove(){
        setBackground(POSSIBLE_MOVE);
    }
}
