import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class Chessboard {
    private final Piece[][] board  =  new Piece[8][8];
    BoardHistory boardHistory;
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);

    /**
     * Initialises the board with the pieces in default positions.
     * Blank squares are null.
     * TODO: replace with that fern thing
     */
    public Chessboard(){
        for(int y = 0; y < 6; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
    }

    void populateBoard(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces){
        this.whitePieces.addAll(whitePieces);
        this.blackPieces.addAll(blackPieces);
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /**
     * Finds the piece in a specific square
     * @param x x position
     * @param y y position
     * @return the piece on that square.
     */
    @NotNull
    public Piece getPiece(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    /**
     * Checks if a square is blank or contains a piece
     * @param x x value of square to check
     * @param y y value of square to check
     * @return if the square is blank
     */
    public boolean isSquareBlank(int x, int y){return board[y][x] instanceof Blank;}

    void setSquare(int x, int y, @NotNull Piece piece){board[y][x] = piece;}

    ArrayList<Piece> getColourPieces(int colour){
        if(colour == Piece.BLACK_PIECE)
            return blackPieces;
        return whitePieces;
    }

    King getKing(int colour){
        if(colour == Piece.BLACK_PIECE)
            return (King) blackPieces.getFirst();
        return (King) whitePieces.getFirst();
    }

    void addPiece(@NotNull Piece piece){
        if(piece.getDirection() == Piece.BLACK_PIECE)
            blackPieces.add(piece);
        else
            whitePieces.add(piece);
    }

    void removePiece(@NotNull Piece piece){
        if(piece.getDirection() == Piece.BLACK_PIECE)
            blackPieces.remove(piece);
        else
            whitePieces.remove(piece);
    }

    int getState(){
        return Arrays.deepHashCode(board);
    }

    /**
     * Calculates if moving a piece to a position would put that teams king in check.
     * This assumes the piece moving to the new position is a valid move.
     * @param newX the x position to move to.
     * @param newY the y position to move to.
     * @param pieceToCheck the piece being moved.
     * @return true if in check, false if not
     */
    boolean isMoveUnsafe(int newX, int newY, Piece pieceToCheck){
        Piece lastPiece = boardHistory.getLastPieceMoved();
        Move move = new Move(newX, newY, pieceToCheck, lastPiece, this);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getDirection());
        move.undo();
        return isMoveUnsafe;
    }

    /**
     * Checks if a given King is in check.
     * @param colour black or white king which will be checked
     * @return if the king is in check
     */
    boolean isKingInCheck(int colour){
        King king = getKing(colour);
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
        Iterable<Piece> enemyPieces = getColourPieces(colour * -1);
        for(Piece piece : enemyPieces){
            if(piece.getPossibleMoves(this).contains(kingPos)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks all possible moves that can be made and if any result in non-check.
     * @return if in checkmate.
     */
    boolean isCheckmate(int colour){
        if(!isKingInCheck(colour))
            return false;
        ArrayList<Piece> enemyPieces = getColourPieces(colour);
        for (Piece enemyPiece : enemyPieces) {
            for (Coordinate move : enemyPiece.getPossibleMoves(this)) {
                if (!isMoveUnsafe(move.x(), move.y(), enemyPiece))
                    return false;
            }
        }
        return true;
    }

    /**
     * Calculates if the current position is a draw.
     * @param side the side which may have been stalemated
     * @return if its a draw
     */
    boolean isDraw(int side){
        return isStalemate(side) ||
                isDraw50Move() ||
                is3Repetition();
    }

    boolean isStalemate(int stalemateSide){
        if(isKingInCheck(stalemateSide))
            return false;
        Iterable<Piece> pieces = getColourPieces(stalemateSide);
        for(Piece piece : pieces){
            if(!piece.getPossibleMoves(this).isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a move and pushes it to the stack.
     * @param oldX current x position of the piece
     * @param oldY current y position of the piece
     * @param newX new x position of the piece
     * @param newY new y position of the piece
     */
    private void makeMove(int oldX, int oldY, int newX, int newY){
        Move move;
        if (moves.isEmpty())
            move = new Move(newX, newY, getPiece(oldX, oldY), null,this);
        else
            move = new Move(newX, newY, getPiece(oldX, oldY), moves.getFirst().getPiece(), this);
        if(move.getPiece() instanceof Pawn || move.hasTaken())
            lastPawnOrCapture = moves.size();
        lastMoveMade = move;
        boardHistory.push(move);
    }

    boolean is3Repetition(){
        if(moves.size() < 8)
            return false;
        int boardState = board.getState();
        for(int i = 0; i < 2; i++){
            undoMultipleMovesSilent(4);
            if(boardState != board.getState()) {
                redoAllMovesSilent();
                return false;
            }
        }
        redoAllMovesSilent();
        return true;
    }

    boolean isDraw50Move(){
        return (moves.size() - lastPawnOrCapture) == 50;
    }
}
