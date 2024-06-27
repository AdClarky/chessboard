import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChessboardFactory {
    public static @NotNull Chessboard createChessboardDefaultSetup() {
        Chessboard chessboard = new Chessboard();
        ArrayList<Piece> whitePieces = new ArrayList<>(16);
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Knight(1, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Bishop(2, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Queen(4, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Bishop(5, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Knight(6, 0, Piece.WHITE_PIECE, chessboard));
        whitePieces.add(new Rook(7, 0, Piece.WHITE_PIECE, chessboard));
        ArrayList<Piece> blackPieces = new ArrayList<>(16);
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Rook(0, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Knight(1, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Bishop(2, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Queen(4, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Bishop(5, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Knight(6, 7, Piece.BLACK_PIECE, chessboard));
        blackPieces.add(new Rook(7, 7, Piece.BLACK_PIECE, chessboard));
        for(int x = 0; x < 8; x++){
            blackPieces.add(new Pawn(x, 6, Piece.BLACK_PIECE, chessboard));
            whitePieces.add(new Pawn(x, 1, Piece.WHITE_PIECE, chessboard));
        }
        chessboard.populateBoard(whitePieces, blackPieces);
        return chessboard;
    }
}
