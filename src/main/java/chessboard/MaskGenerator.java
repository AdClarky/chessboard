package chessboard;

public class MaskGenerator {
    private final Chessboard board;
    private final static long RANK_TWO = 0xff00L;
    private final static long RANK_SEVEN = 0xff000000000000L;
    private final static long NOT_A_FILE = 0xFEFEFEFEFEFEFEFEL;
    private final static long NOT_H_FILE = 0x7F7F7F7F7F7F7F7FL;
    private final static long KING_LONG_CASTLING_WHITE = 0x00000000000000eL;
    private final static long KING_SHORT_CASTLING_WHITE = 0x0000000000000060L;
    private final static long KING_LONG_CASTLING_BLACK = 0xe00000000000000L;
    private final static long KING_SHORT_CASTLING_BLACK = 0x6000000000000000L;
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
            case Pieces.PAWN -> getPawnMask(piecePos);
            case Pieces.BISHOP -> getBishopMask(piecePos);
            case Pieces.ROOK -> getRookMask(piecePos);
            case Pieces.QUEEN -> getQueenMask(piecePos);
            case Pieces.KNIGHT -> getKnightMask(piecePos);
            case Pieces.KING -> getKingMask(piecePos);
            case Pieces.BLANK -> 0;
        };
    }

    private long getPawnMask(Coordinate piecePos) {
        PieceColour colour = board.getColour(piecePos);
        long emptySquares = board.getEmptySquares().getBoard();
        long enemyPieces = board.getAllColourPositions(colour.invert()).getBoard();
        long pawnMask = 0L;
        long pos = piecePos.getBitboardValue();
        Coordinate enPassant = board.getEnPassantSquare();
        if (colour == PieceColour.BLACK) {
            pawnMask = ((pos >>> 8) & emptySquares) | ((((pos & RANK_SEVEN) >>> 16) & emptySquares) & (emptySquares >>> 8));
            pawnMask |= BLACK_PAWN_ATTACKS[piecePos.getBitboardIndex()] & enemyPieces;
            if(enPassant != null && piecePos.y() == enPassant.y() && Math.abs(enPassant.x() - piecePos.x()) == 1)
                pawnMask |= enPassant.getBitboardValue() >> 8;
        }else {
            pawnMask = ((pos << 8) & emptySquares) | ((((pos & RANK_TWO) << 16) & emptySquares) & (emptySquares << 8));
            pawnMask |= WHITE_PAWN_ATTACKS[piecePos.getBitboardIndex()] & enemyPieces;
            if(enPassant != null && piecePos.y() == enPassant.y() && Math.abs(enPassant.x() - piecePos.x()) == 1)
                pawnMask |= enPassant.getBitboardValue() << 8;
        }

        return pawnMask;
    }

    private long getKingMask(Coordinate piecePos) {
        Bitboard mask = new Bitboard(KING_ATTACKS[piecePos.getBitboardIndex()]);
        Bitboard castlingRights = new Bitboard(board.getCastlingRights());
        if(castlingRights.contains(piecePos)){
            long blankPieces = board.getEmptySquares().getBoard();
            if(castlingRights.contains(new Coordinate(0, piecePos.y())) &&
                ((piecePos.y() == 7 && (blankPieces & KING_LONG_CASTLING_BLACK) == KING_LONG_CASTLING_BLACK) ||
                        (piecePos.y() == 0 && (blankPieces & KING_LONG_CASTLING_WHITE) == KING_LONG_CASTLING_WHITE))){
                mask.add(new Coordinate(2, piecePos.y()));
            }
            if(castlingRights.contains(new Coordinate(7, piecePos.y())) &&
                    ((piecePos.y() == 7 && (blankPieces & KING_SHORT_CASTLING_BLACK) == KING_SHORT_CASTLING_BLACK) ||
                            (piecePos.y() == 0 && (blankPieces & KING_SHORT_CASTLING_WHITE) == KING_SHORT_CASTLING_WHITE))){
                mask.add(new Coordinate(6, piecePos.y()));
            }
        }
        return removeFriendlyPieces(mask, board.getColour(piecePos));
    }

    private long getKnightMask(Coordinate piecePos) {
        Bitboard mask = new Bitboard(KNIGHT_ATTACKS[piecePos.getBitboardIndex()]);
        return removeFriendlyPieces(mask, board.getColour(piecePos));
    }

    private long removeFriendlyPieces(Bitboard mask, PieceColour colour){
        Bitboard friendlyPieces = board.getAllColourPositions(colour);
        mask.removeAll(friendlyPieces);
        return mask.getBoard();
    }

    private long getBishopMask(Coordinate piecePos) {
        long mask = 0L;
        mask |= getMaskForLine(piecePos, 7);
        mask |= getMaskForLine(piecePos, 9);
        mask |= getMaskForLine(piecePos, -7);
        mask |= getMaskForLine(piecePos, -9);
        return mask;
    }

    private long getRookMask(Coordinate piecePos) {
        long mask = 0L;
        mask |= getMaskForLine(piecePos, 8);
        mask |= getMaskForLine(piecePos, -8);
        mask |= getMaskForLine(piecePos, 1);
        mask |= getMaskForLine(piecePos, -1);
        return mask;
    }

    private long getQueenMask(Coordinate piecePos) {
        long mask = 0L;
        mask |= getMaskForLine(piecePos, 8);
        mask |= getMaskForLine(piecePos, -8);
        mask |= getMaskForLine(piecePos, 1);
        mask |= getMaskForLine(piecePos, -1);
        mask |= getMaskForLine(piecePos, 7);
        mask |= getMaskForLine(piecePos, 9);
        mask |= getMaskForLine(piecePos, -7);
        mask |= getMaskForLine(piecePos, -9);
        return mask;
    }

    // top left - << 7
    // up - << 8
    // top right - << 9
    // right - << 1
    // left - >> -1
    // bottom left - >> -9
    // down - >> -8
    // bottom right - >> -7
    private long getMaskForLine(Coordinate piecePos, int direction) {
        PieceColour colour = board.getColour(piecePos);
        long enemyPieces = board.getAllColourPositions(colour.invert()).getBoard();
        long friendlyPieces = board.getAllColourPositions(colour).getBoard();
        long currentPos = piecePos.getBitboardValue();
        long mask = 0L;

        while(currentPos != 0) {
            switch(direction) {
                case 7: // top left
                    if((currentPos & NOT_A_FILE) == 0) return mask;
                    currentPos <<= 7;
                    break;
                case 8: // up
                    currentPos <<= 8;
                    break;
                case 9: // top right
                    if((currentPos & NOT_H_FILE) == 0) return mask;
                    currentPos <<= 9;
                    break;
                case 1: // right
                    if((currentPos & NOT_H_FILE) == 0) return mask;
                    currentPos <<= 1;
                    break;
                case -1: // left
                    if((currentPos & NOT_A_FILE) == 0) return mask;
                    currentPos >>>= 1;
                    break;
                case -9: // bottom left
                    if((currentPos & NOT_A_FILE) == 0) return mask;
                    currentPos >>>= 9;
                    break;
                case -8: // down
                    currentPos >>>= 8;
                    break;
                case -7: // bottom right
                    if((currentPos & NOT_H_FILE) == 0) return mask;
                    currentPos >>>= 7;
                    break;
            }
            if(currentPos == 0)
                return mask;
            if((currentPos & friendlyPieces) != 0)
                return mask;
            mask |= currentPos;
            if((currentPos & enemyPieces) != 0)
                return mask;
        }

        return mask;
    }

    static {
        long notABFile = 0xFCFCFCFCFCFCFCFCL;
        long notHGFile = 0x3F3F3F3F3F3F3F3FL;
        for (int square = 0; square < 64; square++) {
            long pos = 1L << square;

            long knightMoves = 0L;
            if ((pos & notHGFile) != 0)
                knightMoves |= (pos >>> 6) | (pos << 10);
            if ((pos & notABFile) != 0)
                knightMoves |= (pos >>> 10) | (pos << 6);
            if ((pos & NOT_H_FILE) != 0)
                knightMoves |= (pos >>> 15) | (pos << 17);
            if ((pos & NOT_A_FILE) != 0)
                knightMoves |= (pos >>> 17) | (pos << 15);
            KNIGHT_ATTACKS[square] = knightMoves;

            long kingMoves = 0L;
            if ((pos & NOT_A_FILE) != 0)
                kingMoves |= (pos >>> 1) | (pos >>> 9) | (pos << 7);
            if ((pos & NOT_H_FILE) != 0)
                kingMoves |= (pos >>> 7) | (pos << 1) | (pos << 9);
            kingMoves |= (pos >>> 8) | (pos << 8);
            KING_ATTACKS[square] = kingMoves;

            WHITE_PAWN_ATTACKS[square] = ((pos << 7) & NOT_H_FILE) | ((pos << 9) & NOT_A_FILE);
            BLACK_PAWN_ATTACKS[square] = ((pos >>> 9) & NOT_H_FILE) | ((pos >>> 7) & NOT_A_FILE);
        }
    }
}
