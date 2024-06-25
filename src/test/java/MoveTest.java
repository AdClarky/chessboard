import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MoveTest {
    Board board = new Board();

    @AfterEach
    void setUp() {
        board = new Board();
    }

    @Test
    void basicMove(){
        Move move = new Move(3, 3, board.getPiece(3, 1), board);
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
    }

    @Test
    void basicMoveUndo(){
        Move move = new Move(3, 3, board.getPiece(3, 1), board);
        move.undo();
        assertInstanceOf(Blank.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
    }

    @Test
    void basicMoveRedo(){
        Move move = new Move(3, 3, board.getPiece(3, 1), board);
        move.undo();
        move.makeMove();
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
    }

    @Test
    void takingMove(){
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), board);
        assertInstanceOf(Blank.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
        assertInstanceOf(Pawn.class, board.getPiece(4, 4));
        assertEquals(4, board.getPiece(4, 4).getX());
        assertEquals(4, board.getPiece(4, 4).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(4, 4).getDirection());
    }

    @Test
    void takingMoveUndo(){
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), board);
        pawnXD5.undo();
        int x = 3, y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 4; y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void takingMoveRedo(){
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), board);
        pawnXD5.undo();
        pawnXD5.makeMove();
        int x = 3, y = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        x = 4; y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void basicPromotion(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(2, board.getColourPieces(1).size());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void basicPromotionUndo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        assertEquals(2, board.getColourPieces(1).size());
        assertInstanceOf(Pawn.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
    }

    @Test
    void basicPromotionRedo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(2, board.getColourPieces(1).size());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void promotionTaking(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(1, board.getColourPieces(-1).size());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void promotionTakingUndo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(2, board.getColourPieces(-1).size());
        assertInstanceOf(Pawn.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void promotionTakingRedo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(1, board.getColourPieces(-1).size());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
    }

    @Test
    void castling(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), board);
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 1;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void castlingUndo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), board);
        shortCastle.undo();
        int x = 0, y = 0;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 3;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void castlingRedo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(1);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
        board = new Board(whitePieces, blackPieces);
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), board);
        shortCastle.undo();
        shortCastle.makeMove();
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 1;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void enPassant(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
        blackPieces.getLast().firstMove();
        board = new Board(whitePieces, blackPieces);
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), board);
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        y = 5;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void enPassantUndo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
        blackPieces.getLast().firstMove();
        board = new Board(whitePieces, blackPieces);
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), board);
        enPassant.undo();
        int x = 3, y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());
        y = 5;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(2, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void enPassantRedo(){
        ArrayList<Piece> whitePieces = new ArrayList<>(2);
        ArrayList<Piece> blackPieces = new ArrayList<>(2);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
        blackPieces.getLast().firstMove();
        board = new Board(whitePieces, blackPieces);
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), board);
        enPassant.undo();
        enPassant.makeMove();
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLANK_PIECE, board.getPiece(x, y).getDirection());
        y = 5;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }
}
