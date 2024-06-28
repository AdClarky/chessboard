import javax.swing.Icon;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Bishop for Chess. Only moves diagonally.
 */
public class Bishop extends Piece{
    /**
     * Constructor for a bishop.
     * @param x starting x position
     * @param y starting y position
     * @param colour {@link PieceColour}
     */
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
    private static Icon getIcon(PieceColour colour){
        if(colour == PieceColour.BLACK)
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/black_bishop.png"));
        else
            return ImageUtils.getStretchedImage(Bishop.class.getResource("/white_bishop.png"));
    }
}
