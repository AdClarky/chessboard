package common;

import org.jetbrains.annotations.NotNull;

public record MoveValue(Coordinate oldPos, Coordinate newPos, Pieces promotionPiece) {
    public MoveValue(Coordinate oldPos, Coordinate newPos) {
        this(oldPos, newPos, null);
    }

    public MoveValue(MoveValue move, Pieces selectedPiece) {
        this(move.oldPos, move.newPos, selectedPiece);
    }

    public boolean isPieceInSamePosition(){
        return oldPos.equals(newPos);
    }

    static @NotNull MoveValue createStationaryMove(Coordinate position){
        return new MoveValue(position, position);
    }
}