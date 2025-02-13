package chessboard;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

class BitboardTest {

    @Test
    void addBasic(){
        Bitboard bitboard = new Bitboard();
        Coordinate a1 = new Coordinate(0, 0);
        assertTrue(bitboard.add(a1));
        assertEquals(1, bitboard.getBoard());
    }

    @Test
    void addNull(){
        Bitboard bitboard = new Bitboard();
        assertThrows(NullPointerException.class, ()->bitboard.add(null));
    }


    @Test
    void removeNull() {
        Collection<Coordinate> bitboard = new Bitboard();
        assertThrows(NullPointerException.class, ()->bitboard.remove(null));
    }

    @Test
    void removeEmpty(){
        Collection<Coordinate> bitboard = new Bitboard();
        assertFalse(bitboard.remove(new Coordinate(0, 0)));
    }

    @Test
    void removeNonCoordinate(){
        Bitboard bitboard = new Bitboard();
        assertThrows(ClassCastException.class, ()->bitboard.remove("hello"));
    }

    @Test
    void removeDoesNotContain(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertFalse(bitboard.remove(new Coordinate(0, 1)));
    }

    @Test
    void removeCoordinate(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertTrue(bitboard.remove(new Coordinate(0, 0)));
    }

    @Test
    void containsAllSameBitboard(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        assertTrue(bitboard.containsAll(bitboard));
    }

