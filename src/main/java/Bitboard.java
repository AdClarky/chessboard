import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Bitboard implements Collection<Coordinate> {
    private long board = 0;

    public Bitboard() {

    }

    @Override
    public boolean add(Coordinate position) {
        if(position == null)
            throw new NullPointerException();
        if(!position.isInRange())
            throw new OutOfRangeException(position);
        board |= 1L << shift(position);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null)
            throw new NullPointerException();
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
            if(!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Coordinate> c) {
        if(c instanceof Bitboard bitboard){
            board |= bitboard.getBoard();
            return true;
        }
        boolean changed = false;
        for(Coordinate coordinate : c)
            if(add(coordinate))
                changed = true;
        return changed;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        if(c instanceof Bitboard bitboard){
            board &= ~bitboard.getBoard();
            return true;
        }
        boolean changed = false;
        for(Object o : c) {
            if(remove(o))
                 changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        if(c instanceof Bitboard bitboard){
            board = bitboard.getBoard() & board;
            return true;
        }
        boolean changed = false;
        Iterator<Coordinate> iterator = iterator();
        while(iterator.hasNext()){
            if(!c.contains(iterator.next())){
                iterator.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear(){
        board = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Bitboard bitboard)) return false;
        return board == bitboard.board;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }

    @Override
    public boolean contains(Object o) {
        if(o == null)
            throw new NullPointerException();
        if(!(o instanceof Coordinate coordinate))
            throw new ClassCastException();
        return ((board >> shift(coordinate)) & 1) == 1;
    }

    private class Itr implements Iterator<Coordinate> {
        int current = 63;
        int numFound = 0;

        Itr() {}

        @Override
        public boolean hasNext() {
            return numFound != size();
        }

        @Override
        public Coordinate next() {
            if(current < 0 || numFound > size()){
                throw new NoSuchElementException();
            }
            while(((board << current) & 1) == 0 && current >= 0){
                current--;
            }
            numFound++;
            return new Coordinate(getX(current), getY(current));
        }
    }

    @NotNull
    @Override
    public Iterator<Coordinate> iterator() {
        return new Itr();
    }

    @Override
    public int size() {
        return Long.bitCount(board);
    }

    @Override
    public boolean isEmpty(){
        return board == 0;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for(int bit = 63; bit >= 0; bit--){
            if(((board >> 63) & 1) == 0)
                continue;
            array[i++] = new Coordinate(getX(bit), getY(bit));
        }
        return array;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(@NotNull T[] a) {
        if(a.length < size())
            return (T[]) toArray();
        int size = size();
        Object[] array = toArray();
        for(int i = 0; i< size; i++){
            a[i] = (T) array[i];
        }
        if(a.length > size)
            a[size] = null;
        return a;
    }

    public long getBoard(){
        return board;
    }

    private static int shift(Coordinate coordinate){
        return coordinate.x() + (coordinate.y() << 3);
    }

    private int getX(int bit){
        int x = 8 - ((bit + 1) % 8);
        return x == 8 ? 0 : x;
    }

    private int getY(int bit){
        return 8 - Math.ceilDiv(bit + 1, 8);
    }
}
