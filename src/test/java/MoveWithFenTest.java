import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MoveWithFenTest {
    @Test
    void basicPromotion(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionUndo(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        assertInstanceOf(Pawn.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionRedo(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void promotionTaking(){
        Chessboard board = new ChessboardBuilder().FromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void promotionTakingUndo(){
        Chessboard board = new ChessboardBuilder().FromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Pawn.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLACK, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(2, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void promotionTakingRedo(){
        Chessboard board = new ChessboardBuilder().FromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getAllColourPieces(PieceColour.WHITE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void castling(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 1;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void castlingUndo(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
        shortCastle.undo();
        int x = 0, y = 0;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 3;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void castlingRedo(){
        Chessboard board = new ChessboardBuilder().FromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
        shortCastle.undo();
        shortCastle.makeMove();
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 1;
        assertInstanceOf(King.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Rook.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void enPassant(){
        Chessboard board = new ChessboardBuilder().FromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 5;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void enPassantUndo(){
        Chessboard board = new ChessboardBuilder().FromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
        enPassant.undo();
        int x = 3, y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLACK, board.getPiece(x, y).getColour());
        y = 5;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(2, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void enPassantRedo(){
        Chessboard board = new ChessboardBuilder().FromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
        enPassant.undo();
        enPassant.makeMove();
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 5;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }
}
