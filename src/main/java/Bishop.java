import org.jetbrains.annotations.NotNull;
import javax.swing.Icon;
import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, getIcon(colour), colour, 'B', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        calculateDiagonalMoves(board, moves);
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public void firstMove() {}

    @Override
    public void undoMoveCondition(){}

    @Override
    public boolean hadFirstMove(){return false;}

    /**
     * Returns an icon based on the piece colour.
     * @param colour the colour of the piece
     * @return an icon which is white or black.
     */
    private static @NotNull Icon getIcon(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/black_bishop.png"));
        else if(colour == PieceColour.WHITE)
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/white_bishop.png"));
        throw new IllegalArgumentException("Invalid colour");
    }
}
