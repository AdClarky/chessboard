import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BlankTest {
    private final Blank blank = new Blank(new Coordinate(0, 0));

    @Test
    void testPossibleMoves(){
        assertTrue(blank.getPossibleMoves().isEmpty());
        assertEquals(0, blank.getX());
        assertEquals(0, blank.getY());
    }

    @Test
    void stringTest(){
        assertEquals("Blank", blank.toString());
    }

    @Test
    void characterIsZ(){
        assertEquals('Z', new Blank(new Coordinate(0, 0)).toCharacter());
    }
}
