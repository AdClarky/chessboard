import ChessBoard.Board;

public class Driver {
    public static void main(String[] args) {
        Board board = new Board();
        new Thread(() -> {
                Window window = new Window(board);
        }).start();
        Client client = new Client("192.168.1.91", 4444, board);
        client.listenForMessage();
    }
}
