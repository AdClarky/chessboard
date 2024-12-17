import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MoveTest {
    private Chessboard board = new ChessboardBuilder().defaultSetup();
    Coordinate a1 = new Coordinate(0, 0);
    Coordinate c1 = new Coordinate(2, 0);
    Coordinate c8 = new Coordinate(2, 7);
    Coordinate d1 = new Coordinate(3, 0);
    Coordinate d4 = new Coordinate(3, 3);
    Coordinate d5 = new Coordinate(3, 4);
    Coordinate d7 = new Coordinate(3, 6);
    Coordinate d8 = new Coordinate(3, 7);
    Coordinate e1 = new Coordinate(4, 0);
    Coordinate e2 = new Coordinate(4,  1);
    Coordinate e4 = new Coordinate(4,  3);
    Coordinate e5 = new Coordinate(4, 4);
    Coordinate e6 = new Coordinate(4,  5);

    @AfterEach
    void setUp() {
        board = new ChessboardBuilder().defaultSetup();
    }

    @Test
    void basicMove(){
        new Move(board, e2, e4);
        assertEquals(Pieces.BLANK, board.getPiece(e2));
        assertEquals(Pieces.PAWN, board.getPiece(e4));
        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
        assertEquals(PieceColour.BLACK, board.getTurn());
    }

    @Test
    void basicMoveUndo(){
        Move move = new Move(board, e2, e4);
        move.undo();
        assertEquals(Pieces.BLANK, board.getPiece(e4));
        assertEquals(Pieces.PAWN, board.getPiece(e2));
        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
        assertEquals(PieceColour.WHITE, board.getTurn());
    }

    @Test
    void basicMoveRedo(){
        Move move = new Move(board, e2, e4);
        move.undo();
        move.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(e2));
        assertEquals(Pieces.PAWN, board.getPiece(e4));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
        assertEquals(PieceColour.BLACK, board.getTurn());
    }

    @Test
    void takingMove(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        new Move(board, e4, d5);
        assertEquals(Pieces.BLANK, board.getPiece(e4));
        assertEquals(Pieces.PAWN, board.getPiece(d5));
        assertEquals(PieceColour.WHITE, board.getColour(d5));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveUndo(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        Move pawnXD5 = new Move(board, e4, d5);
        pawnXD5.undo();
        assertEquals(Pieces.PAWN, board.getPiece(d4));
        assertEquals(PieceColour.WHITE, board.getColour(d4));
        assertEquals(Pieces.PAWN, board.getPiece(d5));
        assertEquals(PieceColour.BLACK, board.getColour(d5));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveUndoRedoRepeated(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        Move pawnXD5 = new Move(board, e4, d5);
        pawnXD5.undo();
        pawnD5.undo();
        pawnD5.makeMove();
        pawnD5.undo();
        pawnD5.makeMove();
        pawnXD5.makeMove();
        pawnXD5.undo();
        pawnXD5.makeMove();
        pawnXD5.undo();
        pawnD5.undo();
        assertEquals(Pieces.BLANK, board.getPiece(e2));
        assertEquals(PieceColour.BLANK, board.getColour(e2));
        assertEquals(Pieces.PAWN, board.getPiece(e4));
        assertEquals(PieceColour.WHITE, board.getColour(e4));
        assertEquals(Pieces.BLANK, board.getPiece(d5));
        assertEquals(PieceColour.BLANK, board.getColour(d5));
        assertEquals(Pieces.PAWN, board.getPiece(d7));
        assertEquals(PieceColour.BLACK, board.getColour(d7));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
    }


    @Test
    void takingMoveDoubleUndo(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        Move pawnXD5 = new Move(board, e4, d5);
        pawnXD5.undo();
        pawnD5.undo();
        assertEquals(Pieces.BLANK, board.getPiece(e2));
        assertEquals(PieceColour.BLANK, board.getColour(e2));
        assertEquals(Pieces.PAWN, board.getPiece(e4));
        assertEquals(PieceColour.WHITE, board.getColour(e4));
        assertEquals(Pieces.BLANK, board.getPiece(d5));
        assertEquals(PieceColour.BLANK, board.getColour(d5));
        assertEquals(Pieces.PAWN, board.getPiece(d7));
        assertEquals(PieceColour.BLACK, board.getColour(d7));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveDoubleRedo(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        Move pawnXD5 = new Move(board, e4, d5);
        pawnXD5.undo();
        pawnD5.undo();
        pawnD5.makeMove();
        pawnXD5.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(e2));
        assertEquals(PieceColour.BLANK, board.getColour(e2));
        assertEquals(Pieces.BLANK, board.getPiece(e4));
        assertEquals(PieceColour.BLANK, board.getColour(e4));
        assertEquals(Pieces.PAWN, board.getPiece(d5));
        assertEquals(PieceColour.WHITE, board.getColour(d5));
        assertEquals(Pieces.BLANK, board.getPiece(d7));
        assertEquals(PieceColour.BLANK, board.getColour(d7));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveRedo(){
        Move pawnE4 = new Move(board, e2, e4);
        Move pawnD5 = new Move(board, d7, d5);
        Move pawnXD5 = new Move(board, e4, d5);
        pawnXD5.undo();
        pawnXD5.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(e4));
        assertEquals(Pieces.PAWN, board.getPiece(d5));
        assertEquals(PieceColour.WHITE, board.getColour(d5));

        assertEquals(16, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotion(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board, d7, new Coordinate(4, 7));
        assertEquals(Pieces.BLANK, board.getPiece(d7));
        assertEquals(Pieces.QUEEN, board.getPiece(d8));
        assertEquals(PieceColour.WHITE, board.getColour(d8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionUndo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board, d7, new Coordinate(4, 7));
        pawnPromotion.undo();
        assertEquals(Pieces.PAWN, board.getPiece(d7));
        assertEquals(PieceColour.WHITE, board.getColour(d7));
        assertEquals(Pieces.BLANK, board.getPiece(d8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionRedo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board, d7, new Coordinate(4, 7));
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(d7));
        assertEquals(Pieces.QUEEN, board.getPiece(d8));
        assertEquals(PieceColour.WHITE, board.getColour(d8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void promotionTaking(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        new Move(board, d7, c8);
        assertEquals(Pieces.BLANK, board.getPiece(d7));
        assertEquals(Pieces.QUEEN, board.getPiece(c8));
        assertEquals(PieceColour.WHITE, board.getColour(c8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void promotionTakingUndo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board, d7, c8);
        pawnPromotion.undo();
        assertEquals(Pieces.PAWN, board.getPiece(d7));
        assertEquals(Pieces.PAWN, board.getPiece(c8));
        assertEquals(PieceColour.BLACK, board.getColour(c8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(2, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void promotionTakingRedo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board, d7, c8);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(d7));
        assertEquals(Pieces.QUEEN, board.getPiece(c8));
        assertEquals(PieceColour.WHITE, board.getColour(c8));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void castling(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/8/8/8/8/8/8/R3K3 w Q - 0 1"));
        new Move(board, e1, c1);
        assertEquals(Pieces.BLANK, board.getPiece(a1));
        assertEquals(PieceColour.BLANK, board.getColour(a1));
        assertEquals(Pieces.KING, board.getPiece(c1));
        assertEquals(PieceColour.WHITE, board.getColour(c1));
        assertEquals(Pieces.ROOK, board.getPiece(d1));
        assertEquals(PieceColour.WHITE, board.getColour(d1));
        assertEquals(Pieces.BLANK, board.getPiece(e1));
        assertEquals(PieceColour.BLANK, board.getColour(e1));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void castlingUndo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/8/8/8/8/8/8/R3K3 w Q - 0 1"));
        Move shortCastle = new Move(board, e1, c1);
        shortCastle.undo();
        assertEquals(Pieces.ROOK, board.getPiece(a1));
        assertEquals(PieceColour.WHITE, board.getColour(a1));
        assertEquals(Pieces.BLANK, board.getPiece(c1));
        assertEquals(PieceColour.BLANK, board.getColour(c1));
        assertEquals(Pieces.BLANK, board.getPiece(d1));
        assertEquals(PieceColour.BLANK, board.getColour(d1));
        assertEquals(Pieces.KING, board.getPiece(e1));
        assertEquals(PieceColour.WHITE, board.getColour(e1));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void castlingRedo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/8/8/8/8/8/8/R3K3 w Q - 0 1"));
        Move shortCastle = new Move(board, e1, c1);
        shortCastle.undo();
        shortCastle.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(a1));
        assertEquals(PieceColour.BLANK, board.getColour(a1));
        assertEquals(Pieces.KING, board.getPiece(c1));
        assertEquals(PieceColour.WHITE, board.getColour(c1));
        assertEquals(Pieces.ROOK, board.getPiece(d1));
        assertEquals(PieceColour.WHITE, board.getColour(d1));
        assertEquals(Pieces.BLANK, board.getPiece(e1));
        assertEquals(PieceColour.BLANK, board.getColour(e1));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void enPassant(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("4k3/8/8/3Pp3/8/8/8/4K3 w - e6 0 2"));
        new Move(board, d5, e6);
        assertEquals(Pieces.BLANK, board.getPiece(d5));
        assertEquals(PieceColour.BLANK, board.getColour(d5));
        assertEquals(Pieces.BLANK, board.getPiece(e5));
        assertEquals(PieceColour.BLANK, board.getColour(e5));
        assertEquals(Pieces.PAWN, board.getPiece(e6));
        assertEquals(PieceColour.WHITE, board.getColour(e6));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void enPassantUndo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("4k3/8/8/3Pp3/8/8/8/4K3 w - e6 0 2"));
        Move enPassant = new Move(board, d5, e6);
        enPassant.undo();
        assertEquals(Pieces.PAWN, board.getPiece(d5));
        assertEquals(PieceColour.WHITE, board.getColour(d5));
        assertEquals(Pieces.PAWN, board.getPiece(e5));
        assertEquals(PieceColour.BLACK, board.getColour(e5));
        assertEquals(Pieces.BLANK, board.getPiece(e6));
        assertEquals(PieceColour.BLANK, board.getColour(e6));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(2, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void enPassantRedo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("4k3/8/8/3Pp3/8/8/8/4K3 w - e6 0 2"));
        Move enPassant = new Move(board, d5, e6);
        enPassant.undo();
        enPassant.makeMove();
        assertEquals(Pieces.BLANK, board.getPiece(d5));
        assertEquals(PieceColour.BLANK, board.getColour(d5));
        assertEquals(Pieces.BLANK, board.getPiece(e5));
        assertEquals(PieceColour.BLANK, board.getColour(e5));
        assertEquals(Pieces.PAWN, board.getPiece(e6));
        assertEquals(PieceColour.WHITE, board.getColour(e6));

        assertEquals(2, board.getAllColourPositions(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPositions(PieceColour.BLACK).size());
    }

    @Test
    void doesCastlingGoBackAfterUndo() {
        ChessInterface game = assertDoesNotThrow(()->new ChessInterface("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1"));
        assertDoesNotThrow(()->game.makeMove("Kd1"));
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R2K1B1R b kq - 1 1", game.getFenString());
        game.undoMove();
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1", game.getFenString());
    }

    @Test
    void doesMovingChangeCastlingRights() {
        ChessInterface game = assertDoesNotThrow(()->new ChessInterface("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1"));
        assertDoesNotThrow(()->game.makeMove("Kd1"));
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R2K1B1R b kq - 1 1", game.getFenString());
    }
}
