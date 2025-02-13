package window;

import common.PieceColour;
import common.PieceValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.ImageIcon;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Used for generating a larger image. */
final class ImageUtils {
    private static final Map<String, ImageIcon> IMAGE_MAP = new HashMap<>(16);
    private static final int SCALE = 64;

    static {
        String[] pieces = {"pawn", "knight", "bishop", "rook", "queen", "king"};
        String[] colours = {"white", "black"};

        for (String colour : colours) {
            for (String piece : pieces) {
                String path = "/" +
                        colour + "_" +
                        piece + ".png";
                URL imageURL = ImageUtils.class.getResource(path);
                if(imageURL == null)
                    throw new IllegalArgumentException("Piece must be in lower case with a PieceColour of black or white");
                IMAGE_MAP.put(colour+piece,
                        ImageUtils.getStretchedImage(Objects.requireNonNull(ImageUtils.class.getResource(path))));
            }
        }
    }

    private ImageUtils() {}

    /** Generates an Icon which is larger than the original. */
    private static @NotNull ImageIcon getStretchedImage(@NotNull URL imageURL) {
        ImageIcon image = new ImageIcon(imageURL);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }

    public static @Nullable ImageIcon getPieceImage(PieceValue piece){
        if(piece.colour() == null)
            return null;

        return IMAGE_MAP.get(piece.colour().toString() + piece.pieceType());
    }
}
