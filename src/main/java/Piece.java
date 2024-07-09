import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class which parents all chess pieces.
 *
 */
public abstract class Piece {
    private final int startX;
    private final int startY;
    protected final List<Coordinate> possibleMoves = new ArrayList<>(15);
    protected final PieceColour colour;
    private Icon pieceIcon;
    protected int x;
    protected int y;

    protected Piece(int x, int y, PieceColour colour) {
        startX = x;
        startY = y;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    /**
     * @return a list of coordinates the piece can move to.
     */
    public List<Coordinate> getPossibleMoves(){
        return possibleMoves;
    }

    abstract void calculatePossibleMoves(ChessLogic board);

    abstract void firstMove();

    abstract void undoMoveCondition();

    /**
     * For pieces where the first move must be tracked.
     * @return if they've had their first move.
     */
    public abstract boolean hadFirstMove();

    List<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        List<MoveValue> moves = new ArrayList<>(1);
        moves.add(new MoveValue(this, newX, newY));
        return moves;
    }

    @Override
    public abstract String toString();

    /**
     * The character used by FEN Strings for a chess piece.
     * @return the uppercase piece character.
     */
    public abstract char toCharacter();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return startX == piece.startX && x == piece.x && y == piece.y &&
                startY == piece.startY && colour == piece.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX, startY, colour, toCharacter());
    }

    /**
     * An {@code Icon} which can be useful for GUIs.
     * @return the icon of the chess piece.
     */
    public Icon getPieceIcon() {
        if(pieceIcon == null) {
            pieceIcon = ImageUtils.getPieceImage(toString(), colour);
        }
        return pieceIcon;
    }

    /**
     * @return if the piece is black or white
     */
    public PieceColour getColour() {return colour;}


    void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Calculates how far a piece can move in each diagonal direction.
     */
    protected void calculateDiagonalMoves(ChessLogic board){
        calculateSingleDirection(board, 1, 1);
        calculateSingleDirection(board, -1, -1);
        calculateSingleDirection(board, 1, -1);
        calculateSingleDirection(board, -1, 1);
    }

    /**
     * Calculates how far a piece can move in each straight direction.
     */
    protected void calculateStraightMoves(ChessLogic board) {
        calculateSingleDirection(board, 1, 0);
        calculateSingleDirection(board, -1, 0);
        calculateSingleDirection(board, 0, 1);
        calculateSingleDirection(board, 0, -1);
    }

    private void calculateSingleDirection(ChessLogic board, int xIncrement, int yIncrement){
        for(int x = this.x+xIncrement, y = this.y+yIncrement; x < 8 && x>=0 && y>=0 && y < 8; x+=xIncrement, y+=yIncrement) {
            if(cantMove(board, x, y))
                break;
        }
    }

    /**
     * Calculates if a move to a specific square is valid.
     * Validates the coords are within the board and then checks if it's a friendly piece.
     * @return if the move is valid
     */
    protected boolean cantMove(ChessLogic board, int x, int y) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        if(!board.isSquareBlank(x,y)){ // if there is a piece in the square
            if(board.isEnemyPiece(x, y, colour))
                possibleMoves.add(new Coordinate(x, y));
            return true;
        }
        possibleMoves.add(new Coordinate(x, y));
        return false;
    }
}
