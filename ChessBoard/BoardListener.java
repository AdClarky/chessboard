package ChessBoard;

public interface BoardListener {
    void boardChanged(int oldX, int oldY, int newX, int newY);
}
