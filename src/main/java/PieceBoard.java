import org.jetbrains.annotations.Nullable;

public class PieceBoard {
    private final Bitboard pawns = new Bitboard();
    private final Bitboard knights = new Bitboard();
    private final Bitboard rooks = new Bitboard();
    private final Bitboard bishops = new Bitboard();
    private final Bitboard queens = new Bitboard();
    private final Bitboard kings = new Bitboard();

    public PieceBoard(){}

    public void add(Pieces piece, Coordinate position){
        switch(piece) {
            case PAWN:
                pawns.add(position);
                break;
            case KNIGHT:
                knights.add(position);
                break;
            case ROOK:
                rooks.add(position);
                break;
            case BISHOP:
                bishops.add(position);
                break;
            case QUEEN:
                queens.add(position);
                break;
            case KING:
                kings.add(position);
                break;
        }
    }

    public void remove(Pieces piece, Coordinate position){
        switch(piece) {
            case PAWN:
                pawns.remove(position);
                break;
            case KNIGHT:
                knights.remove(position);
                break;
            case ROOK:
                rooks.remove(position);
                break;
            case BISHOP:
                bishops.remove(position);
                break;
            case QUEEN:
                queens.remove(position);
                break;
            case KING:
                kings.remove(position);
                break;
        }
    }

    @Nullable
    public Pieces getPiece(Coordinate position){
        if(pawns.contains(position))
            return Pieces.PAWN;
        if(knights.contains(position))
            return Pieces.KNIGHT;
        if(bishops.contains(position))
            return Pieces.BISHOP;
        if(rooks.contains(position))
            return Pieces.ROOK;
        if(queens.contains(position))
            return Pieces.QUEEN;
        if(kings.contains(position))
            return Pieces.KING;
        return null;
    }
}
