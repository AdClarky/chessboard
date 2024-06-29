import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains functions relating to chess move notation in algebraic form.
 * Since chess moves are context based, it requires a board to be passed for most functions in order to calculate
 * their true meaning.
 */
public final class ChessUtils {
    private ChessUtils(){}
    /** Converts the chessboard coordinates to chess algebraic notation. */
    public static @NotNull String coordsToChess(int x, int y){
        return Character.toString('h' - x) + (y+1);
    }

    @Deprecated
    public static String moveToChess(Chessboard board, Piece piece, int newX, int newY){
        if(piece instanceof King && Math.abs(piece.getX() - newX) == 2) {// castling
            if(piece.getX() - newX == 2) // long castle
                return  "O-O-O";
            else // short castle
                return "O-O";
        }
        // TODO: check if ambiguous
        String moveString = piece.toString();
        if(!board.isSquareBlank(newX, newY)) {
            moveString += "x";
        }
        moveString += coordsToChess(newX, newY);

        Move move = new Move(newX, newY, piece, null, board);
        if(board.isCheckmate())
            moveString += "#";
        else if(board.isKingInCheck(PieceColour.getOtherColour(piece)))
            moveString += "+";
        move.undo();
        return moveString;
    }

    public static MoveValue chessToMove(String move, ChessGame chessGame){
        if("O-O".equals(move)) {
            return getCastlingMove(1, chessGame);
        }
        if("O-O-O".equals(move)) {
            return getCastlingMove(5, chessGame);
        }
        Coordinate newCoordinate = Coordinate.createCoordinateFromString(move);
        String pieceLetter;
        if(Character.isLowerCase(move.charAt(0))) // if a pawn
            pieceLetter = "";
        else // any other piece
            pieceLetter = move.charAt(0) + "";
        ArrayList<Piece> possiblePieces = new ArrayList<>(2);
        for(Piece piece : chessGame.getColourPieces(chessGame.getCurrentTurn())){
            if(!(piece.toString()).equals(pieceLetter)) // if its not type of piece that moved
                continue;
            if(piece.getPossibleMoves().contains(newCoordinate))
                possiblePieces.add(piece);
        }
        if(possiblePieces.size() > 1)
            disambiguatePiece(possiblePieces, move);
        Piece piece = possiblePieces.getFirst();
        return new MoveValue(piece, newCoordinate.x(), newCoordinate.y());
    }

    private static MoveValue getCastlingMove(int newX, @NotNull ChessGame chessGame){
        if(chessGame.getCurrentTurn() == PieceColour.BLACK){
            return new MoveValue(chessGame.getPiece(3,7),newX,7);
        }else{
            return new MoveValue(chessGame.getPiece(3, 0), newX, 0);
        }
    }

    /**
     * If two pieces can move to the same square, it uses the file or rank to calculate which piece.
     * @param possiblePieces list of pieces that could move
     * @param move the algebraic chess move
     */
    @VisibleForTesting
    static void disambiguatePiece(Collection<Piece> possiblePieces, @NotNull CharSequence move){
        int length = move.length();
        for(int i = 0; i < length - 2; i++){
            if(Character.isLowerCase(move.charAt(i)) && move.charAt(i) != 'x'){ // x value given
                Coordinate correctX = Coordinate.createCoordinateFromString(move.charAt(i) + "0");
                possiblePieces.removeIf(piece -> piece.getX() != correctX.x());
            }else if(Character.isDigit(move.charAt(i))){ // y value given
                Coordinate correctY = Coordinate.createCoordinateFromString("a" + move.charAt(i));
                possiblePieces.removeIf(piece -> piece.getY() != correctY.y());
            }
        }
    }
}
