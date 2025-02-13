package common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Coordinate class which stores the x and y value of a coordinate.
 * @param x x coordinate
 * @param y y coordinate
 */
public record Coordinate(int x, int y) {
    private static final Coordinate[] COORDINATES = new Coordinate[64];

    @Override
    public @NotNull String toString() {
        if(x < 0 || y < 0 || x > 7 || y > 7) return "Invalid";
        return Character.toString('a' + x) + (y+1);
    }

    public boolean isNotInRange(){
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    /**
     * Calculates the coordinates of a move.
     * It must not be a castling move.
     * @param move the move in chess algebraic notation
     * @return the coordinate of a move
     * @throws IllegalArgumentException when a castling move is input
     */
    @Contract("_ -> new")
    static @NotNull Coordinate fromString(@NotNull String move) throws IllegalArgumentException {
        if(move.contains("O-"))
            throw new IllegalArgumentException("Invalid move - cannot create coordinate from castle");
        int length = move.length();
        if(move.charAt(length - 1) == '+' || move.charAt(length - 1) == '#')
            length--; // so it ignores the checks/checkmates
        if(move.contains("=")) // promotion
            length = 2;
        int x = Math.abs(move.charAt(length - 2) - 'a');
        int y = move.charAt(length - 1) - '0' - 1;
        return new Coordinate(x, y);
    }

    @Contract("_ -> new")
    static @NotNull Coordinate fromBitboard(long bitboard){
        return COORDINATES[Long.numberOfTrailingZeros(bitboard)];
    }

    @Contract("_ -> new")
    static @NotNull Coordinate fromBitboardIndex(int index){
        return COORDINATES[index];
    }

    public int getBitboardIndex(){
        return (x + (y << 3));
    }

    public long getBitboardValue(){
        return 1L << getBitboardIndex();
    }

    static {
        for(int i = 0; i < 64; i++){
            COORDINATES[i] = new Coordinate(i % 8, i / 8);
        }
    }
}
