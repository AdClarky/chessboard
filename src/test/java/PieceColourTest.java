import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PieceColourTest {
    @Test
    void whiteColourOtherColour(){
        assertEquals(PieceColour.BLACK, PieceColour.getOtherColour(PieceColour.WHITE));
    }

    @Test
    void blackOtherColour(){
        assertEquals(PieceColour.WHITE, PieceColour.getOtherColour(PieceColour.BLACK));
    }

    @Test
    void blankOtherColour(){
        assertEquals(PieceColour.BLANK, PieceColour.getOtherColour(PieceColour.BLANK));
    }

    @Test
    void whiteDirection(){
        assertEquals(1, PieceColour.getDirectionFromColour(PieceColour.WHITE));
    }

    @Test
    void blackDirection(){
        assertEquals(-1, PieceColour.getDirectionFromColour(PieceColour.BLACK));
    }

    @Test
    void blankDirection(){
        assertEquals(0, PieceColour.getDirectionFromColour(PieceColour.BLANK));
    }

    @Test
    void whiteString(){
        assertEquals("white", PieceColour.getStringFromColour(PieceColour.WHITE));
    }

    @Test
    void blackString(){
        assertEquals("black", PieceColour.getStringFromColour(PieceColour.BLACK));
    }

    @Test
    void blankString(){
        assertEquals("blank", PieceColour.getStringFromColour(PieceColour.BLANK));
    }
}