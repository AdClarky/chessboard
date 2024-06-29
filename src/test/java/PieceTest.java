import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class PieceTest {

    @Test
    void areAllPiecesDifferentHash(){
        Chessboard board = new Chessboard();
        int blankHash = new Blank(0,0).hashCode();
        int queenHash = new Queen(0,0, PieceColour.WHITE, board).hashCode();
        int pawnHash = new Pawn(0,0, PieceColour.WHITE, board).hashCode();
        int rookHash = new Rook(0,0, PieceColour.WHITE, board).hashCode();
        int knightHash = new Knight(0,0, PieceColour.WHITE, board).hashCode();
        int bishopHash = new Bishop(0,0, PieceColour.WHITE, board).hashCode();
        int kingHash = new King(0,0, PieceColour.WHITE, board).hashCode();
        int queenBlackHash = new Queen(0,0, PieceColour.BLACK, board).hashCode();
        int pawnBlackHash = new Pawn(0,0, PieceColour.BLACK, board).hashCode();
        int rookBlackHash = new Rook(0,0, PieceColour.BLACK, board).hashCode();
        int knightBlackHash = new Knight(0,0, PieceColour.BLACK, board).hashCode();
        int bishopBlackHash = new Bishop(0,0, PieceColour.BLACK, board).hashCode();
        int kingBlackHash = new King(0,0, PieceColour.BLACK, board).hashCode();
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
