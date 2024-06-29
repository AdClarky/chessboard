import org.junit.jupiter.api.Test;
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
        Chessboard board = new Chessboard();
        Rook dRook = new Rook(4,7, PieceColour.BLACK, board);
        Rook hRook = new Rook(0,7, PieceColour.BLACK, board);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(dRook);
        possiblePieces.add(hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "Rdf8");
        assertEquals(1, possiblePieces.size());
        assertEquals(dRook, possiblePieces.getFirst());
    }

    @Test
    void hRookDisambiguated(){
        Chessboard board = new Chessboard();
        Rook dRook = new Rook(4,7, PieceColour.BLACK, board);
        Rook hRook = new Rook(0,7, PieceColour.BLACK, board);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(dRook);
        possiblePieces.add(hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "Rhf8");
        assertEquals(1, possiblePieces.size());
        assertEquals(hRook, possiblePieces.getFirst());
    }

    @Test
    void RookA5Disambiguated() {
        Chessboard board = new Chessboard();
        Rook a1Rook = new Rook(7, 0, PieceColour.BLACK, board);
        Rook a5hRook = new Rook(7, 4, PieceColour.BLACK, board);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(a1Rook);
        possiblePieces.add(a5hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "R5a3");
        assertEquals(1, possiblePieces.size());
        assertEquals(a5hRook, possiblePieces.getFirst());
    }

    @Test
    void RookA1Disambiguated() {
        Chessboard board = new Chessboard();
        Rook a1Rook = new Rook(7, 0, PieceColour.BLACK, board);
        Rook a5hRook = new Rook(7, 4, PieceColour.BLACK, board);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(a1Rook);
        possiblePieces.add(a5hRook);
        ChessUtils.disambiguatePiece(possiblePieces, "R1a3");
        assertEquals(1, possiblePieces.size());
        assertEquals(a1Rook, possiblePieces.getFirst());
    }

    @Test
    void queenDoubleDisambiguation(){
        Chessboard board = new Chessboard();
        Queen queenE4 = new Queen(3,3, PieceColour.BLACK, board);
        Queen queenH4 = new Queen(0,3, PieceColour.BLACK, board);
        Queen queenH1 = new Queen(0,0, PieceColour.BLACK, board);
        SequencedCollection<Piece> possiblePieces = new ArrayList<>(2);
        possiblePieces.add(queenH1);
        possiblePieces.add(queenH4);
        possiblePieces.add(queenE4);
        ChessUtils.disambiguatePiece(possiblePieces, "Qh4e1");
        assertEquals(1, possiblePieces.size());
        assertEquals(queenH4, possiblePieces.getFirst());
    }

    @Test
    void shortCastleWhite() {
        ChessGame game = new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        MoveValue move = ChessUtils.chessToMove("O-O", game);
        assertEquals(new MoveValue(game.getPiece(3,0), 1, 0), move);
    }

    @Test
    void longCastleWhite() {
        ChessGame game = new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        MoveValue move = ChessUtils.chessToMove("O-O-O", game);
        assertEquals(new MoveValue(game.getPiece(3,0), 5, 0), move);
    }

    @Test
    void shortCastleBlack() {
        ChessGame game = new ChessGame("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b kq - 0 1");
        MoveValue move = ChessUtils.chessToMove("O-O", game);
        assertEquals(new MoveValue(game.getPiece(3,7), 1, 7), move);
    }

    @Test
    void longCastleBlack() {
        ChessGame game = new ChessGame("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b kq - 0 1");
        MoveValue move = ChessUtils.chessToMove("O-O-O", game);
        assertEquals(new MoveValue(game.getPiece(3,7), 5, 7), move);
    }

    @Test
    void basicPawnMove() {
        ChessGame game = new ChessGame("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w kq - 0 1");
        MoveValue move = ChessUtils.chessToMove("e4", game);
        assertEquals(new MoveValue(game.getPiece(3,1), 3, 3), move);
    }

    @Test
    void takingPawnMove() {
        ChessGame game = new ChessGame("r3k2r/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/R3K2R w kq - 0 1");
        MoveValue move = ChessUtils.chessToMove("exd5", game);
        assertEquals(new MoveValue(game.getPiece(3,3), 4, 4), move);
    }

    @Test
    void knightMove() {
        ChessGame game = new ChessGame();
        MoveValue move = ChessUtils.chessToMove("Nf3", game);
        assertEquals(new MoveValue(game.getPiece(1,0), 2, 2), move);
    }
}