import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/** A chess board that is automatically populated with blank squares. */
public class Chessboard {
    private final Piece[][] board  =  new Piece[8][8];
    private final BoardHistory history;
    private final Set<Piece> blackPieces = new HashSet<>(16);
    private King blackKing;
    private final Set<Piece> whitePieces = new HashSet<>(16);
    private King whiteKing;
    private PieceColour currentTurn = PieceColour.WHITE;

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
            if(piece instanceof King)
                blackKing = (King) piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
            if(piece instanceof King)
                whiteKing = (King) piece;
        }
        new ChessLogic(this).calculatePossibleMoves();
    }

    /** Finds the piece at x and y. If there is no piece or the x and y are invalid it returns {@link Blank}. */
    @NotNull
    public Piece getPiece(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    @NotNull
    public Piece getPiece(Coordinate coordinate){
        return getPiece(coordinate.x(), coordinate.y());
    }

    @NotNull
    public PieceColour getPieceColour(int x, int y){
        return getPiece(x, y).getColour();
    }

    public boolean hasPieceHadFirstMove(int x, int y){
        return getPiece(x, y).hadFirstMove();
    }

    public boolean isSquareBlank(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return false;
        return getPiece(x, y) instanceof Blank;
    }


    public void movePiece(int x, int y, @NotNull Piece piece){
        board[piece.getY()][piece.getX()] = new Blank(piece.getX(), piece.getY());
        board[y][x] = piece;
        piece.setPos(x, y);
    }

    public void movePiece(@NotNull MoveValue move){
        movePiece(move.newX(), move.newY(), move.piece());
    }

    public Collection<Piece> getAllColourPieces(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return getPieces(blackPieces);
        else if(colour == PieceColour.WHITE)
            return getPieces(whitePieces);
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    private Collection<Piece> getPieces(Collection<Piece> pieces){
        return new ArrayList<>(pieces);
    }

    public King getKing(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return blackKing;
        else if(colour == PieceColour.WHITE)
            return whiteKing;
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    public boolean hasKingMoved(PieceColour colour){
        return getKing(colour).hadFirstMove();
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

    public void makeMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
        Move move = new Move(newX, newY, getPiece(oldX, oldY), history.getLastPieceMoved(), this);
        history.push(move);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Chessboard otherChessboard)) return false;
        return Objects.deepEquals(board, otherChessboard.board) && currentTurn == otherChessboard.currentTurn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(Piece[] row : board){
            for(Piece piece : row){
                hash += Objects.hash(piece, piece.getX(), piece.getY());
            }
        }
        return hash;
    }

    public void setCurrentTurn(PieceColour newTurn){currentTurn = newTurn;}

    public PieceColour getCurrentTurn(){return currentTurn;}

    @NotNull
    public List<MoveValue> getLastMoves() {
        return history.getLastMoves();
    }

    @Nullable
    public Piece getLastPieceMoved(){
        return history.getLastPieceMoved();
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
