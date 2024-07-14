import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The colour of a piece.
 */
public enum PieceColour {
    /**
     * A White Chess piece.
     */
    WHITE,
    /**
     * A Black Chess piece.
     */
    BLACK,
    /**
     * A Blank square, a piece should never have this value.
     */
    BLANK;

    /**
     * Finds the opposite colour of a given colour. Blank returns blank.
     * @param otherColour the colour you want the opposite of.
     * @return the opposite colour.
     */
    public static PieceColour getOtherColour(@NotNull PieceColour otherColour) {
        return switch (otherColour) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            case BLANK -> BLANK;
        };
    }

    static int getDirectionFromColour(@NotNull PieceColour colour){
        return switch (colour){
            case WHITE -> 1;
            case BLACK -> -1;
            case BLANK -> 0;
        };
    }

    @Contract(pure = true)
    static @NotNull String getStringFromColour(@NotNull PieceColour colour){
        return switch(colour){
            case WHITE -> "white";
            case BLACK -> "black";
            case BLANK -> "blank";
        };
    }
}
