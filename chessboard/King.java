package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class King extends Piece{
    private boolean notMoved = true;

    public King(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(x, y, board, moves);
            }
        }
        if(notMoved){ // castling
            if(board.getPiece(x+3, y) instanceof Rook rook && rook.getNotMoved()){
                if(board.getPiece(x+1, y) == null && board.getPiece(x+2, y) == null)
                    moves.add(new Coordinate(x+2, y));
            }
            if(board.getPiece(x-4, y) instanceof Rook rook && rook.getNotMoved()){
                if(board.getPiece(x-1, y) == null && board.getPiece(x-2, y) == null && board.getPiece(x-3, y) == null)
                    moves.add(new Coordinate(x-2, y));
            }
        }
        removeMovesInCheck(board, moves);
        // stops castling through check
        if(!moves.contains(new Coordinate(x-1, y)))
            moves.remove(new Coordinate(x-2, y));
        if(!moves.contains(new Coordinate(x+1, y)))
            moves.remove(new Coordinate(x+2, y));
        return moves;
    }

    @Override
    public ArrayList<Move> getMoves(int newX, int newY, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if(newX - x == -2) { // long castle
            moves.add(new Move(0, newY, 3, newY));
        }else if(x - newX == -2) {
            moves.add(new Move(7, newY, 5, newY));
        }
        moves.add(new Move(x, y, newX, newY));
        return moves;
    }

    @Override
    public void firstMove(){
        notMoved = false;
    }

    @Override
    public String toString() {
        return "ChessBoard.King, " + x + "," + y + ", " + direction + "; ";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_king.png");
        else
            return ImageUtils.getStrechedImage("assets/white_king.png");
    }
}
