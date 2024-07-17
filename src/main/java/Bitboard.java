public class Bitboard {
    private long board = 0;

    public Bitboard() {

    }

    public void setPosition(Coordinate position){
        board |= 1L << shift(position);
    }

    public void removePossible(Coordinate position) {
        board &= ~(1L << shift(position));
    }

    public void clear(){
        board = 0;
    }

    public boolean isActive(Coordinate coordinate){
        return ((board >> shift(coordinate)) & 1) == 1;
    }

    public boolean isBoardEmpty(){
        return board == 0;
    }

    private static int shift(Coordinate coordinate){
        return coordinate.x() + (coordinate.y() << 3);
    }
}
