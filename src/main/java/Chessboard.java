import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/** A chess board that is automatically populated with blank squares. */
class Chessboard {
    private final Piece[][] board = new Piece[8][8];
    private final ColourBoard colourBoard = new ColourBoard();
    private final PossibleMoves possibleMoves = new PossibleMoves();
    private final BoardHistory history;
    private final Bitboard castlingRights = new Bitboard();
    private final Collection<Piece> blackPieces = new HashSet<>(16);
    private King blackKing;
    private final Collection<Piece> whitePieces = new HashSet<>(16);
    private King whiteKing;
    private PieceColour currentTurn = PieceColour.WHITE;
    private Coordinate enPassantSquare;


    /**
     * Initialises the board with all squares blank.
     */
    public Chessboard() {
        history = new BoardHistory();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                board[y][x] = new Blank(x, y);
            }
        }
    }

    /**
     * Adds all the pieces in the collections to the board based on the pieces x and y values
     */
    void populateBoard(Collection<Piece> whitePieces, Collection<Piece> blackPieces) {
        castlingRights.addAll(List.of(new Coordinate(0, 0), new Coordinate(3, 0),
                new Coordinate(3, 0), new Coordinate(0, 7), new Coordinate(7, 7),
                new Coordinate(3, 7)));
        this.whitePieces.addAll(whitePieces);
        this.blackPieces.addAll(blackPieces);
        for (Piece piece : blackPieces) {
            colourBoard.add(piece);
            board[piece.getY()][piece.getX()] = piece;
            if (piece instanceof King)
                blackKing = (King) piece;
        }
        for (Piece piece : whitePieces) {
            colourBoard.add(piece);
            board[piece.getY()][piece.getX()] = piece;
            if (piece instanceof King)
                whiteKing = (King) piece;
        }
        new ChessLogic(this).calculatePossibleMoves();
    }

    /**
     * Finds the piece at x and y. If there is no piece or the x and y are invalid it returns {@link Blank}.
     */
    @NotNull
    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    public boolean isSquareColour(Coordinate position, PieceColour colour){
        if(!position.isInRange())
            return false;
        return colourBoard.isPositionColour(position, colour);
    }

    public boolean hasPieceHadFirstMove(int x, int y) {
        return getPiece(x, y).hadFirstMove();
    }

    public boolean isSquareBlank(int x, int y) {
        return isSquareBlank(new Coordinate(x, y));
    }

    public boolean isSquareBlank(Coordinate coordinate) {
        if(!coordinate.isInRange())
            return false;
        return colourBoard.isSquareBlank(coordinate);
    }

    public void movePiece(int x, int y, @NotNull Piece piece) {
        colourBoard.movePiece(piece.getColour(), piece.getPosition(), new Coordinate(x, y));
        board[piece.getY()][piece.getX()] = new Blank(piece.getX(), piece.getY());
        board[y][x] = piece;
        piece.setPos(x, y);
    }

    public void movePiece(@NotNull MoveValue move) {
        movePiece(move.newX(), move.newY(), move.piece());
    }

    public Collection<Piece> getAllColourPieces(PieceColour colour) {
        if (colour == PieceColour.BLACK)
            return blackPieces;
        else if (colour == PieceColour.WHITE)
            return whitePieces;
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    public King getKing(PieceColour colour) {
        if (colour == PieceColour.BLACK)
            return blackKing;
        else if (colour == PieceColour.WHITE)
            return whiteKing;
        else
            throw new IllegalArgumentException("Invalid colour: " + colour);
    }

    public void removeCastlingRight(Coordinate position){
        castlingRights.remove(position);
    }

    public boolean canCastle(Coordinate position){
        return castlingRights.contains(position);
    }

    public boolean canAnythingCastle(){
        return castlingRights.isEmpty();
    }

    public void removeCastlingRights(PieceColour colour){
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        Collection<Coordinate> positions = List.of(new Coordinate(0, backRow), new Coordinate(3, backRow),
                new Coordinate(7, backRow));
        castlingRights.removeAll(positions);
    }

    public boolean hasKingMoved(PieceColour colour) {
        return getKing(colour).hadFirstMove();
    }

    public void setKingMoved(PieceColour kingToSet) {
        getKing(kingToSet).firstMove();
    }

    public void setOtherRookMoved(int x, int y) {
        Rook rookNotMoved = (Rook) getPiece(x, y);
        for (Piece piece : getAllColourPieces(rookNotMoved.getColour())) {
            if (piece instanceof Rook && piece != rookNotMoved) {
                piece.firstMove();
            }
        }
    }

    public void setEnPassantSquare(@Nullable Coordinate position) {
        enPassantSquare = position;
    }

    @Nullable
    public Coordinate getEnPassantSquare() {
        return enPassantSquare;
    }

    /**
     * Adds the piece to the list of pieces. Used only when adding a new piece to the board.
     */
    public void addPiece(@NotNull Piece piece) {
        if (piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece);
        else if (piece.getColour() == PieceColour.WHITE)
            whitePieces.add(piece);
        else
            throw new IllegalArgumentException("Invalid piece: " + piece);
    }

    /**
     * Removes the piece from the list of pieces. Should only be used if a piece is no longer on the board.
     */
    public void removePiece(@NotNull Piece piece) {
        if (piece.getColour() == PieceColour.BLACK)
            blackPieces.remove(piece);
        else if (piece.getColour() == PieceColour.WHITE)
            whitePieces.remove(piece);
        else
            throw new IllegalArgumentException("Invalid piece: " + piece);
    }

    public void makeMove(int oldX, int oldY, int newX, int newY) {
        if (history.canRedoMove())
            history.clearRedoMoves();
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
        int[] hashRows = new int[8];
        for (int y = 0; y < 8; y++) {
            int hashRow = 0;
            for (Piece piece : board[y]) {
                hashRow += Objects.hash(piece, piece.getX(), piece.getY());
            }
            hashRows[y] = hashRow;
        }
        return Arrays.hashCode(hashRows);
    }

    public void setCurrentTurn(PieceColour newTurn) {
        currentTurn = newTurn;
    }


    void nextTurn() {
        currentTurn = currentTurn == PieceColour.WHITE ? PieceColour.BLACK : PieceColour.WHITE;
    }

    public PieceColour getCurrentTurn() {
        return currentTurn;
    }

    @NotNull
    public List<MoveValue> getLastMoves() {
        return history.getLastMoves();
    }

    @Nullable
    public Piece getLastPieceMoved() {
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

    public void setNumFullMoves(int numFullMoves) throws AccessedHistoryDuringGameException {
        history.setNumFullMoves(numFullMoves);
    }

    public void setNumHalfMoves(int numHalfMoves) throws AccessedHistoryDuringGameException {
        history.setNumHalfMoves(numHalfMoves);
    }

    public void undoMultipleMoves(int numOfMoves) {
        history.undoMultipleMoves(numOfMoves);
    }

    public boolean canRedoMove() {
        return history.canRedoMove();
    }

    public boolean wasMoveCapture() {
        return history.wasMoveCapture();
    }

    public void updatePossibleMoves(PieceColour colour, Collection<Coordinate> moves) {
        possibleMoves.updatePossibleMoves(colour, moves);
    }

    public boolean isPossible(PieceColour colour, Coordinate position) {
        return possibleMoves.isPossible(colour, position);
    }

    public void removePossible(PieceColour colour, Coordinate position){
        possibleMoves.removePossible(colour, position);
    }

    public void clearPossibleMoves(PieceColour colour) {
        possibleMoves.clearBoard(colour);
    }

    public boolean isCheckmate(PieceColour colour) {
        return possibleMoves.isCheckmate(colour);
    }

    public long getPossible(PieceColour colour) {
        return possibleMoves.getBoardValue(colour);
    }

    public void setPossibleMoves(PieceColour colour, long enemyPossible) {
        possibleMoves.setPossible(colour, enemyPossible);
    }
}
