import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.ImageIcon;
import java.net.URL;

/** Used for generating a larger image. */
public final class ImageUtils {
    private static final int SCALE = 64;

    private ImageUtils() {}

    /** Generates an Icon which is larger than the original. */
    private static @NotNull ImageIcon getStretchedImage(@NotNull URL imageURL) {
        ImageIcon image = new ImageIcon(imageURL);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }

    public static @Nullable ImageIcon getPieceImage(String piece, PieceColour colour){
        if("Blank".equals(piece))
            return null;
        String path = "/" +
                PieceColour.getStringFromColour(colour) + "_" +
                 piece + ".png";
        URL imageURL = ImageUtils.class.getResource(path);
        if(imageURL == null)
            throw new IllegalArgumentException("Piece must be in lower case with a PieceColour of black or white");
        return ImageUtils.getStretchedImage(Piece.class.getResource(path));
    }
}
