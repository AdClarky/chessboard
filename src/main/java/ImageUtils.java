import org.jetbrains.annotations.Nullable;

import javax.swing.ImageIcon;
import java.net.URL;

/** Used for generating a larger image. */
public final class ImageUtils {
    private static final int SCALE = 64;

    private ImageUtils() {}

    /** Generates an Icon which is larger than the original. */
    private static ImageIcon getStretchedImage(URL imageURL) {
        ImageIcon image = new ImageIcon(imageURL);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }

    public static @Nullable ImageIcon getPieceImage(String piece, PieceColour colour){
        if("Blank".equals(piece))
            return null;
        String path = "/" +
                PieceColour.getStringFromColour(colour) + "_" +
                 piece + ".png";
        return ImageUtils.getStretchedImage(Piece.class.getResource(path));
    }
}
