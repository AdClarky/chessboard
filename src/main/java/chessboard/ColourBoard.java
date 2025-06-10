package chessboard;

import common.Coordinate;
import common.PieceColour;
import common.PieceValue;
import org.jetbrains.annotations.Nullable;

public record ColourBoard (Bitboard whitePieces, Bitboard blackPieces) {
    public ColourBoard(){
        this(new Bitboard(), new Bitboard());
    }

    public ColourBoard add(PieceValue piece){
        if(piece.colour() == PieceColour.WHITE)
            return new ColourBoard(whitePieces.add(piece.position()), blackPieces);
        else if(piece.colour() == PieceColour.BLACK)
            return new ColourBoard(whitePieces, blackPieces.add(piece.position()));
        return this;
    }

    public ColourBoard movePiece(Coordinate oldPosition, Coordinate newPosition){
        PieceColour colour = getColourAtPosition(oldPosition);
        if (colour == PieceColour.WHITE)
            return new ColourBoard(whitePieces.remove(oldPosition).add(newPosition), blackPieces.remove(newPosition));
        if (colour == PieceColour.BLACK)
            return new ColourBoard(whitePieces.remove(newPosition), blackPieces.remove(oldPosition).add(newPosition));
        return this;
    }

    public boolean isSquareBlank(Coordinate coordinate) {
        return !whitePieces.contains(coordinate) && !blackPieces.contains(coordinate);
    }

    public boolean isPositionColour(Coordinate position, PieceColour colour) {
        return getBoard(colour).contains(position);
    }

    public Bitboard getBoard(PieceColour colour){
        if(colour == PieceColour.WHITE)
            return whitePieces;
        return blackPieces;
    }

    public Bitboard getEmptySquares(){
        long empty = ~(whitePieces.getBoard() | blackPieces.getBoard());
        return new Bitboard(empty);
    }

    @Nullable
    public PieceColour getColourAtPosition(Coordinate position) {
        if(whitePieces.contains(position))
            return PieceColour.WHITE;
        if(blackPieces.contains(position))
            return PieceColour.BLACK;
        return null;
    }

    public ColourBoard add(PieceColour colour, Coordinate position) {
        if(colour == PieceColour.WHITE)
            return new ColourBoard(whitePieces.add(position), blackPieces);
        if(colour == PieceColour.BLACK)
            return new ColourBoard(whitePieces, blackPieces.add(position));
        return this;
    }

    public ColourBoard remove(Coordinate position) {
    PieceColour colour = getColourAtPosition(position);
    if (colour == PieceColour.WHITE)
        return new ColourBoard(whitePieces.remove(position), blackPieces);
    if (colour == PieceColour.BLACK)
        return new ColourBoard(whitePieces, blackPieces.remove(position));
    return this;
}

    public Coordinate getKingPosition(long kingPositions, PieceColour colour) {
        kingPositions &= getBoard(colour).getBoard();
        return Coordinate.fromBitboard(kingPositions);
    }
}
