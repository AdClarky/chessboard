package chessboard;

import common.Coordinate;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Bitboard implements Iterable<Coordinate> {
    private final long board;

    public Bitboard() {
        board = 0;
    }

    public Bitboard(long board) {
        this.board = board;
    }

    private static int shift(Coordinate coordinate) {
        return coordinate.x() + (coordinate.y() << 3);
    }

    public Bitboard add(Coordinate position) {
        if (position == null)
            throw new NullPointerException();
        if (contains(position))
            return this;
        long newBoard = board | 1L << shift(position);
        return new Bitboard(newBoard);
    }

    public Bitboard remove(Coordinate coordinate) {
        if (coordinate == null)
            throw new NullPointerException();
        if (!contains(coordinate))
            return this;
        long newBoard = board & ~(1L << shift(coordinate));
        return new Bitboard(newBoard);
    }

    public Bitboard removeAll(Bitboard bitboard) {
        long newBoard = board & ~bitboard.getBoard();
        return new Bitboard(newBoard);
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
            throw new UnsupportedOperationException("Bitboard is immutable");
        }
    }
}
