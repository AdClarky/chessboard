package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Pawn extends Piece{
    public static Icon black = ImageUtils.getStrechedImage("assets/black_pawn.png");
    public static Icon white = ImageUtils.getStrechedImage("assets/white_pawn.png");
    private boolean canBePassanted = false;

    public Pawn(int x, int y, Icon icon, int direction) {
        super(x, y, icon, direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        if(y != 7 && y != 0 && board.getPiece(x, y+direction) == null) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if (((y == 6 && direction == UP) || (y == 1 && direction == DOWN)) && board.getPiece(x, y + (direction << 1)) == null) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        if(x != 0 && board.getPiece(x-1,y+direction) != null && board.getPiece(x-1,y+direction).getDirection() != direction) // can take left
            moves.add(new Coordinate(x-1,y+direction));
        if(x != 7 && board.getPiece(x+1,y+direction) != null && board.getPiece(x+1,y+direction).getDirection() != direction) // can take right
            moves.add(new Coordinate(x+1,y+direction));
        if((direction == UP && y == 3) || (direction == DOWN && y == 4)){ // en passant
            if(board.getPiece(x-1,y) instanceof Pawn pawn && pawn == board.getPassantable())
                moves.add(new Coordinate(x-1,y+direction));
            else if (board.getPiece(x+1,y) instanceof Pawn pawn && pawn == board.getPassantable()) {
                moves.add(new Coordinate(x+1,y+direction));
            }
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public String toString() {
        return "ChessBoard.Pawn, " + x + "," + y + ", " + direction + "; ";
    }
}
