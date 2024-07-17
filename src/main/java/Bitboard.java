import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class Bitboard implements Collection<Coordinate> {
    private long board = 0;

    public Bitboard() {

    }

    @Override
    public boolean add(Coordinate position) {
        if(!position.isInRange())
            throw new OutOfRangeException(position);
        board |= 1L << shift(position);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(!(o instanceof Coordinate coordinate))
            throw new ClassCastException();
        if(!contains(coordinate))
            return false;
        board &= ~(1L << shift(coordinate));
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if(c instanceof Bitboard bitboard)
            return (bitboard.getBoard() & board) == bitboard.getBoard();
        for(Object o : c) {
            if (!(o instanceof Coordinate))
                throw new ClassCastException();
            if(!contains((Coordinate) o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Coordinate> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    public void clear(){
        board = 0;
    }

    public boolean contains(Coordinate coordinate){
        return ((board >> shift(coordinate)) & 1) == 1;
    }

    @Override
    public int size() {
        return Long.bitCount(board);
    }

    public boolean isEmpty(){
        return board == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<Coordinate> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return null;
    }

    public long getBoard(){
        return board;
    }

    private static int shift(Coordinate coordinate){
        return coordinate.x() + (coordinate.y() << 3);
    }
}
