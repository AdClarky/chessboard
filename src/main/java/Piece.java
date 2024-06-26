import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class which parents all chess pieces.
 */
public abstract class Piece {
    /**
     * Flag for a piece being white
     */
    public static final int WHITE_PIECE = 1;
    /**
     * Flag for a piece being black
     */
    public static final int BLACK_PIECE = -1;
    /**
     * Flag for a blank piece.
     */
    public static final int BLANK_PIECE = 0;
    private final Icon pieceIcon;
    protected int x;
    protected int y;
    protected final int direction;

    /**
     * Initialises the position and whether the piece is white or black
     * @param x starting x value
     * @param y starting y value
     * @param pieceIcon image of the piece
     * @param direction value should be the flag {@link #BLACK_PIECE} or {@link #WHITE_PIECE}
     */
    protected Piece(int x, int y, Icon pieceIcon, int direction) {
        this.x = x;
        this.y = y;
        this.pieceIcon = pieceIcon;
        this.direction = direction;
    }

    /**
     * Calculates all possible moves based on surrounding pieces and checks.
     * @param board the board which the piece is on
     * @return a list of coordinates the piece can move to.
     */
    public abstract List<Coordinate> getPossibleMoves(Board board);

    /**
     * For pieces where the first move must be tracked.
     * Implementations in Pawn, King and Rook.
     */
    public abstract void firstMove();

    public abstract void undoMoveCondition();

    /**
     * For pieces where the first move must be tracked.
     * @return if they've had their first move.
     */
    public abstract boolean hadFirstMove();

    /**
     * Calculates a list of moves required to move a piece to a new place.
     * @param newX the new x position
     * @param newY the new y position
     * @param board the board
     * @return the moves needed
     */
    public List<MoveValue> getMoves(int newX, int newY, Board board) {
        List<MoveValue> moves = new ArrayList<>(1);
        moves.add(new MoveValue(this, newX, newY));
        return moves;
    }

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return x == piece.x && y == piece.y && direction == piece.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    public Icon getPieceIcon() {return pieceIcon;}

    public int getDirection() {return direction;}


    void setX(int x) {this.x = x;}
    void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}

    /**
     * Calculates if a move to a specific square is valid.
     * Validates the coords are within the board and then checks if it's a friendly piece.
     * @param x new x position
     * @param y new y position
     * @param board the board this piece is on
     * @param moves a collection of possible moves
     * @return if the move is valid
     */
    protected boolean cantMove(int x, int y, Board board, Collection<Coordinate> moves) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(!board.isSquareBlank(x,y)){ // if there is a piece in the square
            if(board.getPiece(x, y).direction != direction) // if it's an enemy piece
                moves.add(new Coordinate(x, y));
            return true;
        }
        moves.add(new Coordinate(x, y));
        return false;
    }

    /**
     * Removes moves which would put the king in check.
     * @param board the board this piece is on
     * @param moves the possible moves not considering checks
     */
    protected void removeMovesInCheck(@NotNull Board board, Collection<Coordinate> moves) {
        if(board.getCurrentTurn() != direction)
            return;
        moves.removeIf(move -> board.isMoveUnsafe(move.x(), move.y(), this));
    }

    /**
     * Calculates how far a piece can move in each diagonal direction.
     * @param board the board being worked on
     * @param moves a list of possible moves
     */
    protected void calculateDiagonalMoves(Board board, Collection<Coordinate> moves){
        for(int x = this.x+1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x++, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x--, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x+1, y = this.y-1; x < 8 && x>=0 && y>=0 && y < 8; x++, y--) {
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1, y = this.y+1; x < 8 && x>=0 && y>=0 && y < 8; x--, y++) {
            if(cantMove(x, y, board, moves))
                break;
        }
    }

    /**
     * Calculates how far a piece can move in each straight direction.
     * @param board the board being worked on
     * @param moves a list of possible moves
     */
    protected void calculateStraightMoves(Board board, Collection<Coordinate> moves) {
        for(int x = this.x+1; x < 8 && x >= 0; x++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int x = this.x-1; x < 8 && x >= 0; x--){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y+1; y < 8 && y >= 0; y++){
            if(cantMove(x, y, board, moves))
                break;
        }
        for(int y = this.y-1; y < 8 && y >= 0; y--){
            if(cantMove(x, y, board, moves))
                break;
        }
    }
}