    @Test
    void containsAllDifferentBitboardDifferentValues(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(1, 0));
        bitboard2.add(new Coordinate(1, 1));
        assertFalse(bitboard.containsAll(bitboard2));
    }

    @Test
    void containsAllDifferentBitboardSameValues(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        assertTrue(bitboard.containsAll(bitboard2));
    }

    @Test
    void containsAllMainHasMoreThanOther(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        assertTrue(bitboard.containsAll(bitboard2));
    }

    @Test
    void containsAllButNullObj(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(0, 0));
        list.add(null);
        assertThrows(NullPointerException.class, ()->bitboard.containsAll(list));
    }

    @Test
    void containsAllButEmptyObj(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(0);
        assertTrue(bitboard.containsAll(list));
    }

    @Test
    void containsAllValid(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(0, 0));
        list.add(new Coordinate(0, 1));
        list.add(new Coordinate(1, 1));
        assertTrue(bitboard.containsAll(list));
    }

    @Test
    void containsAllCoordinateFalse(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(0, 0));
        list.add(new Coordinate(0, 1));
        list.add(new Coordinate(0, 6));
        assertFalse(bitboard.containsAll(list));
    }

    @Test
    void addAllBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(0, 1));
        assertTrue(bitboard2.addAll(bitboard));
        assertEquals(bitboard, bitboard2);
    }

    @Test
    void addAllSameBitboardFalse(){
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(0, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        bitboard2.add(new Coordinate(0, 1));
        assertFalse(bitboard2.addAll(bitboard));
        assertEquals(bitboard, bitboard2);
    }

    @Test
    void addAllNullInCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(0, 0));
        list.add(null);
        assertThrows(NullPointerException.class, ()->bitboard.addAll(list));
    }

    @Test
    void addAllAlreadyInCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(0, 0));
        assertFalse(bitboard.addAll(list));
    }

    @Test
    void addAllEmptyCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        assertFalse(bitboard.addAll(list));
    }

    @Test
    void addAllNormal() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> list = new ArrayList<>(2);
        list.add(new Coordinate(7,7));
        list.add(new Coordinate(6,7));
        list.add(new Coordinate(5,7));
        assertTrue(bitboard.addAll(list));
        assertEquals(6, bitboard.size());
    }

    @Test
    void removeAllSameBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        bitboard2.add(new Coordinate(1, 1));
        assertTrue(bitboard.removeAll(bitboard2));
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void removeAllNothingInCommonBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(2, 0));
        bitboard2.add(new Coordinate(2, 1));
        bitboard2.add(new Coordinate(3, 1));
        assertFalse(bitboard.removeAll(bitboard2));
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void removeAllSomeInCommonBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(2, 1));
        bitboard2.add(new Coordinate(3, 1));
        assertTrue(bitboard.removeAll(bitboard2));
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void removeAllEmptyCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(1);
        assertFalse(bitboard.removeAll(bitboard2));
    }

    @Test
    void removeAllNullInCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(1);
        bitboard2.add(null);
        assertThrows(NullPointerException.class, ()->bitboard.removeAll(bitboard2));
    }

    @Test
    void removeAllSameCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        bitboard2.add(new Coordinate(1, 1));
        assertTrue(bitboard.removeAll(bitboard2));
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void removeAllSomeInCommon() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        assertTrue(bitboard.removeAll(bitboard2));
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void retainAllSameBitboard() {
        Bitboard bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        bitboard2.add(new Coordinate(1, 1));
        long bitboardBoard = bitboard.getBoard();
        assertFalse(bitboard.retainAll(bitboard2));
        assertFalse(bitboard.isEmpty());
        assertEquals(bitboardBoard, bitboard.getBoard());
    }

    @Test
    void retainAllCompletelyDifferentBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(2, 0));
        bitboard2.add(new Coordinate(2, 1));
        bitboard2.add(new Coordinate(2, 1));
        assertTrue(bitboard.retainAll(bitboard2));
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void retainAllSimilarBitboard() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(2, 1));
        bitboard2.add(new Coordinate(2, 1));
        assertTrue(bitboard.retainAll(bitboard2));
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void retainAllCollectionHasNull() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(2, 1));
        bitboard2.add(null);
        assertThrows(NullPointerException.class, ()->bitboard.retainAll(bitboard2));
    }

    @Test
    void retainAllCollectionIsEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        assertTrue(bitboard.retainAll(bitboard2));
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void retainAllSameCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(0, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(0, 1));
        bitboard2.add(new Coordinate(1, 1));
        assertFalse(bitboard.retainAll(bitboard2));
    }

    @Test
    void retainAllSimilarCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(1, 1));
        bitboard.add(new Coordinate(2, 1));
        Collection<Coordinate> bitboard2 = new ArrayList<>(3);
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(1, 1));
        assertTrue(bitboard.retainAll(bitboard2));
    }

    @Test
    void clearWhenEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.clear();
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void clearWhenFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                bitboard.add(new Coordinate(x, y));
            }
        }
        bitboard.clear();
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void sameBitboardEquality() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(1, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(0, 0));
        bitboard2.add(new Coordinate(1, 1));
        bitboard2.add(new Coordinate(1, 1));
        assertEquals(bitboard, bitboard2);
    }

    @Test
    void differentBitboardEquality() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(1, 1));
        bitboard.add(new Coordinate(1, 1));
        Collection<Coordinate> bitboard2 = new Bitboard();
        bitboard2.add(new Coordinate(1, 0));
        bitboard2.add(new Coordinate(1, 1));
        bitboard2.add(new Coordinate(1, 1));
        assertNotEquals(bitboard, bitboard2);
    }

    @Test
    void containsNull() {
        Collection<Coordinate> bitboard = new Bitboard();
        assertThrows(NullPointerException.class, ()->bitboard.contains(null));
    }

    @Test
    void containsNonCoordinate() {
        Collection<Coordinate> bitboard = new Bitboard();
        assertThrows(ClassCastException.class, ()->bitboard.contains("hello"));
    }

    @Test
    void containsButNotInCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 1));
        assertFalse(bitboard.contains(new Coordinate(0,0)));
    }

    @Test
    void containsInCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertTrue(bitboard.contains(new Coordinate(0,0)));
    }

    @Test
    void containsOutOfRange() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertFalse(bitboard.contains(new Coordinate(0,10)));
    }

    @Test
    void iteratorEmptyCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        Iterator<Coordinate> iterator = bitboard.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorFullCollection() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                bitboard.add(new Coordinate(x, y));
            }
        }
        Collection<Coordinate> list = new HashSet<>(64);
        Iterator<Coordinate> iterator = bitboard.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        assertEquals(64, list.size());
        assertTrue(bitboard.containsAll(list));
        assertTrue(list.containsAll(bitboard));
    }

    @Test
    void iteratorOnlyOneElement() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        Collection<Coordinate> list = new HashSet<>(64);
        Iterator<Coordinate> iterator = bitboard.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        assertEquals(1, list.size());
        assertTrue(bitboard.containsAll(list));
        assertTrue(list.containsAll(bitboard));
    }

    @Test
    void iteratorNextWhenNoElements() {
        Collection<Coordinate> bitboard = new Bitboard();
        Collection<Coordinate> list = new HashSet<>(64);
        Iterator<Coordinate> iterator = bitboard.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        assertEquals(0, list.size());
        assertTrue(bitboard.containsAll(list));
        assertTrue(list.containsAll(bitboard));
    }

    @Test
    void iteratorNextWhenEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        Iterator<Coordinate> iterator = bitboard.iterator();
        Assertions.assertEquals(new Coordinate(0, 0), iterator.next());
        assertThrows(NoSuchElementException.class, ()->iterator.next());
    }

    @Test
    void iteratorHasNextEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        Iterator<Coordinate> iterator = bitboard.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void sizeOnEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        assertEquals(0, bitboard.size());
    }

    @Test
    void sizeOnFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                bitboard.add(new Coordinate(x, y));
            }
        }
        assertEquals(64, bitboard.size());
    }

    @Test
    void sizeOnPartiallyFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(y % 2 == 0)
                    bitboard.add(new Coordinate(x, y));
            }
        }
        assertEquals(32, bitboard.size());
    }

    @Test
    void isEmptyOnFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                bitboard.add(new Coordinate(x, y));
            }
        }
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void isEmptyOnEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        assertTrue(bitboard.isEmpty());
    }

    @Test
    void isEmptyOnPartiallyFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(y % 2 == 0)
                    bitboard.add(new Coordinate(x, y));
            }
        }
        assertFalse(bitboard.isEmpty());
    }

    @Test
    void toArrayEmpty() {
        Collection<Coordinate> bitboard = new Bitboard();
        Object[] array = bitboard.toArray();
        assertEquals(0, array.length);
    }

    @Test
    void toArrayFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                bitboard.add(new Coordinate(x, y));
            }
        }
        Object[] array = bitboard.toArray();
        assertEquals(64, array.length);
    }

    @Test
    void toArrayPartiallyFull() {
        Collection<Coordinate> bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        bitboard.add(new Coordinate(1, 1));
        bitboard.add(new Coordinate(1, 1));
    }

    @Test
    void getXTest() {
        Bitboard bitboard = new Bitboard();
        assertEquals(0, bitboard.getX(0));
        assertEquals(1, bitboard.getX(1));
        assertEquals(2, bitboard.getX(2));
        assertEquals(3, bitboard.getX(3));
        assertEquals(4, bitboard.getX(4));
        assertEquals(5, bitboard.getX(5));
        assertEquals(6, bitboard.getX(6));
        assertEquals(7, bitboard.getX(7));
        assertEquals(0, bitboard.getX(8));
        assertEquals(1, bitboard.getX(9));
        assertEquals(2, bitboard.getX(10));
        assertEquals(3, bitboard.getX(11));
        assertEquals(4, bitboard.getX(12));
        assertEquals(5, bitboard.getX(13));
        assertEquals(6, bitboard.getX(14));
        assertEquals(7, bitboard.getX(15));
        assertEquals(0, bitboard.getX(16));
        assertEquals(1, bitboard.getX(17));
        assertEquals(2, bitboard.getX(18));
        assertEquals(3, bitboard.getX(19));
    }

    @Test
    void getYTest() {
        Bitboard bitboard = new Bitboard();
        assertEquals(0, bitboard.getY(0));
        assertEquals(0, bitboard.getY(1));
        assertEquals(0, bitboard.getY(2));
        assertEquals(0, bitboard.getY(3));
        assertEquals(0, bitboard.getY(4));
        assertEquals(0, bitboard.getY(5));
        assertEquals(0, bitboard.getY(6));
        assertEquals(0, bitboard.getY(7));
        assertEquals(1, bitboard.getY(8));
        assertEquals(1, bitboard.getY(9));
        assertEquals(1, bitboard.getY(10));
        assertEquals(1, bitboard.getY(11));
        assertEquals(1, bitboard.getY(12));
        assertEquals(1, bitboard.getY(13));
        assertEquals(1, bitboard.getY(14));
        assertEquals(1, bitboard.getY(15));
        assertEquals(2, bitboard.getY(16));
        assertEquals(2, bitboard.getY(17));
        assertEquals(2, bitboard.getY(18));
        assertEquals(2, bitboard.getY(19));
    }

    @Test
    void addBitXPositionTest() {
        Bitboard bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertEquals(1, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(1, 0));
        assertEquals(2, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(2, 0));
        assertEquals(4, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(3, 0));
        assertEquals(8, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(4, 0));
        assertEquals(16, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(5, 0));
        assertEquals(32, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(6, 0));
        assertEquals(64, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(7, 0));
        assertEquals(128, bitboard.getBoard());
        bitboard.clear();
    }

    @Test
    void addBitYPositionTest() {
        Bitboard bitboard = new Bitboard();
        bitboard.add(new Coordinate(0, 0));
        assertEquals(1, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 1));
        assertEquals(256, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 2));
        assertEquals(65536, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 3));
        assertEquals(16777216, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 4));
        assertEquals(4_294_967_296L, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 5));
        assertEquals(1099511627776L, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 6));
        assertEquals(281474976710656L, bitboard.getBoard());
        bitboard.clear();
        bitboard.add(new Coordinate(0, 7));
            assertEquals(72057594037927936L, bitboard.getBoard());
        bitboard.clear();
    }
}