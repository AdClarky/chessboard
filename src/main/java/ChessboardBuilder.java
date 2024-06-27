import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChessboardBuilder {
    Chessboard board = new Chessboard();
    ArrayList<Piece> whitePieces = new ArrayList<>(16);
    ArrayList<Piece> blackPieces = new ArrayList<>(16);
    int squaresProcessed = 7;

    public @NotNull Chessboard createChessboardDefaultSetup() {
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Knight(1, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Bishop(2, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Queen(4, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Bishop(5, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Knight(6, 0, Piece.WHITE_PIECE, board));
        whitePieces.add(new Rook(7, 0, Piece.WHITE_PIECE, board));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Rook(0, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Knight(1, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Bishop(2, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Queen(4, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Bishop(5, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Knight(6, 7, Piece.BLACK_PIECE, board));
        blackPieces.add(new Rook(7, 7, Piece.BLACK_PIECE, board));
        for(int x = 0; x < 8; x++){
            blackPieces.add(new Pawn(x, 6, Piece.BLACK_PIECE, board));
            whitePieces.add(new Pawn(x, 1, Piece.WHITE_PIECE, board));
        }
        board.populateBoard(whitePieces, blackPieces);
        return board;
    }

    public @NotNull Chessboard createChessboardFromFen(String fenString) {
        ArrayList<String> sections = separateIntoSections(fenString, ' ');
        populateBoardFromFenString(sections.getFirst());
        setTurnToMove(sections.get(1));
        setCastlingRights(sections.get(2));
        setEnPassant(sections.get(3));
        setHalfMoves(sections.get(4));
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
        int colour = getColourFromCharacter(character);
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
        board.setCurrentTurn(getColourFromCharacter(character));
    }

    void setCastlingRights(String rights){
        int length = rights.length();
        if(length == 4)
            return;
        if(rights.contains("-")){
            board.getKing(Piece.WHITE_PIECE).firstMove();
            board.getKing(Piece.BLACK_PIECE).firstMove();
            return;
        }
        StringBuilder upper = new StringBuilder();
        StringBuilder lower = new StringBuilder();
        getUpperAndLower(rights, upper, lower);
        if(upper.isEmpty())
            board.getKing(Piece.WHITE_PIECE).firstMove();
        else if(upper.length() == 1)
            setOtherRookMoved(upper.charAt(0));
        if(lower.isEmpty())
            board.getKing(Piece.WHITE_PIECE).firstMove();
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
        for(Piece piece : board.getColourPieces(rookNotMoved.getDirection())){
            if(piece instanceof Rook && piece != rookNotMoved){
                piece.firstMove();
            }
        }
    }

    void setEnPassant(String section){
        if(section.contains("-"))
            return;
        Coordinate location = Coordinate.createCoordinateFromString(section);
        board.getPiece(location.x() + board.getCurrentTurn(), location.y()).firstMove();
    }

    void setHalfMoves(String section){
        board.setNumHalfMoves(Integer.parseInt(section));
    }

    int getColourFromCharacter(char c){
        return Character.isUpperCase(c) ? Piece.WHITE_PIECE : Piece.BLACK_PIECE;
    }

    ArrayList<Piece> getPiecesFromChar(char c){
        return Character.isUpperCase(c) ? whitePieces : blackPieces;
    }
}
