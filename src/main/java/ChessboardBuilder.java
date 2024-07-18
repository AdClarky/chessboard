import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Used to generate a {@link Chessboard}. Can be built in the default configuration or using a fen string.
 */
class ChessboardBuilder {
    private final static Pattern REGEX = Pattern.compile("([prknqb|0-8]{1,8}/){7}[prknqb|0-8]{1,8} [wb] [-kq]{1,4} (-|([a-h][1-8])) (\\d+) (\\d+)", Pattern.CASE_INSENSITIVE);
    private final Chessboard board = new Chessboard();
    private final List<Piece> whitePieces = new ArrayList<>(16);
    private final List<Piece> blackPieces = new ArrayList<>(16);
    private int squaresProcessed = 7;

    ChessboardBuilder() {
    }

    public @NotNull Chessboard defaultSetup() {
        getBoardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        return board;
    }

    public @NotNull Chessboard fromFen(@NotNull String fenString) throws InvalidFenStringException{
        if(!doesStringMatchFen(fenString))
            throw new InvalidFenStringException();
        return getBoardFromFen(fenString);
    }

    private Chessboard getBoardFromFen(@NotNull String fenString) {
        String[] sections = fenString.split(" ");
        try {
            setTurnToMove(sections[1]);
            setHalfMoves(sections[4]);
            setFullMoves(sections[5]);
            setCastlingRights(sections[2]);
            populateBoardFromFenString(sections[0]);
            setEnPassant(sections[3]);
        } catch (AccessedHistoryDuringGameException e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    private void populateBoardFromFenString(@NotNull String positionSection) {
        String[] sections = positionSection.split("/");
        for(int row = 0; row < 8; row++){
            for(char character : sections[row].toCharArray()){
                processCharacter(character, 7 - row);
            }
            squaresProcessed = 7;
        }
        board.populateBoard(whitePieces, blackPieces);
    }

    private void processCharacter(char character, int row){
        if(Character.isDigit(character)){
            squaresProcessed -= Character.getNumericValue(character);
            return;
        }
        PieceColour colour = getColourFromCharacter(character);
        List<Piece> pieces = getPiecesFromChar(character);
        switch(Character.toLowerCase(character)){
            case 'r':
                pieces.add(new Rook(squaresProcessed, row, colour));
                break;
            case 'n':
                pieces.add(new Knight(squaresProcessed, row, colour));
                break;
            case 'b':
                pieces.add(new Bishop(squaresProcessed, row, colour));
                break;
            case 'k':
                pieces.addFirst(new King(squaresProcessed, row, colour));
                break;
            case 'q':
                pieces.add(new Queen(squaresProcessed, row, colour));
                break;
            case 'p':
                pieces.add(new Pawn(squaresProcessed, row, colour));
                break;
        }
        squaresProcessed--;
    }

    private void setTurnToMove(@NotNull CharSequence turnSection){
        char character = turnSection.charAt(0);
        board.setCurrentTurn(character == 'w' ? PieceColour.WHITE : PieceColour.BLACK);
    }

    private void setCastlingRights(@NotNull String rights){
        int length = rights.length();
        if(length == 4)
            return;
        if(rights.contains("-")){
            board.removeAllCastling(PieceColour.WHITE);
            board.removeAllCastling(PieceColour.BLACK);
            return;
        }
        StringBuilder upper = new StringBuilder();
        StringBuilder lower = new StringBuilder();
        getUpperAndLower(rights, upper, lower);
        if(upper.isEmpty())
            board.removeAllCastling(PieceColour.WHITE);
        else if(upper.length() == 1)
            setOtherRookMoved(upper.charAt(0));
        if(lower.isEmpty())
            board.removeAllCastling(PieceColour.BLACK);
        else if(lower.length() == 1)
            setOtherRookMoved(lower.charAt(0));
    }

    private static void getUpperAndLower(@NotNull String rights, StringBuilder upper, StringBuilder lower) {
        for(char c : rights.toCharArray()){
            if(Character.isUpperCase(c))
                upper.append(c);
            else
                lower.append(c);
        }
    }

    private void setOtherRookMoved(char rookWithRights){
        int y = Character.isUpperCase(rookWithRights) ? 0 : 7;
        int x = Character.toLowerCase(rookWithRights) == 'k' ? 7 : 0;
        board.removeCastlingRight(new Coordinate(x, y));
    }

    private void setEnPassant(@NotNull String section){
        if(section.contains("-"))
            return;
        Coordinate location = Coordinate.createCoordinateFromString(section);
        PieceColour enPassantColour = PieceColour.getOtherColour(board.getCurrentTurn());
        int direction = PieceColour.getDirectionFromColour(enPassantColour);
        board.setEnPassantSquare(new Coordinate(location.x(), location.y()+direction));
    }

    private void setHalfMoves(String section) throws AccessedHistoryDuringGameException {
        board.setNumHalfMoves(Integer.parseInt(section));
    }

    private void setFullMoves(String section) throws AccessedHistoryDuringGameException {
        board.setNumFullMoves(Integer.parseInt(section));
    }

    private static PieceColour getColourFromCharacter(char c){
        return Character.isUpperCase(c) ? PieceColour.WHITE : PieceColour.BLACK;
    }

    private List<Piece> getPiecesFromChar(char c){
        return Character.isUpperCase(c) ? whitePieces : blackPieces;
    }

    private static boolean doesStringMatchFen(CharSequence fenString){
        return REGEX.matcher(fenString).matches();
    }
}
