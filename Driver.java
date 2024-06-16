import ChessBoard.Board;

public class Driver {
    public static void main(String[] args) {
        Board board = new Board();
        new Thread(() -> {
                Window window = new Window(board);
        }).start();
    }
}
