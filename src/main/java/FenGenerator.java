import org.jetbrains.annotations.NotNull;
import java.util.List;

public class FenGenerator {
    private final Chessboard board;
    private final StringBuilder fenString = new StringBuilder();

    public FenGenerator(Chessboard board) {
        this.board = board;
    }

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

    private char charFromPiece(@NotNull Piece piece) {
        String pieceString = piece.toString();
        if(pieceString.isEmpty()) {
            pieceString = "P";
        }
        char character = pieceString.charAt(0);
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
        if(board.getKing(PieceColour.WHITE).hadFirstMove() && board.getKing(PieceColour.BLACK).hadFirstMove()) {
            fenString.append("- ");
            return;
        }
        if(!board.getKing(PieceColour.WHITE).hadFirstMove())
            addColourCastleRight(0);
        if(!board.getKing(PieceColour.BLACK).hadFirstMove())
            addColourCastleRight(7);
        fenString.append(" ");
    }

    private void addColourCastleRight(int backRow){
        if(!board.getPiece(0, backRow).hadFirstMove())
            fenString.append(backRow == 0 ? 'K' : 'k');
        if(!board.getPiece(7, backRow).hadFirstMove())
            fenString.append(backRow == 0 ? 'Q' : 'q');
    }

    private void addEnPassant(){
        List<MoveValue> lastMove = board.getLastMoveMade();
        if(lastMove.size() != 1) {
            fenString.append("- ");
            return;
        }
        if(!(lastMove.getFirst().piece() instanceof Pawn)) {
            fenString.append("- ");
            return;
        }
        Pawn pawn = (Pawn) lastMove.getFirst().piece();
        int direction = PieceColour.getDirectionFromColour(pawn.getColour());
        Coordinate coordinateBehind = new Coordinate(pawn.getX() - direction, pawn.getY());
        fenString.append(coordinateBehind).append(" ");
    }

    private void addHalfMoves(){
        fenString.append(board.getNumHalfMoves()).append(' ');
    }

    private void addFullMoves(){
        fenString.append(board.getNumFullMoves());
    }
}

