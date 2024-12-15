import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/** A chess board that is automatically populated with blank squares. */
class Chessboard {
    private final PieceBoard board = new PieceBoard();
    private final PieceBoard pieceBoard = new PieceBoard();
    private final ColourBoard colourBoard = new ColourBoard();
    private final PossibleMoves possibleMoves = new PossibleMoves();
    private final BoardHistory history = new BoardHistory();
    private final Bitboard castlingRights = new Bitboard();
    private PieceColour currentTurn = PieceColour.WHITE;
    private Coordinate enPassantSquare;


    /**
     * Initialises the board with all squares blank.
     */
    public Chessboard() {
        castlingRights.addAll(List.of(new Coordinate(0, 0), new Coordinate(3, 0),
                new Coordinate(7, 0), new Coordinate(0, 7), new Coordinate(7, 7),
                new Coordinate(3, 7)));
    }

    /**
     * Adds all the pieces in the collections to the board based on the pieces x and y values
     */
    void populateBoard(Iterable<PieceValue> whitePieces, Iterable<PieceValue> blackPieces) {
        for (PieceValue piece : blackPieces) {
            colourBoard.add(piece);
            pieceBoard.add(piece);
        }
        for (PieceValue piece : whitePieces) {
            colourBoard.add(piece);
            pieceBoard.add(piece);
        }
        new ChessLogic(this).calculatePossibleMoves();
    }

    /**
     * Finds the piece at x and y. If there is no piece or the x and y are invalid it returns {@link Blank}.
     */
    @NotNull
    public Pieces getPiece(Coordinate position) {
        return board.get(position);
    }

    public boolean isSquareColour(Coordinate position, PieceColour colour){
        if(position.isNotInRange())
            return false;
        return colourBoard.isPositionColour(position, colour);
    }

    @NotNull
    public PieceColour getColourAtPosition(Coordinate kingPos) {
        return colourBoard.getColourAtPosition(kingPos);
    }

    public boolean isSquareBlank(Coordinate coordinate) {
        if(coordinate.isNotInRange())
            return false;
        return colourBoard.isSquareBlank(coordinate);
    }

    public void movePiece(Coordinate oldPos, Coordinate newPos) {
        colourBoard.movePiece(oldPos, newPos);
        board.move(oldPos, newPos);
    }

    public Bitboard getAllColourPositions(PieceColour colour){
        return colourBoard.getBoard(colour);
    }

    public Bitboard getEmptySquares(){
        return colourBoard.getEmptySquares();
    }

    public void movePiece(@NotNull MoveValue move) {
        movePiece(move.oldPos(), move.newPos());
    }

    public void removeCastlingRight(Coordinate position){
        castlingRights.remove(position);
    }

    public boolean canCastle(Coordinate position){
        return castlingRights.contains(position);
    }

    public boolean canKingCastle(PieceColour colour){
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        return canCastle(new Coordinate(3, backRow));
    }

    public boolean canAnythingCastle(){
        return !castlingRights.isEmpty();
    }

    public void calculateCastling(Coordinate position){
        castlingRights.remove(position);
    }

    public long getCastlingRights(){
        return castlingRights.getBoard();
    }

    public void setCastlingRights(long rights){
        castlingRights.set(rights);
    }

    public void removeAllCastling(PieceColour colour){
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        Collection<Coordinate> positions = List.of(new Coordinate(0, backRow), new Coordinate(3, backRow),
                new Coordinate(7, backRow));
        castlingRights.removeAll(positions);
    }

    public void setEnPassantSquare(@Nullable Coordinate position) {
        enPassantSquare = position;
    }

    @Nullable
    public Coordinate getEnPassantSquare() {
        return enPassantSquare;
    }

    public void makeMove(Coordinate oldPos, Coordinate newPos){
        if (history.canRedoMove())
            history.clearRedoMoves();
        Move move = new Move(oldPos, newPos, this);
        history.push(move);
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

    public Coordinate getKingPos(PieceColour colour) {
        Collection<Coordinate> kingPositions = board.getKingPositions();
        return colourBoard.getKingPosition(kingPositions, colour);
    }

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

    public PieceColour getPieceColour(Coordinate position) {
        return colourBoard.getColourAtPosition(position);
    }

    public void promotion(Coordinate position) {
        board.add(Pieces.QUEEN, position);
        colourBoard.add(currentTurn, position);
    }

    public void removePiece(Coordinate position) {
        board.remove(position);
        colourBoard.remove(position);
    }
}
