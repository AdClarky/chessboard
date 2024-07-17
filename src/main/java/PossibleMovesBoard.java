public class PossibleMovesBoard {
    private long board = 0;

    public PossibleMovesBoard() {

    }

    public void possibleMove(Coordinate coordinate){
        board += 1L << shift(coordinate);
    }

    public void clear(){
        board = 0;
    }

    public boolean isPossible(Coordinate coordinate){
        return ((board >> shift(coordinate)) & 1) == 1;
    }

    private int shift(Coordinate coordinate){
        return coordinate.x() + (coordinate.y() << 3);
    }
}
