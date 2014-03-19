
package medimage.views.improvedimageview;

import java.util.List;
import javax.swing.JLabel;
import medimage.ImageIterator;
import medimage.models.DisplayState;
import medimage.models.Image;

/**
 * Panel used by the ImageView to display images.
 * @author col32
 */
public abstract class ImagePanel extends javax.swing.JPanel {
    
    /**
     * Returns true if the panel doesn't need a command stack or save states.
     * (ex. the reconstruction panel)
     * @return 
     */
    protected abstract boolean isReadOnly();
    
    /**
     * Creates a display state from the currently viewed image.
     * @param iter Current image iterator.
     * @return Current display state.
     */
    protected abstract DisplayState getDisplayState(ImageIterator iter);
    
    /**
     * Returns an array of all the image containers to populate with images.
     * @return 
     */
    protected abstract JLabel[] getImageContainers();
    
    /**
     * Creates an ImageIterator that iterates over all of the images to view.
     * @param images Images to view
     * @param index Index to start the iterator at
     * @return An appropriate iterator.
     */
    protected abstract ImageIterator createIterator(List<Image> images, int index);
}
