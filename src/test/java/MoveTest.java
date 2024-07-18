import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SequencedCollection;

class MoveTest {
    private Chessboard board = new ChessboardBuilder().defaultSetup();

    @AfterEach
    void setUp() {
        board = new ChessboardBuilder().defaultSetup();
    }

    @Test
    void basicMove(){
        Coordinate e4 = new Coordinate(3,  3);
        new Move(board.getPiece(3, 1), e4, board);
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(e4));
        assertEquals(3, board.getPiece(e4).getX());
        assertEquals(3, board.getPiece(e4).getY());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
        assertEquals(PieceColour.BLACK, board.getCurrentTurn());
    }

    @Test
    void basicMoveUndo(){
        Coordinate e4 = new Coordinate(3, 3);
        Move move = new Move(board.getPiece(3, 1), e4, board);
        move.undo();
        assertInstanceOf(Blank.class, board.getPiece(e4));
        assertEquals(3, board.getPiece(e4).getX());
        assertEquals(3, board.getPiece(e4).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
        assertEquals(PieceColour.WHITE, board.getCurrentTurn());
    }

    @Test
    void basicMoveRedo(){
        Coordinate e4 = new Coordinate(3, 3);
        Move move = new Move(board.getPiece(3, 1), e4, board);
        move.undo();
        move.makeMove();
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(e4));
        assertEquals(3, board.getPiece(e4).getX());
        assertEquals(3, board.getPiece(e4).getY());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
        assertEquals(PieceColour.BLACK, board.getCurrentTurn());
    }

    @Test
    void takingMove(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        new Move(board.getPiece(e4), d5, board);
        assertInstanceOf(Blank.class, board.getPiece(e4));
        assertEquals(3, board.getPiece(e4).getX());
        assertEquals(3, board.getPiece(e4).getY());
        assertInstanceOf(Pawn.class, board.getPiece(d5));
        assertEquals(4, board.getPiece(d5).getX());
        assertEquals(4, board.getPiece(d5).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(d5).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveUndo(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        Move pawnXD5 = new Move(board.getPiece(e4), d5, board);
        pawnXD5.undo();
        int x = 3, y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 4; y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLACK, board.getPiece(x, y).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveUndoRedoRepeated(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        Move pawnXD5 = new Move(board.getPiece(e4), d5, board);
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
        int x = 3, y = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 4; y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLACK, board.getPiece(x, y).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
    }


    @Test
    void takingMoveDoubleUndo(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        Move pawnXD5 = new Move(board.getPiece(e4), d5, board);
        pawnXD5.undo();
        pawnD5.undo();
        int x = 3, y = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        x = 4; y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLACK, board.getPiece(x, y).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(16, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveDoubleRedo(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        Move pawnXD5 = new Move(board.getPiece(e4), d5, board);
        pawnXD5.undo();
        pawnD5.undo();
        pawnD5.makeMove();
        pawnXD5.makeMove();
        int x = 3, y = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        y = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());
        x = 4; y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.BLANK, board.getPiece(x, y).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void takingMoveRedo(){
        Coordinate e4 = new Coordinate(3, 3);
        Coordinate d5 = new Coordinate(4, 4);
        Move pawnE4 = new Move(board.getPiece(3, 1), e4, board);
        Move pawnD5 = new Move(board.getPiece(4, 6), d5, board);
        Move pawnXD5 = new Move(board.getPiece(e4), d5, board);
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
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(16, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(15, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotion(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        new Move(board.getPiece(4, 6), new Coordinate(4, 7), board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionUndo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board.getPiece(4, 6), new Coordinate(4, 7), board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());
        y = 7;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void basicPromotionRedo(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board.getPiece(4, 6), new Coordinate(4, 7), board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(PieceColour.WHITE, board.getPiece(x, y).getColour());

        assertEquals(2, board.getAllColourPieces(PieceColour.WHITE).size());
        assertEquals(1, board.getAllColourPieces(PieceColour.BLACK).size());
    }

    @Test
    void promotionTaking(){
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        new Move(board.getPiece(4, 6), new Coordinate(5, 7), board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
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
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board.getPiece(4, 6), new Coordinate(5, 7), board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
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
        board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1"));
        Move pawnPromotion = new Move(board.getPiece(4, 6), new Coordinate(5, 7), board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        Collection<Piece> blackPieces = new ArrayList<>(1);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Rook(new Coordinate(0, 0), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(0, 7), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        new Move(board.getPiece(3, 0), new Coordinate(1, 0), board);
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        Collection<Piece> blackPieces = new ArrayList<>(1);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Rook(new Coordinate(0, 0), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(0, 7), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        Move shortCastle = new Move(board.getPiece(3, 0), new Coordinate(1, 0), board);
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        Collection<Piece> blackPieces = new ArrayList<>(1);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Rook(new Coordinate(0, 0), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(0, 7), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        Move shortCastle = new Move(board.getPiece(3, 0), new Coordinate(1, 0), board);
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        SequencedCollection<Piece> blackPieces = new ArrayList<>(2);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Pawn(new Coordinate(3, 4), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(3, 7), PieceColour.BLACK));
        blackPieces.add(new Pawn(new Coordinate(2, 4), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        new Move(board.getPiece(3, 4), new Coordinate(2, 5), board);
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        SequencedCollection<Piece> blackPieces = new ArrayList<>(2);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Pawn(new Coordinate(3, 4), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(3, 7), PieceColour.BLACK));
        blackPieces.add(new Pawn(new Coordinate(2, 4), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        Move enPassant = new Move(board.getPiece(3, 4), new Coordinate(2, 5), board);
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
        Collection<Piece> whitePieces = new ArrayList<>(2);
        SequencedCollection<Piece> blackPieces = new ArrayList<>(2);
        board = new Chessboard();
        whitePieces.add(new King(new Coordinate(3, 0), PieceColour.WHITE));
        whitePieces.add(new Pawn(new Coordinate(3, 4), PieceColour.WHITE));
        blackPieces.add(new King(new Coordinate(3, 7), PieceColour.BLACK));
        blackPieces.add(new Pawn(new Coordinate(2, 4), PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        Move enPassant = new Move(board.getPiece(3, 4), new Coordinate(2, 5), board);
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

    @Test
    void doesCastlingGoBackAfterUndo() {
        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1"));
        assertDoesNotThrow(()->game.makeMove("Kd1"));
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R2K1B1R b kq - 1 1", game.getFenString());
        game.undoMove();
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1", game.getFenString());
    }

    @Test
    void doesMovingChangeCastlingRights() {
        ChessGame game = assertDoesNotThrow(()->new ChessGame("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R3KB1R w Qkq - 0 1"));
        assertDoesNotThrow(()->game.makeMove("Kd1"));
        assertEquals("rnbqkb1r/pppppppp/8/5n2/8/6N1/PPPPPPPP/R2K1B1R b kq - 1 1", game.getFenString());
    }
}
