public interface BoardListener {
    void boardChanged(Piece piece, Piece newPiece);

    void pieceSelected(Piece piece);
}
