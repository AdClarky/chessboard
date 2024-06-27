import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.SequencedCollection;

class ChessUtilsTest {
    @Test
    void doCoordinatesMapCorrectly() {
        assertEquals("h1", ChessUtils.coordsToChess(0, 0));
        assertEquals("g2", ChessUtils.coordsToChess(1, 1));
        assertEquals("f3", ChessUtils.coordsToChess(2, 2));
        assertEquals("e4", ChessUtils.coordsToChess(3, 3));
        assertEquals("d5", ChessUtils.coordsToChess(4, 4));
        assertEquals("c6", ChessUtils.coordsToChess(5, 5));
        assertEquals("b7", ChessUtils.coordsToChess(6, 6));
        assertEquals("a8", ChessUtils.coordsToChess(7, 7));
        assertEquals("h8", ChessUtils.coordsToChess(0, 7));
        assertEquals("a1", ChessUtils.coordsToChess(7, 0));
        assertEquals("d8", ChessUtils.coordsToChess(4, 7));
    }

    @Test
    void dRookDisambiguated(){
        Rook dRook = new Rook(4,7, Piece.BLACK_PIECE);
        Rook hRook = new Rook(0,7, Piece.BLACK_PIECE);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(dRook);
        possiblePieces.add(hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "Rdf8");
        assertEquals(1, possiblePieces.size());
        assertEquals(dRook, possiblePieces.getFirst());
    }

    @Test
    void hRookDisambiguated(){
        Rook dRook = new Rook(4,7, Piece.BLACK_PIECE);
        Rook hRook = new Rook(0,7, Piece.BLACK_PIECE);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(dRook);
        possiblePieces.add(hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "Rhf8");
        assertEquals(1, possiblePieces.size());
        assertEquals(hRook, possiblePieces.getFirst());
    }

    @Test
    void RookA5Disambiguated() {
        Rook a1Rook = new Rook(7, 0, Piece.BLACK_PIECE);
        Rook a5hRook = new Rook(7, 4, Piece.BLACK_PIECE);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(a1Rook);
        possiblePieces.add(a5hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "R5a3");
        assertEquals(1, possiblePieces.size());
        assertEquals(a5hRook, possiblePieces.getFirst());
    }

    @Test
    void RookA1Disambiguated() {
        Rook a1Rook = new Rook(7, 0, Piece.BLACK_PIECE);
        Rook a5hRook = new Rook(7, 4, Piece.BLACK_PIECE);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(a1Rook);
        possiblePieces.add(a5hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "R1a3");
        assertEquals(1, possiblePieces.size());
        assertEquals(a1Rook, possiblePieces.getFirst());
    }

    @Test
    void queenDoubleDisambiguation(){
        Queen queenE4 = new Queen(3,3, Piece.BLACK_PIECE);
        Queen queenH4 = new Queen(0,3, Piece.BLACK_PIECE);
        Queen queenH1 = new Queen(0,0, Piece.BLACK_PIECE);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(queenH1);
        possiblePieces.add(queenH4);
        possiblePieces.add(queenE4);
        ChessUtils.disambiguatePiece(possiblePieces, "Qh4e1");
        assertEquals(1, possiblePieces.size());
        assertEquals(queenH4, possiblePieces.getFirst());
    }

    @Disabled
    void shortCastleWhite() {
    }

    @Disabled
    void longCastleWhite() {
    }

    @Disabled
    void shortCastleBlack() {
    }

    @Disabled
    void longCastleBlack() {
    }

    @Disabled
    void basicPawnMove() {
    }

    @Disabled
    void takingPawnMove() {
    }

    @Disabled
    void knightMove() {
    }
}