import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChessboardBuilder {
    private final Chessboard board = new Chessboard();
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private int squaresProcessed = 7;

    public @NotNull Chessboard defaultSetup() {
        FromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        return board;
    }

    public @NotNull Chessboard FromFen(String fenString) {
        ArrayList<String> sections = separateIntoSections(fenString, ' ');
        populateBoardFromFenString(sections.getFirst());
        setTurnToMove(sections.get(1));
        setCastlingRights(sections.get(2));
        setEnPassant(sections.get(3));
        setHalfMoves(sections.get(4));
        setFullMoves(sections.get(5));
        return board;
    }

    @NotNull ArrayList<String> separateIntoSections(@NotNull String fenString, char delimiter){
        ArrayList<String> sections = new ArrayList<>(6);
        StringBuilder builder = new StringBuilder();
        for(char c : fenString.toCharArray()){
            if(c == delimiter){
                sections.add(builder.toString());
                builder.setLength(0);
            }else
                builder.append(c);
        }
        sections.add(builder.toString());
        return sections;
    }

    void populateBoardFromFenString(@NotNull String positionSection) {
        ArrayList<String> sections = separateIntoSections(positionSection, '/');
        for(int row = 0; row < 8; row++){
            for(char character : sections.get(row).toCharArray()){
                processCharacter(character, 7 - row);
            }
            squaresProcessed = 7;
        }
        board.populateBoard(whitePieces, blackPieces);
    }

    void processCharacter(char character, int row){
        if(Character.isDigit(character)){
            squaresProcessed -= Character.getNumericValue(character);
            return;
        }
        PieceColour colour = getColourFromCharacter(character);
        ArrayList<Piece> pieces = getPiecesFromChar(character);
        switch(Character.toLowerCase(character)){
            case 'r':
                pieces.add(new Rook(squaresProcessed, row, colour, board));
                break;
            case 'n':
                pieces.add(new Knight(squaresProcessed, row, colour, board));
                break;
            case 'b':
                pieces.add(new Bishop(squaresProcessed, row, colour, board));
                break;
            case 'k':
                pieces.addFirst(new King(squaresProcessed, row, colour, board));
                break;
            case 'q':
                pieces.add(new Queen(squaresProcessed, row, colour, board));
                break;
            case 'p':
                pieces.add(new Pawn(squaresProcessed, row, colour, board));
                break;
        }
        squaresProcessed--;
    }

    void setTurnToMove(CharSequence turnSection){
        char character = turnSection.charAt(0);
        board.setCurrentTurn(character == 'w' ? PieceColour.WHITE : PieceColour.BLACK);
    }

    void setCastlingRights(String rights){
        int length = rights.length();
        if(length == 4)
            return;
        if(rights.contains("-")){
            board.getKing(PieceColour.WHITE).firstMove();
            board.getKing(PieceColour.BLACK).firstMove();
            return;
        }
        StringBuilder upper = new StringBuilder();
        StringBuilder lower = new StringBuilder();
        getUpperAndLower(rights, upper, lower);
        if(upper.isEmpty())
            board.getKing(PieceColour.WHITE).firstMove();
        else if(upper.length() == 1)
            setOtherRookMoved(upper.charAt(0));
        if(lower.isEmpty())
            board.getKing(PieceColour.BLACK).firstMove();
        else if(lower.length() == 1)
            setOtherRookMoved(lower.charAt(0));
    }

    void getUpperAndLower(String rights, StringBuilder upper, StringBuilder lower) {
        for(char c : rights.toCharArray()){
            if(Character.isUpperCase(c))
                upper.append(c);
            else
                lower.append(c);
        }
    }

    void setOtherRookMoved(char rookWithRights){
        int y = Character.isUpperCase(rookWithRights) ? 0 : 7;
        int x = rookWithRights == 'k' ? 0 : 7;
        Rook rookNotMoved = (Rook) board.getPiece(x, y);
        for(Piece piece : board.getAllColourPieces(rookNotMoved.getColour())){
            if(piece instanceof Rook && piece != rookNotMoved){
                piece.firstMove();
            }
        }
    }

    void setEnPassant(String section){
        if(section.contains("-"))
            return;
        Coordinate location = Coordinate.createCoordinateFromString(section);
        PieceColour enPassantColour = PieceColour.getOtherColour(board.getCurrentTurn());
        int direction = PieceColour.getDirectionFromColour(enPassantColour);
        board.getPiece(location.x(), location.y() + direction).firstMove();
    }

    void setHalfMoves(String section){
        board.setNumHalfMoves(Integer.parseInt(section));
    }

    void setFullMoves(String section){
        board.setNumFullMoves(Integer.parseInt(section));
    }

    PieceColour getColourFromCharacter(char c){
        return Character.isUpperCase(c) ? PieceColour.WHITE : PieceColour.BLACK;
    }

    ArrayList<Piece> getPiecesFromChar(char c){
        return Character.isUpperCase(c) ? whitePieces : blackPieces;
    }
}
