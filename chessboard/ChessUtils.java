package chessboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for converting x and y coordinates into chess notation.
 */
public final class ChessUtils {
    private ChessUtils(){}


    private static final Map<Integer, String> X_AXIS = new HashMap<>() {{
        put(0, "a");
        put(1, "b");
        put(2, "c");
        put(3, "d");
        put(4, "e");
        put(5, "f");
        put(6, "g");
        put(7, "h");
    }};

    /**
     * Converts the chessboard coordinates to chess notation.
     * It does not convert to a Move notation, just a square on the board.
     * @param x the chessboard x value
     * @param y the chessboard y value
     * @return the string in chess notation
     */
    public static String coordsToChess(int x, int y){
        return X_AXIS.get(x) + (y+1);
    }

    public static String moveToChess(Board board, Piece piece, int newX, int newY){
        if(piece instanceof King king && Math.abs(piece.getX() - newX) == 2) {// castling
            if(piece.getX() - newX == 2) // long castle
                return  "O-O-O";
            else // short castle
                return "O-O";
        }
        String move = piece.toString();
        move += coordsToChess(piece.getX(), piece.getY());
        if(!board.isSquareBlank(newX, newY))
            move += "x";
        move += coordsToChess(newX, newY);

        board.tempMove(newX, newY, piece);
        if(board.isCheckmate())
            move += "#";
        else if(board.isKingInCheck(piece.getDirection() * -1))
            move += "+";
        return move;
    }
}
