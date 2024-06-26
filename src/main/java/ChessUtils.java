import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains functions relating to chess move notation in algebraic form.
 * Since chess moves are context based, it requires a board to be passed for most functions in order to calcualte
 * their true meaning.
 */
public final class ChessUtils {
    private ChessUtils(){}
    /**
     * Converts the chessboard coordinates to chess algabraic notation.
     * @param x the chessboard x value
     * @param y the chessboard y value
     * @return the string in chess notation
     */
    public static String coordsToChess(int x, int y){
        return Character.toString('h' - x) + (y+1);
    }

    public static String moveToChess(Board board, Piece piece, int newX, int newY){
        if(piece instanceof King king && Math.abs(piece.getX() - newX) == 2) {// castling
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
        else if(board.isKingInCheck(piece.getDirection() * -1))
            moveString += "+";
        move.undo();
        return moveString;
    }

    public static MoveValue chessToMove(String move, Board board){
        if("O-O".equals(move)) {
            if(board.getCurrentTurn() == Piece.BLACK_PIECE){
                return new MoveValue(board.getPiece(3,7),1,7);
            }else{
                return new MoveValue(board.getPiece(3, 0), 1, 0);
            }
        }else if("O-O-O".equals(move)) {
            if(board.getCurrentTurn() == Piece.BLACK_PIECE){
                return new MoveValue(board.getPiece(3, 7), 5, 7);
            }else{
                return new MoveValue(board.getPiece(3, 0), 5, 0);
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
        return new MoveValue(piece, newCoordinate.x(), newCoordinate.y());
    }

    /**
     * If two pieces can move to the same square, it uses the file or rank to calculate which piece.
     * @param possiblePieces list of pieces that could move
     * @param move the algebraic chess move
     */
    static void disambiguatePiece(Collection<Piece> possiblePieces, CharSequence move){
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
