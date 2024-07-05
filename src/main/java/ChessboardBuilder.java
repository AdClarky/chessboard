import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/** Used to generate a {@link Chessboard}. Can be built in the default configuration or using a fen string.*/
public class ChessboardBuilder {
    private final Chessboard board = new Chessboard();
    private final List<Piece> whitePieces = new ArrayList<>(16);
    private final List<Piece> blackPieces = new ArrayList<>(16);
    private int squaresProcessed = 7;

    public @NotNull Chessboard defaultSetup() {
        try {
            fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        } catch (InvalidFenStringException e) {
            throw new RuntimeException(e); // should not be possible as its a default fen string
        }
        return board;
    }

    public @NotNull Chessboard fromFen(@NotNull String fenString) throws InvalidFenStringException{
        String[] sections = fenString.split(" ");
        if(sections.length != 6)
            throw new InvalidFenStringException();
        try {
            setHalfMoves(sections[4]);
            setFullMoves(sections[5]);
            populateBoardFromFenString(sections[0]);
            setTurnToMove(sections[1]);
            setCastlingRights(sections[2]);
            setEnPassant(sections[3]);
        } catch (AccessedHistoryDuringGameException e) {
            throw new RuntimeException(e);
        }

        return board;
    }

    private void populateBoardFromFenString(@NotNull String positionSection) throws InvalidFenStringException {
        String[] sections = positionSection.split("/");
        for(int row = 0; row < 8; row++){
            for(char character : sections[row].toCharArray()){
                processCharacter(character, 7 - row);
            }
            squaresProcessed = 7;
        }
        board.populateBoard(whitePieces, blackPieces);
    }

    private void processCharacter(char character, int row) throws InvalidFenStringException {
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
            default:
                throw new InvalidFenStringException();
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
            board.setKingMoved(PieceColour.WHITE);
            board.setKingMoved(PieceColour.BLACK);
            return;
        }
        StringBuilder upper = new StringBuilder();
        StringBuilder lower = new StringBuilder();
        getUpperAndLower(rights, upper, lower);
        if(upper.isEmpty())
            board.setKingMoved(PieceColour.WHITE);
        else if(upper.length() == 1)
            setOtherRookMoved(upper.charAt(0));
        if(lower.isEmpty())
            board.setKingMoved(PieceColour.BLACK);
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
        int x = Character.toLowerCase(rookWithRights) == 'k' ? 0 : 7;
        board.setOtherRookMoved(x, y);
    }

    private void setEnPassant(@NotNull String section){
        if(section.contains("-"))
            return;
        Coordinate location = Coordinate.createCoordinateFromString(section);
        PieceColour enPassantColour = PieceColour.getOtherColour(board.getCurrentTurn());
        int direction = PieceColour.getDirectionFromColour(enPassantColour);
        board.setPawnEnPassantable(location.x(), location.y() + direction);
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
}
