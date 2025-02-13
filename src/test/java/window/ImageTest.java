package window;

import common.PieceColour;
import common.PieceValue;
import common.Pieces;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.ImageIcon;

class ImageTest {
    @Test
    void blankImage(){
        ImageIcon icon = ImageUtils.getPieceImage(new PieceValue(null, null, null));
        assertNull(icon);
    }

    @Test
    void correctWhitePiece(){
        ImageIcon icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.PAWN, PieceColour.WHITE));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.BISHOP, PieceColour.WHITE));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.KNIGHT, PieceColour.WHITE));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.ROOK, PieceColour.WHITE));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.KING, PieceColour.WHITE));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.QUEEN, PieceColour.WHITE));
        assertNotNull(icon);
    }

    @Test
    void correctBlackPiece(){
        ImageIcon icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.PAWN, PieceColour.BLACK));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.BISHOP, PieceColour.BLACK));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.KNIGHT, PieceColour.BLACK));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.ROOK, PieceColour.BLACK));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.KING, PieceColour.BLACK));
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage(new PieceValue(null, Pieces.QUEEN, PieceColour.BLACK));
        assertNotNull(icon);
    }
}