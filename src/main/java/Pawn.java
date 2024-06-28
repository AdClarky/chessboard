import org.jetbrains.annotations.NotNull;

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
     * @param colour black or white
     */
    public Pawn(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, getIcon(colour), colour, '\u0000', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(4);
        int direction = PieceColour.getDirectionFromColour(colour);
        if(y < 7 && y > 0 && board.isSquareBlank(x, y+direction)) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if (((y == 6 && colour == PieceColour.BLACK) || (y == 1 && colour == PieceColour.WHITE)) && board.isSquareBlank(x, y + (direction << 1))) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        if(x > 0 && !board.isSquareBlank(x-1,y+direction) && board.getPiece(x-1,y+direction).getColour() != colour) // can take left
            moves.add(new Coordinate(x-1,y+direction));
        if(x < 7 && !board.isSquareBlank(x+1,y+direction) && board.getPiece(x+1,y+direction).getColour() != colour) // can take right
            moves.add(new Coordinate(x+1,y+direction));
        if((colour == PieceColour.BLACK && y == 3) || (colour == PieceColour.WHITE && y == 4)){ // en passant
            if(board.getPiece(x-1,y) instanceof Pawn pawn && pawn.hadFirstMove())
                moves.add(new Coordinate(x-1,y+direction));
            else if (board.getPiece(x+1,y) instanceof Pawn pawn && pawn.hadFirstMove()) {
                moves.add(new Coordinate(x+1,y+direction));
            }
        }
        removeMovesInCheck(moves);
        return moves;
    }

    @Override
    public ArrayList<MoveValue> getMoves(int newX, int newY) {
        ArrayList<MoveValue> moves = new ArrayList<>(2);
        if(newY == 7 || newY == 0) { // if pawn promotion
            moves.add(new MoveValue(this, newX, newY));
            moves.add(new MoveValue(new Queen(newX, newY, colour, board), newX, newY));
        }else if(newX != x && board.isSquareBlank(newX, newY)){ // if passanting
            int direction = PieceColour.getDirectionFromColour(colour);
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

    private static @NotNull Icon getIcon(PieceColour colour) {
        if(colour == PieceColour.BLACK)
            return ImageUtils.getStretchedImage(Pawn.class.getResource("/black_pawn.png"));
        else
            return ImageUtils.getStretchedImage(Pawn.class.getResource("/white_pawn.png"));
    }
}
