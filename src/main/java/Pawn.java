import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Pawn in chess which can move one space forward.
 * On the first move it can move 2 spaces.
 * It can only take diagonally forward.
 */
public class Pawn extends Piece{
    private boolean canBePassanted = false;

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
        if(board.isSquareBlank(x, y+direction)) {// basic move forward
            possibleMoves.add(new Coordinate(x, y + direction));
            if(board.isSquareBlank(x, y+(direction << 1))) // double move first go
                possibleMoves.add(new Coordinate(x, y + (direction << 1)));
        }
        getTakingMoves(board);
        if((colour == PieceColour.BLACK && y == 3) || (colour == PieceColour.WHITE && y == 4)){ // en passant
            getEnPassantMoves(board);
        }
    }

    private void getEnPassantMoves(@NotNull ChessLogic board){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isPiecePawn(x+1, y) && board.hasPieceHadFirstMove(x+1, y))
             possibleMoves.add(new Coordinate(x+1,y+direction));
        if(board.isPiecePawn(x-1, y) && board.hasPieceHadFirstMove(x-1, y))
            possibleMoves.add(new Coordinate(x-1,y+direction));
    }

    private void getTakingMoves(@NotNull ChessLogic board){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isEnemyPiece(x + 1, y + direction, colour))
            possibleMoves.add(new Coordinate(x+1,y+direction));
        if(board.isEnemyPiece(x-1, y + direction, colour))
            possibleMoves.add(new Coordinate(x-1,y+direction));
    }

    @Override
    List<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        List<MoveValue> moves = new ArrayList<>(2);
        if(newY == 7 || newY == 0) { // if pawn promotion
            moves.add(new MoveValue(this, newX, newY));
            moves.add(new MoveValue(new Queen(newX, newY, colour), newX, newY));
        }else if(newX != x && board.isSquareBlank(newX, newY)){ // if passanting
            int direction = PieceColour.getDirectionFromColour(colour);
            moves.add(board.getMoveForOtherPiece(newX, newY-direction, newX, newY));
            moves.add(new MoveValue(this, newX, newY));
        }else{
            moves.add(new MoveValue(this, newX, newY));
        }
        return moves;
    }

    @Override
    void firstMove() {
        canBePassanted = true;
    }

    @Override
    public boolean hadFirstMove(){
        return canBePassanted;
    }

    @Override
    void undoMoveCondition(){canBePassanted = false;}


    @Override
    public String toString() {
        return "pawn";
    }

    @Override
    public char toCharacter() {
        return '\u0000';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Pawn pawn = (Pawn) obj;
        return canBePassanted == pawn.canBePassanted;
    }
}
