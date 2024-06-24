import javax.swing.ImageIcon;
import java.net.URL;
import java.nio.file.Path;

/**
 * Used for generating a larger image.
 */
public final class ImageUtils {
    private static final int SCALE = 64;

    private ImageUtils() {}

    /**
     * Generates an Icon which is larger than the original.
     * @param imageURL the image to be made large
     * @return the Icon scaled up
     */
    public static ImageIcon getStretchedImage(URL imageURL) {
        ImageIcon image = new ImageIcon(imageURL);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }
}
