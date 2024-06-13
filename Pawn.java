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
        if(y != 7 && y != 0 && board.getPiece(x, y+direction) == null)
            moves.add(new Coordinate(x, y+direction));
        if(((y == 6 && direction == UP) || (y == 1 && direction == DOWN)) && board.getPiece(x, y+(direction<<1)) == null)
            moves.add(new Coordinate(x, y+(direction<<1)));
        if(x != 0 && board.getPiece(x-1,y+direction) != null && board.getPiece(x-1,y+direction).getDirection() != direction)
            moves.add(new Coordinate(x-1,y+direction));
        if(x != 7 && board.getPiece(x+1,y+direction) != null && board.getPiece(x+1,y+direction).getDirection() != direction)
            moves.add(new Coordinate(x+1,y+direction));
        if((direction == UP && y == 3) || (direction == DOWN && y == 4)){
            if(board.getPiece(x-1,y) instanceof Pawn pawn && pawn.getPassantable())
                moves.add(new Coordinate(x-1,y+direction));
            else if (board.getPiece(x+1,y) instanceof Pawn pawn && pawn.getPassantable()) {
                moves.add(new Coordinate(x+1,y+direction));
            }
        }
        checkMovesForCheck(board, moves);
        return moves;
    }

    public boolean getPassantable(){return canBePassanted;}

    public void setCanBePassanted(boolean canBePass) {this.canBePassanted = canBePass;}

    @Override
    public String toString() {
        return "Pawn, " + x + "," + y + ", " + direction + "; ";
    }
}
