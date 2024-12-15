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

    public PieceColour invert() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            case BLANK -> BLANK;
        };
    }

    public int direction(){
        return switch (this){
            case WHITE -> 1;
            case BLACK -> 0;
            case BLANK -> -1;
        };
    }

    public @NotNull String toString(){
        return name().toLowerCase();
    }
}
