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
    private final List<PieceValue> whitePieces = new ArrayList<>(16);
    private final List<PieceValue> blackPieces = new ArrayList<>(16);
    private int squaresProcessed = 0;
    private int numHalfMoves;
    private int numFullMoves;

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
        setTurnToMove(sections[1]);
        setHalfMoves(sections[4]);
        setFullMoves(sections[5]);
        setCastlingRights(sections[2]);
        populateBoardFromFenString(sections[0]);
        setEnPassant(sections[3]);
        return board;
    }

    private void populateBoardFromFenString(@NotNull String positionSection) {
        String[] sections = positionSection.split("/");
        for(int row = 0; row < 8; row++){
            for(char character : sections[row].toCharArray()){
                processCharacter(character, 7 - row);
            }
            squaresProcessed = 0;
        }
        board.populateBoard(whitePieces, blackPieces);
    }

    private void processCharacter(char character, int row){
        if(Character.isDigit(character)){
            squaresProcessed += Character.getNumericValue(character);
            return;
        }
        PieceColour colour = getColourFromCharacter(character);
        List<PieceValue> pieces = getPiecesFromChar(character);
        Coordinate position = new Coordinate(squaresProcessed, row);
        switch(Character.toLowerCase(character)){
            case 'r':
                pieces.add(new PieceValue(position, Pieces.ROOK, colour));
                break;
            case 'n':
                pieces.add(new PieceValue(position, Pieces.KNIGHT, colour));
                break;
            case 'b':
                pieces.add(new PieceValue(position, Pieces.BISHOP, colour));
                break;
            case 'k':
                pieces.addFirst(new PieceValue(position, Pieces.KING, colour));
                break;
            case 'q':
                pieces.add(new PieceValue(position, Pieces.QUEEN, colour));
                break;
            case 'p':
                pieces.add(new PieceValue(position, Pieces.PAWN, colour));
                break;
        }
        squaresProcessed++;
    }

    private void setTurnToMove(@NotNull CharSequence turnSection){
        char character = turnSection.charAt(0);
        if(character == 'b')
            board.nextTurn();
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
        PieceColour enPassantColour = board.getTurn().invert();
        int direction = enPassantColour.direction();
        board.setEnPassantSquare(new Coordinate(location.x(), location.y()+direction));
    }

    private void setHalfMoves(String section) {
        numHalfMoves = Integer.parseInt(section);
    }

    public int getNumHalfMoves(){
        return numHalfMoves;
    }

    private void setFullMoves(String section) {
        numFullMoves = Integer.parseInt(section);
    }

    public int getNumFullMoves(){
        return numFullMoves;
    }

    private static PieceColour getColourFromCharacter(char c){
        return Character.isUpperCase(c) ? PieceColour.WHITE : PieceColour.BLACK;
    }

    private List<PieceValue> getPiecesFromChar(char c){
        return Character.isUpperCase(c) ? whitePieces : blackPieces;
    }

    private static boolean doesStringMatchFen(CharSequence fenString){
        return REGEX.matcher(fenString).matches();
    }
}
