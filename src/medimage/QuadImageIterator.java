/**
 * File: QuadImageIterator.java
 * Description: An implementation of ImageIterator for moving through four
 * images at a time.
 */

package medimage;

import java.util.List;
import medimage.models.Image;

/**
 * ImageIterator that iterates over sets of four images at a time.
 * @author col32
 */
public class QuadImageIterator implements ImageIterator {
    
    private final List<Image> images;
    private int index;

    /**
     * Creates a QuadImageIterator and starts it at the beginning of the list.
     * @param images Images to iterate over.
     */
    public QuadImageIterator(List<Image> images) {
        this.images = images;
        this.index = 0;
    }
    
    /**
     * Creates a QuadImageIterator and starts it at the specified index.
     * @param images Images to iterate over.
     * @param index Index to start at.
     */
    public QuadImageIterator(List<Image> images, int index) {
        this.images = images;
        this.index = (index/4)*4; // Round down to nearest 4th
    }
    
    @Override
    public boolean next() {
        if(index+4 >= images.size())
            return false;
        index += 4;
        return true;
    }

    @Override
    public boolean prev() {
        if(index-4 < 0)
            return false;
        index -= 4;
        return true;
    }

    @Override
    public Image[] getImages() {
        Image[] set = new Image[4];
        set[0] = getOrNull(index);
        set[1] = getOrNull(index+1);
        set[2] = getOrNull(index+2);
        set[3] = getOrNull(index+3);
        return set;
    }

    @Override
    public int getIndex() {
        return index;
    }
    
    /**
     * Returns the image at the index, or null if the index is out of bounds.
     * @param i Index of image.
     * @return Image or null.
     */
    private Image getOrNull(int i) {
        if(i >= images.size())
            return null;
        return images.get(i);
    }
}
