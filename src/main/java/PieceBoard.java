public class PieceBoard {
    private final Bitboard pawns = new Bitboard();
    private final Bitboard knights = new Bitboard();
    private final Bitboard rooks = new Bitboard();
    private final Bitboard bishops = new Bitboard();
    private final Bitboard queens = new Bitboard();
    private final Bitboard kings = new Bitboard();

    public PieceBoard(){}

    public void add(Piece piece){
        if(piece instanceof Pawn)
            pawns.add(piece.getPosition());
        else if(piece instanceof Knight)
            knights.add(piece.getPosition());
        else if(piece instanceof Rook)
            rooks.add(piece.getPosition());
        else if(piece instanceof Bishop)
            bishops.add(piece.getPosition());
        else if(piece instanceof Queen)
            queens.add(piece.getPosition());
        else if(piece instanceof King)
            kings.add(piece.getPosition());
    }

    public void remove(Piece piece){
        if(piece instanceof Pawn)
            pawns.remove(piece.getPosition());
        else if(piece instanceof Knight)
            knights.remove(piece.getPosition());
        else if(piece instanceof Rook)
            rooks.remove(piece.getPosition());
        else if(piece instanceof Bishop)
            bishops.remove(piece.getPosition());
        else if(piece instanceof Queen)
            queens.remove(piece.getPosition());
        else if(piece instanceof King)
            kings.remove(piece.getPosition());
    }
}
