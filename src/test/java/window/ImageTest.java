package window;

import common.PieceColour;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.ImageIcon;

class ImageTest {
    @Test
    void blankImage(){
        ImageIcon icon = ImageUtils.getPieceImage("Blank", PieceColour.WHITE);
        assertNull(icon);
    }

    @Test
    void randomString(){
        assertThrows(IllegalArgumentException.class, ()->{
            ImageIcon icon = ImageUtils.getPieceImage("asdjkasjkdl", PieceColour.WHITE);
        });
    }

    @Test
    void correctWhitePiece(){
        ImageIcon icon = ImageUtils.getPieceImage("pawn", PieceColour.WHITE);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("bishop", PieceColour.WHITE);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("knight", PieceColour.WHITE);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("rook", PieceColour.WHITE);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("king", PieceColour.WHITE);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("queen", PieceColour.WHITE);
        assertNotNull(icon);
    }

    @Test
    void correctBlackPiece(){
        ImageIcon icon = ImageUtils.getPieceImage("pawn", PieceColour.BLACK);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("bishop", PieceColour.BLACK);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("knight", PieceColour.BLACK);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("rook", PieceColour.BLACK);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("king", PieceColour.BLACK);
        assertNotNull(icon);
        icon = ImageUtils.getPieceImage("queen", PieceColour.BLACK);
        assertNotNull(icon);
    }
}