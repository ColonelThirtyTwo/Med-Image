
package medimage.models;

import java.awt.image.BufferedImage;

/**
 * Represents an image.
 * @author col32
 */
public abstract class Image {
    
    /**
     * Gets the image data.
     * @return the image data.
     */
    public abstract BufferedImage getImageData();
}
