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
    private Icon pieceIcon;
    private Coordinate position;
    /**
     * The possible moves this pieces can make.
     */
    protected final Bitboard possibleMoves = new Bitboard();
    /**
     * The colour of the piece.
     */
    protected final PieceColour colour;

    /**
     * Creates a chess piece.
     * @param x starting x position.
     * @param y starting y position.
     * @param colour the colour of the piece.
     */
    protected Piece(int x, int y, PieceColour colour) {
        startX = x;
        startY = y;
        position = new Coordinate(x, y);
        this.colour = colour;
    }

    public Bitboard getPossibleMoves(){
        return possibleMoves;
    }

    void removePossibleMove(Coordinate move){
        possibleMoves.remove(move);
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
        return startX == piece.startX && getX() == piece.getX() && getY() == piece.getY() &&
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
     * Gets the colour of the piece.
     * @return if the piece is black or white
     */
    public PieceColour getColour() {return colour;}


    void setPos(int x, int y) {
        position = new Coordinate(x, y);
    }

    /**
     * The current x position of the piece.
     * @return the x position
     */
    public int getX() {
        return position.x();
    }

    /**
     * The current y position of the piece.
     * @return the y position
     */
    public int getY() {
        return position.y();
    }

    public Coordinate getPosition() {
        return position;
    }

    /**
     * Calculates how far a piece can move in each diagonal direction.
     * @param board the board the piece is moving on.
     */
    protected void calculateDiagonalMoves(ChessLogic board){
        calculateSingleDirection(board, 1, 1);
        calculateSingleDirection(board, -1, -1);
        calculateSingleDirection(board, 1, -1);
        calculateSingleDirection(board, -1, 1);
    }

    /**
     * Calculates how far a piece can move in each straight direction.
     * @param board the board the piece is moving on.
     */
    protected void calculateStraightMoves(ChessLogic board) {
        calculateSingleDirection(board, 1, 0);
        calculateSingleDirection(board, -1, 0);
        calculateSingleDirection(board, 0, 1);
        calculateSingleDirection(board, 0, -1);
    }

    private void calculateSingleDirection(ChessLogic board, int xIncrement, int yIncrement){
        for(int x = getX()+xIncrement, y = getY()+yIncrement; x < 8 && x>=0 && y>=0 && y < 8; x+=xIncrement, y+=yIncrement) {
            Coordinate newPosition = new Coordinate(x, y);
            possibleMoves.add(newPosition);
            if(!board.isSquareBlank(newPosition))
                break;
        }
    }

    /**
     * Calculates if a move to a specific square is valid.
     * Validates the coords are within the board and then checks if it's a friendly piece.
     *
     * @param x the potential x
     * @param y the potential y
     */
    protected void addIfInRange(int x, int y) {
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return;
        possibleMoves.add(new Coordinate(x, y));
    }
}
