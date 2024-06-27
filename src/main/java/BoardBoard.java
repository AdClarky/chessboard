import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A regular chess board.
 * Only method for interaction is the moveWithValidation function.
 * At the end of a move boardChanged is called.
 * Can use getMoves to find the individual moves made - e.g. in castling what moves were made.
 */
public class BoardBoard {
    private final Piece[][] board  =  new Piece[8][8];
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);

    /**
     * Initialises the board with the pieces in default positions.
     * Blank squares are null.
     */
    public BoardBoard(){
        whitePieces.add(new King(3, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(0, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Knight(1, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Bishop(2, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Queen(4, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Bishop(5, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Knight(6, 0, Piece.WHITE_PIECE));
        whitePieces.add(new Rook(7, 0, Piece.WHITE_PIECE));
        blackPieces.add(new King(3, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Rook(0, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Knight(1, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Bishop(2, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Queen(4, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Bishop(5, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Knight(6, 7, Piece.BLACK_PIECE));
        blackPieces.add(new Rook(7, 7, Piece.BLACK_PIECE));
        for(int x = 0; x < 8; x++){
            blackPieces.add(new Pawn(x, 6, Piece.BLACK_PIECE));
            whitePieces.add(new Pawn(x, 1, Piece.WHITE_PIECE));
        }
        for(int y = 2; y < 6; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /**
     * Intiialises the board with only the pieces in the collection. Their position on the board
     * is determined by their x and y values. Every other square is blank.
     * @param whitePieces
     * @param blackPieces
     */
    public BoardBoard(Collection<Piece> whitePieces, Collection<Piece> blackPieces){
        this.whitePieces.addAll(whitePieces);
        this.blackPieces.addAll(blackPieces);
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                board[y][x] = new Blank(x, y);
            }
        }
        for(Piece piece : blackPieces){
            board[piece.getY()][piece.getX()] = piece;
        }
        for(Piece piece : whitePieces){
            board[piece.getY()][piece.getX()] = piece;
        }
    }

    /**
     * Finds the piece in a specific square
     * @param x x position
     * @param y y position
     * @return the piece on that square.
     */
    @NotNull
    public Piece getPiece(int x, int y){
        if(x < 0 || x >= 8 || y < 0 || y >= 8)
            return new Blank(x, y);
        return board[y][x];
    }

    /**
     * Checks if a square is blank or contains a piece
     * @param x x value of square to check
     * @param y y value of square to check
     * @return if the square is blank
     */
    public boolean isSquareBlank(int x, int y){return board[y][x] instanceof Blank;}

    void setSquare(int x, int y, Piece piece){board[y][x] = piece;}

    ArrayList<Piece> getColourPieces(int direction){
        if(direction == Piece.BLACK_PIECE)
            return blackPieces;
        return whitePieces;
    }

    void addPiece(@NotNull Piece piece){
        if(piece.getDirection() == Piece.BLACK_PIECE)
            blackPieces.add(piece);
        else
            whitePieces.add(piece);
    }

    void removePiece(@NotNull Piece piece){
        if(piece.getDirection() == Piece.BLACK_PIECE)
            blackPieces.remove(piece);
        else
            whitePieces.remove(piece);
    }

    int boardState(){
        return Arrays.deepHashCode(board);
    }
}
