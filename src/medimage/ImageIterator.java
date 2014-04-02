/**
 * File: ImageIterator.java
 * Description: Defines the interface for an ImageIterator. This is implemented
 * by QuadImageIterator and SingleImageIterator
 */
package medimage;

import medimage.models.Image;

/**
 * Interface for image iterators, used for scrolling.
 * This iterator interface doesn't behave like normal Java iterators. Instead,
 * it behaves more like a tape drive; there's the currently read data, as well
 * as functions to scroll it left or right. This is needed as the view may need
 * to get the images multiple times without scrolling, and serializing Java-style
 * iterators can get challenging.
 * @author col32
 */
public interface ImageIterator {
    
    /**
     * Advances the iterator to the next image set.
     * @return True if successful, or false if the iterator is at the end.
     */
    public abstract boolean next();
    
    /**
     * Rewinds the iterator to the previous image set.
     * @return True if successful, or false if the iterator is at the beginning.
     */
    public abstract boolean prev();
    
    /**
     * Returns the current image set.
     * @return Image set, which may contain null items.
     */
    public abstract Image[] getImages();
    
    /**
     * Gets the index of the current image set.
     * @return Index.
     */
    public abstract int getIndex();
    
}
