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
        assertEquals("b3", new Coordinate(6, 2).toString());
        assertEquals("d2", new Coordinate(4, 1).toString());
        assertEquals("f5", new Coordinate(2, 4).toString());
        assertEquals("e4", new Coordinate(3, 3).toString());
    }

    @Test
    void outOfBoundsTest(){
        assertEquals("Invalid", new Coordinate(-1, 0).toString());
        assertEquals("Invalid", new Coordinate(100, 7).toString());
        assertEquals("Invalid", new Coordinate(0, -10).toString());
        assertEquals("Invalid", new Coordinate(0, 123).toString());
    }

    @Test
    void pawnMove(){
        assertEquals(new Coordinate(0, 1), Coordinate.createCoordinateFromString("h2"));
    }

    @Test
    void pawnTaking() {
        assertEquals(new Coordinate(4, 3), Coordinate.createCoordinateFromString("cxd4"));
    }

    @Test
    void pawnPromotion(){
        assertEquals(new Coordinate(7, 7), Coordinate.createCoordinateFromString("a8=Q+"));
    }

    @Test
    void shortCastle(){
        assertThrows(IllegalArgumentException.class, () -> Coordinate.createCoordinateFromString("O-O"));
    }

    @Test
    void longCastle() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.createCoordinateFromString("O-O-O"));
    }

    @Test
    void checkMove() {
        assertEquals(new Coordinate(6, 6), Coordinate.createCoordinateFromString("Qb7+"));
    }

    @Test
    void mateMove() {
        assertEquals(new Coordinate(1, 6), Coordinate.createCoordinateFromString("Qg7#"));
    }

    @Test
    void pieceMove() {
        assertEquals(new Coordinate(0, 7), Coordinate.createCoordinateFromString("Kh8"));
    }
}
