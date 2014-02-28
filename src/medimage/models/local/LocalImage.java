
package medimage.models.local;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import medimage.models.Image;

/**
 * Image on the local filesystem.
 * @author col32
 */
public class LocalImage extends Image {
    
    private BufferedImage image;
    
    /**
     * Loads a local image.
     * @param f File of the image.
     */
    public LocalImage(File f) {
        try {
            image = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(LocalImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the image data.
     * @return image data.
     */
    @Override
    public BufferedImage getImageData() {
        return image;
    }
    
}
