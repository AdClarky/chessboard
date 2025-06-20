package chessboard;

import common.Coordinate;
import common.MoveValue;
import common.PieceColour;
import common.Pieces;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A chess board that is automatically populated with blank squares.
 */
record Chessboard(
        PieceBoard pieceBoard,
        ColourBoard colourBoard,
        Bitboard castlingRights,
        PieceColour currentTurn,
        Coordinate enPassantSquare) {


    /**
     * Initialises the board with all squares blank.
     */
    public Chessboard() {
        this(new PieceBoard(),
                new ColourBoard(),
                new Bitboard().add(new Coordinate(0, 0))
                        .add(new Coordinate(4, 0))
                        .add(new Coordinate(0, 7))
                        .add(new Coordinate(7, 7))
                        .add(new Coordinate(7, 0))
                        .add(new Coordinate(4, 7)),
                PieceColour.WHITE,
                null);
    }

    @NotNull
    public Pieces getPiece(Coordinate position) {
        return pieceBoard.get(position);
    }

    public boolean isSquareColour(Coordinate position, PieceColour colour) {
        return colourBoard.isPositionColour(position, colour);
    }

    public boolean isSquareBlank(Coordinate coordinate) {
        return colourBoard.isSquareBlank(coordinate);
    }

    public Chessboard movePiece(Coordinate oldPos, Coordinate newPos) {
        return new Chessboard(pieceBoard.move(oldPos, newPos),
                colourBoard.move(oldPos, newPos),
                castlingRights,
                currentTurn,
                enPassantSquare);
    }

    public Chessboard movePiece(@NotNull MoveValue move) {
        return movePiece(move.oldPos(), move.newPos());
    }

    public Bitboard getAllColourPositions(PieceColour colour) {
        return colourBoard.getBoard(colour);
    }

    public Bitboard getEmptySquares() {
        return colourBoard.getEmptySquares();
    }

    public Chessboard removeCastlingRight(Coordinate position) {
        return new Chessboard(pieceBoard,
                colourBoard,
                castlingRights.remove(position),
                currentTurn,
                enPassantSquare);
    }

    public long getCastlingRights() {
        return castlingRights.getBoard();
    }

    public Chessboard setCastlingRights(long rights) {
        return new Chessboard(pieceBoard,
                colourBoard,
                new Bitboard(rights),
                currentTurn,
                enPassantSquare);
    }

    public Chessboard removeAllCastling(PieceColour colour) {
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        return new Chessboard(pieceBoard,
                colourBoard,
                castlingRights.remove(new Coordinate(0, backRow))
                        .remove(new Coordinate(7, backRow))
                        .remove(new Coordinate(4, backRow)),
                currentTurn,
                enPassantSquare);
    }

    @Nullable
    public Coordinate getEnPassantSquare() {
        return enPassantSquare;
    }

    public Chessboard setEnPassantSquare(@Nullable Coordinate position) {
        return new Chessboard(pieceBoard,
                colourBoard,
                castlingRights,
                currentTurn,
                position);
    }

    Chessboard nextTurn() {
        return new Chessboard(pieceBoard,
                colourBoard,
                castlingRights,
                currentTurn == PieceColour.WHITE ? PieceColour.BLACK : PieceColour.WHITE,
                enPassantSquare);
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

    public Chessboard promotion(Coordinate position, Pieces promotionPiece) {
        if (!promotionPiece.isPromotionPiece()) {
            throw new RuntimeException("Invalid promotion promotionPiece");
        }
        return new Chessboard(pieceBoard.remove(position).add(promotionPiece, position),
                colourBoard,
                castlingRights,
                currentTurn,
                null);
    }

    public Chessboard removePiece(Coordinate position) {
        return new Chessboard(pieceBoard.remove(position),
                colourBoard.remove(position),
                castlingRights,
                currentTurn,
                enPassantSquare);
    }

    public Chessboard addPiece(Pieces piece, Coordinate position, PieceColour colour) {
        if (piece == Pieces.BLANK)
            throw new IllegalArgumentException("Added promotionPiece cannot be blank");
        return new Chessboard(pieceBoard.add(piece, position),
                colourBoard.add(colour, position),
                castlingRights,
                currentTurn,
                enPassantSquare);
    }
}
