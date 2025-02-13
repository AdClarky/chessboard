package common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PieceValue(Coordinate position, Pieces pieceType, PieceColour colour) {
    @Contract("_, _ -> new")
    public static @NotNull PieceValue of(Coordinate position, Character piece) {
        PieceColour colour;
        if(Character.isLowerCase(piece)) {
            colour = PieceColour.BLACK;
        }else{
            colour = PieceColour.WHITE;
        }
        Pieces type = Pieces.valueOf("" + Character.toUpperCase(piece));

        return new PieceValue(position, type, colour);
    }
}
