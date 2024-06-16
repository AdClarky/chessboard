package chessboard;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract class which parents all chess pieces.
 */
public abstract class Piece {
    /**
     * Flag for a piece being white
     */
    public static final int DOWN = 1;
    /**
     * Flag for a piece being black
     */
    public static final int UP = -1;
    protected Icon icon;
    protected int x;
    protected int y;
    protected int direction;

    /**
     * Initialises the position and whether the piece is white or black
     * @param x starting x value
     * @param y starting y value
     * @param icon image of the piece
     * @param direction value should be the flag {@link #UP} or {@link #DOWN}
     */
    public Piece(int x, int y, Icon icon, int direction) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.direction = direction;
    }

    /**
     * Calculates all possible moves based on surrounding pieces and checks.
     * @param board the board which the piece is on
     * @return a list of coordinates the piece can move to.
     */
    public abstract ArrayList<Coordinate> getPossibleMoves(Board board);

    @Override
    public abstract String toString();

    public Icon getIcon() {return icon;}

    public int getDirection() {return direction;}


    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}

    protected boolean cantMove(int x, int y, Board board, Collection<Coordinate> moves) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(board.getPiece(x, y) != null){ // if there is a piece in the square
            if(board.getPiece(x, y).direction != direction) // if it's an enemy piece
                moves.add(new Coordinate(x, y));
            return true;
        }
        moves.add(new Coordinate(x, y));
        return false;
    }

    protected void removeMovesInCheck(Board board, Collection<Coordinate> moves) {
        if(board.getTurn() != direction)
            return;
        moves.removeIf(move -> board.inCheck(move.getX(), move.getY(), this));
    }
}
