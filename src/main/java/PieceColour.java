import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum PieceColour {
    WHITE, BLACK, BLANK;

    public static PieceColour getOtherColour(@NotNull PieceColour otherColour) {
        return switch (otherColour) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            case BLANK -> BLANK;
        };
    }

    public static int getDirectionFromColour(@NotNull PieceColour colour){
        return switch (colour){
            case WHITE -> 1;
            case BLACK -> -1;
            case BLANK -> 0;
        };
    }

    @Contract(pure = true)
    public static @NotNull String getStringFromColour(@NotNull PieceColour colour){
        return switch(colour){
            case WHITE -> "white";
            case BLACK -> "black";
            case BLANK -> "blank";
        };
    }
}
