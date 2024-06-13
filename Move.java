public class Move {
    private Piece piece;
    private int x;
    private int y;
    public Move(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {return piece;}
    public int getX() {return x;}
    public int getY() {return y;}
}
