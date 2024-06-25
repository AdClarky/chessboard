/**
 * Records a move from one position to another
 * @param piece the piece being moved
 * @param newX new x
 * @param newY new Y
 */
public record MoveValue(Piece piece, int newX, int newY) {
}