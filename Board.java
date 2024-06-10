import javax.swing.JFrame;
import java.awt.Color;

public class Board {
    private final Piece[][] board  =  new Piece[8][8];

    public Board(){
        board[0][0] = new Rook(0, 0, Rook.white);
        board[0][1] = new Knight(1, 0, Knight.white);
        board[0][2] = new Bishop(2, 0, Bishop.white);
        board[0][3] = new Queen(3, 0, Queen.white);
        board[0][4] = new King(4, 0, King.white);
        board[0][5] = new Bishop(5, 0, Bishop.white);
        board[0][6] = new Knight(6, 0, Knight.white);
        board[0][7] = new Rook(7, 0, Rook.white);
        board[7][0] = new Rook(0, 0, Rook.black);
        board[7][1] = new Knight(1, 0, Knight.black);
        board[7][2] = new Bishop(2, 0, Bishop.black);
        board[7][3] = new Queen(3, 0, Queen.black);
        board[7][4] = new King(4, 0, King.black);
        board[7][5] = new Bishop(5, 0, Bishop.black);
        board[7][6] = new Knight(6, 0, Knight.black);
        board[7][7] = new Rook(7, 0, Rook.black);
        for(int x = 0; x < 8; x++){
            board[6][x] = new Pawn(x, 6, Pawn.black);
            board[1][x] = new Pawn(x, 1, Pawn.white);
        }
    }

    public Piece getPiece(int x, int y){return board[y][x];}
}
