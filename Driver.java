import chessboard.Board;

public class Driver {
    public static void main(String[] args) {
        Board board = new Board();
        new Thread(() -> {
                GameWindow gameWindow = new GameWindow(board);
        }).start();
    }
}
