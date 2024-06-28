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

    public static PieceColour getOtherColour(@NotNull Piece piece) {
        return switch (piece.getColour()) {
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
}
