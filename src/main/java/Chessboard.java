import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class Chessboard {
    private final Piece[][] board  =  new Piece[8][8];
    private BoardHistory history;
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private int numHalfMoves = 0;
    private PieceColour currentTurn;

    /**
     * Initialises the board with the pieces in default positions.
     * Blank squares are null.
     * TODO: replace with that fern thing
     */
    public Chessboard(){
        history = new BoardHistory();
        for(int y = 0; y < 8; y++){
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

    ArrayList<Piece> getColourPieces(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return blackPieces;
        return whitePieces;
    }

    King getKing(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return (King) blackPieces.getFirst();
        return (King) whitePieces.getFirst();
    }

    void addPiece(@NotNull Piece piece){
        if(piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece);
        else
            whitePieces.add(piece);
    }

    void removePiece(@NotNull Piece piece){
        if(piece.getColour() == PieceColour.BLACK)
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
        Piece lastPiece = history.getLastPieceMoved();
        Move move = new Move(newX, newY, pieceToCheck, lastPiece, this);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getColour());
        move.undo();
        return isMoveUnsafe;
    }

    /**
     * Checks if a given King is in check.
     * @param colour black or white king which will be checked
     * @return if the king is in check
     */
    boolean isKingInCheck(PieceColour colour){
        King king = getKing(colour);
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
        Iterable<Piece> enemyPieces = getColourPieces(PieceColour.getOtherColour(colour));
        for(Piece piece : enemyPieces){
            if(piece.getPossibleMoves().contains(kingPos)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks all possible moves that can be made and if any result in non-check.
     * @return if in checkmate.
     */
    boolean isCheckmate(){
        if(!isKingInCheck(currentTurn))
            return false;
        ArrayList<Piece> enemyPieces = getColourPieces(currentTurn);
        for (Piece enemyPiece : enemyPieces) {
            for (Coordinate move : enemyPiece.getPossibleMoves( )) {
                if (!isMoveUnsafe(move.x(), move.y(), enemyPiece))
                    return false;
            }
        }
        return true;
    }

    /**
     * Calculates if the current position is a draw.
     * @return if its a draw
     */
    boolean isDraw(){
        return isStalemate() ||
                isDraw50Move() ||
                is3Repetition();
    }

    boolean isStalemate(){
        if(isKingInCheck(currentTurn))
            return false;
        Iterable<Piece> pieces = getColourPieces(currentTurn);
        for(Piece piece : pieces){
            if(!piece.getPossibleMoves().isEmpty()){
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
    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        if(!getPiece(oldX, oldY).getPossibleMoves().contains(new Coordinate(newX, newY))) // if invalid move
            throw new InvalidMoveException("That isn't a valid move!");
        Move move = new Move(newX, newY, getPiece(oldX, oldY), history.getLastPieceMoved(), this);
        if(move.getPiece() instanceof Pawn || move.hasTaken())
            numHalfMoves = history.getNumberOfMoves();
        history.push(move);
    }

    boolean is3Repetition(){
        if(history.getNumberOfMoves() < 8)
            return false;
        int boardState = getState();
        for(int i = 0; i < 2; i++){
            history.undoMultipleMoves(4);
            if(boardState != getState()) {
                history.redoAllMoves();
                return false;
            }
        }
        history.undoMultipleMoves(4);
        return true;
    }

    boolean isDraw50Move(){
        return (history.getNumberOfMoves() - numHalfMoves) == 50;
    }

    public List<MoveValue> getLastMoveMade(){return history.getLastMoves();}
    public int getNumHalfMoves(){return history.getNumberOfMoves();}
    public int getNumFullMoves(){return Math.floorDiv(history.getNumberOfMoves(), 2);}
    public @Nullable Move undoMove(){return history.undoMove();}
    public @Nullable Move redoMove(){return history.redoMove();}
    public boolean canRedoMove(){return history.canRedoMove();}
    public void setCurrentTurn(PieceColour newTurn){currentTurn = newTurn;}
    public PieceColour getCurrentTurn(){return currentTurn;}
    void setNumHalfMoves(int numHalfMoves){this.numHalfMoves = numHalfMoves;}
}
