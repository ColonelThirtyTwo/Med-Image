
package medimage.models;

import java.awt.image.BufferedImage;

/**
 * Represents an image in a study.
 * @author col32
 */
public abstract class Image {
    
    /**
     * Gets the image data.
     * @return Image data.
     */
    public abstract BufferedImage getImageData();
    
    /**
     * Returns the name of the image.
     * @return 
     */
    public abstract String getName();
}
