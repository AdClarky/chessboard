import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class PieceTest {

    @Test
    void areAllPiecesDifferentHash(){
        int blankHash = new Blank(0,0).hashCode();
        int queenHash = new Queen(0,0, Piece.WHITE_PIECE).hashCode();
        int pawnHash = new Pawn(0,0, Piece.WHITE_PIECE).hashCode();
        int rookHash = new Rook(0,0, Piece.WHITE_PIECE).hashCode();
        int knightHash = new Knight(0,0, Piece.WHITE_PIECE).hashCode();
        int bishopHash = new Bishop(0,0, Piece.WHITE_PIECE).hashCode();
        int kingHash = new King(0,0, Piece.WHITE_PIECE).hashCode();
        int queenBlackHash = new Queen(0,0, Piece.BLACK_PIECE).hashCode();
        int pawnBlackHash = new Pawn(0,0, Piece.BLACK_PIECE).hashCode();
        int rookBlackHash = new Rook(0,0, Piece.BLACK_PIECE).hashCode();
        int knightBlackHash = new Knight(0,0, Piece.BLACK_PIECE).hashCode();
        int bishopBlackHash = new Bishop(0,0, Piece.BLACK_PIECE).hashCode();
        int kingBlackHash = new King(0,0, Piece.BLACK_PIECE).hashCode();
        Set<Integer> hashes = new HashSet<>(13);
        hashes.add(blankHash);
        hashes.add(queenHash);
        hashes.add(pawnHash);
        hashes.add(rookHash);
        hashes.add(knightHash);
        hashes.add(bishopHash);
        hashes.add(kingHash);
        hashes.add(queenBlackHash);
        hashes.add(pawnBlackHash);
        hashes.add(rookBlackHash);
        hashes.add(knightBlackHash);
        hashes.add(bishopBlackHash);
        hashes.add(kingBlackHash);
        assertEquals(13, hashes.size());
    }
}
