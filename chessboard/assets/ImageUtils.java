package chessboard.assets;

import javax.swing.ImageIcon;

public final class ImageUtils {
    public static ImageIcon getStrechedImage(String path) {
        ImageIcon image = new ImageIcon(path);
        return new ImageIcon((image.getImage().getScaledInstance(64, 64,java.awt.Image.SCALE_SMOOTH)));
    }

}
