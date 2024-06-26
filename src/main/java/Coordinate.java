import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Coordinate class which stores the x and y value of a coordinate.
 * Used for comparing coordinates.
 */
public record Coordinate(int x, int y) {
    // TODO: test coordinate string
    @Override
    public @NotNull String toString() {
        if(x < 0 || y < 0 || x > 7 || y > 7) return "Invalid";
        return ChessUtils.coordsToChess(x, y);
    }

    @Contract("_ -> new")
    public static @NotNull Coordinate createCoorindateFromString(@NotNull CharSequence move){
        int length = move.length();
        if(move.charAt(length - 1) == '+' || move.charAt(length - 1) == '#')
            length--; // so it ignores the checks/checkmates
        int x = Math.abs(7 - (move.charAt(length - 2) - 97));
        int y = move.charAt(length - 1) - '0' - 1;
        return new Coordinate(x, y);
    }
}
