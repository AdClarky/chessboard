import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ColourBoard {
    Bitboard whitePieces = new Bitboard();
    Bitboard blackPieces = new Bitboard();

    public ColourBoard(){}

    public void add(Piece piece){
        if(piece.getColour() == PieceColour.WHITE)
            whitePieces.add(piece.getPosition());
        else if(piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece.getPosition());
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

    private Bitboard getBoard(PieceColour colour){
        if(colour == PieceColour.WHITE)
            return whitePieces;
        return blackPieces;
    }

    @NotNull
    public PieceColour getColourAtPosition(Coordinate kingPos) {
        if(whitePieces.contains(kingPos))
            return PieceColour.WHITE;
        if(blackPieces.contains(kingPos))
            return PieceColour.BLACK;
        return PieceColour.BLANK;
    }

    public void add(PieceColour colour, Coordinate position) {
        getBoard(colour).add(position);
    }

    public void remove(Coordinate position) {
        whitePieces.remove(position);
        blackPieces.remove(position);
    }

    public Coordinate getKingPosition(Collection<Coordinate> kingPositions, PieceColour colour) {
        kingPositions.retainAll(getBoard(colour));
        return kingPositions.iterator().next();
    }
}
