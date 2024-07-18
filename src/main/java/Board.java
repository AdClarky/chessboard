public class Board {
    Bitboard whitePieces = new Bitboard();
    Bitboard blackPieces = new Bitboard();

    public Board(){}

    public void add(Piece piece){
        if(piece.getColour() == PieceColour.WHITE)
            whitePieces.add(piece.getPosition());
        else if(piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece.getPosition());
    }

    public void movePiece(PieceColour colour, Coordinate oldPosition, Coordinate newPosition){
        whitePieces.remove(oldPosition);
        blackPieces.remove(oldPosition);
        if(colour == PieceColour.WHITE)
            whitePieces.add(newPosition);
        else if(colour == PieceColour.BLACK)
            blackPieces.add(newPosition);
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
}
