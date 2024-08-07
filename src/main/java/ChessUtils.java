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
    /**
     * Converts the chessboard coordinates to chess algebraic notation.
     * @param x x position
     * @param y y position
     * @return the coordinate in algebraic form.
     */
    public static @NotNull String coordsToChess(int x, int y){
        return Character.toString('h' - x) + (y+1);
    }

    static MoveValue chessToMove(Chessboard board, String move) throws InvalidMoveException {
        if("O-O".equals(move)) {
            return getCastlingMove(board, 1);
        }
        if("O-O-O".equals(move)) {
            return getCastlingMove(board, 5);
        }
        Coordinate newCoordinate = Coordinate.createCoordinateFromString(move);
        char pieceLetter;
        if(Character.isLowerCase(move.charAt(0))) // if a pawn
            pieceLetter = '\u0000';
        else // any other piece
            pieceLetter = move.charAt(0);
        ArrayList<Piece> possiblePieces = new ArrayList<>(2);
        for(Piece piece : board.getAllColourPieces(board.getCurrentTurn())){
            if(piece.toCharacter() != pieceLetter) // if its not type of piece that moved
                continue;
            possiblePieces.add(piece);
        }
        possiblePieces.removeIf(piece -> !piece.getPossibleMoves().contains(newCoordinate));
        if(possiblePieces.size() > 1)
            disambiguatePiece(possiblePieces, move);
        if(possiblePieces.isEmpty())
            throw new InvalidMoveException(move);
        Piece piece = possiblePieces.getFirst();
        return new MoveValue(piece, newCoordinate.x(), newCoordinate.y());
    }

    private static MoveValue getCastlingMove(@NotNull Chessboard board, int newX){
        if(board.getCurrentTurn() == PieceColour.BLACK){
            return new MoveValue(board.getPiece(3,7),newX,7);
        }else{
            return new MoveValue(board.getPiece(3, 0), newX, 0);
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
