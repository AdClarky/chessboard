import javax.swing.Icon;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Chess board king piece.
 * Only one per side.
 */
public class King extends Piece{
    private boolean moved = false;

    /**
     * Initialises the king piece.
     * @param x starting x value
     * @param y starting y value
     * @param direction white or black
     */
    public King(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction, 'K');
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(x, y, board, moves);
            }
        }
        if(!moved){ // castling
            if(board.getPiece(x-3, y) instanceof Rook rook && !rook.hadFirstMove()){
                if(board.isSquareBlank(x-1, y) && board.isSquareBlank(x-2, y))
                    moves.add(new Coordinate(x-2, y));
            }
            if(board.getPiece(x+4, y) instanceof Rook rook && !rook.hadFirstMove()){
                if(board.isSquareBlank(x+1, y) && board.isSquareBlank(x+2, y) && board.isSquareBlank(x+3, y))
                    moves.add(new Coordinate(x+2, y));
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
    public ArrayList<MoveValue> getMoves(int newX, int newY, Board board) {
        ArrayList<MoveValue> moves = new ArrayList<>(2);
        if(newX - x == -2) { // short castle
            moves.add(new MoveValue(board.getPiece(0, newY), 2   , newY));
        }else if(x - newX == -2) {
            moves.add(new MoveValue(board.getPiece(7, newY), 4, newY));
        }
        moves.add(new MoveValue(this, newX, newY));
        return moves;
    }

    @Override
    public void firstMove(){
        moved = true;
    }

    @Override
    public boolean hadFirstMove(){return moved;}

    @Override
    public void undoMoveCondition(){moved = false;}

    // TODO: test king equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        King king = (King) o;
        return moved == king.moved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moved);
    }

    private static Icon getIcon(int colour){
        if(colour == BLACK_PIECE)
            return ImageUtils.getStretchedImage(King.class.getResource("/black_king.png"));
        else
            return ImageUtils.getStretchedImage(King.class.getResource("/white_king.png"));
    }
}
