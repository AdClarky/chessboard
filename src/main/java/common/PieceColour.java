package common;

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
    BLACK;

    public PieceColour invert() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }

    public int direction(){
        return switch (this){
            case WHITE -> 1;
            case BLACK -> -1;
        };
    }

    public @NotNull String toString(){
        return name().toLowerCase();
    }
}
