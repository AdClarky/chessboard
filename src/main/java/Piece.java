import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class which parents all chess pieces.
 */
public abstract class Piece {
    private Icon pieceIcon;
    protected final PieceColour colour;
    protected int x;
    protected int y;

    protected Piece(int x, int y, PieceColour colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
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
    public abstract String toString();

    public abstract char toCharacter();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return x == piece.x && y == piece.y && colour == piece.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, colour, toCharacter());
    }

    public Icon getPieceIcon() {
        if(pieceIcon == null) {
            pieceIcon = ImageUtils.getPieceImage(toString(), colour);
        }
        return pieceIcon;
    }

    public PieceColour getColour() {return colour;}


    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    protected void removeMovesInCheck(Collection<Coordinate> moves, ChessLogic board) {
        if(board.getCurrentTurn() != colour)
            return;
        moves.removeIf(move -> board.isMoveUnsafe(move.x(), move.y(), this));
    }

    /**
     * Calculates how far a piece can move in each diagonal direction.
     *
     * @param moves a list of possible moves
     * @param board
     */
    protected void calculateDiagonalMoves(Collection<Coordinate> moves, ChessLogic board){
        calculateSingleDirection(moves, board, 1, 1);
        calculateSingleDirection(moves, board, -1, -1);
        calculateSingleDirection(moves, board, 1, -1);
        calculateSingleDirection(moves, board, -1, 1);
    }

    /**
     * Calculates how far a piece can move in each straight direction.
     *
     * @param moves a list of possible moves
     * @param board
     */
    protected void calculateStraightMoves(Collection<Coordinate> moves, ChessLogic board) {
        calculateSingleDirection(moves, board, 1, 0);
        calculateSingleDirection(moves, board, -1, 0);
        calculateSingleDirection(moves, board, 0, 1);
        calculateSingleDirection(moves, board, 0, -1);
    }

    private void calculateSingleDirection(Collection<Coordinate> moves, ChessLogic board, int xIncrement, int yIncrement){
        for(int x = this.x+xIncrement, y = this.y+yIncrement; x < 8 && x>=0 && y>=0 && y < 8; x+=xIncrement, y+=yIncrement) {
            if(cantMove(moves, board, x, y))
                break;
        }
    }

    /**
     * Calculates if a move to a specific square is valid.
     * Validates the coords are within the board and then checks if it's a friendly piece.
     *
     * @param moves a collection of possible moves
     * @param board
     * @return if the move is valid
     */
    protected boolean cantMove(Collection<Coordinate> moves, ChessLogic board, int x, int y) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(!board.isSquareBlank(x,y)){ // if there is a piece in the square
            if(!board.isFriendlyPiece(x, y, colour))
                moves.add(new Coordinate(x, y));
            return true;
        }
        moves.add(new Coordinate(x, y));
        return false;
    }
}
