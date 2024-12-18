import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CoordinateTest {
    @Test
    void cornersTest(){
        assertEquals("h1", new Coordinate(7, 0).toString());
        assertEquals("h8", new Coordinate(7, 7).toString());
        assertEquals("a1", new Coordinate(0, 0).toString());
        assertEquals("a8", new Coordinate(0, 7).toString());
    }

    @Test
    void middleTest(){
        assertEquals("g3", new Coordinate(6, 2).toString());
        assertEquals("e2", new Coordinate(4, 1).toString());
        assertEquals("c5", new Coordinate(2, 4).toString());
        assertEquals("d4", new Coordinate(3, 3).toString());
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
        assertEquals(new Coordinate(0, 1), Coordinate.createCoordinateFromString("a2"));
    }

    @Test
    void pawnTaking() {
        assertEquals(new Coordinate(4, 3), Coordinate.createCoordinateFromString("dxe4"));
    }

    @Test
    void pawnPromotion(){
        assertEquals(new Coordinate(7, 7), Coordinate.createCoordinateFromString("h8=Q+"));
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
        assertEquals(new Coordinate(6, 6), Coordinate.createCoordinateFromString("Qg7+"));
    }

    @Test
    void mateMove() {
        assertEquals(new Coordinate(1, 6), Coordinate.createCoordinateFromString("Qb7#"));
    }

    @Test
    void pieceMove() {
        assertEquals(new Coordinate(0, 7), Coordinate.createCoordinateFromString("Ka8"));
    }

    @Test
    void cornersTestCoordinate(){
        Coordinate a1 = new Coordinate(0, 0);
        Coordinate a8 = new Coordinate(0, 7);
        Coordinate h1 = new Coordinate(7, 0);
        Coordinate h8 = new Coordinate(7, 7);

        assertEquals(0, a1.getBitboardIndex());
        assertEquals(7, h1.getBitboardIndex());
        assertEquals(56, a8.getBitboardIndex());
        assertEquals(63, h8.getBitboardIndex());

        assertEquals(1, a1.getBitboardValue());
        assertEquals(128, h1.getBitboardValue());
        assertEquals(0x100000000000000L, a8.getBitboardValue());
        assertEquals(0x8000000000000000L, h8.getBitboardValue());
    }

    @Test
    void rangeTest(){
        Coordinate a1 = new Coordinate(0, 0);
        Coordinate a8 = new Coordinate(0, 7);
        Coordinate h1 = new Coordinate(7, 0);
        Coordinate h8 = new Coordinate(7, 7);
        Coordinate outOfRange1 = new Coordinate(-1 , 0);
        Coordinate outOfRange2 = new Coordinate(8 , 0);
        Coordinate outOfRange3 = new Coordinate(0 , -1);
        Coordinate outOfRange4 = new Coordinate(0 , 8);

        assertTrue(outOfRange1.isNotInRange());
        assertTrue(outOfRange2.isNotInRange());
        assertTrue(outOfRange3.isNotInRange());
        assertTrue(outOfRange4.isNotInRange());
        assertFalse(a1.isNotInRange());
        assertFalse(a8.isNotInRange());
        assertFalse(h1.isNotInRange());
        assertFalse(h8.isNotInRange());
    }
}
