import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class which parents all chess pieces.
 */
public abstract class Piece {
    private final Icon pieceIcon;
    private final char pieceCharacter;
    protected final Chessboard board;
    protected int x;
    protected int y;
    protected final PieceColour colour;

    protected Piece(int x, int y, PieceColour colour, char pieceCharacter, Chessboard board) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.pieceCharacter = pieceCharacter;
        pieceIcon = ImageUtils.getPieceImage(toString(), colour);
        this.board = board;
    }

    /**
     * Calculates all possible moves based on surrounding pieces and checks.
     * @return a list of coordinates the piece can move to.
     */
    public abstract List<Coordinate> getPossibleMoves();

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

    /** Calculates a list of moves required to move a piece to a new place. */
    public List<MoveValue> getMoves(int newX, int newY) {
        List<MoveValue> moves = new ArrayList<>(1);
        moves.add(new MoveValue(this, newX, newY));
        return moves;
    }

    @Override
    public String toString(){
        return pieceCharacter + "";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return x == piece.x && y == piece.y && colour == piece.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, colour, pieceCharacter);
    }

    public Icon getPieceIcon() {return pieceIcon;}

    public PieceColour getColour() {return colour;}


    void setX(int x) {this.x = x;}
    void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}

    protected void removeMovesInCheck(Collection<Coordinate> moves) {
        if(board.getCurrentTurn() != colour)
            return;
        moves.removeIf(move -> board.isMoveUnsafe(move.x(), move.y(), this));
    }

    /**
     * Calculates if a move to a specific square is valid.
     * Validates the coords are within the board and then checks if it's a friendly piece.
     * @param moves a collection of possible moves
     * @return if the move is valid
     */
    protected boolean cantMove(int x, int y, Collection<Coordinate> moves) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(!board.isSquareBlank(x,y)){ // if there is a piece in the square
            if(board.getPiece(x, y).colour != colour) // if it's an enemy piece
                moves.add(new Coordinate(x, y));
            return true;
        }
        moves.add(new Coordinate(x, y));
        return false;
    }

    /**
     * Calculates how far a piece can move in each diagonal direction.
     * @param moves a list of possible moves
     */
    protected void calculateDiagonalMoves(Collection<Coordinate> moves){
        calculateSingleDirection(moves, 1, 1);
        calculateSingleDirection(moves, -1, -1);
        calculateSingleDirection(moves, 1, -1);
        calculateSingleDirection(moves, -1, 1);
    }

    /**
     * Calculates how far a piece can move in each straight direction.
     * @param moves a list of possible moves
     */
    protected void calculateStraightMoves(Collection<Coordinate> moves) {
        calculateSingleDirection(moves, 1, 0);
        calculateSingleDirection(moves, -1, 0);
        calculateSingleDirection(moves, 0, 1);
        calculateSingleDirection(moves, 0, -1);
    }

    private void calculateSingleDirection(Collection<Coordinate> moves, int xIncrement, int yIncrement){
        for(int x = this.x+xIncrement, y = this.y+yIncrement; x < 8 && x>=0 && y>=0 && y < 8; x+=xIncrement, y+=yIncrement) {
            if(cantMove(x, y, moves))
                break;
        }
    }
}
