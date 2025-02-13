package chessboard;

import common.Coordinate;
import common.PieceColour;
import common.PieceValue;
import common.Pieces;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class ChessboardTest {
    @Test
    void doesCustomPositionWork(){
        Chessboard board = new Chessboard();
        Collection<PieceValue> whitePieces = new ArrayList<>(3);
        Collection<PieceValue> blackPieces = new ArrayList<>(3);
        whitePieces.add(new PieceValue(new Coordinate(0, 0), Pieces.KING, PieceColour.WHITE));
        whitePieces.add(new PieceValue(new Coordinate(0, 1), Pieces.KING, PieceColour.WHITE));
        whitePieces.add(new PieceValue(new Coordinate(0, 2), Pieces.KING, PieceColour.WHITE));
        blackPieces.add(new PieceValue(new Coordinate(0, 3), Pieces.KING, PieceColour.BLACK));
        blackPieces.add(new PieceValue(new Coordinate(0, 4), Pieces.KING, PieceColour.BLACK));
        blackPieces.add(new PieceValue(new Coordinate(0, 5), Pieces.KING, PieceColour.BLACK));
        board.populateBoard(whitePieces, blackPieces);
        for(int x = 1; x < 8; x++){
            for(int y = 0; y < 8; y++){
                assertEquals(Pieces.BLANK, board.getPiece(new Coordinate(x, y)));
            }
        }
        for(int y = 0; y < 6; y++){
            assertEquals(Pieces.KING, board.getPiece(new Coordinate(0, y)));
        }
    }

    @Test
    void doesBlankCheckerWork() {
        Chessboard board = new ChessboardBuilder().defaultSetup();
        for(int x = 0; x < 8; x++) {
            for(int y = 2; y < 6; y++) {
                assertTrue(board.isSquareBlank(new Coordinate(x,y)));
            }
        }
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 2; y++){
                assertFalse(board.isSquareBlank(new Coordinate(x,y)));
                assertFalse(board.isSquareBlank(new Coordinate(x,y+6)));
            }
        }
    }

    @Test
    void queenNextToKingInCheck(){
        Chessboard board = assertDoesNotThrow(()->new ChessboardBuilder().fromFen("rnb1kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBqKBNR w KQkq - 0 1"));
        ChessLogic logic = new ChessLogic(board, new BoardHistory());
        logic.calculatePossibleMoves();
        assertTrue(logic.isKingInCheck());
    }

    @Test
    void hashcodeOnSameState(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        int state = board.hashCode();
        assertEquals(state, board.hashCode());
    }

    @Test
    void addBlankSquare(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertThrows(IllegalArgumentException.class, ()-> board.addPiece(Pieces.BLANK, new Coordinate(0, 0), PieceColour.WHITE));
    }

    @Test
    void areEdgeSquaresBlank(){
        Chessboard board = new Chessboard();
        assertTrue(board.isSquareBlank(new Coordinate(0, 0)));
        assertTrue(board.isSquareBlank(new Coordinate(7, 0)));
        assertTrue(board.isSquareBlank(new Coordinate(7, 7)));
        assertTrue(board.isSquareBlank(new Coordinate(0, 7)));
    }

    @Test
    void areEdgeSquaresPieces(){
        Chessboard board = new ChessboardBuilder().defaultSetup();
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 0)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 0)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(7, 7)));
        assertEquals(Pieces.ROOK, board.getPiece(new Coordinate(0, 7)));
    }
}