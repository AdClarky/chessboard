import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pawn extends Piece{
    private boolean canBePassanted = false;

    public Pawn(int x, int y, PieceColour colour) {
        super(x, y,  colour);
    }

    @Override
    public void calculatePossibleMoves(ChessLogic board) {
        possibleMoves.clear();
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isSquareBlank(x, y+direction)) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if(board.isSquareBlank(x, y+(direction << 1))) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        moves.addAll(getTakingMoves(board));
        if((colour == PieceColour.BLACK && y == 3) || (colour == PieceColour.WHITE && y == 4)){ // en passant
            moves.addAll(getEnPassantMoves(board));
        }
        removeMovesInCheck(moves, board);
        return moves;
    }

    @NotNull
    private List<Coordinate> getEnPassantMoves(@NotNull ChessLogic board){
        List<Coordinate> moves = new ArrayList<>(2);
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isPiecePawn(x+1, y) && board.hasPieceHadFirstMove(x+1, y))
             moves.add(new Coordinate(x+1,y+direction));
        if(board.isPiecePawn(x-1, y) && board.hasPieceHadFirstMove(x-1, y))
            moves.add(new Coordinate(x-1,y+direction));
        return moves;
    }

    @NotNull
    private List<Coordinate> getTakingMoves(@NotNull ChessLogic board){
        List<Coordinate> moves = new ArrayList<>(2);
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isEnemyPiece(x + 1, y + direction, colour))
            moves.add(new Coordinate(x+1,y+direction));
        if(board.isEnemyPiece(x-1, y + direction, colour))
            moves.add(new Coordinate(x-1,y+direction));
        return moves;
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
