/**For classes which want to be notified about changes to the chess board.*/
public interface BoardListener {
    /**
     * Called when a piece is moved on the board.
     * After being called, getMoves() will return a list of individual moves which can make promotion,
     * en passant and castling easier to deal with.
     */
    void boardChanged(int oldX, int oldY, int newX, int newY);

    void checkmate(int kingX, int kingY);

    void draw(int whiteX, int whiteY, int blackX, int blackY);
}
