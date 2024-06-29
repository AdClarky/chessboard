import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/** A move a piece could make. */
public record MoveValue(Piece piece, int newX, int newY) {
    /**
     * Creates a move value which stays in the same position.
     * @param piece the piece which will be 'moved'
     */
    @Contract("_ -> new")
    static @NotNull MoveValue createStationaryMove(Piece piece){
        return new MoveValue(piece, piece.getX(), piece.getY());
    }
}