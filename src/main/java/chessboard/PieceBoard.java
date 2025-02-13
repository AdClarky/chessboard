package chessboard;

import common.Coordinate;
import common.PieceValue;
import common.Pieces;
import org.jetbrains.annotations.Nullable;

public class PieceBoard {
    private final Bitboard pawns = new Bitboard();
    private final Bitboard knights = new Bitboard();
    private final Bitboard rooks = new Bitboard();
    private final Bitboard bishops = new Bitboard();
    private final Bitboard queens = new Bitboard();
    private final Bitboard kings = new Bitboard();

    public PieceBoard(){}

    public void add(PieceValue piece){
        add(piece.pieceType(), piece.position());
    }

    public void add(Pieces piece, Coordinate position){
        switch(piece) {
            case Pieces.PAWN:
                pawns.add(position);
                break;
            case Pieces.KNIGHT:
                knights.add(position);
                break;
            case Pieces.ROOK:
                rooks.add(position);
                break;
            case Pieces.BISHOP:
                bishops.add(position);
                break;
            case Pieces.QUEEN:
                queens.add(position);
                break;
            case Pieces.KING:
                kings.add(position);
                break;
        }
    }

    public void remove(Pieces piece, Coordinate position){
        switch(piece) {
            case Pieces.PAWN:
                pawns.remove(position);
                break;
            case Pieces.KNIGHT:
                knights.remove(position);
                break;
            case Pieces.ROOK:
                rooks.remove(position);
                break;
            case Pieces.BISHOP:
                bishops.remove(position);
                break;
            case Pieces.QUEEN:
                queens.remove(position);
                break;
            case Pieces.KING:
                kings.remove(position);
                break;
        }
    }

    public @Nullable Pieces remove(Coordinate position){
        if(pawns.remove(position))
            return Pieces.PAWN;
        if(knights.remove(position))
            return Pieces.KNIGHT;
        if(rooks.remove(position))
            return Pieces.ROOK;
        if(bishops.remove(position))
            return Pieces.BISHOP;
        if(queens.remove(position))
            return Pieces.QUEEN;
        if(kings.remove(position))
            return Pieces.KING;
        return null;
    }

    @Nullable
    public Pieces get(Coordinate position){
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
        return Pieces.BLANK;
    }

    public void move(Coordinate from, Coordinate to){
        Pieces piece = remove(from);
        if(piece == null)
            return;
        remove(to);
        add(piece, to);
    }

    public long getKingPositions(){
        return kings.getBoard();
    }
}
