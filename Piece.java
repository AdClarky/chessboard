import javax.swing.Icon;
import java.util.ArrayList;

public abstract class Piece {
    public static int DOWN = 1;
    public static int UP = -1;
    protected Icon icon;
    protected int x;
    protected int y;
    protected int direction;

    public Piece(int x, int y, Icon icon, int direction) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.direction = direction;
    }

    public abstract ArrayList<Coordinate> getPossibleMoves(Board board);

    protected boolean cantMove(int x, int y, Board board, ArrayList<Coordinate> moves) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(board.getPiece(x, y) != null){ // if there is a piece in the square
            if(board.getPiece(x, y).getDirection() != direction) // if its an enemy piece
                moves.add(new Coordinate(x, y));
            return true;
        }
        moves.add(new Coordinate(x, y));
        return false;
    }

    @Override
    public abstract String toString();

    public Icon getIcon() {return icon;}

    public int getDirection() {return direction;}


    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}
}
