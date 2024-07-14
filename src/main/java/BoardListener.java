/**
 * The listener interface for board events (move made, checkmate, draw and board changed).
 * When added to a board using {@code .addBoardListener}, each method will be notified
 * during their respective events.
 */
public interface BoardListener {
    /**
     * Called when a piece is moved on the board.
     * After being called, getMoves() will return a list of individual moves which can make promotion,
     * en passant and castling easier to deal with.
     * @param oldX the old x position of the piece
     * @param oldY the old y position of the piece
     * @param newX the new x position of the piece
     * @param newY the new y position of the piece
     */
    void moveMade(int oldX, int oldY, int newX, int newY);

    /**
     * Called when a checkmate has occurred on the board.
     * Only called after all the move logic is completed.
     * @param kingX the x of the king who has been checkmated
     * @param kingY the y of the king who has been checkmated
     */
    void checkmate(int kingX, int kingY);

    /**
     * Called when a draw has occurred on the board.
     * Only called after all the move logic is completed.
     * @param whiteX the x of the white king
     * @param whiteY the y of the white king
     * @param blackX the x of the black king
     * @param blackY the y of the black king
     */
    void draw(int whiteX, int whiteY, int blackX, int blackY);

    /**
     * Called when the board has changed, i.e. undo or redo.
     * Works like {@link BoardListener#moveMade(int, int, int, int)}
     * @param oldX the old x position of the piece
     * @param oldY the old y position of the piece
     * @param newX the new x position of the piece
     * @param newY the new y position of the piece
     */
    void boardChanged(int oldX, int oldY, int newX, int newY);
}
