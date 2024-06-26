/**
 * Records a move from one position to another
 * @param piece the piece being moved
 * @param newX new x
 * @param newY new Y
 */
public record MoveValue(Piece piece, int newX, int newY) {
    /**
     * Creates a move value which stays in the same position.
     * @param piece the piece which will be 'moved'
     */
    static MoveValue createStationaryMove(Piece piece){
        return new MoveValue(piece, piece.getX(), piece.getY());
    }
}