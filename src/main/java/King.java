import java.util.ArrayList;
import java.util.List;
/**
 * A King in chess which can only move to adjacent squares.
 */
public class King extends Piece{
    private boolean moved = false;

    /**
     * Creates a {@code King}
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public King(int x, int y, PieceColour colour) {
        super(x, y, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        for(int y = getY()-1; y <= getY()+1; y++) {
            for(int x = getX()-1; x <= getX()+1 ; x++) {
                cantMove(board, x, y);
                if(x == getX() && y == getY())
                    possibleMoves.removeLast();
            }
        }
        if(!moved)
            calculateCastling(board);
    }

    private void calculateCastling(ChessLogic board){
        if(!board.hasPieceHadFirstMove(getX()-3, getY())){
            if(board.isSquareBlank(getX()-1, getY()) && board.isSquareBlank(getX()-2, getY()))
                possibleMoves.add(new Coordinate(getX()-2, getY()));
        }
        if(!board.hasPieceHadFirstMove(getX()+4, getY())){
            if(board.isSquareBlank(getX()+1, getY()) && board.isSquareBlank(getX()+2, getY()) && board.isSquareBlank(getX()+3, getY()))
                possibleMoves.add(new Coordinate(getX()+2, getY()));
        }
    }

    void removeCastlingThroughCheck(){
        if(!possibleMoves.contains(new Coordinate(getX()-1, getY())))
            possibleMoves.remove(new Coordinate(getX()-2, getY()));
        if(!possibleMoves.contains(new Coordinate(getX()+1, getY())))
            possibleMoves.remove(new Coordinate(getX()+2, getY()));
    }

    @Override
    List<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        List<MoveValue> moves = new ArrayList<>(2);
        if(newX - getX() == -2) { // short castle
            moves.add(board.getMoveForOtherPiece(0, newY, 2, newY));
        }else if(getX() - newX == -2) {
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
    void firstMove(){
        moved = true;
    }

    @Override
    public boolean hadFirstMove(){return moved;}

    @Override
    void undoMoveCondition(){moved = false;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        King king = (King) obj;
        return moved == king.moved;
    }
}
