import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MoveWithFenTest {
    @Test
    void basicMove(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move move = new Move(3, 3, board.getPiece(3, 1), null, board);
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void basicMoveUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move move = new Move(3, 3, board.getPiece(3, 1), null, board);
        move.undo();
        assertInstanceOf(Blank.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void basicMoveRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move move = new Move(3, 3, board.getPiece(3, 1), null, board);
        move.undo();
        move.makeMove();
        assertInstanceOf(Blank.class, board.getPiece(3, 1));
        assertEquals(3, board.getPiece(3, 1).getX());
        assertEquals(1, board.getPiece(3, 1).getY());
        assertInstanceOf(Pawn.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void takingMove(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
        assertInstanceOf(Blank.class, board.getPiece(3, 3));
        assertEquals(3, board.getPiece(3, 3).getX());
        assertEquals(3, board.getPiece(3, 3).getY());
        assertInstanceOf(Pawn.class, board.getPiece(4, 4));
        assertEquals(4, board.getPiece(4, 4).getX());
        assertEquals(4, board.getPiece(4, 4).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(4, 4).getDirection());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(15, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void takingMoveUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
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

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void takingMoveUndoRedoRepeated(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
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
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 4; y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }


    @Test
    void takingMoveDoubleUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
        pawnXD5.undo();
        pawnD5.undo();
        int x = 3, y = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 3;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        x = 4; y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(16, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void takingMoveDoubleRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
        pawnXD5.undo();
        pawnD5.undo();
        pawnD5.makeMove();
        pawnXD5.makeMove();
        int x = 3, y = 1;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 3;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        x = 4; y = 4;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(15, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void takingMoveRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardDefaultSetup();
        Move pawnE4 = new Move(3, 3, board.getPiece(3, 1), null, board);
        Move pawnD5 = new Move(4, 4, board.getPiece(4, 6), pawnE4.getPiece(), board);
        Move pawnXD5 = new Move(4, 4, board.getPiece(3, 3), pawnD5.getPiece(), board);
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

        assertEquals(16, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(15, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void basicPromotion(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void basicPromotionUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());
        assertInstanceOf(Pawn.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void basicPromotionRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(4, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 4; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void promotionTaking(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(1, board.getColourPieces(-1).size());
    }

    @Test
    void promotionTakingUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        int x = 4, y = 6;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Pawn.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.BLACK_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(2, board.getColourPieces(-1).size());
    }

    @Test
    void promotionTakingRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("2p4k/3P4/8/8/8/8/8/7K w - - 0 1");
        Move pawnPromotion = new Move(5, 7, board.getPiece(4, 6), null, board);
        pawnPromotion.undo();
        pawnPromotion.makeMove();
        int x = 4, y = 6;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertInstanceOf(Queen.class, board.getColourPieces(Piece.WHITE_PIECE).getLast());
        x = 5; y = 7;
        assertInstanceOf(Queen.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(1).size());
        assertEquals(1, board.getColourPieces(-1).size());
    }

    @Test
    void castling(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
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
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void castlingUndo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
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
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
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
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("7k/8/8/8/8/8/8/4K2R w - - 0 1");
        Move shortCastle = new Move(1, 0, board.getPiece(3, 0), null, board);
        shortCastle.undo();
        shortCastle.makeMove();
        int x = 0, y = 0;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
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
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void enPassant(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
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
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
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
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(2, board.getColourPieces(Piece.BLACK_PIECE).size());
    }

    @Test
    void enPassantRedo(){
        Chessboard board = new ChessboardBuilder().createChessboardFromFen("4k3/8/8/4Pp2/8/8/8/4K3 w - f6 0 1");
        Move enPassant = new Move(2, 5, board.getPiece(3, 4), null, board);
        enPassant.undo();
        enPassant.makeMove();
        int x = 3, y = 4;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        x = 2;
        assertInstanceOf(Blank.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.EMPTY_PIECE, board.getPiece(x, y).getDirection());
        y = 5;
        assertInstanceOf(Pawn.class, board.getPiece(x, y));
        assertEquals(x, board.getPiece(x, y).getX());
        assertEquals(y, board.getPiece(x, y).getY());
        assertEquals(Piece.WHITE_PIECE, board.getPiece(x, y).getDirection());

        assertEquals(2, board.getColourPieces(Piece.WHITE_PIECE).size());
        assertEquals(1, board.getColourPieces(Piece.BLACK_PIECE).size());
    }
}
