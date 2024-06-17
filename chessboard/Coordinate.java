package chessboard;

/**
 * Coordinate class which stores the x and y value of a coordinate.
 * Used for comparing coordinates.
 */
public record Coordinate(int x, int y) {

    @Override
    public String toString() {
        return ChessUtils.coordsToChess(x, y);
    }
}
