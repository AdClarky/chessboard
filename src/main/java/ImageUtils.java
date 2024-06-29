import org.jetbrains.annotations.NotNull;

import javax.swing.ImageIcon;
import java.net.URL;

/** Used for generating a larger image. */
public final class ImageUtils {
    private static final int SCALE = 64;

    private ImageUtils() {}

    /** Generates an Icon which is larger than the original. */
    public static ImageIcon getStretchedImage(URL imageURL) {
        ImageIcon image = new ImageIcon(imageURL);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }

    public static ImageIcon getPieceImage(String piece, PieceColour colour){
        String path = "/" +
                PieceColour.getStringFromColour(colour) + "_" +
                getStringFromPiece(piece) + ".png";
        return ImageUtils.getStretchedImage(Piece.class.getResource(path));
    }

    private static @NotNull String getStringFromPiece(@NotNull String piece){
        return switch (piece){
            case "B" -> "bishop";
            case "K" -> "king";
            case "Q" -> "queen";
            case "R" -> "rook";
            case "N" -> "knight";
            case "" -> "pawn";
            default -> throw new IllegalArgumentException("Invalid piece: " + piece);
        };
    }
}
