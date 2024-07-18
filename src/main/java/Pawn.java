import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Pawn in chess which can move one space forward.
 * On the first move it can move 2 spaces.
 * It can only take diagonally forward.
 */
public class Pawn extends Piece{
    /**
     * Creates a {@code Pawn}
     * @param colour if it is black or white
     */
    public Pawn(Coordinate position, PieceColour colour) {
        super(position,  colour);
    }

    @Override
    void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        int direction = PieceColour.getDirectionFromColour(colour);
        Coordinate forwardOne = new Coordinate(getX(), getY()+direction);
        if(board.isSquareBlank(forwardOne)) {
            possibleMoves.add(forwardOne);
            Coordinate forwardTwo = new Coordinate(getX(), getY()+(direction << 1));
            if(board.isSquareBlank(forwardTwo) && (getY() == 6 || getY() == 1)) // double move first go
                possibleMoves.add(forwardTwo);
        }
        getTakingMoves(board);
        if((colour == PieceColour.BLACK && getY() == 3) || (colour == PieceColour.WHITE && getY() == 4)){ // en passant
            getEnPassantMoves(board);
        }
    }

    private void getEnPassantMoves(@NotNull ChessLogic board){
        int direction = PieceColour.getDirectionFromColour(colour);
        Coordinate enPassantSquare = board.getEnPassantSquare();
        if(enPassantSquare == null)
            return;
        if(getY() != enPassantSquare.y())
            return;
        if(getX()+1 == enPassantSquare.x())
            possibleMoves.add(new Coordinate(getX()+1,getY()+direction));
        else if(getX()-1 == enPassantSquare.x())
            possibleMoves.add(new Coordinate(getX()-1,getY()+direction));
    }

    private void getTakingMoves(@NotNull ChessLogic board){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isEnemyPiece(getX() + 1, getY() + direction, colour))
            possibleMoves.add(new Coordinate(getX()+1,getY()+direction));
        if(board.isEnemyPiece(getX()-1, getY() + direction, colour))
            possibleMoves.add(new Coordinate(getX()-1,getY()+direction));
    }

    @Override
    List<MoveValue> getMoves(ChessLogic board, Coordinate position) {
        List<MoveValue> moves = new ArrayList<>(2);
        if(position.y() == 7 || position.y() == 0) { // if pawn promotion
            moves.add(new MoveValue(this, position));
            moves.add(new MoveValue(new Queen(position, colour), position));
        }else if(position.x() != getX() && board.isSquareBlank(position)){ // if passanting
            int direction = PieceColour.getDirectionFromColour(colour);
            moves.add(board.getMoveForOtherPiece(position.x(), position.y()-direction, position.x(), position.y()));
            moves.add(new MoveValue(this, position));
        }else{
            moves.add(new MoveValue(this, position));
        }
        return moves;
    }

    @Override
    public String toString() {
        return "pawn";
    }

    @Override
    public char toCharacter() {
        return '\u0000';
    }
}
