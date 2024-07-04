import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/** Coordinate class which stores the x and y value of a coordinate. */
public record Coordinate(int x, int y) {
    public Coordinate(Piece piece) {
        this(piece.getX(), piece.getY());
    }

    @Override
    public @NotNull String toString() {
        if(x < 0 || y < 0 || x > 7 || y > 7) return "Invalid";
        return ChessUtils.coordsToChess(x, y);
    }

    /**
     * Calculates the coordinates of a move.
     * @param move the move in chess algebraic notation
     * @return the coordinate of a move
     * @throws IllegalArgumentException when a castling move is input
     */
    @Contract("_ -> new")
    public static @NotNull Coordinate createCoordinateFromString(@NotNull String move) throws IllegalArgumentException {
        if(move.contains("O-"))
            throw new IllegalArgumentException("Invalid move - cannot create coordinate from castle");
        int length = move.length();
        if(move.charAt(length - 1) == '+' || move.charAt(length - 1) == '#')
            length--; // so it ignores the checks/checkmates
        if(move.contains("=")) // promotion
            length = 2;
        int x = Math.abs(7 - (move.charAt(length - 2) - 'a'));
        int y = move.charAt(length - 1) - '0' - 1;
        return new Coordinate(x, y);
    }
}
