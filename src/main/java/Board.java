public class Board {
    Bitboard whitePieces = new Bitboard();
    Bitboard blackPieces = new Bitboard();

    public Board(){}

    public void addPiece(Piece piece){
        if(piece.getColour() == PieceColour.WHITE)
            whitePieces.add(piece.getPosition());
        else if(piece.getColour() == PieceColour.BLACK)
            blackPieces.add(piece.getPosition());
    }

    public void movePiece(PieceColour colour, Coordinate position1, Coordinate position2){
        whitePieces.remove(position1);
        blackPieces.remove(position2);
        if(colour == PieceColour.WHITE)
            whitePieces.add(position1);
        else if(colour == PieceColour.BLACK)
            blackPieces.add(position2);
    }
}
