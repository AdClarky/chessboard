import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Pawn chess piece
 */
public class Pawn extends Piece{
    private boolean canBePassanted = false;

    /**
     * Initialises the pawn to a set position
     * @param x starting x position
     * @param y starting y position
     * @param direction black or white
     */
    public Pawn(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction, '\u0000');
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(4);
        if(y < 7 && y > 0 && board.isSquareBlank(x, y+direction)) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if (((y == 6 && direction == BLACK_PIECE) || (y == 1 && direction == WHITE_PIECE)) && board.isSquareBlank(x, y + (direction << 1))) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        if(x > 0 && !board.isSquareBlank(x-1,y+direction) && board.getPiece(x-1,y+direction).getDirection() != direction) // can take left
            moves.add(new Coordinate(x-1,y+direction));
        if(x < 7 && !board.isSquareBlank(x+1,y+direction) && board.getPiece(x+1,y+direction).getDirection() != direction) // can take right
            moves.add(new Coordinate(x+1,y+direction));
        if((direction == BLACK_PIECE && y == 3) || (direction == WHITE_PIECE && y == 4)){ // en passant
            if(board.getPiece(x-1,y) instanceof Pawn pawn && pawn.hadFirstMove())
                moves.add(new Coordinate(x-1,y+direction));
            else if (board.getPiece(x+1,y) instanceof Pawn pawn && pawn.hadFirstMove()) {
                moves.add(new Coordinate(x+1,y+direction));
            }
        }
        removeMovesInCheck(board, moves);
        return moves;
    }

    @Override
    public ArrayList<MoveValue> getMoves(int newX, int newY, Board board) {
        ArrayList<MoveValue> moves = new ArrayList<>(2);
        if(newY == 7 || newY == 0) { // if pawn promotion
            moves.add(new MoveValue(this, newX, newY));
            moves.add(new MoveValue(new Queen(newX, newY, direction), newX, newY));
        }else if(newX != x && board.isSquareBlank(newX, newY)){ // if passanting
            moves.add(new MoveValue(board.getPiece(newX, newY-direction), newX, newY));
            moves.add(new MoveValue(this, newX, newY));
        }else{
            moves.add(new MoveValue(this, newX, newY));
        }
        return moves;
    }

    @Override
    public void firstMove() {
        canBePassanted = true;
    }

    @Override
    public boolean hadFirstMove(){
        return canBePassanted;
    }

    @Override
    public void undoMoveCondition(){canBePassanted = false;}


    @Override
    public String toString() {return "";}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Pawn pawn = (Pawn) obj;
        return canBePassanted == pawn.canBePassanted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), canBePassanted);
    }

    private static Icon getIcon(int colour) {
        if(colour == BLACK_PIECE)
            return ImageUtils.getStretchedImage(Pawn.class.getResource("/black_pawn.png"));
        else
            return ImageUtils.getStretchedImage(Pawn.class.getResource("/white_pawn.png"));
    }
}
