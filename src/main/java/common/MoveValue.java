package common;

import org.jetbrains.annotations.NotNull;

public record MoveValue(Coordinate oldPos, Coordinate newPos, Pieces promotionPiece) {
    public MoveValue(Coordinate oldPos, Coordinate newPos) {
        this(oldPos, newPos, null);
    }
    public boolean isPieceInSamePosition(){
        return oldPos.equals(newPos);
    }

    public MoveValue withPromotionPiece(Pieces promotionPiece){
        return new MoveValue(oldPos, newPos, promotionPiece);
    }

    static @NotNull MoveValue createStationaryMove(Coordinate position){
        return new MoveValue(position, position);
    }
}