import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

class PieceTest {

    @Test
    void areAllPiecesDifferentHash(){
        Chessboard board = new Chessboard();
        int blankHash = new Blank(0,0).hashCode();
        int queenHash = new Queen(0,0, PieceColour.WHITE).hashCode();
        int pawnHash = new Pawn(0,0, PieceColour.WHITE).hashCode();
        int rookHash = new Rook(0,0, PieceColour.WHITE).hashCode();
        int knightHash = new Knight(0,0, PieceColour.WHITE).hashCode();
        int bishopHash = new Bishop(0,0, PieceColour.WHITE).hashCode();
        int kingHash = new King(0,0, PieceColour.WHITE).hashCode();
        int queenBlackHash = new Queen(0,0, PieceColour.BLACK).hashCode();
        int pawnBlackHash = new Pawn(0,0, PieceColour.BLACK).hashCode();
        int rookBlackHash = new Rook(0,0, PieceColour.BLACK).hashCode();
        int knightBlackHash = new Knight(0,0, PieceColour.BLACK).hashCode();
        int bishopBlackHash = new Bishop(0,0, PieceColour.BLACK).hashCode();
        int kingBlackHash = new King(0,0, PieceColour.BLACK).hashCode();
        Collection<Integer> hashes = new HashSet<>(13);
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
