import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CoordinateTest {
    @Test
    void cornersTest(){
        assertEquals("a1", new Coordinate(7, 0).toString());
        assertEquals("a8", new Coordinate(7, 7).toString());
        assertEquals("h1", new Coordinate(0, 0).toString());
        assertEquals("h8", new Coordinate(0, 7).toString());
    }

    @Test
    void middleTest(){
        assertEquals("b3", new Coordinate(7, 0).toString());
        assertEquals("d2", new Coordinate(7, 7).toString());
        assertEquals("f5", new Coordinate(0, 0).toString());
        assertEquals("e4", new Coordinate(0, 7).toString());
    }

    @Test
    void outOfBoundsTest(){
        assertEquals("Invalid", new Coordinate(-1, 0).toString());
        assertEquals("Invalid", new Coordinate(100, 7).toString());
        assertEquals("Invalid", new Coordinate(0, -10).toString());
        assertEquals("Invalid", new Coordinate(0, 123).toString());
    }
}
