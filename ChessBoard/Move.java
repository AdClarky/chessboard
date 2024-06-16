package ChessBoard;

public class Move {
    private Piece piece;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
    public Move(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public int getOldX() {return oldX;}
    public int getOldY() {return oldY;}
    public int getNewX() {return newX;}
    public int getNewY() {return newY;}
}
