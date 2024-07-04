import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece{
    private boolean canBePassanted = false;

    public Pawn(int x, int y, PieceColour colour) {
        super(x, y,  colour);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves(ChessLogic board) {
        ArrayList<Coordinate> moves = new ArrayList<>(4);
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isSquareBlank(x, y+direction)) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if(board.isSquareBlank(x, y+(direction << 1))) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        moves.add(getTakingMove(board, 1));
        moves.add(getTakingMove(board, -1));
        if((colour == PieceColour.BLACK && y == 3) || (colour == PieceColour.WHITE && y == 4)){ // en passant
            moves.add(getEnPassantMove(board, -1));
            moves.add(getEnPassantMove(board, 1));
        }
        removeMovesInCheck(moves, board);
        return moves;
    }

    @Nullable
    private Coordinate getEnPassantMove(ChessLogic board, int leftOrRight){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isPiecePawn(x+leftOrRight, y) && board.hasPieceHadFirstMove(x+leftOrRight, y))
            return new Coordinate(x+leftOrRight,y+direction);
        return null;
    }

    @Nullable
    private Coordinate getTakingMove(ChessLogic board, int leftOrRight){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isEnemyPiece(x + leftOrRight, y + direction, colour))
            return new Coordinate(x+leftOrRight,y+direction);
        return null;
    }

    @Override
    public ArrayList<MoveValue> getMoves(ChessLogic board, int newX, int newY) {
        ArrayList<MoveValue> moves = new ArrayList<>(2);
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
    public String toString() {return "pawn";}

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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), canBePassanted);
    }
}
