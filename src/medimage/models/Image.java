/**
 * File: Image.java
 * Description: A model for the Image abstract class.
 */

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
    
    /**
     * Pushes a modified image to the image's undo stack.
     */
    public abstract void pushModifiedImage(BufferedImage img);
    
    /**
     * Pops a modified image from the image's undo stack.
     */
    public abstract void popModifiedImage();
}
