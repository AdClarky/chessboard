package chessboard;

import common.Coordinate;
import common.PieceValue;
import common.Pieces;

public record PieceBoard(
        Bitboard pawns,
        Bitboard knights,
        Bitboard rooks,
        Bitboard bishops,
        Bitboard queens,
        Bitboard kings) {


    public PieceBoard() {
        this(new Bitboard(), new Bitboard(), new Bitboard(), new Bitboard(), new Bitboard(), new Bitboard());
    }

    public PieceBoard add(PieceValue piece) {
        return add(piece.pieceType(), piece.position());
    }

    public PieceBoard add(Pieces piece, Coordinate position) {
        return switch (piece) {
            case PAWN -> new PieceBoard(pawns.add(position), knights, rooks, bishops, queens, kings);
            case KNIGHT -> new PieceBoard(pawns, knights.add(position), rooks, bishops, queens, kings);
            case ROOK -> new PieceBoard(pawns, knights, rooks.add(position), bishops, queens, kings);
            case BISHOP -> new PieceBoard(pawns, knights, rooks, bishops.add(position), queens, kings);
            case QUEEN -> new PieceBoard(pawns, knights, rooks, bishops, queens.add(position), kings);
            case KING -> new PieceBoard(pawns, knights, rooks, bishops, queens, kings.add(position));
            case BLANK -> this;
        };
    }

    public PieceBoard remove(Pieces piece, Coordinate position) {
        return switch (piece) {
            case PAWN -> new PieceBoard(pawns.remove(position), knights, rooks, bishops, queens, kings);
            case KNIGHT -> new PieceBoard(pawns, knights.remove(position), rooks, bishops, queens, kings);
            case ROOK -> new PieceBoard(pawns, knights, rooks.remove(position), bishops, queens, kings);
            case BISHOP -> new PieceBoard(pawns, knights, rooks, bishops.remove(position), queens, kings);
            case QUEEN -> new PieceBoard(pawns, knights, rooks, bishops, queens.remove(position), kings);
            case KING -> new PieceBoard(pawns, knights, rooks, bishops, queens, kings.remove(position));
            case BLANK -> this;
        };
    }

    public PieceBoard remove(Coordinate position) {
        return remove(get(position), position);
    }

    public Pieces get(Coordinate position) {
        if (pawns.contains(position))
            return Pieces.PAWN;
        if (knights.contains(position))
            return Pieces.KNIGHT;
        if (bishops.contains(position))
            return Pieces.BISHOP;
        if (rooks.contains(position))
            return Pieces.ROOK;
        if (queens.contains(position))
            return Pieces.QUEEN;
        if (kings.contains(position))
            return Pieces.KING;
        return Pieces.BLANK;
    }

    public PieceBoard move(Coordinate from, Coordinate to) {
        Pieces piece = get(from);
        if (piece == Pieces.BLANK)
            return this;
        return remove(from).remove(to).add(piece, to);
    }

    public long getKingPositions() {
        return kings.getBoard();
    }
}
