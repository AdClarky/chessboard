public class MaskGenerator {
    private Chessboard board;
    private static final long[] KNIGHT_ATTACKS = new long[64];
    private static final long[] KING_ATTACKS = new long[64];

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
        return 0;
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

    private

    static {
        long notAFile = 0xFEFEFEFEFEFEFEFEL;
        long notHFile = 0x7F7F7F7F7F7F7F7FL;
        long notABFile = 0xFCFCFCFCFCFCFCFCL;
        long notHGFile = 0x3F3F3F3F3F3F3F3FL;
        for (int square = 0; square < 64; square++) {
            long knight = 1L << square;
            long attacks = 0L;
            if ((knight & notHFile) != 0)
                attacks |= (knight << 6) | (knight >> 10);
            if ((knight & notAFile) != 0)
                attacks |= (knight << 10) | (knight >> 6);
            if ((knight & notHGFile) != 0)
                attacks |= (knight << 15) | (knight >> 17);
            if ((knight & notABFile) != 0)
                attacks |= (knight << 17) | (knight >> 15);
            KNIGHT_ATTACKS[square] = attacks;
        }

        for (int square = 0; square < 64; square++) {
            long king = 1L << square;
            long attacks = 0L;
            if ((king & notAFile) != 0)
                attacks |= (king >> 1) | (king >> 9) | (king << 7);
            if ((king & notHFile) != 0)
                attacks |= (king >> 7) | (king << 1) | (king << 9);
            attacks |= (king >> 8) | (king << 8);
            KING_ATTACKS[square] = attacks;
        }
    }
}
