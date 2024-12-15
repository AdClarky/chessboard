import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class which parents all chess pieces.
 *
 */
public abstract class Piece {
    private final Coordinate startingPos;
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
     * @param colour the colour of the piece.
     */
    protected Piece(Coordinate position, PieceColour colour) {
        startingPos = position;
        this.position = position;
        this.colour = colour;
    }

    public Bitboard getPossibleMoves(){
        return possibleMoves;
    }

    void removePossibleMove(Coordinate move){
        possibleMoves.remove(move);
    }

    abstract void calculatePossibleMoves(ChessLogic board);

    List<MoveValue> getMoves(ChessLogic board, Coordinate position) {
        List<MoveValue> moves = new ArrayList<>(1);
        moves.add(new MoveValue(position, position));
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
        return startingPos.equals(piece.startingPos) && position.equals(piece.position) && colour == piece.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingPos, colour, toCharacter());
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

    void setPos(Coordinate newPos){
        position = newPos;
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
