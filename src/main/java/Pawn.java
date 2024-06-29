import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Pawn extends Piece{
    private boolean canBePassanted = false;

    public Pawn(int x, int y, PieceColour colour, Chessboard board) {
        super(x, y,  colour, board);
    }

    @Override
    public ArrayList<Coordinate> getPossibleMoves() {
        ArrayList<Coordinate> moves = new ArrayList<>(4);
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.isSquareBlank(x, y+direction)) {// basic move forward
            moves.add(new Coordinate(x, y + direction));
            if(board.isSquareBlank(x, y+(direction << 1))) // double move first go
                moves.add(new Coordinate(x, y + (direction << 1)));
        }
        addTakingMove(moves, 1);
        addTakingMove(moves, -1);
        if((colour == PieceColour.BLACK && y == 3) || (colour == PieceColour.WHITE && y == 4)){ // en passant
            addEnPassantMoves(moves, -1);
            addEnPassantMoves(moves, 1);
        }
        removeMovesInCheck(moves);
        return moves;
    }

    private void addEnPassantMoves(Collection<Coordinate> moves, int leftOrRight){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.getPiece(x+leftOrRight,y) instanceof Pawn pawn && pawn.hadFirstMove())
            moves.add(new Coordinate(x+leftOrRight,y+direction));
    }

    private void addTakingMove(Collection<Coordinate> moves, int leftOrRight){
        int direction = PieceColour.getDirectionFromColour(colour);
        if(board.getPiece(x+leftOrRight,y+direction).getColour() == PieceColour.getOtherColour(colour)) // can take left
            moves.add(new Coordinate(x+leftOrRight,y+direction));
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
