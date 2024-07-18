import java.util.ArrayList;
import java.util.List;
/**
 * A King in chess which can only move to adjacent squares.
 */
public class King extends Piece{
    /**
     * Creates a {@code King}
     * @param colour if it is black or white
     */
    public King(Coordinate position, PieceColour colour) {
        super(position, colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        int currentX = getX(), currentY = getY();
        for(int y = currentY-1; y <= currentY+1; y++) {
            for(int x = currentX-1; x <= currentX+1 ; x++) {
                addIfInRange(x, y);
            }
        }
        possibleMoves.remove(new Coordinate(currentX, currentY));
        calculateCastling(board);
    }

    private void calculateCastling(ChessLogic board){
        if(!board.canAnythingCastle())
            return;
        if(!board.canKingCastle(colour))
            return;
        int backRow = colour == PieceColour.WHITE ? 0 : 7;
        if(board.canCastle(new Coordinate(getX()-3, backRow))){
            if(board.isSquareBlank(getX()-1, backRow) &&
                    board.isSquareBlank(getX()-2, backRow)) {
                possibleMoves.add(new Coordinate(getX() - 2, backRow));
            }
        }
        if(board.canCastle(new Coordinate(getX()+4, backRow))){
            if(board.isSquareBlank(getX()+1, backRow) &&
                    board.isSquareBlank(getX()+2, backRow) &&
                    board.isSquareBlank(getX()+3, backRow)) {
                possibleMoves.add(new Coordinate(getX() + 2, backRow));
            }
        }
    }

    void removeCastlingThroughCheck(){
        if(!possibleMoves.contains(new Coordinate(getX()-1, getY())))
            possibleMoves.remove(new Coordinate(getX()-2, getY()));
        if(!possibleMoves.contains(new Coordinate(getX()+1, getY())))
            possibleMoves.remove(new Coordinate(getX()+2, getY()));
    }

    @Override
    List<MoveValue> getMoves(ChessLogic board, Coordinate position) {
        List<MoveValue> moves = new ArrayList<>(2);
        if(position.x() - getX() == -2) { // short castle
            moves.add(board.getMoveForOtherPiece(0, position.y(), 2, position.y()));
        }else if(getX() - position.x() == -2) {
            moves.add(board.getMoveForOtherPiece(7, position.y(), 4, position.y()));
        }
        moves.add(new MoveValue(this, position));
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
}
