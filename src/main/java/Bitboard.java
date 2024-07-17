public class Bitboard {
    private long board = 0;

    public Bitboard() {

    }

    public void add(Coordinate position){
        board |= 1L << shift(position);
    }

    public void remove(Coordinate position) {
        board &= ~(1L << shift(position));
    }

    public void clear(){
        board = 0;
    }

    public boolean contains(Coordinate coordinate){
        return ((board >> shift(coordinate)) & 1) == 1;
    }

    public boolean isEmpty(){
        return board == 0;
    }

    public long getBoard(){
        return board;
    }

    private static int shift(Coordinate coordinate){
        return coordinate.x() + (coordinate.y() << 3);
    }
}
