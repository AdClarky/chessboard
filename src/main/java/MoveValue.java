import org.jetbrains.annotations.NotNull;

public record MoveValue(Coordinate oldPos, Coordinate newPos) {
    public boolean isPieceInSamePosition(){
        return oldPos.equals(newPos);
    }

    static @NotNull MoveValue createStationaryMove(Coordinate position){
        return new MoveValue(position, position);
    }
}