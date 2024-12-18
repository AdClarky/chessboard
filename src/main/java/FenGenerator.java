import org.jetbrains.annotations.NotNull;

/** Used for generating FenStrings from a board position. When given a board, getFenString can be called
 * at any point and the fen string will be calculated. */
class FenGenerator {
    private final ChessGame board;
    private final StringBuilder fenString = new StringBuilder();

    public FenGenerator(ChessGame board) {
        this.board = board;
    }

    /** Calculates and returns the fen string */
    public String getFenString() {
        fenString.setLength(0);
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
            for(int x = 0; x < 8; x++) {
                Coordinate pos = new Coordinate(x, y);
                if(board.getPiece(pos) == Pieces.BLANK) {
                    blankSquare++;
                    continue;
                }
                Pieces piece = board.getPiece(pos);
                if(blankSquare != 0){
                    fenString.append(blankSquare);
                    blankSquare = 0;
                }
                fenString.append(charFromPiece(piece, board.getColour(pos)));
            }
            if(blankSquare != 0)
                fenString.append(blankSquare);
            blankSquare = 0;
            fenString.append("/");
        }
        fenString.deleteCharAt(fenString.length()-1);
        fenString.append(' ');
    }

    private static char charFromPiece(@NotNull Pieces piece, PieceColour colour) {
        char character = switch (piece) {
            case PAWN -> 'P';
            case KNIGHT -> 'N';
            case BISHOP -> 'B';
            case ROOK -> 'R';
            case QUEEN -> 'Q';
            case KING -> 'K';
            case BLANK -> 'Z';
        };
        if(colour == PieceColour.BLACK)
            character = Character.toLowerCase(character);
        return character;
    }

    private void addCurrentTurn() {
        if(board.getTurn() == PieceColour.BLACK)
            fenString.append("b ");
        else
            fenString.append("w ");
    }

    private void addCastlingRights() {
        Bitboard castlingRights = new Bitboard(board.getCastlingRights());
        if(castlingRights.isEmpty()){
            fenString.append("- ");
            return;
        }
        if(castlingRights.contains(new Coordinate(4, 0)))
            addColourCastleRight(0, castlingRights);
        if(castlingRights.contains(new Coordinate(4, 7)))
            addColourCastleRight(7, castlingRights);
        fenString.append(" ");
    }

    private void addColourCastleRight(int backRow, Bitboard castlingRights){
        if(castlingRights.contains(new Coordinate(7, backRow)))
            fenString.append(backRow == 0 ? 'K' : 'k');
        if(castlingRights.contains(new Coordinate(0, backRow)))
            fenString.append(backRow == 0 ? 'Q' : 'q');
    }

    private void addEnPassant(){
        Coordinate enPassantSquare = board.getEnPassantSquare();
        if(enPassantSquare == null) {
            fenString.append("- ");
            return;
        }
        int direction = enPassantSquare.y() == 4 ? 1 : -1;
        Coordinate coordinateBehind = new Coordinate(enPassantSquare.x(), enPassantSquare.y() + direction);
        fenString.append(coordinateBehind).append(" ");
    }

    private void addHalfMoves(){
        fenString.append(board.getNumHalfMoves()).append(' ');
    }

    private void addFullMoves(){
        fenString.append(board.getNumFullMoves());
    }
}

