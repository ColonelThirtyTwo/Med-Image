
package medimage;

import medimage.models.Image;

/**
 * Interface for image iterators, used for scrolling.
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
     * @return image set. Items in here can be null.
     */
    public abstract Image[] getImages();
    
    /**
     * Gets the index of the current image set.
     * @return index.
     */
    public abstract int getIndex();
    
}
