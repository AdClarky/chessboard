//import org.junit.jupiter.api.AfterEach;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//class MoveTest {
//    ChessGame chessGame = new ChessGame();
//
//    @AfterEach
//    void setUp() {
//        chessGame = new ChessGame();
//    }
//
//    @Test
//    void basicMove(){
////        Move move = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
////        assertInstanceOf(Blank.class, chessGame.getPiece(3, 1));
////        assertEquals(3, chessGame.getPiece(3, 1).getX());
////        assertEquals(1, chessGame.getPiece(3, 1).getY());
////        assertInstanceOf(Pawn.class, chessGame.getPiece(3, 3));
////        assertEquals(3, chessGame.getPiece(3, 3).getX());
////        assertEquals(3, chessGame.getPiece(3, 3).getY());
////
////        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
////        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void basicMoveUndo(){
//        Move move = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        move.undo();
//        assertInstanceOf(Blank.class, chessGame.getPiece(3, 3));
//        assertEquals(3, chessGame.getPiece(3, 3).getX());
//        assertEquals(3, chessGame.getPiece(3, 3).getY());
//        assertInstanceOf(Pawn.class, chessGame.getPiece(3, 1));
//        assertEquals(3, chessGame.getPiece(3, 1).getX());
//        assertEquals(1, chessGame.getPiece(3, 1).getY());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void basicMoveRedo(){
//        Move move = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        move.undo();
//        move.makeMove();
//        assertInstanceOf(Blank.class, chessGame.getPiece(3, 1));
//        assertEquals(3, chessGame.getPiece(3, 1).getX());
//        assertEquals(1, chessGame.getPiece(3, 1).getY());
//        assertInstanceOf(Pawn.class, chessGame.getPiece(3, 3));
//        assertEquals(3, chessGame.getPiece(3, 3).getX());
//        assertEquals(3, chessGame.getPiece(3, 3).getY());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void takingMove(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        assertInstanceOf(Blank.class, chessGame.getPiece(3, 3));
//        assertEquals(3, chessGame.getPiece(3, 3).getX());
//        assertEquals(3, chessGame.getPiece(3, 3).getY());
//        assertInstanceOf(Pawn.class, chessGame.getPiece(4, 4));
//        assertEquals(4, chessGame.getPiece(4, 4).getX());
//        assertEquals(4, chessGame.getPiece(4, 4).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(4, 4).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(15, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void takingMoveUndo(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        pawnXD5.undo();
//        int x = 3, y = 3;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 4; y = 4;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.BLACK_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void takingMoveUndoRedoRepeated(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        pawnXD5.undo();
//        pawnD5.undo();
//        pawnD5.makeMove();
//        pawnD5.undo();
//        pawnD5.makeMove();
//        pawnXD5.makeMove();
//        pawnXD5.undo();
//        pawnXD5.makeMove();
//        pawnXD5.undo();
//        pawnD5.undo();
//        int x = 3, y = 1;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 3;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 4; y = 4;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 6;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.BLACK_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//
//    @Test
//    void takingMoveDoubleUndo(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        pawnXD5.undo();
//        pawnD5.undo();
//        int x = 3, y = 1;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 3;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 4; y = 4;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 6;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.BLACK_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(16, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void takingMoveDoubleRedo(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        pawnXD5.undo();
//        pawnD5.undo();
//        pawnD5.makeMove();
//        pawnXD5.makeMove();
//        int x = 3, y = 1;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 3;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 4; y = 4;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 6;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(15, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void takingMoveRedo(){
//        Move pawnE4 = new Move(3, 3, chessGame.getPiece(3, 1), null, chessGame);
//        Move pawnD5 = new Move(4, 4, chessGame.getPiece(4, 6), pawnE4.getPiece(), chessGame);
//        Move pawnXD5 = new Move(4, 4, chessGame.getPiece(3, 3), pawnD5.getPiece(), chessGame);
//        pawnXD5.undo();
//        pawnXD5.makeMove();
//        int x = 3, y = 3;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        x = 4; y = 4;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(16, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(15, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void basicPromotion(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        int x = 4, y = 6;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertInstanceOf(Queen.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Queen.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void basicPromotionUndo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        pawnPromotion.undo();
//        int x = 4, y = 6;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        assertInstanceOf(Pawn.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void basicPromotionRedo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        pawnPromotion.undo();
//        pawnPromotion.makeMove();
//        int x = 4, y = 6;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertInstanceOf(Queen.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Queen.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void promotionTaking(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        int x = 4, y = 6;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertInstanceOf(Queen.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Queen.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(1).size());
//        assertEquals(1, chessGame.getColourPieces(-1).size());
//    }
//
//    @Test
//    void promotionTakingUndo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        pawnPromotion.undo();
//        int x = 4, y = 6;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertInstanceOf(Pawn.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.BLACK_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(1).size());
//        assertEquals(2, chessGame.getColourPieces(-1).size());
//    }
//
//    @Test
//    void promotionTakingRedo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(0, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(4, 6, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(4, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move pawnPromotion = new Move(4, 7, chessGame.getPiece(4, 6), null, chessGame);
//        pawnPromotion.undo();
//        pawnPromotion.makeMove();
//        int x = 4, y = 6;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertInstanceOf(Queen.class, chessGame.getColourPieces(Piece.WHITE_PIECE).getLast());
//        x = 4; y = 7;
//        assertInstanceOf(Queen.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(1).size());
//        assertEquals(1, chessGame.getColourPieces(-1).size());
//    }
//
//    @Test
//    void castling(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move shortCastle = new Move(1, 0, chessGame.getPiece(3, 0), null, chessGame);
//        int x = 0, y = 0;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 1;
//        assertInstanceOf(King.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Rook.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 3;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void castlingUndo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move shortCastle = new Move(1, 0, chessGame.getPiece(3, 0), null, chessGame);
//        shortCastle.undo();
//        int x = 0, y = 0;
//        assertInstanceOf(Rook.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 1;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 3;
//        assertInstanceOf(King.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void castlingRedo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(1);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
//        blackPieces.add(new King(0, 7, Piece.BLACK_PIECE));
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move shortCastle = new Move(1, 0, chessGame.getPiece(3, 0), null, chessGame);
//        shortCastle.undo();
//        shortCastle.makeMove();
//        int x = 0, y = 0;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 1;
//        assertInstanceOf(King.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Rook.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 3;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void enPassant(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
//        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
//        blackPieces.getLast().firstMove();
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move enPassant = new Move(2, 5, chessGame.getPiece(3, 4), null, chessGame);
//        int x = 3, y = 4;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 5;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void enPassantUndo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
//        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
//        blackPieces.getLast().firstMove();
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move enPassant = new Move(2, 5, chessGame.getPiece(3, 4), null, chessGame);
//        enPassant.undo();
//        int x = 3, y = 4;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.BLACK_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 5;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(2, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//
//    @Test
//    void enPassantRedo(){
//        ArrayList<Piece> whitePieces = new ArrayList<>(2);
//        ArrayList<Piece> blackPieces = new ArrayList<>(2);
//        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
//        whitePieces.add(new Pawn(3, 4, Piece.WHITE_PIECE));
//        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
//        blackPieces.add(new Pawn(2, 4, Piece.BLACK_PIECE));
//        blackPieces.getLast().firstMove();
//        chessGame = new ChessGame(whitePieces, blackPieces);
//        Move enPassant = new Move(2, 5, chessGame.getPiece(3, 4), null, chessGame);
//        enPassant.undo();
//        enPassant.makeMove();
//        int x = 3, y = 4;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        x = 2;
//        assertInstanceOf(Blank.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.EMPTY_PIECE, chessGame.getPiece(x, y).getDirection());
//        y = 5;
//        assertInstanceOf(Pawn.class, chessGame.getPiece(x, y));
//        assertEquals(x, chessGame.getPiece(x, y).getX());
//        assertEquals(y, chessGame.getPiece(x, y).getY());
//        assertEquals(Piece.WHITE_PIECE, chessGame.getPiece(x, y).getDirection());
//
//        assertEquals(2, chessGame.getColourPieces(Piece.WHITE_PIECE).size());
//        assertEquals(1, chessGame.getColourPieces(Piece.BLACK_PIECE).size());
//    }
//}
