import javax.swing.ImageIcon;

/**
 * Used for generating a larger image.
 */
public final class ImageUtils {
    private static final int SCALE = 64;

    private ImageUtils() {}

    /**
     * Generates an Icon which is larger than the original.
     * @param path the image to be made large
     * @return the Icon scaled up
     */
    public static ImageIcon getStretchedImage(String path) {
        ImageIcon image = new ImageIcon(path);
        return new ImageIcon((image.getImage().getScaledInstance(SCALE, SCALE,java.awt.Image.SCALE_SMOOTH)));
    }

}
