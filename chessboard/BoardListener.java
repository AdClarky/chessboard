package chessboard;

/**
 * For classes which want to be notified about changes to the chess board.
 */
public interface BoardListener {
    /**
     * Called when a piece is moved on the board.
     * After being called, getMoves() will return a list of individual moves which can make promotion,
     * en passant and castling easier to deal with.
     * @param oldX the original x position of the piece
     * @param oldY the original y position of the piece
     * @param newX the new x position of the piece
     * @param newY the new y position of the piece
     */
    void boardChanged(int oldX, int oldY, int newX, int newY);

    /**
     * Called when its checkmate with the position of the king as the param.
     * @param x king x position
     * @param y king y position
     */
    void checkmate(int x, int y);
}
