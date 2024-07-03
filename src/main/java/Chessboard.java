import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/** A chess board that is automatically populated with blank squares. */
public class Chessboard {
    private final Piece[][] board  =  new Piece[8][8];
    private final BoardHistory history;
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private PieceColour currentTurn;

    /** Initialises the board with all squares blank. */
    public Chessboard(){
        history = new BoardHistory();
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
    }

    /** Adds all the pieces in the collections to the board based on the pieces x and y values */
    void populateBoard(Collection<Piece> whitePieces, Collection<Piece> blackPieces){
        this.whitePieces.addAll(whitePieces);
        this.blackPieces.addAll(blackPieces);
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /** Finds the piece at x and y. If there is no piece or the x and y are invalid it returns {@link Blank}. */
    @NotNull
    public Piece getPiece(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    public boolean isSquareBlank(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        return board[y][x] instanceof Blank;
    }

    public void movePiece(int x, int y, @NotNull Piece piece){
        board[piece.getY()][piece.getX()] = new Blank(piece.getX(), piece.getY());
        board[y][x] = piece;
        piece.setPos(x, y);
    }

    public void movePiece(@NotNull MoveValue move){
        movePiece(move.newX(), move.newY(), move.piece());
    }

    public List<Piece> getAllColourPieces(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return blackPieces;
        else if(colour == PieceColour.WHITE)
            return whitePieces;
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    public King getKing(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return (King) blackPieces.getFirst();
        else if(colour == PieceColour.WHITE)
            return (King) whitePieces.getFirst();
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    public void setKingMoved(PieceColour kingToSet){
        getKing(kingToSet).firstMove();
    }

    public void setOtherRookMoved(int x, int y){
        Rook rookNotMoved = (Rook) getPiece(x, y);
        for(Piece piece : getAllColourPieces(rookNotMoved.getColour())){
            if(piece instanceof Rook && piece != rookNotMoved){
                piece.firstMove();
            }
        }
    }

    public void setPawnEnPassantable(int x, int y){
        getPiece(x, y).firstMove();
    }

    /** Adds the piece to the list of pieces. Used only when adding a new piece to the board. */
    public void addPiece(@NotNull Piece piece){
        if(piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece);
        else if(piece.getColour() == PieceColour.WHITE)
            whitePieces.add(piece);
        else
            throw new IllegalArgumentException("Invalid piece: " + piece);
    }

    /** Removes the piece from the list of pieces. Should only be used if a piece is no longer on the board. */
    public void removePiece(@NotNull Piece piece){
        if(piece.getColour() == PieceColour.BLACK)
            blackPieces.remove(piece);
        else if(piece.getColour() == PieceColour.WHITE)
            whitePieces.remove(piece);
        else
            throw new IllegalArgumentException("Invalid piece: " + piece);
    }

    /** Validates a move is correct then moves the piece. */
    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        if(!getPiece(oldX, oldY).getPossibleMoves().contains(new Coordinate(newX, newY))) // if invalid move
            throw new InvalidMoveException(oldX, oldY, newX, newY);
        Move move = new Move(newX, newY, getPiece(oldX, oldY), history.getLastPieceMoved(), this);
        history.push(move);
    }

    /** Gets the hash of the board. The previous moves is not included in this calculation, only the state of each
     * piece. */
    public int getState(){
        return Arrays.deepHashCode(board);
    }

    /**
     * Calculates if moving a piece to a position would put that teams king in check.
     * This assumes the piece moving to the new position is a valid move.
     * @param pieceToCheck the piece being moved.
     * @return true if in check, false if not
     */
    public boolean isMoveUnsafe(int newX, int newY, @NotNull Piece pieceToCheck){
        Piece lastPiece = history.getLastPieceMoved();
        Move move = new Move(newX, newY, pieceToCheck, lastPiece, this);
        boolean isMoveUnsafe = isKingInCheck(pieceToCheck.getColour());
        move.undo();
        return isMoveUnsafe;
    }

    public boolean isKingInCheck(@NotNull PieceColour kingToCheck){
        King king = getKing(kingToCheck);
        Coordinate kingPos = new Coordinate(king.getX(), king.getY());
        Iterable<Piece> enemyPieces = getAllColourPieces(PieceColour.getOtherColour(kingToCheck));
        for(Piece piece : enemyPieces){
            if(piece.getPossibleMoves().contains(kingPos)){
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(){
        if(!isKingInCheck(currentTurn))
            return false;
        Iterable<Piece> enemyPieces = getAllColourPieces(currentTurn);
        for (Piece enemyPiece : enemyPieces) {
            for (Coordinate move : enemyPiece.getPossibleMoves( )) {
                if (!isMoveUnsafe(move.x(), move.y(), enemyPiece))
                    return false;
            }
        }
        return true;
    }

    public boolean isDraw(){
        return isStalemate() ||
                isDraw50Move() ||
                isRepetition();
    }

    private boolean isStalemate(){
        if(isKingInCheck(currentTurn))
            return false;
        Iterable<Piece> pieces = getAllColourPieces(currentTurn);
        for(Piece piece : pieces){
            if(!piece.getPossibleMoves().isEmpty())
                return false;
        }
        return true;
    }

    private boolean isRepetition(){
        if(history.getNumFullMoves() < 4)
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

    private boolean isDraw50Move(){
        return history.getNumHalfMoves() == 50;
    }

    public void setCurrentTurn(PieceColour newTurn){currentTurn = newTurn;}

    public PieceColour getCurrentTurn(){return currentTurn;}

    @NotNull
    public List<MoveValue> getLastMoves() {
        return history.getLastMoves();
    }

    @Nullable
    public Move redoMove() {
        return history.redoMove();
    }

    public void redoAllMoves() {
        history.redoAllMoves();
    }

    @Nullable
    public Move undoMove() {
        return history.undoMove();
    }

    public int getNumHalfMoves() {
        return history.getNumHalfMoves();
    }

    public int getNumFullMoves() {
        return history.getNumFullMoves();
    }

    public void setNumFullMoves(int numFullMoves) {
        history.setNumFullMoves(numFullMoves);
    }

    public void setNumHalfMoves(int numHalfMoves) {
        history.setNumHalfMoves(numHalfMoves);
    }

    public void undoMultipleMoves(int numOfMoves) {
        history.undoMultipleMoves(numOfMoves);
    }

    public boolean canRedoMove() {
        return history.canRedoMove();
    }
}
