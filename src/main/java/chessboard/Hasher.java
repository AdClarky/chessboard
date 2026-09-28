package chessboard;

import common.Coordinate;
import common.PieceColour;
import common.Pieces;

import java.util.Random;

public class Hasher {
    /**
     * WHITE PAWN -> 0;
     * WHITE ROOK -> 1;
     * WHITE KNIGHT -> 2;
     * WHITE BISHOP -> 3;
     * WHITE QUEEN -> 4;
     * WHITE KING -> 5;
     * Black + 6
     */
    private static final long[][] pieceKeys = new long[64][12];
    private static final long sideToMoveKey;
    private static final long[] castlingKeys = new long[16];
    private static final long[] enPassantKeys = new long[8];

    static {
        Random random = new Random(123456);
        for (int square = 0; square < 64; square++) {
            for (int piece = 0; piece < 12; piece++) {
                pieceKeys[square][piece] = random.nextLong();
            }
        }
        sideToMoveKey = random.nextLong();
        for (int i = 0; i < 16; i++) {
            castlingKeys[i] = random.nextLong();
        }
        for (int i = 0; i < 8; i++) {
            enPassantKeys[i] = random.nextLong();
        }
    }

    public Hasher() {}

    private static int getPieceIndex(Pieces piece, PieceColour colour) {
        int pieceIndex = piece.toIndex();
        if (colour == PieceColour.BLACK)
            pieceIndex += 6;
        return pieceIndex;
    }

    public long getHash(Chessboard board) {
        long hash = 0L;
        Iterable<Coordinate> squares = new Bitboard(~board.getEmptySquares().getBoard());
        for (Coordinate square : squares) {
            int pieceIndex = getPieceIndex(board.getPiece(square), board.getColour(square));
            hash ^= pieceKeys[square.getBitboardIndex()][pieceIndex];
        }

        if (board.getTurn() == PieceColour.BLACK) {
            hash ^= sideToMoveKey;
        }

        hash ^= castlingKeys[getCastlingIndex(board.getCastlingRights())];

        Coordinate enPassantSquare = board.getEnPassantSquare();
        if (enPassantSquare != null) {
            int enPassantIndex = enPassantSquare.x();
            hash ^= enPassantKeys[enPassantIndex];
        }

        return hash;
    }

    private int getCastlingIndex(long castlingRights) {
        long whiteKing = 0x10L;
        long blackKing = 0x1000000000000000L;
        long whiteQueenRook = 0x1L;
        long whiteKingRook = 0x80L;
        long blackQueenRook = 0x100000000000000L;
        long blackKingRook = 0x8000000000000000L;

        int index = 0;
        if ((castlingRights & whiteKing) != 0) { // white castling
            if ((castlingRights & whiteQueenRook) != 0)
                index |= 1;
            if ((castlingRights & whiteKingRook) != 0)
                index |= 2;
        }
        if ((castlingRights & blackKing) != 0) { // black castling
            if ((castlingRights & blackQueenRook) != 0)
                index |= 4;
            if ((castlingRights & blackKingRook) != 0)
                index |= 8;
        }
        return index;
    }

    public long updateHash(long hash, Move move) {
        hash ^= sideToMoveKey;
        Coordinate prevEnPassant = move.getOldBoard().getEnPassantSquare();
        if (prevEnPassant != null)
            hash ^= enPassantKeys[prevEnPassant.x()];
        Coordinate enPassantSquare = move.getNewBoard().getEnPassantSquare();
        if (enPassantSquare != null)
            hash ^= enPassantKeys[enPassantSquare.x()];

        int pieceIndex = getPieceIndex(move.getPiece(), move.getColour());
        hash ^= pieceKeys[move.getOldPos().getBitboardIndex()][pieceIndex];


        if (move.isTaking()) {
            Pieces takenPiece = move.getOldBoard().getPiece(move.getNewPos());
            if(takenPiece.equals(Pieces.BLANK) && prevEnPassant != null) // en passant
                takenPiece = move.getOldBoard().getPiece(new Coordinate(prevEnPassant.x(), move.getOldPos().y()));
            pieceIndex = getPieceIndex(takenPiece, move.getColour().invert());
            hash ^= pieceKeys[move.getNewPos().getBitboardIndex()][pieceIndex];
        }
        Pieces newPiece = move.getNewBoard().getPiece(move.getNewPos());
        if(newPiece != null) {
            pieceIndex = getPieceIndex(newPiece, move.getColour());
            hash ^= pieceKeys[move.getNewPos().getBitboardIndex()][pieceIndex];
        }


        hash ^= castlingKeys[getCastlingIndex(move.getOldBoard().castlingRights().getBoard())];
        hash ^= castlingKeys[getCastlingIndex(move.getNewBoard().castlingRights().getBoard())];

        return hash;
    }
}
