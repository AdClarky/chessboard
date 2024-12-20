import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class ColourBoard {
    Bitboard whitePieces = new Bitboard();
    Bitboard blackPieces = new Bitboard();

    public ColourBoard(){}

    public void add(PieceValue piece){
        if(piece.colour() == PieceColour.WHITE)
            whitePieces.add(piece.position());
        else if(piece.colour() == PieceColour.BLACK)
            blackPieces.add(piece.position());
    }

    public void movePiece(Coordinate oldPosition, Coordinate newPosition){
        boolean white = whitePieces.remove(oldPosition);
        boolean black = blackPieces.remove(oldPosition);
        whitePieces.remove(newPosition);
        blackPieces.remove(newPosition);
        if(white) {
            whitePieces.add(newPosition);
        }
        else if(black) {
            blackPieces.add(newPosition);
        }
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

    public void add(PieceColour colour, Coordinate position) {
        getBoard(colour).add(position);
    }

    public void remove(Coordinate position) {
        whitePieces.remove(position);
        blackPieces.remove(position);
    }

    public Coordinate getKingPosition(long kingPositions, PieceColour colour) {
        kingPositions &= getBoard(colour).getBoard();
        return Coordinate.fromBitboard(kingPositions);
    }
}
