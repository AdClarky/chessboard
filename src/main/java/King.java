import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class King extends Piece{
    private boolean moved = false;

    public King(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        for(int y = this.y-1; y <= this.y+1; y++) {
            for(int x = this.x-1; x <= this.x+1 ; x++) {
                cantMove(moves, board, x, y);
            }
        }
        if(moved){
            removeMovesInCheck(moves, board);
        }
        calculateCastling(moves, board);
    }

    private void calculateCastling(Collection<Coordinate> moves, ChessLogic board){
        if(!board.hasPieceHadFirstMove(x-3, y)){
            if(board.isSquareBlank(x-1, y) && board.isSquareBlank(x-2, y))
                moves.add(new Coordinate(x-2, y));
        }
        if(!board.hasPieceHadFirstMove(x-4, y)){
            if(board.isSquareBlank(x+1, y) && board.isSquareBlank(x+2, y) && board.isSquareBlank(x+3, y))
                moves.add(new Coordinate(x+2, y));
        }
        removeMovesInCheck(moves, board);
        // stops castling through check
        if(!moves.contains(new Coordinate(x-1, y)))
            moves.remove(new Coordinate(x-2, y));
        if(!moves.contains(new Coordinate(x+1, y)))
            moves.remove(new Coordinate(x+2, y));
    }

    @Override
    public ArrayList<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        ArrayList<MoveValue> moves = new ArrayList<>(2);
        if(newX - x == -2) { // short castle
            moves.add(board.getMoveForOtherPiece(0, newY, 2, newY));
        }else if(x - newX == -2) {
            moves.add(board.getMoveForOtherPiece(7, newY, 4, newY));
        }
        moves.add(new MoveValue(this, newX, newY));
        return moves;
    }

    @Override
    public String toString() {
        return "king";
    }

    @Override
    public char toCharacter() {
        return 'K';
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
