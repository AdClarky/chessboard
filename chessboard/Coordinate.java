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

    public static Coordinate fromString(CharSequence move){
        int x = Math.abs(7 - (move.charAt(move.length() - 2) - 97));
        int y = move.charAt(move.length() - 1) - '0' - 1;
        return new Coordinate(x, y);
    }
}
