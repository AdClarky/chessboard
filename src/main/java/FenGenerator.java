import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Used for generating FenStrings from a board position. When given a board, getFenString can be called
 * at any point and the fen string will be calculated. */
public class FenGenerator {
    private final Chessboard board;
    private final StringBuilder fenString = new StringBuilder();

    public FenGenerator(Chessboard board) {
        this.board = board;
    }

    /** Calculates and returns the fen string */
    public String getFenString() {
        addBoardPosition();
        addCurrentTurn();
        addCastlingRights();
        addEnPassant();
        addHalfMoves();
        addFullMoves();
        return fenString.toString();
    }

    private void addBoardPosition() {
        int blankSquare = 0;
        for(int y = 7; y >= 0; y--) {
            for(int x = 7; x >= 0; x--) {
                Piece piece = board.getPiece(x, y);
                if(piece instanceof Blank)
                    blankSquare++;
                else {
                    if(blankSquare != 0){
                        fenString.append(blankSquare);
                        blankSquare = 0;
                    }
                    fenString.append(charFromPiece(piece));
                }
            }
            if(blankSquare != 0)
                fenString.append(blankSquare);
            blankSquare = 0;
            fenString.append("/");
        }
        fenString.deleteCharAt(fenString.length()-1);
        fenString.append(' ');
    }

    private static char charFromPiece(@NotNull Piece piece) {
        char character = piece.toCharacter();
        if(character == '\u0000') {
            character = 'P';
        }
        if(piece.getColour() == PieceColour.BLACK)
            character = Character.toLowerCase(character);
        return character;
    }

    private void addCurrentTurn() {
        if(board.getCurrentTurn() == PieceColour.BLACK)
            fenString.append("b ");
        else
            fenString.append("w ");
    }

    private void addCastlingRights() {
        if(board.hasKingMoved(PieceColour.WHITE) && board.hasKingMoved(PieceColour.BLACK)){
            fenString.append("- ");
            return;
        }
        if(!board.hasKingMoved(PieceColour.WHITE))
            addColourCastleRight(0);
        if(!board.hasKingMoved(PieceColour.BLACK))
            addColourCastleRight(7);
        fenString.append(" ");
    }

    private void addColourCastleRight(int backRow){
        if(!board.hasPieceHadFirstMove(0, backRow))
            fenString.append(backRow == 0 ? 'K' : 'k');
        if(!board.hasPieceHadFirstMove(7, backRow))
            fenString.append(backRow == 0 ? 'Q' : 'q');
    }

    private void addEnPassant(){
        Pawn pawn = findEnPassantPawn();
        if(pawn == null) {
            fenString.append("- ");
            return;
        }
        int direction = PieceColour.getDirectionFromColour(pawn.getColour());
        Coordinate coordinateBehind = new Coordinate(pawn.getX(), pawn.getY() - direction);
        fenString.append(coordinateBehind).append(" ");
    }

    private @Nullable Pawn findEnPassantPawn(){
        for(int y = 3; y < 5; y++) {
            for (int x = 0; x < 8; x++) {
                Piece possiblePiece = board.getPiece(x, y);
                if(possiblePiece instanceof Pawn pawn && possiblePiece.hadFirstMove())
                    return pawn;
            }
        }
        return null;
    }

    private void addHalfMoves(){
        fenString.append(board.getNumHalfMoves()).append(' ');
    }

    private void addFullMoves(){
        fenString.append(board.getNumFullMoves());
    }
}

