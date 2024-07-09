import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A move a piece could make.
 */
public record MoveValue(Piece piece, int newX, int newY) {
    /**
     * Checks if the Piece is in the same position as its new x and y.
     * @return if the piece is in the same position.
     */
    public boolean isPieceInSamePosition(){
        return piece.getX() == newX && piece.getY() == newY;
    }

    /**
     * Creates a move value which stays in the same position.
     * @param piece the piece which will be 'moved'
     */
    @Contract("_ -> new")
    static @NotNull MoveValue createStationaryMove(Piece piece){
        return new MoveValue(piece, piece.getX(), piece.getY());
    }
}