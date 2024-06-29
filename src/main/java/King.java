import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Objects;

public class King extends Piece{
    private boolean moved = false;

    public King(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y, colour, 'K', board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(8);
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(x, y, moves);
            }
        }
        if(!moved){ // castling
            if(!board.getPiece(x-3, y).hadFirstMove()){
                if(board.isSquareBlank(x-1, y) && board.isSquareBlank(x-2, y))
                    moves.add(new Coordinate(x-2, y));
            }
            if(!board.getPiece(x+4, y).hadFirstMove()){
                if(board.isSquareBlank(x+1, y) && board.isSquareBlank(x+2, y) && board.isSquareBlank(x+3, y))
                    moves.add(new Coordinate(x+2, y));
            }
        }
        removeMovesInCheck(moves);
        // stops castling through check
        if(!moves.contains(new Coordinate(x-1, y)))
            moves.remove(new Coordinate(x-2, y));
        if(!moves.contains(new Coordinate(x+1, y)))
            moves.remove(new Coordinate(x+2, y));
        return moves;
    }

    @Override
    public ArrayList<MoveValue> getMoves(int newX, int newY) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        King king = (King) obj;
        return moved == king.moved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), moved);
    }
}
