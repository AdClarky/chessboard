import java.util.ArrayList;

public class Board {
    private ArrayList<BoardListener> boardListeners = new ArrayList<>(1);
    private Piece pieceSelected = null;
    private final Piece[][] board  =  new Piece[8][8];

    public Board(){
        board[0][0] = new Rook(0, 0, Rook.white, Piece.UP);
        board[0][1] = new Knight(1, 0, Knight.white, Piece.UP);
        board[0][2] = new Bishop(2, 0, Bishop.white, Piece.UP);
        board[0][3] = new Queen(3, 0, Queen.white, Piece.UP);
        board[0][4] = new King(4, 0, King.white, Piece.UP);
        board[0][5] = new Bishop(5, 0, Bishop.white, Piece.UP);
        board[0][6] = new Knight(6, 0, Knight.white, Piece.UP);
        board[0][7] = new Rook(7, 0, Rook.white, Piece.UP);
        board[7][0] = new Rook(0, 0, Rook.black, Piece.DOWN);
        board[7][1] = new Knight(1, 0, Knight.black, Piece.DOWN);
        board[7][2] = new Bishop(2, 0, Bishop.black, Piece.DOWN);
        board[7][3] = new Queen(3, 0, Queen.black, Piece.DOWN);
        board[7][4] = new King(4, 0, King.black, Piece.DOWN);
        board[7][5] = new Bishop(5, 0, Bishop.black, Piece.DOWN);
        board[7][6] = new Knight(6, 0, Knight.black, Piece.DOWN);
        board[7][7] = new Rook(7, 0, Rook.black, Piece.DOWN);
        for(int x = 0; x < 8; x++){
            board[6][x] = new Pawn(x, 6, Pawn.black, Piece.DOWN);
            board[1][x] = new Pawn(x, 1, Pawn.white, Piece.UP);
        }
    }

    public Piece getPiece(int x, int y){return board[y][x];}

    public void movePiece(int x, int y, Piece piece){

    }

    public void setSelectedPiece(Piece piece){
        pieceSelected = piece;
    }

    public void removeSelectedPiece(){
        pieceSelected = null;
    }

    public void addBoardListener(BoardListener listener){
        boardListeners.add(listener);
    }

    public void notifyBoardListeners(Piece pieceChanged){
        for(BoardListener listener : boardListeners){
            listener.boardChanged(pieceChanged);
        }
    }
}
