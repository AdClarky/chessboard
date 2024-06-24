import javax.swing.Icon;
import java.util.ArrayList;

/**
 * Chess board king piece.
 * Only one per side.
 */
public class King extends Piece{
    private boolean notMoved = true;

    /**
     * Initialises the king piece.
     * @param x starting x value
     * @param y starting y value
     * @param direction white or black
     */
    public King(int x, int y, int direction) {
        super(x, y, getIcon(direction), direction);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(Board board) {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(x, y, board, moves);
            }
        }
        if(notMoved){ // castling
            if(board.getPiece(x-3, y) instanceof Rook rook && rook.hadFirstMove()){
                if(board.isSquareBlank(x-1, y) && board.isSquareBlank(x-2, y))
                    moves.add(new Coordinate(x-2, y));
            }
            if(board.getPiece(x+4, y) instanceof Rook rook && rook.hadFirstMove()){
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
    public ArrayList<Move> getMoves(int newX, int newY, Board board) {
        ArrayList<Move> moves = new ArrayList<>(2);
        if(newX - x == -2) { // short castle
            moves.add(new Move(0, newY, 2   , newY));
        }else if(x - newX == -2) {
            moves.add(new Move(7, newY, 4, newY));
        }
        moves.add(new Move(x, y, newX, newY));
        return moves;
    }

    @Override
    public void firstMove(){
        notMoved = false;
    }

    @Override
    public boolean hadFirstMove(){return notMoved;}

    @Override
    public String toString() {
        return "K";
    }

    private static Icon getIcon(int direction){
        if(direction == BLACK_PIECE)
            return ImageUtils.getStretchedImage("src/main/resources/black_king.png");
        else
            return ImageUtils.getStretchedImage("src/main/resources/white_king.png");
    }
}
