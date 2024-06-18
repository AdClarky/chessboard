package chessboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Used for converting x and y coordinates into chess notation.
 */
public final class ChessUtils {
    private ChessUtils(){}
    /**
     * Converts the chessboard coordinates to chess notation.
     * It does not convert to a Move notation, just a square on the board.
     * @param x the chessboard x value
     * @param y the chessboard y value
     * @return the string in chess notation
     */
    public static String coordsToChess(int x, int y){
        return ('a' + x) + (y+1) + "";
    }

    public static String moveToChess(Board board, Piece piece, int newX, int newY){
        if(piece instanceof King king && Math.abs(piece.getX() - newX) == 2) {// castling
            if(piece.getX() - newX == 2) // long castle
                return  "O-O-O";
            else // short castle
                return "O-O";
        }
        // TODO: check if ambiguous
        String move = piece.toString();
        if(!board.isSquareBlank(newX, newY)) {
            move += "x";
        }
        move += coordsToChess(newX, newY);

        TempMove tempMove = new TempMove(newX, newY, piece, board);
        if(board.isCheckmate())
            move += "#";
        else if(board.isKingInCheck(piece.getDirection() * -1))
            move += "+";
        tempMove.undo();
        return move;
    }

    public static Move chessToMove(String move, Board board){
        if("O-O".equals(move)) {
            if(board.getCurrentTurn() == Piece.BLACK_PIECE){
                return new Move(3,7,1,7);
            }else{
                return new Move(3,0,1,0);
            }
        }else if("O-O-O".equals(move)) {
            if(board.getCurrentTurn() == Piece.BLACK_PIECE){
                return new Move(3,7,5,7);
            }else{
                return new Move(3,0,5,7);
            }
        }

        Coordinate newCoordinate = Coordinate.fromString(move);
        String pieceLetter;
        if(Character.isLowerCase(move.charAt(0))) // if a pawn
            pieceLetter = "";
        else // any other piece
            pieceLetter = move.charAt(0) + "";
        ArrayList<Piece> possiblePieces = new ArrayList<>(2);
        for(Piece piece : board.getColourPieces(board.getCurrentTurn())){
            if(!(piece.toString()).equals(pieceLetter)) // if its not type of piece that moved
                continue;
            if(piece.getPossibleMoves(board).contains(newCoordinate))
                possiblePieces.add(piece);
        }
        if(possiblePieces.size() > 1)
            disambiguatePiece(possiblePieces, move);
        Piece piece = possiblePieces.getFirst();
        return new Move(piece.getX(), piece.getY(), newCoordinate.x(), newCoordinate.y());
    }

    private static void disambiguatePiece(Collection<Piece> possiblePieces, CharSequence move){
        int length = move.length();
        for(int i = 0; i < length; i++){
            if(Character.isLowerCase(move.charAt(i)) && move.charAt(i) != 'x' && i != length - 2){ // x value given
                Coordinate correctX = Coordinate.fromString(move.charAt(i) + "0");
                possiblePieces.removeIf(piece -> piece.getX() != correctX.x());
            }else if(Character.isDigit(move.charAt(i)) && i != length - 1){ // y value given
                Coordinate correctY = Coordinate.fromString("a" + move.charAt(i));
                possiblePieces.removeIf(piece -> piece.getY() != correctY.y());
            }
        }
    }
}
