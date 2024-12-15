public class MaskGenerator {
    private Chessboard board;
    private final static long RANK_TWO = 0xff00L;
    private final static long RANK_SEVEN = 0xff000000000000L;
    private static final long[] KNIGHT_ATTACKS = new long[64];
    private static final long[] KING_ATTACKS = new long[64];
    private static final long[] WHITE_PAWN_ATTACKS = new long[64];
    private static final long[] BLACK_PAWN_ATTACKS = new long[64];

    public MaskGenerator(Chessboard board) {
        this.board = board;
    }

    public long getMaskForPiece(Coordinate piecePos){
        Pieces piece = board.getPiece(piecePos);
        return switch (piece) {
            case PAWN -> getPawnMask(piecePos);
            case BISHOP -> getBishopMask(piecePos);
            case ROOK -> getRookMask(piecePos);
            case QUEEN -> getQueenMask(piecePos);
            case KNIGHT -> getKnightMask(piecePos);
            case KING -> getKingMask(piecePos);
            case BLANK -> 0;
        };
    }

    private long getPawnMask(Coordinate piecePos) {
        PieceColour colour = board.getPieceColour(piecePos);
        long emptySquares = board.getEmptySquares().getBoard();
        long enemyPieces = board.getAllColourPositions(colour.getOppositeColour()).getBoard();
        long pawnMask = 0L;
        long pos = piecePos.getBitboardValue();
        if (colour == PieceColour.BLACK) {
            pawnMask = ((pos >> 8) & emptySquares) | ((((pos & RANK_SEVEN) >> 16) & emptySquares) & (emptySquares >> 8));
            pawnMask |= BLACK_PAWN_ATTACKS[piecePos.getBitboardIndex()] & enemyPieces;
        }else if (colour == PieceColour.WHITE) {
            pawnMask = ((pos << 8) & emptySquares) | ((((pos & RANK_TWO) << 16) & emptySquares) & (emptySquares << 8));
            pawnMask |= WHITE_PAWN_ATTACKS[piecePos.getBitboardIndex()] & enemyPieces;
        }
        return pawnMask;
    }

    private long getKingMask(Coordinate piecePos) {
        Bitboard mask = new Bitboard(KING_ATTACKS[piecePos.getBitboardIndex()]);
        return removeFriendlyPieces(mask, board.getPieceColour(piecePos));
    }

    private long getKnightMask(Coordinate piecePos) {
        Bitboard mask = new Bitboard(KNIGHT_ATTACKS[piecePos.getBitboardIndex()]);
        return removeFriendlyPieces(mask, board.getPieceColour(piecePos));
    }

    private long removeFriendlyPieces(Bitboard mask, PieceColour colour){
        Bitboard friendlyPieces = board.getAllColourPositions(colour);
        mask.removeAll(friendlyPieces);
        return mask.getBoard();
    }


    private long getBishopMask(Coordinate piecePos) {
        return 0;
    }

    private long getRookMask(Coordinate piecePos) {
        return 0;
    }

    private long getQueenMask(Coordinate piecePos) {
        return 0;
    }

    static {
        long notAFile = 0xFEFEFEFEFEFEFEFEL;
        long notHFile = 0x7F7F7F7F7F7F7F7FL;
        long notABFile = 0xFCFCFCFCFCFCFCFCL;
        long notHGFile = 0x3F3F3F3F3F3F3F3FL;
        for (int square = 0; square < 64; square++) {
            long pos = 1L << square;

            long knightMoves = 0L;
            if ((pos & notHFile) != 0)
                knightMoves |= (pos << 6) | (pos >> 10);
            if ((pos & notAFile) != 0)
                knightMoves |= (pos << 10) | (pos >> 6);
            if ((pos & notHGFile) != 0)
                knightMoves |= (pos << 15) | (pos >> 17);
            if ((pos & notABFile) != 0)
                knightMoves |= (pos << 17) | (pos >> 15);
            KNIGHT_ATTACKS[square] = knightMoves;

            long kingMoves = 0L;
            if ((pos & notAFile) != 0)
                kingMoves |= (pos >> 1) | (pos >> 9) | (pos << 7);
            if ((pos & notHFile) != 0)
                kingMoves |= (pos >> 7) | (pos << 1) | (pos << 9);
            kingMoves |= (pos >> 8) | (pos << 8);
            KING_ATTACKS[square] = kingMoves;

            WHITE_PAWN_ATTACKS[square] = ((pos << 7) & notHFile) | ((pos << 9) & notAFile);
            BLACK_PAWN_ATTACKS[square] = ((pos >> 9) & notHFile) | ((pos >> 7) & notAFile);
        }
        }
    }
}
