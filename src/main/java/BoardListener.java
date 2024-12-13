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
     * @param kingPos the position of the king which has been checkmated
     */
    void checkmate(Coordinate kingPos);

    /**
     * Called when a draw has occurred on the board.
     * Only called after all the move logic is completed.
     *
     * @param whitePos the x of the white king
     * @param blackPos the y of the white king
     */
    void draw(Coordinate whitePos, Coordinate blackPos);

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
