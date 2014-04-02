/**
 * File: SingleImageIterator.java
 * Description: An implementation of ImageIterator for moving through one
 * image at a time.
 */

package medimage;

import java.util.List;
import medimage.models.Image;

/**
 * ImageIterator that iterates over single images.
 * @author col32
 */
public class SingleImageIterator implements ImageIterator {
    
    private final List<Image> images;
    private int index;
    
    /**
     * Creates a SingleImageIterator and starts it at the beginning of the list.
     * @param images Images to iterate over.
     */
    public SingleImageIterator(List<Image> images) {
        this.images = images;
        this.index = 0;
    }
    
    /**
     * Creates a SingleImageIterator and starts it at the specified index.
     * @param images Images to iterate over.
     * @param index Index to start at, rounded down to the nearest fourth.
     */
    public SingleImageIterator(List<Image> images, int index) {
        this.images = images;
        this.index = index;
    }
    
    @Override
    public boolean next() {
        if(index == this.images.size()-1)
            return false;
        index++;
        return true;
    }

    @Override
    public boolean prev() {
        if(index == 0)
            return false;
        index--;
        return true;
    }

    @Override
    public Image[] getImages() {
        Image[] set = new Image[1];
        set[0] = images.get(index);
        return set;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
