import java.util.Collection;

public class PieceBoard {
    private Bitboard pawns = new Bitboard();
    private Bitboard knights = new Bitboard();
    private Bitboard rooks = new Bitboard();
    private Bitboard bishops = new Bitboard();
    private Bitboard queens = new Bitboard();
    private Bitboard kings = new Bitboard();

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
}
