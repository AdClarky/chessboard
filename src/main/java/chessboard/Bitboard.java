package chessboard;

import common.Coordinate;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Bitboard implements Iterable<Coordinate> {
    private long board = 0;

    public Bitboard() {

    }

    public Bitboard(long board) {
        this.board = board;
    }

    private static int shift(Coordinate coordinate) {
        return coordinate.x() + (coordinate.y() << 3);
    }

    public boolean add(Coordinate position) {
        if (position == null)
            throw new NullPointerException();
        if (contains(position))
            return false;
        board |= 1L << shift(position);
        return true;
    }

    public boolean remove(Coordinate coordinate) {
        if (coordinate == null)
            throw new NullPointerException();
        if (!contains(coordinate))
            return false;
        board &= ~(1L << shift(coordinate));
        return true;
    }

    public boolean removeAll(Bitboard bitboard) {
        long tempBoard = board;
        board &= ~bitboard.getBoard();
        return tempBoard != board;
    }

    public boolean retainAll(Bitboard bitboard) {
        long tempBoard = board;
        board = bitboard.getBoard() & board;
        return tempBoard != board;
    }

    public void clear() {
        board = 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Bitboard bitboard)) return false;
        return board == bitboard.board;
    }

    public int hashCode() {
        return Objects.hashCode(board);
    }

    public boolean contains(Coordinate coordinate) {
        return ((board >>> shift(coordinate)) & 1) == 1;
    }

    public void set(long possibleMoves) {
        board = possibleMoves;
    }

    @NotNull
    public Iterator<Coordinate> iterator() {
        return new Itr();
    }

    public int size() {
        return Long.bitCount(board);
    }

    public boolean isEmpty() {
        return board == 0;
    }

    public long getBoard() {
        return board;
    }

    public int getX(int bit) {
        return bit % 8;
    }

    public int getY(int bit) {
        return bit / 8;
    }

    private class Itr implements Iterator<Coordinate> {
        private long remainingBits = board;
        private int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return remainingBits != 0;
        }

        @Override
        public Coordinate next() {
            if (remainingBits == 0) {
                throw new NoSuchElementException();
            }

            int index = Long.numberOfTrailingZeros(remainingBits);
            lastReturned = index;
            remainingBits &= ~(1L << index);

            return Coordinate.fromBitboardIndex(index);
        }

        @Override
        public void remove() {
            if (lastReturned == -1) {
                throw new IllegalStateException();
            }
            Bitboard.this.board &= ~(1L << lastReturned);
            lastReturned = -1;
        }
    }
}
