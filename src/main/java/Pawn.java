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
     * @param x starting x position
     * @param y starting y position
     * @param colour if it is black or white
     */
    public Pawn(int x, int y, PieceColour colour) {
        super(x, y,  colour);
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
    List<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        List<MoveValue> moves = new ArrayList<>(2);
        Coordinate newPosition = new Coordinate(newX, newY);
        if(newY == 7 || newY == 0) { // if pawn promotion
            moves.add(new MoveValue(this, newX, newY));
            moves.add(new MoveValue(new Queen(newX, newY, colour), newX, newY));
        }else if(newX != getX() && board.isSquareBlank(newPosition)){ // if passanting
            int direction = PieceColour.getDirectionFromColour(colour);
            moves.add(board.getMoveForOtherPiece(newX, newY-direction, newX, newY));
            moves.add(new MoveValue(this, newX, newY));
        }else{
            moves.add(new MoveValue(this, newX, newY));
        }
        return moves;
    }

    @Override
    void firstMove() {}

    @Override
    public String toString() {
        return "pawn";
    }

    @Override
    public char toCharacter() {
        return '\u0000';
    }
}
