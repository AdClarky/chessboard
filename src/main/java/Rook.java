import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Chess Rook piece
 */
public class Rook extends Piece{
    private boolean moved = false;

    /**
     * Initialises Rook piece
     * @param x starting x
     * @param y starting y
     * @param direction black or white
     */
    public Rook(int x, int y, int direction, Chessboard board) {
        super(x, y, getIcon(direction), direction, 'R', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(10);
        calculateStraightMoves(board, moves);
        removeMovesInCheck(moves);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rook rook = (Rook) o;
        return moved == rook.moved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moved);
    }

    private static @NotNull Icon getIcon(int colour){
        if(colour == BLACK_PIECE)
            return ImageUtils.getStretchedImage(Rook.class.getResource("/black_rook.png"));
        else
            return ImageUtils.getStretchedImage(Rook.class.getResource("/white_rook.png"));
    }
}
