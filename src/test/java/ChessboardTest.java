import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class ChessboardTest {
//    @Test
//    void doesCustomPositionWork(){
//        Chessboard board = new Chessboard();
//        Collection<Piece> whitePieces = new ArrayList<>(3);
//        Collection<Piece> blackPieces = new ArrayList<>(3);
//        whitePieces.add(new King(new Coordinate(0, 0), PieceColour.WHITE));
//        whitePieces.add(new King(new Coordinate(0, 1), PieceColour.WHITE));
//        whitePieces.add(new King(new Coordinate(0, 2), PieceColour.WHITE));
//        blackPieces.add(new King(new Coordinate(0, 3), PieceColour.BLACK));
//        blackPieces.add(new King(new Coordinate(0, 4), PieceColour.BLACK));
//        blackPieces.add(new King(new Coordinate(0, 5), PieceColour.BLACK));
//        board.populateBoard(whitePieces, blackPieces);
//        for(int x = 1; x < 8; x++){
//            for(int y = 0; y < 8; y++){
//                assertInstanceOf(Blank.class, board.getPiece(new Coordinate(x, y)));
//            }
//        }
//        for(int y = 0; y < 6; y++){
//            assertInstanceOf(King.class, board.getPiece(new Coordinate(0, y)));
//        }
//    }
//
//    @Test
//    void doesBlankCheckerWork() {
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        for(int x = 0; x < 8; x++) {
//            for(int y = 2; y < 6; y++) {
//                assertTrue(board.isSquareBlank(new Coordinate(x,y)));
//            }
//        }
//        for(int x = 0; x < 8; x++){
//            for(int y = 0; y < 2; y++){
//                assertFalse(board.isSquareBlank(new Coordinate(x,y)));
//                assertFalse(board.isSquareBlank(new Coordinate(x,y+6)));
//            }
//        }
//    }
//
//    @Test
//    void queenNextToKingInCheck(){
//        Chessboard board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("rnb1kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBqKBNR w KQkq - 0 1"));
//        assertTrue(new ChessLogic(board).isKingInCheck(PieceColour.WHITE));
//    }
//
//    @Test
//    void hashcodeOnSameState(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        int state = board.hashCode();
//        assertEquals(state, board.hashCode());
//    }
//
//    @Test
//    void hashcodeTestOnBasicPawnMoveWithUndo() {
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        int state = board.hashCode();
//        assertDoesNotThrow(()->board.makeMove(4, 1, 4, 3));
//        assertNotEquals(state, board.hashCode());
//        board.undoMove();
//        assertEquals(state, board.hashCode());
//    }
//
//    @Test
//    void hashcodeTestOnMoveWherePreviousWasPassantable() {
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()->board.makeMove(4, 1, 4, 3));
//        int state = board.hashCode();
//        int whitePawnHash = board.getPiece(new Coordinate(4,3)).hashCode();
//        int blackPawnHash = board.getPiece(new Coordinate(4,6)).hashCode();
//        int blankSquareHash = board.getPiece(new Coordinate(4, 4)).hashCode();
//        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
//        assertNotEquals(state, board.hashCode());
//        board.undoMove();
//        assertEquals(whitePawnHash, board.getPiece(new Coordinate(4,3)).hashCode());
//        assertEquals(blackPawnHash, board.getPiece(new Coordinate(4,6)).hashCode());
//        assertEquals(blankSquareHash, board.getPiece(new Coordinate(4, 4)).hashCode());
//        assertEquals(state, board.hashCode());
//    }
//
//    @Test
//    void differentStatesAfterMoveThenUndo(){
//        Chessboard game = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()->game.makeMove(7, 1, 7, 3));
//        assertDoesNotThrow(()->game.makeMove(7, 6, 7, 4));
//        int state = game.hashCode();
//        game.undoMove();
//        game.undoMove();
//        assertNotEquals(state, game.hashCode());
//    }
//
//    @Test
//    void areAllHashesDifferentOnStandardBoard(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        Collection<Integer> hashes = new HashSet<>(64);
//        for(int y = 0; y < 8; y++){
//            for(int x = 0; x < 8; x++){
//                Piece piece = board.getPiece(new Coordinate(x, y));
//                hashes.add(Objects.hash(piece, piece.getX(), piece.getY()));
//            }
//        }
//        assertEquals(64, hashes.size());
//    }
//
//    @Test
//    void undoMoreThanNecessary(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        int state = board.hashCode();
//        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
//        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
//        assertDoesNotThrow(()->board.makeMove(3, 3, 4, 4));
//        board.undoMove();
//        board.undoMove();
//        board.undoMove();
//        board.undoMove();
//        board.undoMove();
//        assertEquals(state, board.hashCode());
//    }
//
//
//    @Test
//    void redoMoreThanNecessary(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertDoesNotThrow(()->board.makeMove(3, 1, 3, 3));
//        assertDoesNotThrow(()->board.makeMove(4, 6, 4, 4));
//        assertDoesNotThrow(()->board.makeMove(3, 3, 4, 4));
//        int state = board.hashCode();
//        board.undoMove();
//        board.undoMove();
//        board.undoMove();
//        board.redoMove();
//        board.redoMove();
//        board.redoMove();
//        board.redoMove();
//        board.redoMove();
//        board.redoMove();
//        assertEquals(state, board.hashCode());
//    }
//
//    @Test
//    void removeBlankSquare(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertThrows(IllegalArgumentException.class, ()-> board.removePiece(new Blank(new Coordinate(0, 0))));
//    }
//
//    @Test
//    void addBlankSquare(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertThrows(IllegalArgumentException.class, ()-> board.addPiece(new Blank(new Coordinate(0, 0))));
//    }
//
//    @Test
//    void getBlankKing(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertThrows(IllegalArgumentException.class, ()-> board.getKing(PieceColour.BLANK));
//    }
//
//    @Test
//    void getBlankPieces(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertThrows(IllegalArgumentException.class, ()-> board.getAllColourPieces(PieceColour.BLANK));
//    }
//
//    @Test
//    void areEdgeSquaresBlank(){
//        Chessboard board = new Chessboard();
//        assertTrue(board.isSquareBlank(new Coordinate(0, 0)));
//        assertTrue(board.isSquareBlank(new Coordinate(7, 0)));
//        assertTrue(board.isSquareBlank(new Coordinate(7, 7)));
//        assertTrue(board.isSquareBlank(new Coordinate(0, 7)));
//    }
//
//    @Test
//    void areOutOfBoundarySquareNotBlank(){
//        Chessboard board = new Chessboard();
//        assertFalse(board.isSquareBlank(new Coordinate(-1, 0)));
//        assertFalse(board.isSquareBlank(new Coordinate(8, 0)));
//        assertFalse(board.isSquareBlank(new Coordinate(0, -1)));
//        assertFalse(board.isSquareBlank(new Coordinate(0, 8)));
//    }
//
//
//    @Test
//    void areEdgeSquaresPieces(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertInstanceOf(Rook.class, board.getPiece(new Coordinate(0, 0)));
//        assertInstanceOf(Rook.class, board.getPiece(new Coordinate(7, 0)));
//        assertInstanceOf(Rook.class, board.getPiece(new Coordinate(7, 7)));
//        assertInstanceOf(Rook.class, board.getPiece(new Coordinate(0, 7)));
//    }
//
//
//    @Test
//    void areOutOfBoundsSquareBlank(){
//        Chessboard board = new ChessboardBuilder().defaultSetup();
//        assertInstanceOf(Blank.class, board.getPiece(new Coordinate(-1, 0)));
//        assertInstanceOf(Blank.class, board.getPiece(new Coordinate(8, 0)));
//        assertInstanceOf(Blank.class, board.getPiece(new Coordinate(0, -1)));
//        assertInstanceOf(Blank.class, board.getPiece(new Coordinate(0, 8)));
//    }
}