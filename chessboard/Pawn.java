package chessboard;

import assets.ImageUtils;
import javax.swing.Icon;
import java.util.ArrayList;

public class Pawn extends Piece{
    private boolean canBePassanted = false;

    public Pawn(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(4);
        if(y < 7 && y > 0 && board.getPiece(x, y+direction) == null) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if (((y == 6 && direction == BLACK_PIECE) || (y == 1 && direction == WHITE_PIECE)) && board.getPiece(x, y + (direction << 1)) == null) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        if(x > 0 && board.getPiece(x-1,y+direction) != null && board.getPiece(x-1,y+direction).getDirection() != direction) // can take left
            moves.add(new Coordinate(x-1,y+direction));
        if(x < 7 && board.getPiece(x+1,y+direction) != null && board.getPiece(x+1,y+direction).getDirection() != direction) // can take right
            moves.add(new Coordinate(x+1,y+direction));
        if((direction == BLACK_PIECE && y == 3) || (direction == WHITE_PIECE && y == 4)){ // en passant
            if(board.getPiece(x-1,y) instanceof Pawn pawn && pawn.canBePassanted())
                moves.add(new Coordinate(x-1,y+direction));
            else if (board.getPiece(x+1,y) instanceof Pawn pawn && pawn.canBePassanted()) {
                moves.add(new Coordinate(x+1,y+direction));
            }
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getMoves(int newX, int newY, Board board) {
        ArrayList<Move> moves = new ArrayList<>(2);
        if(newY == 7 || newY == 0) { // if pawn promotion
            moves.add(new Move(x, y, newX, newY));
            moves.add(new Move(newX, newY, newX, newY));
        }else if(newX != x && (board.getPiece(newX, newY)== null)){ // if passanting
            moves.add(new Move(newX, newY-direction, newX, newY));
            moves.add(new Move(x, y, newX, newY));
        }else{
            moves.add(new Move(x, y, newX, newY));
        }
        return moves;
    }

    @Override
    public void firstMove() {
        canBePassanted = true;
    }

    public void setCanBePassanted(boolean passantable) {canBePassanted = passantable;}

    private boolean canBePassanted() {return canBePassanted;}

    @Override
    public String toString() {
        return "ChessBoard.Pawn, " + x + "," + y + ", " + direction + "; ";
    }

    private static Icon getIcon(int direction) {
        if (direction == BLACK_PIECE)
            return ImageUtils.getStrechedImage("assets/black_pawn.png");
        else
            return ImageUtils.getStrechedImage("assets/white_pawn.png");
    }
}
