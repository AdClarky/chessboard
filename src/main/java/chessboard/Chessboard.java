package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.PieceValue;
import common.Pieces;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * A chess board that is automatically populated with blank squares.
 */
class Chessboard {
    private final PieceBoard pieceBoard;
    private final ColourBoard colourBoard;
    private final Bitboard castlingRights;
    private PieceColour currentTurn;
    private Coordinate enPassantSquare;


    /**
     * Initialises the board with all squares blank.
     */
    public Chessboard() {
        pieceBoard = new PieceBoard();
        colourBoard = new ColourBoard();
        castlingRights = new Bitboard();
        castlingRights.add(new Coordinate(0, 0));
        castlingRights.add(new Coordinate(4, 0));
        castlingRights.add(new Coordinate(0, 7));
        castlingRights.add(new Coordinate(7, 7));
        castlingRights.add(new Coordinate(7, 0));
        castlingRights.add(new Coordinate(4, 7));
        currentTurn = PieceColour.WHITE;
        enPassantSquare = null;
    }

    private Chessboard (PieceBoard pieceBoard, ColourBoard colourBoard, Bitboard castlingRights, PieceColour currentTurn, Coordinate enPassantSquare) {
        this.pieceBoard = pieceBoard;
        this.colourBoard = colourBoard;
        this.castlingRights = castlingRights;
        this.currentTurn = currentTurn;
        this.enPassantSquare = enPassantSquare;
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
    }

    @Nullable
    public Pieces getPiece(Coordinate position) {
        return pieceBoard.get(position);
    }

    public boolean isSquareColour(Coordinate position, PieceColour colour) {
        return colourBoard.isPositionColour(position, colour);
    }

    public boolean isSquareBlank(Coordinate coordinate) {
        return colourBoard.isSquareBlank(coordinate);
    }

    public void movePiece(Coordinate oldPos, Coordinate newPos) {
        colourBoard.movePiece(oldPos, newPos);
        pieceBoard.move(oldPos, newPos);
    }

    public Bitboard getAllColourPositions(PieceColour colour) {
        return colourBoard.getBoard(colour);
    }

    public Bitboard getEmptySquares() {
        return colourBoard.getEmptySquares();
    }

    public void movePiece(@NotNull MoveValue move) {
        movePiece(move.oldPos(), move.newPos());
    }

    public void removeCastlingRight(Coordinate position) {
        castlingRights.remove(position);
    }

    public long getCastlingRights() {
        return castlingRights.getBoard();
    }

    public void setCastlingRights(long rights) {
        castlingRights.set(rights);
    }

    public void removeAllCastling(PieceColour colour) {
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        castlingRights.remove(new Coordinate(0, backRow));
        castlingRights.remove(new Coordinate(7, backRow));
        castlingRights.remove(new Coordinate(4, backRow));
    }

    @Nullable
    public Coordinate getEnPassantSquare() {
        return enPassantSquare;
    }

    public void setEnPassantSquare(@Nullable Coordinate position) {
        enPassantSquare = position;
    }

    void nextTurn() {
        currentTurn = currentTurn == PieceColour.WHITE ? PieceColour.BLACK : PieceColour.WHITE;
    }

    public PieceColour getTurn() {
        return currentTurn;
    }

    public Coordinate getKingPos(PieceColour colour) {
        long kingPositions = pieceBoard.getKingPositions();
        return colourBoard.getKingPosition(kingPositions, colour);
    }

    public PieceColour getColour(Coordinate position) {
        return colourBoard.getColourAtPosition(position);
    }

    public void promotion(Coordinate position, Pieces promotionPiece) {
        if (promotionPiece == Pieces.PAWN || promotionPiece == Pieces.KING || promotionPiece == Pieces.BLANK)
            throw new RuntimeException("Invalid promotion piece");
        pieceBoard.remove(position);
        pieceBoard.add(promotionPiece, position);
    }

    public void removePiece(Coordinate position) {
        pieceBoard.remove(position);
        colourBoard.remove(position);
    }

    public void addPiece(Pieces piece, Coordinate position, PieceColour colour) {
        if (piece == Pieces.BLANK)
            throw new IllegalArgumentException("Added piece cannot be blank");
        pieceBoard.add(piece, position);
        colourBoard.add(colour, position);
    }

    Chessboard copy() {
        PieceBoard pieceBoardCopy = pieceBoard.copy();
        ColourBoard colourBoardCopy = colourBoard.copy();
        Bitboard castlingRightsCopy = new Bitboard(castlingRights.getBoard());
        return new Chessboard(pieceBoardCopy, colourBoardCopy, castlingRightsCopy, currentTurn, enPassantSquare);
    }
}
