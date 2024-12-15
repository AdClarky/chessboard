import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PieceColourTest {
    @Test
    void whiteColourOtherColour(){
        assertEquals(PieceColour.BLACK, PieceColour.WHITE.invert());
    }

    @Test
    void blackOtherColour(){
        assertEquals(PieceColour.WHITE, PieceColour.BLACK.invert());
    }

    @Test
    void blankOtherColour(){
        assertEquals(PieceColour.BLANK, PieceColour.BLANK.invert());
    }

    @Test
    void whiteDirection(){
        assertEquals(1, PieceColour.WHITE.direction());
    }

    @Test
    void blackDirection(){
        assertEquals(-1, PieceColour.BLACK.direction());
    }

    @Test
    void blankDirection(){
        assertEquals(0, PieceColour.BLANK.direction());
    }

    @Test
    void whiteString(){
        assertEquals("white", PieceColour.WHITE.toString());
    }

    @Test
    void blackString(){
        assertEquals("black", PieceColour.BLACK.toString());
    }

    @Test
    void blankString(){
        assertEquals("blank", PieceColour.BLANK.toString());
    }
}