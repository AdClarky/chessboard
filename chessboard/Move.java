package chessboard;

/**
 * Records a move from one position to another
 * @param oldX original X
 * @param oldY original Y
 * @param newX new x
 * @param newY new Y
 */
public record Move(int oldX, int oldY, int newX, int newY) {
}